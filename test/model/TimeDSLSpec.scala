/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package model

import java.lang.{ IllegalArgumentException => IAE }
import org.scalatest.{ Matchers, WordSpec }

class TimeDSLSpec extends WordSpec with Matchers {

  import TimeDSL._

  "Implicitly converting a String into a Time" should {
    "create a properly initialized Time" in {
      ("9:45": Time) shouldEqual Time(9, 45)
    }
  }

  "Calling am on an Int" should {
    "throw an IllegalArgumentException for the Int value not within (0,12)" in {
      an [IAE] should be thrownBy { 0 am }
      an [IAE] should be thrownBy { 12 am }
    }
    "return a correct Time" in {
      (1 am) shouldEqual Time(1)
      (11 am) shouldEqual Time(11)
    }
  }

  "Calling pm on an Int" should {
    "throw an IllegalArgumentException for the Int value not within (0,12]" in {
      an [IAE] should be thrownBy { 0 pm }
      an [IAE] should be thrownBy { 13 pm }
    }
    "return a correct Time" in {
      (1 pm) shouldEqual Time(13)
      (12 pm) shouldEqual Time()
    }
  }

  "Calling am on a Time" should {
    "throw an IllegalArgumentException for the Time value not within (00:00,12:00)" in {
      an [IAE] should be thrownBy { Time() am }
      an [IAE] should be thrownBy { Time(12) am }
    }
    "return a correct Time" in {
      (Time(0, 1) am) shouldEqual Time(0, 1)
      (Time(11, 59) am) shouldEqual Time(11, 59)
    }
  }

  "Calling pm on a Time" should {
    "throw an IllegalArgumentException for the Time value not within (00:00,12:00]" in {
      an [IAE] should be thrownBy { Time() pm }
      an [IAE] should be thrownBy { Time(12, 1) pm }
    }
    "return a correct Time" in {
      (Time(0, 1) pm) shouldEqual Time(12, 1)
      (Time(12) pm) shouldEqual Time()
    }
  }

  "Calling :: on an Int value" should {
    "return a correct Time" in {
      1 :: 30 shouldEqual Time(1, 30)
    }
  }
}
