import stainless.lang._
import stainless.proof._
import stainless.annotation._

object TotalOrderBigInt {
  def bigIntPartialOrder: TotalOrderLaws.TotalOrder[BigInt] = new TotalOrderLaws.TotalOrder[BigInt] {
    def compare(x: BigInt, y: BigInt): Int = {
      if(x < y)
        -1
      else if (x == y)
        0
      else
        1
    }
  }
}
