import stainless.lang._
import stainless.proof._
import stainless.annotation._

object WordCount2 {
  import MonoidLaws._
  import MonoidMap._

  def mapEq[A, B](f: MMap[A, B], g: MMap[A, B]): Boolean = {
    forall((a: A) => f(a) == g(a))
  }

  case class WordCountMonoid() extends Monoid[MMap[String, BigInt]] {
    def empty: MMap[String, BigInt] = MMap.empty
    def append(x: MMap[String, BigInt], y: MMap[String, BigInt]): MMap[String, BigInt] = x.merge(y)

    override def law_leftIdentity(x: MMap[String, BigInt]): Boolean =
      super.law_leftIdentity(x) because lemma_leftIdentity(x)

    def lemma_leftIdentity(x: MMap[String, BigInt]): Boolean = {
      (append(empty, x) == x) because {
        assert(append(empty, x) == append(MMap.empty, x))
        assert(append(MMap.empty, x) == MMap.empty.merge(x))
        /*assert(MMap.empty.merge(x) == MMap{ k: String =>
          M.append(MMap.empty.apply(k), x.apply(k))
        })
        assert(MMap{ k: String =>
          M.append(MMap.empty.apply(k), x.apply(k))
        } == MMap{ k: String =>
          M.append(M.empty, x.apply(k))
        })
        assert(MMap{ k: String =>
          M.append(M.empty, x.apply(k))
        } == MMap{ k: String =>
          M.law_leftIdentity(x.apply(k))
          M.append(M.empty, x.apply(k))
        })
        assert(MMap{ k: String =>
          M.law_leftIdentity(x.apply(k))
          M.append(M.empty, x.apply(k))
        } == MMap{ k: String =>
          x.apply(k)
        })
        assert(MMap{ k: String =>
          x.apply(k)
        } == x)*/
        assert(mapEq(MMap.empty.merge(x), x))
        check(append(empty, x) == x)
      }
    }.holds
  }

  implicit def M: Monoid[BigInt] = new Monoid[BigInt] {
    def empty: BigInt = BigInt(0)
    def append(x: BigInt, y: BigInt): BigInt = x + y
  }
}
