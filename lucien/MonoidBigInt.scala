import stainless.lang._
import stainless.proof._
import stainless.annotation._

object MonoidBigInt {
  import MonoidLaws._

  def bigIntSumMonoid: Monoid[Sum[BigInt]] = new Monoid[Sum[BigInt]] {
    def empty: Sum[BigInt] = Sum(0)
    def append(x: Sum[BigInt], y: Sum[BigInt]): Sum[BigInt] = Sum(x.toSum + y.toSum)
  }

  def bigIntProductMonoid: Monoid[Product[BigInt]] = new Monoid[Product[BigInt]] {
    def empty: Product[BigInt] = Product(1)
    def append(x: Product[BigInt], y: Product[BigInt]): Product[BigInt] = Product(x.toProduct * y.toProduct)
  }
}
