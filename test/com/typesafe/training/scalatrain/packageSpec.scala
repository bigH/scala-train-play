/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training.scalatrain

import org.scalatest.{ Matchers, WordSpec }

class packageSpec extends WordSpec with Matchers {

  "Calling isIncreasing" should {
    "be true for an empty or one-element sequence" in {
      isIncreasing(Vector.empty[Time]) shouldBe true
      isIncreasing(Vector(Time())) shouldBe true
      isIncreasing(Vector(O(0))) shouldBe true
    }
    "be false for a not strictly increasing sequence" in {
      isIncreasing(Vector(Time(), Time())) shouldBe false
      isIncreasing(Vector(O(0), O(0))) shouldBe false
      isIncreasing(Vector(Time(), Time(1), Time())) shouldBe false
    }
    "be true for a strictly increasing sequence" in {
      isIncreasing(Vector(Time(1), Time(2))) shouldBe true
      isIncreasing(Vector(O(0), O(1))) shouldBe true
      isIncreasing(Vector(Time(), Time(1), Time(2))) shouldBe true
      isIncreasing(Vector(0, 1, 2)) shouldBe true
    }
  }

  "Calling isIncreasingSliding" should {
    "be true for an empty or one-element sequence" in {
      isIncreasingSliding(Vector.empty[Time]) shouldBe true
      isIncreasingSliding(Vector(Time())) shouldBe true
    }
    "be false for a not strictly increasing sequence" in {
      isIncreasingSliding(Vector(Time(), Time())) shouldBe false
      isIncreasingSliding(Vector(Time(), Time(1), Time())) shouldBe false
    }
    "be true for a strictly increasing sequence" in {
      isIncreasingSliding(Vector(Time(1), Time(2))) shouldBe true
      isIncreasingSliding(Vector(Time(), Time(1), Time(2))) shouldBe true
    }
  }

  private case class O(n: Int) extends Ordered[O] {

    override def compare(that: O): Int =
      this.n - that.n
  }
}
