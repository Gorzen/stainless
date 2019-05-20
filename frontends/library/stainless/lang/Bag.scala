/* Copyright 2009-2018 EPFL, Lausanne */

package stainless.lang
import stainless.annotation._
import scala.collection.immutable.{Map => ScalaMap}

object Bag {
  @library @inline
  def empty[A] = Bag[A]()

  @ignore
  def apply[A](elems: (A, BigInt)*): Bag[A] = {
    Bag(elems.toMap)
  }
}

@ignore
abstract class Bag[A] {
  import scala._

  private val threshold = 1000

  private val size = {
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
      case None => Bag(theMap + (a -> BigInt(1)))
      case Some(i) => Bag(theMap.updated(a, i + 1))
    }
    case BigBag(left, right) if left.size < right.size => BigBag(left + a, right)
    case _ => BigBag(left, right + a)
  }

  def +(a: A, count: BigInt): Bag[A] = this match {
    case SmallBag(theMap) => theMap.get(a) match {
      case None => Bag(theMap + (a -> count))
      case Some(i) => Bag(theMap.updated(a, i + count))
    }
    case BigBag(left, right) if left.size < right.size => BigBag(left.+(a, count), right)
    case _ => BigBag(left, right.+(a, count))
  }

  def ++(that: Bag[A]): Bag[A] = {
    this match {
      case SmallBag(theMap) =>
        if (that.size > theMap.size)
          that ++ this
        else {
          Bag(that.theMap.toSeq.foldLeft(theMap)((z, x) => {
            z.get(x._1) match {
              case None => z + ((x._1, x._2))
              case Some(i) => z.updated(x._1, x._2 + i)
            }
          }))
        }
      case BigBag(left, right) => {
        
      }
    }
  }

  def isEmpty: Boolean = theMap.isEmpty

  def --(that: Bag[A]): Bag[A] = {
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
  })

  case class SmallBag[A](theMap: ScalaMap[A, BigInt])
  case class BigBag[A](left: Bag[A], right: Bag[A])
}
