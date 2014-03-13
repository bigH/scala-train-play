/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import TestData._
import java.lang.{ IllegalArgumentException => IAE }
import org.scalatest.{ Matchers, WordSpec }

class TrainSpec extends WordSpec with Matchers {

  "Creating a Train" should {
    "throw an IllegalArgumentException for a schedule with 0 or 1 elements" in {
      evaluating(Train(TrainInfo.InterCityExpress(724), Vector())) should produce[IAE]
      evaluating(Train(TrainInfo.InterCityExpress(724), Vector(ice724MunichTime -> munich))) should produce[IAE]
    }
    "throw an IllegalArgumentException for a schedule not strictly increasing in time" in {
      evaluating(
        Train(
          TrainInfo.InterCityExpress(724),
          Vector(ice724MunichTime -> munich, ice724MunichTime -> nuremberg)
        )
      ) should produce[IAE]
    }
  }

  "stations" should {
    "be initialized correctly" in {
      ice724.stations shouldEqual Vector(munich, nuremberg, frankfurt, cologne)
    }
  }

  "backToBackStations" should {
    "be initialized correctly" in {
      ice724.backToBackStations shouldEqual Vector(munich -> nuremberg, nuremberg -> frankfurt, frankfurt -> cologne)
    }
  }

  "departureTimes" should {
    "be initialized correctly" in {
      ice724.departureTimes shouldEqual Map(
        munich -> ice724MunichTime,
        nuremberg -> ice724NurembergTime,
        frankfurt -> ice724FrankfurtTime,
        cologne -> ice724CologneTime
      )
    }
  }
}
