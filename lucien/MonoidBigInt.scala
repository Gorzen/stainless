import stainless.lang._
import stainless.proof._
import stainless.annotation._

object MonoidBigInt {
  import MonoidLaws._

  implicit def bigIntSumMonoid: Monoid[Sum[BigInt]] = BigIntSumMonoid()

  case class BigIntSumMonoid() extends Monoid[Sum[BigInt]] {
    def empty: Sum[BigInt] = Sum(BigInt(0))
    def append(x: Sum[BigInt], y: Sum[BigInt]): Sum[BigInt] = Sum(x.toSum + y.toSum)
  }

  implicit def bigIntProductMonoid: Monoid[Product[BigInt]] = BigIntProductMonoid()

  case class BigIntProductMonoid() extends Monoid[Product[BigInt]] {
    def empty: Product[BigInt] = Product(BigInt(1))
    def append(x: Product[BigInt], y: Product[BigInt]): Product[BigInt] = Product(x.toProduct * y.toProduct)
  }
}
