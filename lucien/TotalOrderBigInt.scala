import stainless.lang._
import stainless.proof._
import stainless.annotation._

object TotalOrderBigInt {
  import TotalOrderLaws._

  implicit def bigIntTotalOrder: TotalOrder[BigInt] = BigIntTotalOrder()

  case class BigIntTotalOrder() extends TotalOrder[BigInt] {
    def eqv(x: BigInt, y: BigInt): Boolean = {
      x == y
    }

    def lteqv(x: BigInt, y: BigInt): Boolean = {
      x <= y
    }
  }
}
