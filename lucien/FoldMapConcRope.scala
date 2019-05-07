import stainless.lang._
import stainless.proof._
import stainless.annotation._
import stainless.collection._
import stainless.lang.StaticChecks._
import ConcRope._

object FoldMapConcRope {
  import Parallel._
  import MonoidLaws._
  import FoldMapList.{fold => foldL}
  import ProofFold._
  import ConcRopeFromList._

  val threshold = BigInt(32)

  def fold[A](xs: Conc[A])(implicit M: Monoid[A]): A = {
    xs match {
      case Empty() => M.empty
      case Single(x) => x
      case CC(left, right) if xs.size <= threshold =>
        M.append(fold(left), fold(right))
      case Append(left, right) if xs.size <= threshold =>
        M.append(fold(left), fold(right))
      case CC(left, right) =>
        val (l, r) = parallel(fold(left), fold(right))
        M.append(l, r)
      case Append(left, right) =>
        val (l, r) = parallel(fold(left), fold(right))
        M.append(l, r)
    }
  }

  def fold2[A](xs: Conc[A])(implicit M: Monoid[A]): A = {
    xs match {
      case Empty() => M.empty
      case Single(x) =>
        assert(M.law_leftIdentity(x))
        assert(M.append(M.empty, x) == x)
        x
      case CC(left, right) =>
        val (l, r) = parallel(fold2(left), fold2(right))
        M.append(l, r)
      case Append(left, right) =>
        val (l, r) = parallel(fold2(left), fold2(right))
        M.append(l, r)
    }
  }

  def foldSequential[A](xs: Conc[A])(implicit M: Monoid[A]): A = {
    xs match {
      case Empty() => M.empty
      case Single(x) => x
      case CC(left, right) => M.append(foldSequential(left), foldSequential(right))
      case Append(left, right) => M.append(foldSequential(left), foldSequential(right))
    }
  }

  def concRopeFromList[A](xs: List[A]): Conc[A] = {
    ConcRopeFromList.concRopeFromList(xs)
  }


