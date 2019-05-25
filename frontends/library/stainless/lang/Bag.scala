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
  import HelperMethods._

  private val threshold = 1000

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

  def fixTree: Bag[A] = this match {
    case SmallBag(theMap) => this
    case BigBag(left, right) => {
      if (size <= threshold)
        flatten
      else {
        BigBag(left.fixTree, right.fixTree)
      }
    }
  }

  // Should be constant time if bags > threshold
  def ++(that: Bag[A]): Bag[A] = {
    (this, that) match {
      case (SmallBag(theMap), SmallBag(thatMap)) => {
        if (theMap.size + thatMap.size <= threshold)
          SmallBag(concatMaps(theMap, thatMap))
        else
          BigBag(this, that)
      }
      case (SmallBag(theMap), BigBag(left, right)) => {
        if (theMap.size + that.size <= threshold)
          smallBagConcat(SmallBag(theMap), that.flatten)
        else
          BigBag(this, that)
      }
      case (BigBag(left, right), SmallBag(thatMap)) => {
        if (this.size + thatMap.size <= threshold)
          smallBagConcat(this.flatten, SmallBag(thatMap))
        else
          BigBag(this, that)
      }
      case (BigBag(l1, r1), BigBag(l2, r2)) => {
        if (this.size + that.size <= threshold)
          smallBagConcat(this.flatten, that.flatten)
        else
          BigBag(this, that)
      }
    }
  }

  // Try a different approach to have trees closer to threshold
  /**def ++(that: Bag[A]): Bag[A] = {
    (this, that) match {
      case (SmallBag(theMap), SmallBag(thatMap)) => {
        if (theMap.size + thatMap.size <= threshold)
          SmallBag(concatMaps(theMap, thatMap))
        else
          BigBag(this, that)
      }
      case (SmallBag(theMap), BigBag(left, right)) => {
        if (theMap.size + that.size <= threshold)
          smallBagConcat(SmallBag(theMap), that.flatten)
        else
          BigBag(this.fixTree, that.fixTree)
      }
      case (BigBag(left, right), SmallBag(thatMap)) => {
        if (this.size + thatMap.size <= threshold)
          smallBagConcat(this.flatten, SmallBag(thatMap))
        else
          BigBag(this.fixTree, that.fixTree)
      }
      case (BigBag(l1, r1), BigBag(l2, r2)) => {
        if (this.size + that.size <= threshold)
          smallBagConcat(this.flatten, that.flatten)
        else
          BigBag(this.fixTree, that.fixTree)
      }
    }
  }*/

  // A smarter way to have trees closer to threshold
  /**def ++(that: Bag[A]): Bag[A] = {
    if (that.size > this.size)
      that ++ this
    else
      (this, that) match {
        case (SmallBag(theMap), SmallBag(thatMap)) if theMap.size <= threshold => SmallBag(concatMaps(theMap, thatMap))
        case (SmallBag(theMap), SmallBag(thatMap)) => BigBag(this, that)
        case (SmallBag(theMap), BigBag(left, right)) if theMap.size <= threshold => smallBagConcat(SmallBag(theMap), that.flatten)
        case (SmallBag(theMap), BigBag(left, right)) => BigBag(this, that)
        case (BigBag(left, right), SmallBag(thatMap)) if right.size <= threshold => BigBag(left, right ++ SmallBag(thatMap))
        case (BigBag(left, right), SmallBag(thatMap)) => BigBag(this, that)
        case (BigBag(l1, r1), BigBag(l2, r2)) if r1.size <= threshold => BigBag(BigBag(l1, r1 ++ r2), l2)
        case (BigBag(l1, r1), BigBag(l2, r2)) => BigBag(this, that)
      }
  }*/

  def flatten: SmallBag[A] = this match {
    case SmallBag(theMap) => SmallBag(theMap)
    case BigBag(left, right) => smallBagConcat(left.flatten, right.flatten)
  }

  /**def ++(that: Bag[A]): Bag[A] = {
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
  }*/

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

  def printStructure: Unit = {
    println(toStr)
  }

  def toStr: String = this match {
    case BigBag(left, right) => "(" + left.toStr + ", " + right.toStr + ")"
    case SmallBag(theMap) => theMap.size.toString
  }
}

case class SmallBag[A](theMap: ScalaMap[A, BigInt]) extends Bag[A]
case class BigBag[A](left: Bag[A], right: Bag[A]) extends Bag[A]

object HelperMethods {
  @ignore
  def concatMaps[A](x: ScalaMap[A, BigInt], y: ScalaMap[A, BigInt]): ScalaMap[A, BigInt] = {
    if(x.size < y.size)
      concatMaps(y, x)
    else
      y.toSeq.foldLeft(x)((z: ScalaMap[A, BigInt], e) => {
        val get = z.get(e._1)
        z.get(e._1).isEmpty match {
          case true => z + ((e._1, e._2))
          case false => z.updated(e._1, e._2 + get.get)
        }
      })
  }

  @ignore
  def smallBagConcat[A](x: SmallBag[A], y: SmallBag[A]): SmallBag[A] = {
    SmallBag(concatMaps(x.theMap, y.theMap))
  }
}
