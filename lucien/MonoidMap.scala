import stainless.lang._
import stainless.proof._
import stainless.annotation._

object MonoidMap {
  import MonoidLaws._

  case class monoidSum() extends Monoid[BigInt] {
    def empty: BigInt = BigInt(0)
    def append(x: BigInt, y: BigInt): BigInt = x + y
  }

  case class MMap(f: String => BigInt) {

    def apply(k: String): BigInt = {
      f(k)
    }

    def updated(k: String, v: BigInt): MMap = {
      MMap((x: String) => if (x == k) v else f(x))
    }

    def merge(that: MMap): MMap = {
      MMap { i =>
        val x = this.apply(i)
        val y = that.apply(i)
        monoidSum().append(x, y)
      }
    }
  }

  object MMap {
    def empty: MMap = MMap((k: String) => monoidSum().empty)
    def singleton(x: String) = empty.updated(x, BigInt(1))
  }
}
