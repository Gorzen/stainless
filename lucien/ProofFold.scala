import stainless.lang._
import stainless.proof._
import stainless.annotation._
import stainless.collection._

object ProofFold {
  import MonoidLaws._
  import MonoidBigInt._
  import FoldMapList._

  def foldLeftEqualsFoldRight[A](xs: List[A])(implicit M: Monoid[A]): Boolean = {
    decreases(xs.size)
    (xs.foldLeft(M.empty)(M.append) == xs.foldRight(M.empty)(M.append)) because {
      xs match {
        case Nil() => {
          Nil[A]().foldLeft(M.empty)(M.append)           ==| trivial |
          M.empty                                        ==| trivial |
          Nil[A]().foldRight(M.empty)(M.append)
        }.qed
        case Cons(y, Nil()) => {
          Cons(y, Nil[A]()).foldLeft(M.empty)(M.append)       ==| trivial                  |
          Nil[A]().foldLeft(M.append(M.empty, y))(M.append)   ==| M.law_leftIdentity(y)    |
          Nil[A]().foldLeft(y)(M.append)                      ==| trivial                  |
          y                                                   ==| M.law_rightIdentity(y)   |
          M.append(y, M.empty)                                ==| trivial                  |
          M.append(y, Nil[A]().foldRight(M.empty)(M.append))  ==| trivial                  |
          Cons(y, Nil[A]()).foldRight(M.empty)(M.append)
        }.qed
        case Cons(y1, Cons(y2, ys)) =>
          assert((y1 :: y2 :: ys).foldLeft(M.empty)(M.append) == (y2 :: ys).foldLeft(M.append(M.empty, y1))(M.append))
          assert(M.law_leftIdentity(y1))
          assert((y2 :: ys).foldLeft(M.append(M.empty, y1))(M.append) == (y2 :: ys).foldLeft(y1)(M.append))
          assert((y2 :: ys).foldLeft(y1)(M.append) == ys.foldLeft(M.append(y1, y2))(M.append))
          assert(M.law_leftIdentity(M.append(y1, y2)))
          assert(ys.foldLeft(M.append(y1, y2))(M.append) == ys.foldLeft(M.append(M.empty, M.append(y1, y2)))(M.append))
          assert(ys.foldLeft(M.append(M.empty, M.append(y1, y2)))(M.append) == (M.append(y1, y2) :: ys).foldLeft(M.empty)(M.append))
          assert(foldLeftEqualsFoldRight(M.append(y1, y2) :: ys))
          assert((M.append(y1, y2) :: ys).foldLeft(M.empty)(M.append) == (M.append(y1, y2) :: ys).foldRight(M.empty)(M.append))
          assert((M.append(y1, y2) :: ys).foldRight(M.empty)(M.append) == M.append(M.append(y1, y2), ys.foldRight(M.empty)(M.append)))
          assert(M.law_associativity(y1, y2, ys.foldRight(M.empty)(M.append)))
          assert(M.append(M.append(y1, y2), ys.foldRight(M.empty)(M.append)) == M.append(y1, M.append(y2, ys.foldRight(M.empty)(M.append))))
          assert(M.append(y1, M.append(y2, ys.foldRight(M.empty)(M.append))) == M.append(y1, (y2 :: ys).foldRight(M.empty)(M.append)))
          assert(M.append(y1, (y2 :: ys).foldRight(M.empty)(M.append)) == (y1 :: y2 :: ys).foldRight(M.empty)(M.append))
          check((y1 :: y2 :: ys).foldLeft(M.empty)(M.append) == (y1 :: y2 :: ys).foldRight(M.empty)(M.append))
      }
    }
  }.holds

  def foldLeftMapEqualsFoldRightMap[A, B](xs: List[A])(f: A => B)(implicit M: Monoid[B]): Boolean = {
    assert(check(foldLeftEqualsFoldRight(xs.map(f))))
    check((xs.map(f)).foldLeft(M.empty)(M.append) == (xs.map(f)).foldRight(M.empty)(M.append))
  }.holds
}
