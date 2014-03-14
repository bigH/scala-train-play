package controllers

import model._
import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._

object Application extends Controller {
  import TestData.planner

  case class TripQuery(to: String, from: String)
  
  val form = Form(
    mapping(
      "to" -> text,
      "from" -> text
    )(TripQuery.apply)(TripQuery.unapply)
  )
  
  def search = Action {
    implicit request =>
    form.bindFromRequest.fold(
      BadRequest(views.html.search(_, planner.stations)),
      Redirect(routes.Application.searchResults(_))
    )
  
    Ok(views.html.search(planner.stations))
  }

  def searchResults = Action {
 val results = ""
    Ok(views.html.searchResults(results))
  }

  def stations = Action {
    Ok(views.html.stations(planner.stations))
  }

  def trips(name: String) = Action {
    val hops = planner.hops.get(Station(name))
    Ok(views.html.trips(name, hops))
  }

  def index = Action {
    Ok(views.html.index())
  }
  

}
