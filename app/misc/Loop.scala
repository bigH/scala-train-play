/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package misc

import scala.annotation.tailrec

object Loop {

  @tailrec
  def repeatWhile(condition: => Boolean)(block: => Any): Unit = {
    if (condition) {
      block
      repeatWhile(condition)(block)
    }
  }

  def repeat(block: => Any) = new {
    @tailrec
    def until(condition: => Boolean): Unit = {
      block
      if (!condition) until(condition)
    }
  }
}
