import stainless.collection._
import stainless.lang._
import stainless.proof._

object MergeSort {
  import TotalOrderLaws.TotalOrder

  def sort[T](l: List[T])(implicit comparator: TotalOrder[T]): List[T] =  {
    l match {
      case x1 :: x2 :: xs =>
        val (left, right) = l.evenSplit
        merge(sort(left), sort(right))
      case _ => l
    }
  } ensuring { res =>
    l.groupBy(identity) == res.groupBy(identity)
    isSorted(res)
  }

  def merge[T](left: List[T], right: List[T])(implicit comparator: TotalOrder[T]): List[T] = {
    require(isSorted(left) && isSorted(right))
    (left, right) match {
      case (_, Nil()) => left
      case (Nil(), _) => right
      case (leftHead :: leftTail, rightHead :: rightTail) if(comparator.lteqv(leftHead, rightHead)) =>
        leftHead :: merge(leftTail, right)
      case (leftHead :: leftTail, rightHead :: rightTail) =>
        assert(lemma(leftHead, leftTail, rightHead, rightTail))
        rightHead :: merge(left, rightTail)
    }
  } ensuring { res =>
    (left ++ right).groupBy(identity) == res.groupBy(identity) &&
    isSorted(res)
  }

  def isSorted[T](l: List[T])(implicit comparator: TotalOrder[T]): Boolean = l match {
    case x1 :: x2 :: xs => comparator.lteqv(x1, x2) && isSorted(x2 :: xs)
    case _ => true
  }

  // Can I not prove anything with this technique?
  def lemma[T](leftHead: T, leftTail: List[T], rightHead: T, rightTail: List[T])(implicit comparator: TotalOrder[T]): Boolean = {
    require(isSorted(leftHead :: leftTail) && isSorted(rightHead :: rightTail))
    ((leftHead :: leftTail) ++ (rightHead :: rightTail)).groupBy(identity) == merge(leftHead :: leftTail, rightHead :: rightTail).groupBy(identity)
  }.holds
}
