/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package model

object TimeDSL {

  private val TimePattern = """(\d{1,2}):(\d{1,2})""".r

  implicit def stringToTime(s: String): Time = {
    val TimePattern(hours, minutes) = s
    Time(hours.toInt, minutes.toInt)
  }

  private def am(time: Time): Time = {
    require(time > Time() && time < Time(12), "time must be (00:00,12:00)!")
    time
  }

  private def pm(time: Time): Time = {
    require(time > Time() && time <= Time(12), "time must be within (00:00,12:00]!")
    if (time == Time(12))
      Time()
    else
      time.copy(hours = time.hours + 12)
  }

  implicit class IntOps(val n: Int) extends AnyVal {

    def ::(hours: Int): Time =
      Time(hours, n)

    def am: Time =
      TimeDSL.am(Time(n))

    def pm: Time =
      TimeDSL.pm(Time(n))
  }

  implicit class TimeOps(val time: Time) extends AnyVal {

    def am: Time =
      TimeDSL.am(time)

    def pm: Time =
      TimeDSL.pm(time)
  }
}
