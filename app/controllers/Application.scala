package controllers

import play.api._
import play.api.mvc._
import com.typesafe.training.scalatrain.Station
import com.typesafe.training.scalatrain.TestData

object Application extends Controller {
  import TestData.planner

  def stations = Action {
    Ok(views.html.stations(planner.stations))
  }
  
  def trips(name: String) = Action {
    val station = name
    val hops = planner.hops.get(Station(name))
    Ok(views.html.trips(station, hops))
    
  }

  def index = Action {
    Ok(views.html.index())
  }
  

}