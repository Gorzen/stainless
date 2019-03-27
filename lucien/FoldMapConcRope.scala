import stainless.lang._
import stainless.proof._
import stainless.annotation._
import stainless.collection._
import conc.ConcRope._

object FoldMapConcRope {
  import Parallel._
  import MonoidLaws._
  import FoldMapList.{fold => foldL}
  import ProofFold._

  def fold[A](xs: Conc[A])(implicit M: Monoid[A]): A = {
    xs match {
      case Empty() => M.empty
      case Single(x) =>
        assert(M.law_leftIdentity(x))
        assert(M.append(M.empty, x) == x)
        x
      case CC(left, right) =>
        val (l, r) = parallel(fold(left), fold(right))
        M.append(l, r)
      case Append(left, right) =>
        val (l, r) = parallel(fold(left), fold(right))
        M.append(l, r)
    }
  }

  def concRopeFromList[A](xs: List[A]): Conc[A] = {
    xs match {
      case Nil() => Empty[A]()
      case Cons(y, ys) => append(concRopeFromList(ys), y)
    }
  }

  def proof_concRopeFromList[A](xs: List[A]): Boolean = {
    (concRopeFromList(xs).toList == xs.reverse) because {
      xs match {
        case Nil() =>
          assert(concRopeFromList(Nil[A]()).toList == Nil[A]())
          assert(Nil[A]() == Nil[A]().reverse)
          check(concRopeFromList(Nil[A]()).toList == Nil[A]().reverse)
        case Cons(y, ys) => {
          concRopeFromList(y :: ys).toList ==| trivial |
          append(concRopeFromList(ys), y).toList ==| trivial |
          concRopeFromList(ys).toList ++ Cons(y, Nil[A]()) ==| proof_concRopeFromList(ys) |
          ys.reverse ++ Cons(y, Nil[A]()) ==| trivial |
          ys.reverse ++ Cons(y, Nil[A]()).reverse ==| concat_reverse(Cons(y, Nil[A]()), ys) |
          (Cons(y, Nil[A]()) ++ ys).reverse ==| trivial |
          (y :: ys).reverse
        }.qed
      }
    }
  }.holds

  def concat_reverse[A](xs: List[A], ys: List[A]): Boolean = {
    ((xs ++ ys).reverse == ys.reverse ++ xs.reverse) because {
      xs match {
        case Nil() => {
          (Nil[A]() ++ ys).reverse ==| trivial |
          ys.reverse ==| trivial |
          ys.reverse ++ Nil[A]() ==| trivial |
          ys.reverse ++ Nil[A]().reverse
        }.qed
        case Cons(z, zs) => {
          ((z :: zs) ++ ys).reverse ==| trivial |
          (z :: zs ++ ys).reverse ==| trivial |
          (zs ++ ys).reverse :+ z ==| concat_reverse(zs, ys) |
          (ys.reverse ++ zs.reverse) :+ z ==| trivial |
          ys.reverse ++ zs.reverse :+ z ==| right_assosciative(ys.reverse, zs.reverse, z) |
          ys.reverse ++ (zs.reverse :+ z) ==| trivial |
          ys.reverse ++ (z :: zs).reverse
        }.qed
      }
    }
  }.holds

  def right_assosciative[A](xs: List[A], ys: List[A], y: A): Boolean = {
    (xs ++ ys :+ y == xs ++ (ys :+ y)) because {
      xs match {
        case Nil() => {
          Nil[A]() ++ ys :+ y ==| trivial |
          ys :+ y ==| trivial |
          Nil[A]() ++ (ys :+ y)
        }.qed
        case Cons(z, zs) => {
          (z :: zs) ++ ys :+ y ==| trivial |
          z :: (zs ++ ys :+ y) ==| right_assosciative(zs, ys, y) |
          z :: (zs ++ (ys :+ y)) ==| trivial |
          z :: zs ++ (ys :+ y) ==| trivial |
          (z :: zs) ++ (ys :+ y)
        }.qed
      }
    }
  }.holds

  def proof_fold[A](xs: Conc[A])(implicit M: Monoid[A]): Boolean = {
    decreases(xs.level)
    (fold(xs) == foldL(xs.toList)) because {
      xs match {
        case Empty() =>
          assert(fold(Empty[A]()) == M.empty)
          assert(foldL(Empty[A]().toList) == foldL(Nil[A]()))
          assert(foldL(Nil[A]()) == M.empty)
          check(fold(Empty[A]()) == foldL(Empty[A]().toList))
        case Single(x) =>
          assert(fold(Single(x)) == x)
          assert(foldL(Single(x).toList) == foldL(x :: Nil[A]()))
          assert(foldL(x :: Nil[A]()) == (x :: Nil[A]()).foldLeft(M.empty)(M.append))
          assert((x :: Nil[A]()).foldLeft(M.empty)(M.append) == Nil[A]().foldLeft(M.append(M.empty, x))(M.append))
          assert(M.law_leftIdentity(x))
          assert(Nil[A]().foldLeft(M.append(M.empty, x))(M.append) == Nil[A]().foldLeft(x)(M.append))
          assert(Nil[A]().foldLeft(x)(M.append) == x)
          check(fold(Single(x)) == foldL(Single(x).toList))
        case CC(left, right) =>
          val (l, r) = parallel(fold(left), fold(right))
          assert(fold(CC(left, right)) == M.append(l, r))
          assert(proof_helper(xs, left, right, l, r))
          assert(foldL(left.toList ++ right.toList) == foldL(CC(left, right).toList))
          check(fold(CC(left, right)) == foldL(CC(left, right).toList))
        case Append(left, right) =>
          val (l, r) = parallel(fold(left), fold(right))
          assert(fold(Append(left, right)) == M.append(l, r))
          assert(proof_helper(xs, left, right, l, r))
          assert(foldL(left.toList ++ right.toList) == foldL(Append(left, right).toList))
          check(fold(Append(left, right)) == foldL(Append(left, right).toList))
      }
    }
  }.holds

