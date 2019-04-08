import stainless.collection._
import stainless.lang._
import stainless.proof._
import stainless.lang.StaticChecks._

object InsertionSort {
  import TotalOrderLaws.TotalOrder

  def bag[T](list: List[T]): Bag[T] = list match {
    case Nil() => Bag.empty[T]
    case Cons(x, xs) => bag(xs) + x
  }

  def sort[T](l: List[T])(implicit comparator: TotalOrder[T]): List[T] =  {
    l match {
      case Nil() => Nil[T]()
      case x :: xs => sortedIns(x, sort(xs))
    }
  } ensuring { res =>
    bag(l) == bag(res) &&
    // l.groupBy(identity) == res.groupBy(identity) &&
    isSorted(res)
  }

  def sortedIns[T](e: T, l: List[T])(implicit comparator: TotalOrder[T]): List[T] = {
    require(isSorted(l))
    l match {
      case Nil() => Cons(e, Nil[T]())
      case x :: xs if(comparator.lteqv(e, x)) =>
        e :: x :: xs
      case x :: xs =>
        assert(lemma(e, x, xs))
        x :: sortedIns(e, xs)
    }
  } ensuring { res =>
    bag(e :: l) == bag(res) &&
    l.size + 1 == res.size &&
    // (e :: l).groupBy(identity) == res.groupBy(identity) &&
    isSorted(res)
  }

  def isSorted[T](l: List[T])(implicit comparator: TotalOrder[T]): Boolean = l match {
    case x1 :: x2 :: xs => comparator.lteqv(x1, x2) && isSorted(x2 :: xs)
    case _ => true
  }

  def lemma[T](e: T, x: T, xs: List[T])(implicit comparator: TotalOrder[T]): Boolean = {
    (!comparator.lteqv(e, x) ==> comparator.lteqv(x, e)) because comparator.law_connex_total_order(e, x)
    (isSorted(x :: xs) && comparator.lteqv(x, e)) ==> isSorted(x :: sortedIns(e, xs))
  }.holds
}
