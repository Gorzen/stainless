import stainless.lang._
import stainless.proof._
import stainless.annotation._
import stainless.collection._
import conc.ConcRope._

object FoldMapConcRope {

  def concRopeFromList[A](xs: List[A]): Conc[A] = {
    xs match {
      case Nil() => Empty[A]()
      case Cons(y, ys) => append(concRopeFromList(ys), y)
    }
  } /*ensuring { res =>
    // Doesn't work yet for some reason, even though I proved it
    assert(proof_concRopeFromList(xs))
    res.toList == xs.reverse
  }*/

  def proof_concRopeFromList[A](xs: List[A]): Boolean = {
    (concRopeFromList(xs).toList == xs.reverse) because {
      xs match {
        case Nil() =>
          assert(concRopeFromList(Nil[A]()).toList == Nil[A]())
          assert(Nil[A]() == Nil[A]().reverse)
          check(concRopeFromList(Nil[A]()).toList == Nil[A]().reverse)

          // Why doesn't this compile?
        /*{
          concRopeFromList(Nil[A]()).toList ==| trivial |
          Nil[A]() ==| trivial |
          Nil[A]().reverse
        }.qed*/
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
}
