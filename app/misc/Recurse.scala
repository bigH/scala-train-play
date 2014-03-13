/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package misc

object Recurse {

  def map[A, B](as: List[A])(f: A => B): List[B] =
    as match {
      case a +: as => f(a) +: map(as)(f)
      case _       => Nil
    }

  def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] =
    as match {
      case a +: as => f(a) ++ flatMap(as)(f)
      case _       => Nil
    }

  def filter[A](as: List[A])(f: A => Boolean): List[A] =
    as match {
      case a +: as if f(a) => a +: filter(as)(f)
      case a +: as         => filter(as)(f)
      case _               => Nil
    }
}
