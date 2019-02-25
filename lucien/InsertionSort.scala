import stainless.collection._
import stainless.annotation._
import stainless.lang._
import stainless.proof._

object InsertionSort {
  import TotalOrderLaws.TotalOrder

  def sort[T](l: List[T])(implicit comparator: TotalOrder[T]): List[T] =  {
    l match {
      case Nil() => Nil[T]()
      case Cons(x, xs) => sortedIns(x, sort(xs))
    }
  } ensuring { res =>
    l.content == res.content &&
    l.size == res.size &&
    isSorted(res)
  }

  def sortedIns[T](e: T, l: List[T])(implicit comparator: TotalOrder[T]): List[T] = {
    require(isSorted(l))
    l match {
      case Nil() => Cons(e, Nil[T]())
      case Cons(x, xs) if(comparator.lteqv(e, x)) =>
        assert(lemma1(e, x, xs))
        Cons(e, Cons(x, xs))
      case Cons(x, xs) =>
        assert(lemma2(e, x, xs))
        Cons(x, sortedIns(e, xs))
    }
  } ensuring { res =>
    Cons(e, l).content == res.content &&
    isSorted(res) &&
    (l.size + 1) == res.size
  }

  def isSorted[T](l: List[T])(implicit comparator: TotalOrder[T]): Boolean = l match {
    case Cons(x, Cons(y, ys)) => comparator.lteqv(x, y) && isSorted(Cons(y, ys))
    case _ => true
  }

  def lemma1[T](e: T, x: T, xs: List[T])(implicit comparator: TotalOrder[T]): Boolean = {
    (isSorted(Cons(x, xs)) && comparator.lteqv(e, x)) ==> isSorted(Cons(e, Cons(x, xs)))
  }.holds

  def lemma2[T](e: T, x: T, xs: List[T])(implicit comparator: TotalOrder[T]): Boolean = {
    !comparator.lteqv(e, x) ==> comparator.lteqv(x, e) because comparator.law_connex_total_order(e, x)
    (isSorted(Cons(x, xs)) && comparator.lteqv(x, e)) ==> isSorted(Cons(x, sortedIns(e, xs)))
  }.holds
}
