import stainless.lang._
import stainless.proof._
import stainless.annotation._

object TotalOrderLaws {

  // Laws: connex, antisymmetric, transitive
  abstract class TotalOrder[A] extends PartialOrderLaws.PartialOrder[A] {

    /**
     * Result of comparison of x and y
     * The Int sign of the retrurned value is
     * - negative iff 'x < y'
     * - zero     iff 'x == y'
     * - positive iff 'x > y'
     */
    def compare(x: A, y: A): Int

    final def partialComparison(x: A, y: A): Option[Int] = {
      Some(compare(x, y))
    }

    // Antisymmetric and transitive laws are already ensured by PartialOrder since partialComparison is defined by compare
    @law
    def law_connex_total_order(x: A, y: A) = {
      lteqv(x, y) || lteqv(y, x)
    }
  }

  def bigIntPartialOrder: TotalOrder[BigInt] = new TotalOrder[BigInt] {
    def compare(x: BigInt, y: BigInt): Int = {
      if(x < y)
        -1
      else if (x == y)
        0
      else
        1
    }
  }

  def law_connex_Nat(x: Nat, y: Nat): Boolean = {
    (x <= y || y <= x) because {
      (x, y) match {
        case (Zero, Zero) => true
        case (Succ(_), Zero) => true
        case (Zero, Succ(_)) => true
        case (Succ(n), Succ(m)) =>
          assert(law_connex_Nat(n, m))
          check(x <= y || y <= x)
      }
    }
  }.holds

  def natTotalOrder: TotalOrder[Nat] = new TotalOrder[Nat] {
    def compare(x: Nat, y: Nat): Int = {
      x compare y
    }

    override def law_connex_total_order(x: Nat, y: Nat): Boolean = {
      super.law_connex_total_order(x, y) because law_connex_Nat(x, y)
    }

    // I have to recheck them, so I use what I wrote in PartialOrderLaws
    override def law_transitive_partial_order(x: Nat, y: Nat, z: Nat): Boolean = {
      super.law_transitive_partial_order(x, y, z) because PartialOrderLaws.law_transitive_Nat(x, y, z)
    }

    override def law_reflexive_equality(x: Nat): Boolean = {
      super.law_reflexive_equality(x) because PartialOrderLaws.law_reflexive_equality_Nat(x)
    }

    override def law_antisymmetric_partial_order(x: Nat, y: Nat): Boolean = {
      super.law_antisymmetric_partial_order(x, y) because PartialOrderLaws.law_antisymmetric_Nat(x, y)
    }
  }
}
