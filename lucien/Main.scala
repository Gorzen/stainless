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
  import MonoidMap._
  import WordCount._
  import TotalOrderWC._

  @extern
  def main(args: Array[String]): Unit = {
    assert(args.length >= 1 && args.size <= 3)

    val parallel = if((args.length >= 2 && args(1) == "p") || (args.length >= 3 && args(2) == "p")) true else false
    val merge = if((args.length >= 2 && args(1) == "m") || (args.length >= 3 && args(2) == "m")) true else false

    // Scala list to stainless list?
    val wordsStd = Source.fromFile(args(0)).getLines.flatMap(line => line.split(' ')).toList

    var wordsV: List[String] = Nil[String]()
    for (e <- wordsStd){
      wordsV = Cons(e, wordsV)
    }

    val words: List[String] = wordsV.reverse

    println("Starting word count for file '" + args(0) + "' in " + (if(parallel) "parallel" else "sequential") + " mode with " + (if(merge) "merge" else "insertion") + " sort.")

    val list: List[WC] = words.map(s => WC(MMap.singleton(s)))

    val wc: WC = if(parallel){
      foldC(concRopeFromList(list))(WordCountMonoid())
    }else{
      foldL(list)(WordCountMonoid())
    }

    val wordCount: List[(String, BigInt)] = words.unique.map(s => (s, wc.words.apply(s)))

    val sortedWordCount = if(merge){
      mSort(wordCount)
    }else{
      iSort(wordCount)
    }

    print(List.mkString(sortedWordCount, "\n", (x: (String, BigInt)) => x._1 + " => " + x._2))
  }
}
