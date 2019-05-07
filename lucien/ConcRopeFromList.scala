import stainless.lang._
import stainless.proof._
import stainless.annotation._
import stainless.collection._
import stainless.lang.StaticChecks._
import ConcRope._

object ConcRopeFromList {

  def concRopeFromList[A](xs: List[A]): Conc[A] = {
    xs match {
      case Nil() => Empty[A]()
      case Cons(y, ys) => append(concRopeFromList(ys), y)
    }
  }

  def proof_concRopeFromList[A](xs: List[A]): Boolean = {
    (concRopeFromList(xs).toList == xs.reverse) because lemma_concRopeFromList(xs)
  }.holds

  def lemma_concRopeFromList[A](xs: List[A]): Boolean = {
    decreases(xs.size)
    xs match {
      case Nil() =>
        assert(concRopeFromList(Nil[A]()).toList == Nil[A]())
        assert(Nil[A]() == Nil[A]().reverse)
        check(concRopeFromList(Nil[A]()).toList == Nil[A]().reverse)
      case Cons(y, ys) => {
        assert(concRopeFromList(y :: ys).toList == append(concRopeFromList(ys), y).toList)
        assert(append(concRopeFromList(ys), y).toList == concRopeFromList(ys).toList ++ Cons(y, Nil[A]()))
        assert(lemma_concRopeFromList(ys))
        assert(concRopeFromList(ys).toList ++ Cons(y, Nil[A]()) == ys.reverse ++ Cons(y, Nil[A]()))
        assert(ys.reverse ++ Cons(y, Nil[A]()) == ys.reverse ++ Cons(y, Nil[A]()).reverse)
        assert(concat_reverse(Cons(y, Nil[A]()), ys))
        assert(ys.reverse ++ Cons(y, Nil[A]()).reverse == (Cons(y, Nil[A]()) ++ ys).reverse)
        assert((Cons(y, Nil[A]()) ++ ys).reverse == (y :: ys).reverse)
        check(concRopeFromList(xs).toList == xs.reverse)
      }
    }
  }

  def concat_reverse[A](xs: List[A], ys: List[A]): Boolean = {
    decreases(xs.size)
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
    decreases(xs.size)
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
}
