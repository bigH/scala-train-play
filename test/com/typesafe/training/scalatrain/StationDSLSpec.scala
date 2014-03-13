/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import org.scalatest.{ Matchers, WordSpec }

class StationSpec extends WordSpec with Matchers {

  import StationDSL._

  "Implicitly converting a String into a Station" should {
    "create a properly initialized Station" in {
      val name = "Munich"
      (name: Station).name shouldEqual name
    }
  }
}
