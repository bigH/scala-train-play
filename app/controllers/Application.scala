package controllers

import model._
import play.api.Play._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import scala.concurrent.duration._
import scala.util.DynamicVariable
import java.util.UUID
import play.api.cache.Cache
import scala.concurrent.Future

object Application extends Controller {

  type ActionBlock[A] = (Request[A]) => (Future[SimpleResult], Map[String,String] => Map[String, String])

  trait ComposableAction {
    def doAction[A](block: ActionBlock[A]): ActionBlock[A]
  }

  object ActionComposer {
    def apply(actions: ComposableAction*): ActionBuilder[Request] =
      new ActionBuilder[Request] with Results {
        override protected def invokeBlock[A](request: Request[A],
                                              thunk: Request[A] => Future[SimpleResult]): Future[SimpleResult] = {
          val block: ActionBlock[A] = req => (thunk(req), identity)
          val (result, xform) = actions.foldRight(block)((a, b) => a.doAction(b))(request)
          result map (r => r.withSession(Session(xform(request.session.data))))
        }
      }
  }

  val Secure = ActionComposer(WithAuthToken, WithSession)
  val Open = ActionComposer(WithSession)

  private val sessionIdKey = "session_id"
  private val authTokenKey = "auth_token"

  object WithAuthToken extends ComposableAction {
    private def verifyKey(token: String, session: String) = {

    }

    override def doAction[A](block: ActionBlock[A]): ActionBlock[A] = request => {
      request.session.get(authTokenKey) match {
        case (Some(token), Some(session)) =>
          verifyKey(token, session)
        case (Some(token), _) => // ban/reject -- cannot have authToken without session key
        case _ => // authenticate
      }
    }
  }

  object WithSession extends ComposableAction {
    private val currentSession: DynamicVariable[Option[String]] = new DynamicVariable[Option[String]](None)

    def sessionId = currentSession.value

    override def doAction[A](block: ActionBlock[A]): ActionBlock[A] = request => {
      request.session.get(sessionIdKey) match {
        case Some(uuid) =>
          currentSession.withValue(Some(uuid)) {
            block(request)
          }
        case None =>
          val value =  UUID.randomUUID().toString
          val session = Some(value)
          currentSession.withValue(session) {
            val (fResult, sessionModifier) = block(request)
            (fResult, sessionModifier andThen (_ + (sessionIdKey -> value)))
          }
      }
  }

  implicit class DoAfter[A](workDone: A) {
    def returnAfter(thunk: A => Unit): A = {
      thunk(workDone); workDone
    }
  }

  object Sessioned extends ActionBuilder[Request]
  with play.api.mvc.Results {
    private val sessionIdKey = "session_id"

    private val currentSession: DynamicVariable[Option[String]] = new DynamicVariable[Option[String]](None)

    def sessionId = currentSession.value
    def invokeBlock[A](req: Request[A],
                       block: (Request[A]) => Future[SimpleResult]) = {
      req.session.get(sessionIdKey) match {
        case Some(uuid) =>
          currentSession.withValue(Some(uuid)) {
            block(req)
          }
        case None =>
          val value =  UUID.randomUUID().toString
          val session = Some(value)
          currentSession.withValue(session) {
            block(req).map(r => r.withSession(req.session + (sessionIdKey -> value)))
          }

      }
    }
  }

  import TestData.planner

  def sessionId = Sessioned.sessionId

  def now: Long = System.currentTimeMillis()
  def fiveMinsAgo: Long = now - 1.minutes.toMillis

  def saveHistory[A](history: List[(Long, TripQuery)])(implicit request: Request[A]) = sessionId match {
    case Some(id) => Cache.set(id, history)
    case None => ()
  }

  def searchHistory[A](implicit request: Request[A]): List[(Long, TripQuery)] =
    sessionId match {
      case Some(id) =>
        val history = Cache.getAs[List[(Long, TripQuery)]](id).getOrElse(Nil)
        history.filter(_._1 > fiveMinsAgo) returnAfter (saveHistory(_)(request))
      case None => Nil
    }

  def addSearch[A](query: TripQuery)(implicit request: Request[A]) =
    saveHistory(searchHistory :+ (now -> query))

  case class TripQuery(from: String, to: String, hours: Int, minutes: Int) {
    override def toString: String = s"from $from to $to starting at ${Time(hours, minutes)}"
  }


  val form = Form(
    mapping(
      "from" -> text,
      "to" -> text,
      "hours" -> default(number(0,23),0),
      "minutes" -> default(number(0,59),0)
    )(TripQuery.apply)(TripQuery.unapply).verifying(
      "Trips must go to destinations that are not the starting point.",
      query => query.from != query.to
    )
  )

  def searchLoad = Sessioned {
    implicit request =>
    val defaults = form.fill(TripQuery("Munich", "Frankfurt", 0, 0))
    Ok(views.html.search(defaults, planner.sortedStations, searchHistory))
  }
  
  def search = Secure {
    implicit request =>
    form.bindFromRequest.fold(
      error => BadRequest(views.html.search(error, planner.sortedStations, searchHistory)),
      query => {
        addSearch(query)
        Redirect(routes.Application.searchResults(query.from, query.to, query.hours, query.minutes))
      }
    )
  }

  def searchResults(from: String, to: String, hours: Int, minutes: Int) = Sessioned {
    implicit request =>
    val time = Some(Time(hours, minutes))
    val results = planner.connections(Station(from), Station(to), time)

    val query = TripQuery(from, to, hours, minutes)
    val lastForm = form.fill(query)

    Ok(views.html.searchResults(lastForm, planner.sortedStations, results, searchHistory, query))
  }

  def stations = Sessioned {
    Ok(views.html.stations(planner.stations))
  }

  def trips(name: String) = Sessioned {
    val hops = planner.hops.get(Station(name))
    Ok(views.html.trips(name, hops))
  }

  def index = Sessioned {
    Ok(views.html.index())
  }
  

}
