/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package misc

import java.io.InputStream
//import org.scalamock.scalatest.MockFactory
import org.scalatest.{ Matchers, WordSpec }

class ResourceSpec extends WordSpec with Matchers /*with MockFactory*/ {

  "Calling withResource" should {
    "return the correct result and close the resource" in {
      pending // Wait until ScalaMock catches up with latest ScalaTest!
      // import Resource._
      // val in = stub[InputStream]
      // (in.read _).when() returns 1
      // withResource(in)(_.read()) must be === 1
      // (in.close _).verify()
    }
  }
}
