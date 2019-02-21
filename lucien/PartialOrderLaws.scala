import stainless.lang._
import stainless.proof._
import stainless.annotation._

object PartialOrderLaws {

  // Laws: reflexive, antisymmetric, transitive
  abstract class PartialOrder[A] extends EqualityLaws.Equality[A] {

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
    def law_reflexive_partial_order(x: A) = {
      lteqv(x, x)
    }

    @law
    def law_antisymmetric_partial_order(x: A, y: A) = {
      (lteqv(x, y) && lteqv(y, x)) ==> eqv(x, y)
    }

    @law
    def law_transitive_partial_order(x: A, y: A, z: A) = {
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

  def law_antisymmetric_Nat(x: Nat, y: Nat): Boolean = {
    ((x <= y && y <= x) ==> (x == y)) because {
      (x, y) match {
        case (Zero, Zero) => true
        case (Zero, Succ(_)) => true
        case (Succ(_), Zero) => true
        case (Succ(n), Succ(m)) =>
          assert(law_antisymmetric_Nat(n, m))
          check((x <= y && y <= x) ==> (x == y))
      }
    }
  }.holds

  // Exhaustively write every case? or should it be concise?
  def law_transitive_Nat(x: Nat, y: Nat, z: Nat): Boolean = {
    ((x <= y && y <= z) ==> (x <= z)) because {
      (x, y, z) match {
        case (Zero, Zero, Zero) => true
        case (Succ(_), Zero, Zero) => true
        case (Zero, Succ(_), Zero) => true
        case (Zero, Zero, Succ(_)) => true
        case (Succ(_), Succ(_), Zero) => true
        case (Succ(_), Zero, Succ(_)) => true
        case (Zero, Succ(_), Succ(_)) => true
        case (Succ(n), Succ(m), Succ(l)) =>
          assert(law_transitive_Nat(n, m, l))
          check((x <= y && y <= z) ==> (x <= z))
      }
    }
  }.holds

  // Weird, why didn't it work? (timeout)
  @induct
  def law_reflexive_equality_Nat(x: Nat): Boolean = {
    x == x
  }.holds

  def natPartialOrder: PartialOrder[Nat] = new PartialOrder[Nat] {
    def partialComparison(x: Nat, y: Nat): Option[Int] = {
      Some(x compare y)
    }

    override def law_antisymmetric_partial_order(x: Nat, y: Nat) = {
      super.law_antisymmetric_partial_order(x, y) because law_antisymmetric_Nat(x, y)
    }

    override def law_transitive_partial_order(x: Nat, y: Nat, z: Nat) = {
      super.law_transitive_partial_order(x, y, z) because law_transitive_Nat(x, y, z)
    }

    override def law_reflexive_equality(x: Nat) = {
      super.law_reflexive_equality(x) because law_reflexive_equality_Nat(x)
    }
  }
}
