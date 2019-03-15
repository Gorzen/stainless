import stainless.lang._
import stainless.proof._
import stainless.annotation._

object TotalOrderBigInt {
  import TotalOrderLaws._

  implicit def bigIntPartialOrder: TotalOrder[BigInt] = BigIntPartialOrder()

  case class BigIntPartialOrder() extends TotalOrder[BigInt] {
    def eqv(x: BigInt, y: BigInt): Boolean = {
      x == y
    }

    def lteqv(x: BigInt, y: BigInt): Boolean = {
      x <= y
    }
  }
}
