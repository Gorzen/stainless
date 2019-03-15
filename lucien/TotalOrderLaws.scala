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
    def compare(x: A, y: A): Int = {
      if(lteqv(x, y))
        if(lteqv(y, x))
          0
        else
          -1
      else
        1
    }

    override def partialComparison(x: A, y: A): Option[Int] = {
      Some(compare(x, y))
    }

    // Antisymmetric and transitive laws are already ensured by PartialOrder since partialComparison is defined by compare
    @law
    def law_connex_total_order(x: A, y: A) = {
      lteqv(x, y) || lteqv(y, x)
    }
  }
}
