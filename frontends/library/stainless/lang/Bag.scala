/* Copyright 2009-2018 EPFL, Lausanne */

package stainless.lang
import stainless.annotation._
import scala.collection.immutable.{Map => ScalaMap}

object Bag {
  @library @inline
  def empty[A] = Bag[A]()

  @ignore
  def apply[A](elems: (A, BigInt)*): Bag[A] = {
    SmallBag(elems.toMap)
  }
}

@ignore
sealed abstract class Bag[A] {
  import scala._

  private val threshold = 100000

  private val size: BigInt = {
    this match {
      case SmallBag(theMap) => theMap.size
      case BigBag(left, right) => left.size + right.size
    }
  }

  def apply(a: A): BigInt = get(a)
  def get(a: A): BigInt = this match {
    case SmallBag(theMap) => theMap.getOrElse(a, BigInt(0))
    case BigBag(left, right) => left.get(a) + right.get(a)
  }

  def +(a: A): Bag[A] = this match {
    case SmallBag(theMap) => theMap.get(a) match {
      case None => SmallBag(theMap + (a -> BigInt(1)))
      case Some(i) => SmallBag(theMap.updated(a, i + 1))
    }
    case BigBag(left, right) if left.size < right.size => BigBag(left + a, right)
    case BigBag(left, right) => BigBag(left, right + a)
  }

  def +(a: A, count: BigInt): Bag[A] = this match {
    case SmallBag(theMap) => theMap.get(a) match {
      case None => SmallBag(theMap + (a -> count))
      case Some(i) => SmallBag(theMap.updated(a, i + count))
    }
    case BigBag(left, right) if left.size < right.size => BigBag(left.+(a, count), right)
    case BigBag(left, right) => BigBag(left, right.+(a, count))
  }

  def ++(that: Bag[A]): Bag[A] = {
    if (that.size > this.size)
      that ++ this
    else
      this match {
        case SmallBag(theMap) =>
          if (size >= threshold)
            BigBag(this, that)
          else {
            that match {
              case SmallBag(thatMap) => SmallBag(thatMap.toSeq.foldLeft(theMap)((z, x) => {
                  z.get(x._1) match {
                    case None => z + ((x._1, x._2))
                    case Some(i) => z.updated(x._1, x._2 + i)
                  }
                }))
              case BigBag(left, right) => (this ++ left) ++ right
            }
          }
        case BigBag(left, right) => {
          if(left.size < threshold)
            BigBag(left ++ that, right)
          else if (right.size < threshold)
            BigBag(left, right ++ that)
          else
            BigBag(this, that)
        }
      }
  }

  def isEmpty: Boolean = this match {
    case SmallBag(theMap) => theMap.isEmpty
    case BigBag(left, right) => left.isEmpty && right.isEmpty
  }

  /**def --(that: Bag[A]): Bag[A] = {
    if (that.theMap.size < theMap.size)
      Bag(that.theMap.toSeq.foldLeft(theMap)((z, x) => {
        z.get(x._1) match {
          case None => z
          case Some(i) => {
            val count = i - x._2
            if (count <= 0)
              z - x._1
            else
              z.updated(x._1, count)
          }
        }
      }))
    else
      Bag(this.theMap.toSeq.foldLeft(ScalaMap.empty[A, BigInt])((z, x) => {
        val countToSubtract = that.get(x._1)

        if (countToSubtract > 0) {
          val count = x._2 - countToSubtract

          if(count > 0)
            z + ((x._1, count))
          else
            z

        } else {
          z + x
        }
      }))
  }

  def &(that: Bag[A]): Bag[A] = new Bag[A](theMap.flatMap { case (k,v) =>
    val res = v min that.get(k)
    if (res <= 0) Nil else List(k -> res)
  })*/

  val toList: scala.collection.immutable.List[(A, BigInt)] = this match {
    case SmallBag(theMap) => theMap.toList
    case BigBag(left, right) => left.toList ++ right.toList
  }
}

case class SmallBag[A](theMap: ScalaMap[A, BigInt]) extends Bag[A]
case class BigBag[A](left: Bag[A], right: Bag[A]) extends Bag[A]
