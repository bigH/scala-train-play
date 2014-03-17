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

  def searchLoad = Action{
      Ok(views.html.search(form, planner.sortedStations))


  }
  
  def search = Action {
    implicit request =>
    form.bindFromRequest.fold(
      e=>BadRequest(views.html.search(e, planner.sortedStations)),
      i=>Redirect(routes.Application.searchResults(i.from, i.to))

    )
  }

  def searchResults(from: String, to: String) = Action {
    //
    //val time = Option(Time(0,0))
    val results = planner.connections(Station(from), Station(to), None)
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
