import stainless.lang._
import stainless.proof._
import stainless.annotation._

object SemiGroupLaws{
  abstract class SemiGroup[A]{
    def append(x: A, y: A): A

    @law
    def law_associativity(x: A, y: A, z: A) = {
      append(x, append(y, z)) == append(append(x, y), z)
    }
  }
}
