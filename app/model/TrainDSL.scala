/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package model

import scala.collection.immutable.Seq

// We cannot use structural types until https://issues.scala-lang.org/browse/SI-6336 is closes
object TrainDSL {

  implicit class TrainOps(val train: Train) extends AnyVal {

    def at(time: Time): TrainFromOps =
      new TrainFromOps(train.info, train.schedule, time)
  }

  implicit class TrainInfoOps(val info: TrainInfo) extends AnyVal {

    def at(time: Time): TrainInfoFromOps =
      new TrainInfoFromOps(info, time)
  }

  private[TrainDSL] class TrainFromOps(info: TrainInfo, schedule: Seq[(Time, Station)], time: Time) {

    def from(station: Station): Train =
      new Train(info, schedule :+ time -> station)
  }

  private[TrainDSL] class TrainInfoFromOps(info: TrainInfo, time: Time) {

    def from(station: Station): TrainInfoAtOps =
      new TrainInfoAtOps(info, Vector(time -> station))
  }

  private[TrainDSL] class TrainInfoAtOps(info: TrainInfo, schedule: Seq[(Time, Station)]) {

    def at(time: Time): TrainFromOps =
      new TrainFromOps(info, schedule, time)
  }
}

object StationDSL {

  implicit def stringToStation(s: String): Station =
    Station(s)
}
