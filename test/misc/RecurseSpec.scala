/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package misc

import org.scalatest.{ Matchers, WordSpec }

class RecurseSpec extends WordSpec with Matchers {

  "Calling map" should {
    "work like List.map" in {
      Recurse.map(numbers)(addOne) shouldEqual (numbers map addOne)
    }
  }

  "Calling flatMap" should {
    "work like List.flatMap" in {
      Recurse.flatMap(numbers)(makeThree) shouldEqual (numbers flatMap makeThree)
    }
  }

  "Calling filter" should {
    "work like List.filter" in {
      Recurse.filter(numbers)(isOdd) shouldEqual (numbers filter isOdd)
    }
  }

  private val numbers = List(1, 2, 3)

  private def addOne(n: Int) = n + 1

  private def makeThree(n: Int) = List(n - 1, n, n + 1)

  private def isOdd(n: Int) = n % 2 != 0
}
