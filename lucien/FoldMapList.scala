import stainless.lang._
import stainless.proof._
import stainless.annotation._
import stainless.collection._

object FoldMapList {
  import MonoidLaws._
  import MonoidBigInt._

  def fold[A](xs: List[A])(implicit M: Monoid[A]): A = {
    xs.foldLeft(M.empty)(M.append)
  }

  def foldR[A](xs: List[A])(implicit M: Monoid[A]): A = {
    xs.foldRight(M.empty)(M.append)
  }

  def foldMap[A, B](xs: List[A])(f: A => B)(implicit M: Monoid[B]): B = {
    xs.foldLeft(M.empty)((b, a) => M.append(b, f(a)))
  }

  def foldMapR[A, B](xs: List[A])(f: A => B)(implicit M: Monoid[B]): B = {
    xs.foldRight(M.empty)((a, b) => M.append(f(a), b))
  }

  // Basic sum implementation for testing purposes
  def sum(xs: List[BigInt]): BigInt = {
    xs match  {
      case Nil() => BigInt(0)
      case Cons(y, ys) => y + sum(ys)
    }
  }

  @induct
  def lemma_foldLeft_foldRight[A](xs: List[A])(implicit M: Monoid[A]): Boolean = {
    xs match {
      case Nil() => true
      case Cons(y, ys) =>
        assert(M.law_leftIdentity(y))
        assert(M.law_rightIdentity(y))
        M.append(y, ys.foldLeft(M.empty)(M.append)) == ys.foldLeft(y)(M.append)
    }
  }.holds

  @induct
  def checkFoldLeftFoldRight[A](xs: List[A])(implicit M: Monoid[A]): Boolean = {
    (xs.foldLeft(M.empty)(M.append) == xs.foldRight(M.empty)(M.append)) because {
      xs match {
        case Nil() => true
        case Cons(y, ys) =>
          assert(checkFoldLeftFoldRight(ys)(M))
          assert((y :: ys).foldLeft(M.empty)(M.append) == ys.foldLeft(M.append(M.empty, y))(M.append))

          //(ys).foldLeft(M.append(M.empty, y))(M.append) == (ys).foldLeft(y)(M.append)
          //(ys).foldLeft(y)(M.append) == ys.foldLeft(M.append(y, y2))(M.append)

          assert((y :: ys).foldRight(M.empty)(M.append) == M.append(y, ys.foldRight(M.empty)(M.append)))
          assert(M.append(y, ys.foldRight(M.empty)(M.append)) == M.append(y, ys.foldLeft(M.empty)(M.append)))

          //assert(M.law_associativity(y, M.empty, y2))
          //assert(M.append(y, ys.foldLeft(M.empty)(M.append)) == ys.foldLeft(y)(M.append)) // Maybe a bit too fast
          assert(lemma_foldLeft_foldRight(y :: ys))

          assert(M.law_leftIdentity(y))
          assert(ys.foldLeft(y)(M.append) == ys.foldLeft(M.append(M.empty, y))(M.append))
          assert(ys.foldLeft(M.append(M.empty, y))(M.append) == (y :: ys).foldLeft(M.empty)(M.append))

          check((y :: ys).foldRight(M.empty)(M.append) == (y :: ys).foldLeft(M.empty)(M.append))
        }
      }
    }.holds

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
    assert(checkFoldLeftFoldRight(xs))
    assert(xs.foldRight(M.empty)(M.append) == foldR(xs))

    check(fold(xs) == foldR(xs))
  }.holds

  // Define monoid for BigInt to check fold
  def bigIntSumMonoid2: Monoid[BigInt] = new Monoid[BigInt] {
    def empty: BigInt = BigInt(0)
    def append(x: BigInt, y: BigInt): BigInt = x + y
  }
}
