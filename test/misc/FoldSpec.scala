/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package misc

import org.scalatest.{ Matchers, WordSpec }

class FoldSpec extends WordSpec with Matchers {

  "Calling map" should {
    "work like List.map" in {
      Fold.map(numbers)(addOne) shouldEqual (numbers map addOne)
    }
  }

  "Calling flatMap" should {
    "work like List.flatMap" in {
      Fold.flatMap(numbers)(makeThree) shouldEqual (numbers flatMap makeThree)
    }
  }

  "Calling filter" should {
    "work like List.filter" in {
      Fold.filter(numbers)(isOdd) shouldEqual (numbers filter isOdd)
    }
  }

  private val numbers = List(1, 2, 3)

  private def addOne(n: Int) = n + 1

  private def makeThree(n: Int) = List(n - 1, n, n + 1)

  private def isOdd(n: Int) = n % 2 != 0
}
