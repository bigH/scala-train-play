/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package model

import TestData._
import java.lang.{ IllegalArgumentException => IAE }
import org.scalatest.{ Matchers, WordSpec }

class HopSpec extends WordSpec with Matchers {

  "Creating a Hop" should {
    "throw an IllegalArgumentException for equal from and to" in {
      an [IAE] should be thrownBy { Hop(munich, munich, ice724) }
    }
    "throw an IllegalArgumentException for from and to not back-to-back stations of train" in {
      an [IAE] should be thrownBy { Hop(munich, cologne, ice724) }
    }
  }

  "departureTime" should {
    "be initialized correctly" in {
      Hop(munich, nuremberg, ice724).departureTime shouldEqual ice724MunichTime
    }
  }

  "arrivalTime" should {
    "be initialized correctly" in {
      Hop(munich, nuremberg, ice724).arrivalTime shouldEqual ice724NurembergTime
    }
  }
}
