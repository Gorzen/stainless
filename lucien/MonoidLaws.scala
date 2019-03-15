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
  }

  case class Sum[A](toSum: A)

  case class Product[A](toProduct: A)
}
