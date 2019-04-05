import stainless.lang._
import stainless.proof._
import stainless.annotation._

object WordCount {
  import MonoidLaws._
  import MonoidMap._

  case class WC(words: MMap)

  case class WordCountMonoid() extends Monoid[WC] {
    def empty: WC = WC(MMap.empty)

    def append(x: WC, y: WC): WC = WC(x.words.merge(y.words))

    @library
    override def law_leftIdentity(x: WC) = {
      super.law_leftIdentity(x) because true
    }

    @library
    override def law_rightIdentity(x: WC) = {
      super.law_rightIdentity(x) because true
    }

    @library
    override def law_associativity(x: WC, y: WC, z: WC) = {
      super.law_associativity(x, y, z) because true
    }
  }
}
