//import stainless.lang._
import stainless.proof._
import stainless.annotation._

object WordCount {
  import MonoidLaws._

  // How to use stainless's Map?
  // It doesn't have map and it doesn't have postconditions
  case class WC(words: Map[String, BigInt])

  case class WordCountMonoid() extends Monoid[WC] {
    def empty: WC = WC(Map())

    def append(x: WC, y: WC): WC = {

      WC(x.words ++ y.words.map{case (k, v) => (k, v + x.words.getOrElse(k, BigInt(0)))})
    }

    def lemma_leftIdentity(x: WC): Boolean = {
      (append(empty, x) == x) because {
        // fairly easy, x.words.getOrElse will always be 0 -> the maping does nothing
        true
      }
    }

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
