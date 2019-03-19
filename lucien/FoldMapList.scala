import stainless.lang._
import stainless.proof._
import stainless.annotation._
import stainless.collection._

object FoldMapList {
  import MonoidLaws._
  import MonoidBigInt._
  import ProofFold._

  def fold[A](xs: List[A])(implicit M: Monoid[A]): A = {
    xs.foldLeft(M.empty)(M.append)
  }

  def foldR[A](xs: List[A])(implicit M: Monoid[A]): A = {
    xs.foldRight(M.empty)(M.append)
  }

  def foldMap[A, B](xs: List[A])(f: A => B)(implicit M: Monoid[B]): B = {
    xs.map(f).foldLeft(M.empty)(M.append)
  }

  def foldMapR[A, B](xs: List[A])(f: A => B)(implicit M: Monoid[B]): B = {
    xs.map(f).foldRight(M.empty)(M.append)
  }

  // Basic sum implementation for testing purposes
  def sum(xs: List[BigInt]): BigInt = {
    xs match  {
      case Nil() => BigInt(0)
      case Cons(y, ys) => y + sum(ys)
    }
  }

  @induct
  def checkFoldFoldMap[A, B](xs: List[A])(f: A => B)(implicit M: Monoid[B]): Boolean = {

    foldMapR(xs)(f) == foldR(xs.map(f))
  }.holds

  @induct
  def checkFold(xs: List[BigInt]): Boolean = {
    implicit val monoid = bigIntSumMonoid2

    sum(xs) == foldR(xs)
  }.holds

  @induct
  def checkFoldMap(xs: List[BigInt]): Boolean = {
    // implicit def from MonoidBigInt.scala is used in call
    sum(xs) == foldMapR(xs)(Sum(_)).toSum
  }.holds

  def foldEqualsFoldR[A](xs: List[A])(implicit M: Monoid[A]): Boolean = {
    assert(fold(xs) == xs.foldLeft(M.empty)(M.append))
    assert(foldLeftEqualsFoldRight(xs))
    assert(xs.foldRight(M.empty)(M.append) == foldR(xs))

    check(fold(xs) == foldR(xs))
  }.holds

  def foldMapEqualsFoldMapR[A, B](xs: List[A])(f: A => B)(implicit M: Monoid[B]): Boolean = {
    assert(foldMap(xs)(f) == xs.map(f).foldLeft(M.empty)(M.append))
    assert(foldMapR(xs)(f) == xs.map(f).foldRight(M.empty)(M.append))

    assert(foldLeftMapEqualsFoldRightMap(xs)(f))

    check(foldMap(xs)(f) == foldMapR(xs)(f))
  }.holds

  // Define monoid for BigInt to check fold
  def bigIntSumMonoid2: Monoid[BigInt] = new Monoid[BigInt] {
    def empty: BigInt = BigInt(0)
    def append(x: BigInt, y: BigInt): BigInt = x + y
  }
}
