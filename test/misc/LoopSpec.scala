/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package misc

import org.scalatest.{ Matchers, WordSpec }

class LoopSpec extends WordSpec with Matchers {
  import Loop._

  "Using repeatWhile" should {
    "loop correctly" in {
      var counter = 0
      repeatWhile(counter < 5) {
        counter += 1
      }
      counter shouldEqual 5
    }
  }

  "Using repeat-unil" should {
    "loop correctly" in {
      var counter = 0
      repeat {
        counter += 1
      } until (counter >= 5)
      counter shouldEqual 5
    }
  }
}
