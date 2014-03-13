/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package misc

import org.scalatest.{ Matchers, NonImplicitAssertions, WordSpec }

class EqualSpec extends WordSpec with Matchers with NonImplicitAssertions {

  import Equal._

  "Calling ===" should {
    "be true for nonequal Strings and all other equal objects" in {
      "1" === "2" shouldBe true
      1 === 1 shouldBe true
      1.0 === 1.0 shouldBe true
    }
    "be false for equal Strings and all other nonequal objects" in {
      "1" === "1" shouldBe false
      1 === 2 shouldBe false
    }
  }
}
