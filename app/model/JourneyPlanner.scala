/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package model


class JourneyPlanner(trains: Set[Train]) {

  val stations: Set[Station] =
    // Could also be expressed in short notation: trains flatMap (_.stations)
    trains flatMap (train => train.stations)

  val sortedStations: Seq[(String,String)] = {
    val tmp =stations.map(s => s.name).toSeq.sorted
    tmp zip tmp
  }

  val hops: Map[Station, Set[Hop]] = {
    val hops = for {
      train <- trains
      (from, to) <- train.backToBackStations
    } yield Hop(from, to, train)
    hops groupBy (_.from)
  }

  def trainsAt(station: Station): Set[Train] =
    // Could also be expressed in short notation: trains filter (_.stations contains station)
    trains filter (train => train.stations contains station)

  def stopsAt(station: Station): Set[(Time, Train)] =
    for {
      train <- trains
      (time, `station`) <- train.schedule
    } yield (time, train)

  def isShortTrip(from: Station, to: Station): Boolean =
    trains exists (train =>
      train.stations dropWhile (station => station != from) match {
        case `from` +: `to` +: _      => true
        case `from` +: _ +: `to` +: _ => true
        case _                        => false
      }
    )

  def connections(from: Station, to: Station, departureTime: Option[Time]): Set[Seq[Hop]] = {
    require(from != to, "from and to must not be equal!")
    def connections(soFar: Vector[Hop]): Set[Vector[Hop]] = {
      if (soFar.last.to == to)
        Set(soFar)
      else {
        val soFarStations = soFar.head.from +: (soFar map (_.to))
        val nextHops = hops.getOrElse(soFar.last.to, Set()) filter (hop =>
          (hop.departureTime >= soFar.last.arrivalTime) && !(soFarStations contains hop.to)
        )
        nextHops flatMap (hop => connections(soFar :+ hop))
      }
    }
    val nextHops = hops.getOrElse(from, Set()) filter (_.departureTime >= departureTime.getOrElse(Time(0,0)))
    nextHops flatMap (hop => connections(Vector(hop)))
  }
}

case class Hop(from: Station, to: Station, train: Train) {
  require(from != to, "from must not be equal to to")
  require(train.backToBackStations contains from -> to, "from and to must be back-to-back stations of train")

  val departureTime: Time =
    train departureTimes from

  val arrivalTime: Time =
    train departureTimes to
}
