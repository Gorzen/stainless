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
    def partialComparison(x: A, y: A): Option[Int] = {
      if(lteqv(x, y)) {
        if(lteqv(y, x))
          Some(0)
        else
          Some(-1)
      } else {
        if(lteqv(y, x))
          Some(1)
        else
          None[Int]()
      }
    }

    def lteqv(x: A, y: A): Boolean

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
}
