import stainless.lang._
import stainless.proof._
import stainless.annotation._

object MonoidLaws {
  abstract class Monoid[A] extends SemiGroupLaws.SemiGroup[A]{
    def empty: A

    @law
    def law_leftIdentity(x: A) = {
      append(empty, x) == x
    }

    @law
    def law_rightIdentity(x: A) = {
      append(x, empty) == x
    }

    @law
    override def law_associativity(x: A, y: A, z: A) = {
      true
    }
  }

  case class Sum[A](x: A) {
    def toSum: A = x
  }

  case class Product[A](x: A) {
    def toProduct: A = x
  }
}
