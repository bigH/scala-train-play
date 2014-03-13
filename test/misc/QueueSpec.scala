/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package misc

import org.scalatest.{ Matchers, WordSpec }

class QueueSpec extends WordSpec with Matchers {

  "Calling equals" should {
    "be true for identical objects" in {
      val queue = Queue(1, 2, 3)
      queue == queue shouldBe true
    }
    "be true for equal objects" in {
      Queue(1, 2, 3) == Queue(1, 2, 3) shouldBe true
    }
    "be true for nonequal objects" in {
      Queue(1, 2) == Queue(1, 2, 3) shouldBe false
    }
  }

  "Calling hashCode" should {
    "return the same value for equal objects" in {
      Queue(1, 2, 3).## shouldEqual Queue(1, 2, 3).##
    }
  }

  "Calling toString" should {
    "return the class name and the elements in parentheses" in {
      Queue(1, 2, 3).toString shouldEqual "Queue(1, 2, 3)"
    }
  }

  "Calling dequeue" should {
    "throw an UnsupportedOperationException for an empty queue" in {
      evaluating(Queue().dequeue) should produce[UnsupportedOperationException]
    }
    "return the first element and a new Queue without the dequeued element" in {
      Queue(1, 2, 3).dequeue shouldEqual (1, Queue(2, 3))
    }
  }

  "Calling enqueue" should {
    "return a new Queue with the new element enqueued at the end" in {
      (Queue(1, 2) enqueue 3.0) shouldEqual Queue(1, 2, 3.0)
    }
  }
}
