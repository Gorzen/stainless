import stainless.lang._
import stainless.proof._
import stainless.annotation._
import stainless.collection._

import scala.io.Source
import java.io._

object Main {
  import FoldMapConcRope.{fold => foldC}
  import FoldMapConcRope.concRopeFromList
  import FoldMapConcRope.foldSequential
  import FoldMapList.{fold => foldL}
  import MergeSort.{sort => mSort}
  import InsertionSort.{sort => iSort}
  import WordCount._
  import TotalOrderWC._

  @extern
  def main(args: Array[String]): Unit = {
    assert(args.length >= 2)

    val parallel = args.drop(2).contains("p")
    val merge = args.drop(2).contains("m")
    val noSort = args.drop(2).contains("n")
    val sequential = args.drop(2).contains("s")

    if(parallel && sequential) {
      println("Contradictory arguments, parallel and sequential")
      System.exit(1)
    }

    if(merge && noSort) {
      println("Contradictory arguments, merge-sort and no-sort")
      System.exit(1)
    }

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
    var uniqueWords: List[String] = uniqueWordsV

    println("Starting word count for file '" + args(0) + "' in " + (if(parallel) "parallel on ConcRope" else if(sequential) "sequential on ConcRope" else "sequential on List") + " with " + (if(noSort) "no" else if(merge) "merge" else "insertion") + " sort.")

    val list: List[WC] = wordsV
    var timeAnalysis: String = ""

    println("Finish reading file, start fold")

    val wc: WC = if(parallel) {
      val (c, s1) = time(concRopeFromList(list), "ConcRope from list:")

      val (v, s2) = time(foldC(c)(WordCountMonoid()), "Parallel fold on ConcRope:")
      timeAnalysis += s1 + s2
      v
    } else if(sequential) {
      val (c, s1) = time(concRopeFromList(list), "ConcRope from list:")

      val (v, s2) = time(foldSequential(c)(WordCountMonoid()), "Sequential fold on ConcRope:")
      timeAnalysis += s1 + s2
      v
    } else {
      val (v, s) = time(foldL(list)(WordCountMonoid()), "Sequential fold on List:")
      timeAnalysis += s
      v
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
      val (v, s) = time(mSort(wordCount)(WCTotalOrder()), "Mege sort on output list:")
      timeAnalysis += s
      v
    } else {
      val (v, s) = time(iSort(wordCount)(WCTotalOrder()), "Insertion sort on output list:")
      timeAnalysis += s
      println(s)
      v
    }

    println("Finish sorting, start writing to output")

    val output = List.mkString(sortedWordCount, "\n", (x: (String, BigInt)) => x._1 + " => " + x._2)

    val bw = new BufferedWriter(new FileWriter(new File(args(1))))
    bw.write(output)
    bw.close()

    println("Finished running word count for file '" + args(0) + "' in " + (if(parallel) "parallel on ConcRope" else if(sequential) "sequential on ConcRope" else "sequential on List") + " with " + (if(noSort) "no" else if(merge) "merge" else "insertion") + " sort.")
    println("Time analysis:")
    println(timeAnalysis)
  }

  @extern
  def time[A](f: => A, message: String): (A, String) = {
    val t = System.nanoTime
    val v = f
    val str: String = message + " " + (System.nanoTime - t) / 1e9 + " seconds\n"
    (v, str)
  }
}
