/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import scala.collection.breakOut
import scala.collection.immutable.Seq

case class Train(info: TrainInfo, schedule: Seq[(Time, Station)]) {
  require(schedule.size >= 2, "schedule must contain at least two elements")
  require(isIncreasing(schedule map (_._1)), "schedule must be strictly increasing in time")

  val stations: Seq[Station] =
    // Could also be expressed in short notation: schedule map (_._2)
    schedule map (trainAndStation => trainAndStation._2)

  val backToBackStations: Seq[(Station, Station)] =
    stations zip stations.tail

  val departureTimes: Map[Station, Time] =
    schedule.map(_.swap)(breakOut)
}

object TrainInfo {

  case class InterCityExpress(number: Int, hasWifi: Boolean = false) extends TrainInfo

  case class RegionalExpress(number: Int) extends TrainInfo

  case class BavarianRegional(number: Int) extends TrainInfo
}

sealed abstract class TrainInfo {

  def number: Int
}

case class Station(name: String) extends AnyVal
