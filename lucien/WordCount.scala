import stainless.lang._
import stainless.proof._
import stainless.annotation._

object WordCount {
  import MonoidLaws._
  import MonoidMap._

  // How to use stainless's Map?
  // It doesn't have map and it doesn't have postconditions
  case class WC(words: MMap[String, BigInt])

  case class WordCountMonoid(monoid: Monoid[BigInt]) extends Monoid[WC] {
    //implicit val monoid = M

    def empty: WC = WC(MMap.empty(monoid))

    def append(x: WC, y: WC): WC = WC(x.words.merge(y.words)(monoid))

    /*def lemma_leftIdentity(x: WC): Boolean = {
      (append(empty, x) == x) because {
        assert(append(empty, x) == append(WC(MMap.empty), x))
        assert(append(WC(MMap.empty), x) == WC(MMap.empty.merge(x.words)))
        assert(WC(MMap.empty.merge(x.words)) == WC(MMap{ k =>
          M.append(MMap.empty.apply(k), x.words.apply(k))
        }))
        assert(WC(MMap{ k =>
          M.append(MMap.empty.apply(k), x.words.apply(k))
        }) == WC(MMap{ k =>
          M.append(M.empty, x.words.apply(k))
        }))
        assert(WC(MMap{ k =>
          M.append(M.empty, x.words.apply(k))
        }) == WC(MMap{ k =>
          M.law_leftIdentity(x.words.apply(k))
          M.append(M.empty, x.words.apply(k))
        }))
        assert(WC(MMap{ k =>
          M.law_leftIdentity(x.words.apply(k))
          M.append(M.empty, x.words.apply(k))
        }) == WC(MMap{ k =>
          x.words.apply(k)
        }))
        assert(WC(MMap{ k =>
          x.words.apply(k)
        }) == WC(x.words))
        assert(WC(x.words) == x)
        check(append(empty, x) == x)
      }
    }*/

    def lemma_rightIdentity(x: WC): Boolean = {
      (append(empty, x) == x) because {
        // fairly easy, y.words.map will still be empty map -> you get x.words
        true
      }
    }

    def lemma_associativity(x: WC, y: WC, z: WC): Boolean = {
      (append(x, append(y, z)) == append(append(x, y), z)) because {
        // Doesn't look easy...
        true
      }
    }
  }
}
