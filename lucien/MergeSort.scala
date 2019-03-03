import stainless.collection._
import stainless.lang._
import stainless.proof._

object MergeSort {
  import TotalOrderLaws.TotalOrder

  def sort[T](l: List[T])(implicit comparator: TotalOrder[T]): List[T] =  {
    l match {
      case x1 :: x2 :: xs =>
        val (left, right) = l.evenSplit
        assert(lemma_sort(l, left, right))
        merge(sort(left), sort(right))
      case _ => l
    }
  } ensuring { res =>
    l.size == res.size &&
    l.content == res.content && // this is the issue
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
        assert(lemma_merge(leftHead, leftTail, rightHead, rightTail))
        rightHead :: merge(left, rightTail)
    }
  } ensuring { res =>
    (left ++ right).content == res.content &&
    (left ++ right).size == res.size &&
    isSorted(res)
  }

  def isSorted[T](l: List[T])(implicit comparator: TotalOrder[T]): Boolean = l match {
    case x1 :: x2 :: xs => comparator.lteqv(x1, x2) && isSorted(x2 :: xs)
    case _ => true
  }

  //How does this not terminate? The arguments are smaller
  def lemma_merge[T](leftHead: T, leftTail: List[T], rightHead: T, rightTail: List[T])(implicit comparator: TotalOrder[T]): Boolean = {
    require(isSorted(leftHead :: leftTail) && isSorted(rightHead :: rightTail) && !comparator.lteqv(leftHead, rightHead))
    !comparator.lteqv(leftHead, rightHead) ==> comparator.lteqv(rightHead, leftHead) because comparator.law_connex_total_order(leftHead, rightHead)
    (comparator.lteqv(rightHead, leftHead) && isSorted(merge(leftHead :: leftTail, rightTail))) ==> isSorted(rightHead :: merge(leftHead :: leftTail, rightTail))
  }.holds

  def lemma_sort[T](list: List[T], left: List[T], right: List[T])(implicit comparator: TotalOrder[T]): Boolean = {
    require((left, right) == list.evenSplit)
    //assert(left ++ right == list) stainless cannot prove this
    (left ++ right).content == list.content //stainless cannot prove this either, drop and take have no guarantee together
  }.holds
}
