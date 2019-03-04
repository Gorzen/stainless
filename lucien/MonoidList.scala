import stainless.lang._
import stainless.proof._
import stainless.annotation._
import stainless.collection._

object MonoidList {
  import MonoidLaws.Monoid

  @induct
  def lemma_associativity[T](x: List[T], y: List[T], z: List[T]): Boolean = {
    x ++ (y ++ z) == (x ++ y) ++ z
  }.holds

  def listMonoid[T]: Monoid[List[T]] = new Monoid[List[T]] {
    def empty: List[T] = Nil[T]()
    def append(x: List[T], y: List[T]): List[T] = x ++ y

    override def law_associativity(x: List[T], y: List[T], z: List[T]): Boolean = {
      super.law_associativity(x, y, z) because lemma_associativity(x, y, z)
    }
  }
}
