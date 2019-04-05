import stainless.lang._
import stainless.proof._
import stainless.annotation._
import stainless.collection._

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
    val words: List[String] = List(Source.fromFile(args(0)).getLines.flatMap(line => line.split(' ')))

    println("Starting word count for file '" + args(0) + "' in " + (if(parallel) "parallel" else "sequential") + " mode with " + (if(merge) "merge" else "insertion") + " sort.")

    val list: List[WC] = words.map(WC(s => MMap.singleton(s)))

    val wc: WC = if(parallel){
      foldC(concRopeFromList(list))
    }else{
      foldL(list)
    }

    val wordCount: List[(String, BigInt)] = words.unique.map(s => (s, wc.words.apply(s)))

    val sortedWordCount = if(merge){
      mSort(wordCount)
    }else{
      iSort(wordCount)
    }

    print(List.mkString(sortedWordCount, "\n", (s: String) => s))
  }
}
