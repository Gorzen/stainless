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
      lteqv(x, y) && lteqv(y, x)
      //partialComparison(x, y) == Some(0)
    }

    // Final or not? Cats says they let you override for performance reasons but how to check user functions?
    // Should define more functions?
    final def lteqv(x: A, y: A): Boolean = {
      partialComparison(x, x) match {
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
      if(lteqv(x, y) && lteqv(y, x))
        eqv(x, y)
      else
        true
    }

    @law
    def law_transitive(x: A, y: A, z: A) = {
      if(lteqv(x, y) && lteqv(y, z))
        lteqv(x, z)
      else
        true
    }
  }

  def bigIntPartialOrder: PartialOrder[BigInt] = new PartialOrder[BigInt] {
    def partialComparison(x: BigInt, y: BigInt): Option[Int] = {
      // Why can I not use x.compare(y)?
      if(x.<(y))
        Some(-1)
      else
        if(x.>(y))
          Some(1)
        else
          Some(0)
    }
  }
}
