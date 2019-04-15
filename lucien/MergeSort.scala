import stainless.collection._
import stainless.lang._
import stainless.proof._
import stainless.annotation._
import stainless.lang.StaticChecks._

object MergeSort {
  import TotalOrderLaws.TotalOrder

  def bag[T](list: List[T]): Bag[T] = list match {
    case Nil() => Bag.empty[T]
    case Cons(x, xs) => bag(xs) + x
  }

  def sort[T](l: List[T])(implicit comparator: TotalOrder[T]): List[T] =  {
    decreases(l.size)
    l match {
      case Cons(x1, Cons(x2, xs)) =>
        val (left, right) = split(l)
        merge(sort(left), sort(right))
      case _ => l
    }
  } ensuring { res =>
    l.size == res.size &&
    bag(l) == bag(res) &&
    isSorted(res)
  }

  def split[T](list: List[T]): (List[T], List[T]) = {
    require(list.size > 1)
    val (l, r) = evenSplit(list)
    assert(l ++ r == list)
    assert(bag(l ++ r) == bag(list))
    assert(lemma_bagHomomorphism(l, r))
    (l, r)
  } ensuring { res =>
    res._1 ++ res._2 == list &&
    bag(res._1) ++ bag(res._2) == bag(list) &&
    list.size == res._1.size + res._2.size &&
    res._1.size > 0 &&
    res._2.size > 0
  }

  @induct
  def lemma_bagHomomorphism[T](l: List[T], r: List[T]) = {
    bag(l ++ r) == bag(l) ++ bag(r)
  }.holds

  @inlineOnce
  def evenSplit[T](list: List[T]) : (List[T], List[T]) = {
    val index = list.size / 2
    list match {
      case Nil() => (Nil[T](), Nil[T]())
      case Cons(h, rest) => {
        if (index <= BigInt(0)) {
          (Nil[T](), list)
        } else {
          val (left,right) = rest.splitAtIndex(index - 1)
          (Cons[T](h,left), right)
        }
    }
  }} ensuring { (res:(List[T],List[T])) =>
    res._1 ++ res._2 == list &&
    list.size == res._1.size + res._2.size &&
    res._1.size == list.size / 2 &&
    res._2.size == list.size - list.size / 2
  }

  def merge[T](left: List[T], right: List[T])(implicit comparator: TotalOrder[T]): List[T] = {
    require(isSorted(left) && isSorted(right))
    (left, right) match {
      case (_, Nil()) => left
      case (Nil(), _) => right
      case (Cons(leftHead, leftTail), Cons(rightHead, rightTail)) if(comparator.lteqv(leftHead, rightHead)) =>
        leftHead :: merge(leftTail, right)
      case (Cons(leftHead, leftTail), Cons(rightHead, rightTail)) =>
        assert(!comparator.lteqv(leftHead, rightHead))
        assert(comparator.law_connex_total_order(leftHead, rightHead))
        assert(comparator.lteqv(rightHead, leftHead))

        rightHead :: merge(left, rightTail)
    }
  } ensuring { res =>
    bag(left) ++ bag(right) == bag(res) &&
    (left.size + right.size) == res.size &&
    isSorted(res)
  }

  def isSorted[T](l: List[T])(implicit comparator: TotalOrder[T]): Boolean = l match {
    case Cons(x1, Cons(x2, xs)) => comparator.lteqv(x1, x2) && isSorted(x2 :: xs)
    case _ => true
  }
}
