import stainless.lang._
import stainless.proof._
import stainless.annotation._
import stainless.lang.StaticChecks._

object WordCount {
  import MonoidLaws._

  case class WC(words: Bag[String])

  implicit def wordCountMonoid(): Monoid[WC] = WordCountMonoid()

  case class WordCountMonoid() extends Monoid[WC] {
    def empty: WC = WC(Bag.empty[String])

    def append(x: WC, y: WC): WC = {
      //println(x.words.theBag.size)
      WC(x.words ++ y.words)
    }
  }
}
