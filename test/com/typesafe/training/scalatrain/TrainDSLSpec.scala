/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import TestData._
import org.scalatest.{ Matchers, WordSpec }

class TrainDSLSpec extends WordSpec with Matchers {

  import TrainDSL._

  "Creating a Train starting with a TrainInfo" should {
    "return a properly initialized Train" in {
      (TrainInfo.InterCityExpress(724) at ice724MunichTime from munich at ice724NurembergTime from nuremberg) shouldEqual
        Train(TrainInfo.InterCityExpress(724), Vector(ice724MunichTime -> munich, ice724NurembergTime -> nuremberg))
    }
  }

  "Creating a Train starting with a Train" should {
    "return a properly initialized Train" in {
      (TrainInfo.InterCityExpress(724) at ice724MunichTime from munich at ice724NurembergTime from nuremberg at ice724FrankfurtTime from frankfurt) shouldEqual
        Train(TrainInfo.InterCityExpress(724), Vector(ice724MunichTime -> munich, ice724NurembergTime -> nuremberg, ice724FrankfurtTime -> frankfurt))
    }
  }
}