  def proof_fold[A](xs: Conc[A])(implicit M: Monoid[A]): Boolean = {
    decreases(xs.level)
    (fold2(xs) == foldL(xs.toList)) because {
      xs match {
        case Empty() =>
          assert(fold2(Empty[A]()) == M.empty)
          assert(foldL(Empty[A]().toList) == foldL(Nil[A]()))
          assert(foldL(Nil[A]()) == M.empty)
          check(fold2(Empty[A]()) == foldL(Empty[A]().toList))
        case Single(x) =>
          assert(fold2(Single(x)) == x)
          assert(foldL(Single(x).toList) == foldL(x :: Nil[A]()))
          assert(foldL(x :: Nil[A]()) == (x :: Nil[A]()).foldLeft(M.empty)(M.append))
          assert((x :: Nil[A]()).foldLeft(M.empty)(M.append) == Nil[A]().foldLeft(M.append(M.empty, x))(M.append))
          assert(M.law_leftIdentity(x))
          assert(Nil[A]().foldLeft(M.append(M.empty, x))(M.append) == Nil[A]().foldLeft(x)(M.append))
          assert(Nil[A]().foldLeft(x)(M.append) == x)
          check(fold2(Single(x)) == foldL(Single(x).toList))
        case CC(left, right) =>
          val (l, r) = parallel(fold2(left), fold2(right))
          assert(fold2(CC(left, right)) == M.append(l, r))

          assert(l == fold2(left) && r == fold2(right))
          assert(M.append(l, r) == M.append(fold2(left), fold2(right)))

          assert(proof_fold(left) && proof_fold(right))
          assert(M.append(fold2(left), fold2(right)) == M.append(foldL(left.toList), foldL(right.toList)))

          assert(proof_foldConcat(left.toList, right.toList))
          assert(M.append(foldL(left.toList), foldL(right.toList)) == foldL(left.toList ++ right.toList))

          assert(foldL(left.toList ++ right.toList) == foldL(CC(left, right).toList))
          check(fold2(CC(left, right)) == foldL(CC(left, right).toList))
        case Append(left, right) =>
          val (l, r) = parallel(fold2(left), fold2(right))
          assert(fold2(Append(left, right)) == M.append(l, r))

          assert(l == fold2(left) && r == fold2(right))
          assert(M.append(l, r) == M.append(fold2(left), fold2(right)))

          assert(proof_fold(left) && proof_fold(right))
          assert(M.append(fold2(left), fold2(right)) == M.append(foldL(left.toList), foldL(right.toList)))

          assert(proof_foldConcat(left.toList, right.toList))
          assert(M.append(foldL(left.toList), foldL(right.toList)) == foldL(left.toList ++ right.toList))

          assert(foldL(left.toList ++ right.toList) == foldL(Append(left, right).toList))
          check(fold2(Append(left, right)) == foldL(Append(left, right).toList))
      }
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

  def check_foldFold2[A](xs: Conc[A])(implicit M: Monoid[A]): Boolean = {
    (fold2(xs) == fold(xs)) because {
      xs match {
        case CC(left, right) =>
          if(xs.size <= threshold){
            val (l, r) = parallel(fold2(left), fold2(right))
            assert(fold2(CC(left, right)) == M.append(l, r))
            assert(l == fold2(left))
            assert(r == fold2(right))
            assert(M.append(l, r) == M.append(fold2(left), fold2(right)))
            assert(check_foldFold2(left))
            assert(check_foldFold2(right))
            assert(M.append(fold2(left), fold2(right)) == M.append(fold(left), fold(right)))
            assert(M.append(fold(left), fold(right)) == fold(xs))
            check(fold2(xs) == fold(xs))
          }else{
            val (l, r) = parallel(fold2(left), fold2(right))
            assert(fold2(CC(left, right)) == M.append(l, r))
            assert(l == fold2(left) && r == fold2(right))
            assert(check_foldFold2(left) && check_foldFold2(right))

            val (l2, r2) = parallel(fold(left), fold(right))
            assert(l2 == l && r2 == r)
            assert(fold(CC(left, right)) == M.append(l2, r2))
            check(fold2(xs) == fold(xs))
          }
        case Append(left, right) =>
          if(xs.size <= threshold){
            val (l, r) = parallel(fold2(left), fold2(right))
            assert(fold2(Append(left, right)) == M.append(l, r))
            assert(l == fold2(left))
            assert(r == fold2(right))
            assert(M.append(l, r) == M.append(fold2(left), fold2(right)))
            assert(check_foldFold2(left))
            assert(check_foldFold2(right))
            assert(M.append(fold2(left), fold2(right)) == M.append(fold(left), fold(right)))
            assert(M.append(fold(left), fold(right)) == fold(xs))
            check(fold2(xs) == fold(xs))
          }else{
            val (l, r) = parallel(fold2(left), fold2(right))
            assert(fold2(Append(left, right)) == M.append(l, r))
            assert(l == fold2(left) && r == fold2(right))
            assert(check_foldFold2(left) && check_foldFold2(right))

            val (l2, r2) = parallel(fold(left), fold(right))
            assert(l2 == l && r2 == r)
            assert(fold(Append(left, right)) == M.append(l2, r2))
            check(fold2(xs) == fold(xs))
          }
        case _ => true
      }
    }
  }.holds

  def check_foldSequentialFold2[A](xs: Conc[A])(implicit M: Monoid[A]): Boolean = {
    (foldSequential(xs) == fold2(xs)) because {
      xs match {
        case CC(left, right) =>
          val (l, r) = parallel(fold2(left), fold2(right))
          assert(l == fold2(left) && r == fold2(right))
          assert(check_foldSequentialFold2(left))
          assert(check_foldSequentialFold2(right))
          check(foldSequential(xs) == fold2(xs))
        case Append(left, right) =>
          val (l, r) = parallel(fold2(left), fold2(right))
          assert(l == fold2(left) && r == fold2(right))
          assert(check_foldSequentialFold2(left))
          assert(check_foldSequentialFold2(right))
          check(foldSequential(xs) == fold2(xs))
        case _ => true
      }
    }
  }
}
