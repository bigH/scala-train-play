/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package com.typesafe.training

import scala.annotation.tailrec

package object scalatrain {

  @tailrec
  def isIncreasing[A: Ordering](times: Seq[A]): Boolean =
    times match {
      case t1 +: t2 +: _ => implicitly[Ordering[A]].lt(t1, t2) && isIncreasing(times.tail)
      case _             => true
    }

  def isIncreasingSliding[A <: Ordered[A]](times: Seq[A]): Boolean =
    times sliding 2 forall {
      case t1 +: t2 +: _ => t1 < t2
      case _             => true
    }
}
