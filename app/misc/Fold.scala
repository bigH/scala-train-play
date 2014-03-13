/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package misc

object Fold {

  def map[A, B](as: List[A])(f: A => B): List[B] =
    as.foldLeft(List.empty[B])((bs, a) => bs :+ f(a))

  def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] =
    as.foldLeft(List.empty[B])((bs, a) => bs ++ f(a))

  def filter[A](as: List[A])(f: A => Boolean): List[A] =
    as.foldRight(List.empty[A])((a, as) => if (f(a)) a +: as else as)
}
