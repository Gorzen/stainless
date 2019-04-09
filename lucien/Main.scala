import stainless.lang._
import stainless.proof._
import stainless.annotation._
import stainless.collection._

import scala.io.Source

object Main {
  import FoldMapConcRope.{fold => foldC}
  import FoldMapConcRope.concRopeFromList
  import FoldMapList.{fold => foldL}
  import MergeSort.{sort => mSort}
  import InsertionSort.{sort => iSort}
  import WordCount._
  import TotalOrderWC._

  @extern
  def main(args: Array[String]): Unit = {
    assert(args.length >= 1 && args.size <= 3)

    val parallel = if((args.length >= 2 && args(1) == "p") || (args.length >= 3 && args(2) == "p")) true else false
    val merge = if((args.length >= 2 && args(1) == "m") || (args.length >= 3 && args(2) == "m")) true else false
    val noSort = if((args.length >= 2 && args(1) == "n") || (args.length >= 3 && args(2) == "n")) true else false

    // Scala list to stainless list?
    val wordsStd = Source.fromFile(args(0))
      .getLines
      .flatMap(line => line.split(' '))
      .map(_.trim)
      .filterNot(_.isEmpty)
      .toList

    val uniqueWordsStd = wordsStd.toSet

    val words = wordsStd.map(s => WC(Bag((s, BigInt(1)))))

    var wordsV: List[WC] = Nil[WC]()
    for (e <- words){
      wordsV = Cons(e, wordsV)
    }

    var uniqueWordsV: List[String] = Nil[String]()
    for(e <- uniqueWordsStd){
      uniqueWordsV = Cons(e, uniqueWordsV)
    }

    // This list is reversed, but it doesn't matter if we count words
    //val words: List[String] = wordsV
    var uniqueWords: List[String] = uniqueWordsV

    println("Starting word count for file '" + args(0) + "' in " + (if(parallel) "parallel" else "sequential") + " mode with " + (if(noSort) "no" else if(merge) "merge" else "insertion") + " sort.")

    val list: List[WC] = wordsV

    println("Finish map, start fold")

    val wc: WC = if(parallel){
      foldC(concRopeFromList(list))(WordCountMonoid())
    }else{
      foldL(list)(WordCountMonoid())
    }

    println("Finish fold, start retrieving list")

    val wordCount: List[(String, BigInt)] = uniqueWords
      .foldLeft(List[(String, BigInt)]()) { case (acc, w) =>
        Cons((w, wc.words(w)), acc)
      }

    //.map(s => (s, wc.words.apply(s)))

    println("Finish retrieving list, start sorting")

    val sortedWordCount = if(noSort) {
      wordCount
    } else if(merge) {
      mSort(wordCount)(WCTotalOrder())
    } else {
      iSort(wordCount)(WCTotalOrder())
    }

    println("Finish sorting, start printing")

    print(List.mkString(sortedWordCount, "\n", (x: (String, BigInt)) => x._1 + " => " + x._2))
  }
}
