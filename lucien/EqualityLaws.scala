import stainless.lang._
import stainless.proof._
import stainless.annotation._

object EqualityLaws {

  /**
   * Equality is an equivalence relation, so it must be:
   * - reflexive
   * - symmetric
   * - transitive
   */
  abstract class Equality[A] {
    def eqv(x: A, y: A): Boolean
    final def neqv(x: A, y: A): Boolean = !eqv(x, y)

    @law
    def law_reflexive_equality(x: A) = {
      eqv(x, x)
    }

    @law
    def law_symmetric_equality(x: A, y: A) = {
      eqv(x, y) == eqv(y, x)
    }

    @law
    def law_transitive_equality(x: A, y: A, z: A) = {
      (eqv(x, y) && eqv(y, z)) ==> eqv(x, z)
    }
  }
}
