import stainless.lang._
import stainless.proof._
import stainless.annotation._
import stainless.lang.StaticChecks._

object MonoidMap {
  import MonoidLaws._

  case class monoidSum() extends Monoid[BigInt] {
    def empty: BigInt = BigInt(0)
    def append(x: BigInt, y: BigInt): BigInt = x + y
  }

/*
  case class MMap2(map: scala.collection.immutable.Map) {

    @extern @pure
    def apply(k: String): BigInt = {
      map(k)
    }

    @extern @pure
    def updated(k: String, v: BigInt): MMap = {
      MMap(map.updated(k, v)))
    }

    @extern @pure
    def merge(that: MMap): MMap = {
      val x0 = this.map.theMap.withDefaultValue(0)
      val y0 = that.map.theMap.withDefaultValue(0)
      val keys = this.map.theMap.keys.toSet.union(that.map.theMap.keys.toSet)
      MMap(Map(keys.map(k => (k -> (x0(k) + y0(k)))).toMap
    }
  }

  object MMap {
    @extern @pure
    def empty: MMap = MMap(scala.collection.immutable.Map.empty)

    @extern @pure
    def singleton(x: String) = empty.updated(x, BigInt(1))
  }

  case class MMap2(f: String => BigInt) {

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

  object MMap2 {
    def empty: MMap2 = MMap2((k: String) => monoidSum().empty)
    def singleton(x: String) = empty.updated(x, BigInt(1))
  }*/
}
