/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import TestData._
import java.lang.{ IllegalArgumentException => IAE }
import org.scalatest.{ Matchers, WordSpec }

class JourneyPlannerSpec extends WordSpec with Matchers {

  "stations" should {
    "be initialized correctly" in {
      planner.stations shouldEqual Set(munich, nuremberg, frankfurt, cologne, essen)
    }
  }

  "hops" should {
    "be initialized correctly" in {
      planner.hops shouldEqual Map(
        munich -> Set(Hop(munich, nuremberg, ice724), Hop(munich, nuremberg, ice726)),
        nuremberg -> Set(Hop(nuremberg, frankfurt, ice724), Hop(nuremberg, frankfurt, ice726)),
        frankfurt -> Set(Hop(frankfurt, cologne, ice724), Hop(frankfurt, essen, ice726))
      )
    }
  }

  "Calling trainsAt" should {
    "return the correct trains" in {
      planner.trainsAt(munich) shouldEqual Set(ice724, ice726)
      planner.trainsAt(cologne) shouldEqual Set(ice724)
    }
  }

  "Calling stopsAt" should {
    "return the correct stops" in {
      planner.stopsAt(munich) shouldEqual Set(ice724MunichTime -> ice724, ice726MunichTime -> ice726)
    }
  }

  "Calling isShortTrip" should {
    "return false for more than one station in between" in {
      planner.isShortTrip(munich, cologne) shouldBe false
      planner.isShortTrip(munich, essen) shouldBe false
    }
    "return true for zero or one stations in between" in {
      planner.isShortTrip(munich, nuremberg) shouldBe true
      planner.isShortTrip(munich, frankfurt) shouldBe true
      planner.isShortTrip(nuremberg, frankfurt) shouldBe true
      planner.isShortTrip(nuremberg, essen) shouldBe true
    }
  }

  "Calling connections" should {
    "throw an IllegalArgumentException for equal from and to" in {
      evaluating(planner.connections(munich, munich, Time())) should produce[IAE]
    }
    "return the correct connections" in {
      planner.connections(munich, frankfurt, Time()) shouldEqual Set(
        Vector(Hop(munich, nuremberg, ice724), Hop(nuremberg, frankfurt, ice724)),
        Vector(Hop(munich, nuremberg, ice726), Hop(nuremberg, frankfurt, ice726)),
        Vector(Hop(munich, nuremberg, ice726), Hop(nuremberg, frankfurt, ice724))
      )
      planner.connections(munich, frankfurt, Time(8)) shouldEqual Set(
        Vector(Hop(munich, nuremberg, ice724), Hop(nuremberg, frankfurt, ice724))
      )
    }
  }
}
