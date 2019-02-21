import stainless.lang._
import stainless.proof._
import stainless.annotation._

object PartialOrderLaws {

  // Laws: reflexive, antisymmetric, transitive
  // How to extend Equality
  abstract class PartialOrder[A] /*extends Equality[A]*/ {

    /**
     * Result of comparison of x and y
     * If it returns None there is no comparison possible
     * If it returns Some(a) the Int sign of a is
     * - negative iff 'x < y'
     * - zero     iff 'x == y'
     * - positive iff 'x > y'
     */
    def partialComparison(x: A, y: A): Option[Int]

    final def eqv(x: A, y: A): Boolean = {
      // partialComparison(x, y) == Some(0) fails for some reason
      // but it doesn't really make sense do define the equals by a law.
      //lteqv(x, y) && lteqv(y, x)
      partialComparison(x, y) == Some(0)
    }

    // Final or not? Cats says they let you override for performance reasons but how to check user functions?
    // Should define more functions?
    final def lteqv(x: A, y: A): Boolean = {
      partialComparison(x, y) match {
        case Some(i) => i <= 0
        case None() => false
      }
    }

    @law
    def law_reflexive(x: A) = {
      lteqv(x, x)
    }

    @law
    def law_antisymmetric(x: A, y: A) = {
      (lteqv(x, y) && lteqv(y, x)) ==> eqv(x, y)
    }

    @law
    def law_transitive(x: A, y: A, z: A) = {
      (lteqv(x, y) && lteqv(y, z)) ==> lteqv(x, z)
    }
  }

  def bigIntPartialOrder: PartialOrder[BigInt] = new PartialOrder[BigInt] {
    def partialComparison(x: BigInt, y: BigInt): Option[Int] = {
      if(x < y)
        Some(-1)
      else if (x == y)
        Some(0)
      else
        Some(1)
    }
  }

  sealed abstract class Nat {
    /*def ==(m: Nat): Boolean = {
      (this, m) match {
        case (Succ(ts), Succ(ms)) => ts == ms
        case (Zero, Zero) => true
        case _ => false
      }
    }*/

    def <(m: Nat): Boolean = {
      (this, m) match {
        case (Succ(ts), Succ(ms)) => ts < ms
        case (Zero, Succ(ms)) => true
        case _ => false
      }
    }

    def <=(m: Nat): Boolean = {
      this == m || this < m
    }

    def compare(m: Nat): Int = {
      if(this < m)
        -1
      else if (this == m)
        0
      else
        1
    }
  }

  final case object Zero extends Nat
  final case class Succ(prev: Nat) extends Nat

  def law_antisymmetric_Nat(x: Nat, y: Nat): Boolean = {
    ((x <= y && y <= x) ==> (x == y)) because {
      (x, y) match {
        case (Zero, Zero) => true
        case (Zero, Succ(_)) => true
        case (Succ(n), Zero) => true
        case (Succ(n), Succ(m)) =>
          assert(law_antisymmetric_Nat(n, m))
          check((x <= y && y <= x) ==> (x == y))
      }
    }
  }.holds

  def natPartialOrder: PartialOrder[Nat] = new PartialOrder[Nat] {
    def partialComparison(x: Nat, y: Nat): Option[Int] = {
      Some(x compare y)
    }

    override def law_antisymmetric(x: Nat, y: Nat) = {
      super.law_antisymmetric(x, y) because law_antisymmetric_Nat(x, y)
    }
  }
}
