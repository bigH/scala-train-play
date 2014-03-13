/*
 * Copyright © 2012 Typesafe, Inc. All rights reserved.
 */

package misc

import scala.collection.SeqLike
import scala.collection.generic.CanBuildFrom
import scala.collection.immutable.Seq
import scala.collection.mutable.{ Builder, ListBuffer }

object Queue {

  def apply[A](as: A*): Queue[A] =
    new Queue(as.toVector)

  implicit def cbf[A, B]: CanBuildFrom[Queue[A], B, Queue[B]] = new CanBuildFrom[Queue[A], B, Queue[B]] {

    override def apply(): Builder[B, Queue[B]] =
      newBuilder

    override def apply(from: Queue[A]): Builder[B, Queue[B]] =
      newBuilder
  }

  private def newBuilder[A] = ListBuffer[A]() mapResult (new Queue(_))
}

class Queue[+A] private (private val as: Seq[A]) extends Seq[A] with SeqLike[A, Queue[A]] {

  def dequeue: (A, Queue[A]) =
    as match {
      case a +: as => (a, new Queue(as))
      case _       => throw new UnsupportedOperationException("Cannot dequeue from an empty queue")
    }

  def enqueue[B >: A](b: B): Queue[B] =
    new Queue(as :+ b)

  override def apply(i: Int): A =
    as(i)

  override def length: Int =
    as.length

  override def iterator: Iterator[A] =
    as.iterator

  override protected[this] def newBuilder: Builder[A, Queue[A]] =
    Queue.newBuilder

  override def equals(other: Any): Boolean =
    other match {
      case that: Queue[_] => (this eq that) || (this.as == that.as)
      case _              => false
    }

  override def hashCode: Int =
    as.hashCode

  override def toString: String =
    s"Queue(${as mkString ", "})"
}
