import stainless.lang._
import stainless.proof._
import stainless.annotation._

object TotalOrderWC {
  import TotalOrderLaws._

  implicit def wcTotalOrder: TotalOrder[(String, BigInt)] = WCTotalOrder()

  case class WCTotalOrder() extends TotalOrder[(String, BigInt)] {
    def eqv(x: (String, BigInt), y: (String, BigInt)): Boolean = {
      x._2 == y._2
    }

    def lteqv(x: (String, BigInt), y: (String, BigInt)): Boolean = {
      x._2 <= y._2
    }
  }
}