  @inlineOnce
  def proof_helper[A](x: Conc[A], left: Conc[A], right: Conc[A], l: A, r: A)(implicit M: Monoid[A]): Boolean = {
    require((l, r) == parallel(fold(left), fold(right)) && (x == Append(left, right) || x == CC(left, right)))

    (M.append(l, r) == foldL(left.toList ++ right.toList)) because {
      assert(l == fold(left))
      assert(r == fold(right))
      assert(M.append(l, r) == M.append(fold(left), fold(right)))

      assert(proof_fold(left))
      assert(proof_fold(right))
      assert(M.append(fold(left), fold(right)) == M.append(foldL(left.toList), foldL(right.toList)))

      assert(proof_foldConcat(left.toList, right.toList))
      assert(M.append(foldL(left.toList), foldL(right.toList)) == foldL(left.toList ++ right.toList))

      check(M.append(l, r) == foldL(left.toList ++ right.toList))
    }
  }.holds

  def proof_foldConcat[A](xs: List[A], ys: List[A])(implicit M: Monoid[A]): Boolean = {
    (M.append(foldL(xs), foldL(ys)) == foldL(xs ++ ys)) because {
      xs match {
        case Nil() =>
          assert(M.append(foldL(Nil[A]()), foldL(ys)) == M.append(M.empty, foldL(ys)))
          assert(M.law_leftIdentity(foldL(ys)))
          assert(M.append(M.empty, foldL(ys)) == foldL(ys))
          assert(foldL(ys) == foldL(Nil[A]() ++ ys))
          check(M.append(foldL(Nil[A]()), foldL(ys)) == foldL(Nil[A]() ++ ys))
        case Cons(z, zs) =>
          assert(M.append(foldL(z :: zs), foldL(ys)) == M.append((z :: zs).foldLeft(M.empty)(M.append), foldL(ys)))
          assert(foldLeftEqualsFoldRight(z :: zs))
          assert(M.append((z :: zs).foldLeft(M.empty)(M.append), foldL(ys)) == M.append((z :: zs).foldRight(M.empty)(M.append), foldL(ys)))
          assert(M.append((z :: zs).foldRight(M.empty)(M.append), foldL(ys)) == M.append(M.append(z, zs.foldRight(M.empty)(M.append)), foldL(ys)))
          assert(M.law_associativity(z, zs.foldRight(M.empty)(M.append), foldL(ys)))
          assert(M.append(M.append(z, zs.foldRight(M.empty)(M.append)), foldL(ys)) == M.append(z, M.append(zs.foldRight(M.empty)(M.append), foldL(ys))))
          assert(foldLeftEqualsFoldRight(zs))
          assert(M.append(z, M.append(zs.foldRight(M.empty)(M.append), foldL(ys))) == M.append(z, M.append(zs.foldLeft(M.empty)(M.append), foldL(ys))))
          assert(M.append(z, M.append(zs.foldLeft(M.empty)(M.append), foldL(ys))) == M.append(z, M.append(foldL(zs), foldL(ys))))
          assert(proof_foldConcat(zs, ys))
          assert(M.append(z, M.append(foldL(zs), foldL(ys))) == M.append(z, foldL(zs ++ ys)))
          assert(M.append(z, foldL(zs ++ ys)) == M.append(z, (zs ++ ys).foldLeft(M.empty)(M.append)))
          assert(foldLeftEqualsFoldRight(zs ++ ys))
          assert(M.append(z, (zs ++ ys).foldLeft(M.empty)(M.append)) == M.append(z, (zs ++ ys).foldRight(M.empty)(M.append)))
          assert(M.append(z, (zs ++ ys).foldRight(M.empty)(M.append)) == (z :: zs ++ ys).foldRight(M.empty)(M.append))
          assert(foldLeftEqualsFoldRight(z :: zs ++ ys))
          assert((z :: zs ++ ys).foldRight(M.empty)(M.append) == (z :: zs ++ ys).foldLeft(M.empty)(M.append))
          assert((z :: zs ++ ys).foldLeft(M.empty)(M.append) == foldL(z :: zs ++ ys))
          assert(foldL(z :: zs ++ ys) == foldL((z :: zs) ++ ys))
          check(M.append(foldL(z :: zs), foldL(ys)) == foldL((z :: zs) ++ ys))
      }
    }
  }.holds
}
