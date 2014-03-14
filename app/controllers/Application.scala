package controllers

import model._
import play.api._
import play.api.mvc._
import play.api.data.Form

object Application extends Controller {
  import TestData.planner

  def search = Action {
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
