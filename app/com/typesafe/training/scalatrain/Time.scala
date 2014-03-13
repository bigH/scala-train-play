/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import misc.{ JsonFormat, JsonSerializable }
import scala.collection.immutable.Seq
import scala.util.control.Exception
import scala.util.parsing.json.{ JSONObject => JsonObject }

object Time extends JsonSerializable[Time] {

  implicit val timeJsonFormat: JsonFormat[Time] =
    new TimeJsonFormat

  def fromMinutes(minutes: Int): Time =
    Time(minutes / 60, minutes % 60)

  private class TimeJsonFormat extends JsonFormat[Time] {

    def toJson(time: Time): JsonObject =
      JsonObject(Map("hours" -> time.hours, "minutes" -> time.minutes))

    def fromJson(json: JsonObject): Option[Time] =
      for {
        hoursAny <- json.obj get "hours"
        hours <- Exception.allCatch opt hoursAny.toString.toInt
        minutesAny <- json.obj get "minutes"
        minutes <- Exception.allCatch opt minutesAny.toString.toInt
      } yield Time(hours, minutes)
  }
}

case class Time(hours: Int = 0, minutes: Int = 0) extends Ordered[Time] {
  require(hours >= 0 && hours < 24, "hours must be within 0 and 23")
  require(minutes >= 0 && minutes < 60, "minutes must be within 0 and 59")

  val asMinutes: Int =
    hours * 60 + minutes

  override lazy val toString: String =
    f"$hours%02d:$minutes%02d"

  def minus(that: Time): Int =
    this.asMinutes - that.asMinutes

  def -(that: Time): Int =
    minus(that)

  override def compare(that: Time): Int =
    this - that
}
