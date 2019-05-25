import stainless.lang._
import stainless.proof._
import stainless.annotation._
import stainless.collection._

import scala.io.Source
import java.io._

object Main2 {
  import FoldMapConcRope.{fold => foldC}
  import FoldMapConcRope.foldSequential
  import FoldMapList.{fold => foldL}
  import MergeSort.{sort => mSort}
  import InsertionSort.{sort => iSort}
  import WordCount._
  import TotalOrderWC._
  import ConcRope._
  import ConcRope.Conc.{fromListReversed => fromList}

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

    var timeAnalysis = ""

    // Scala list to stainless list?
    val wordsStd = Source.fromFile(args(0)).getLines

    var time_fold: Long = 0
    var time_conv: Long = 0

    var uniqueWordsStd: scala.collection.immutable.Set[String] = scala.collection.immutable.Set()

    val scalaListOfWC = wordsStd.zipWithIndex.map(x => {
      val line = x._1.split(' ').map(_.trim).filterNot(_.isEmpty).toList
      uniqueWordsStd = uniqueWordsStd ++ line.toSet

      if (x._2 % 10000 == 0){
        println("Doing line " + x._2)
      }

      val words = line.map(s => WC(Bag((s, BigInt(1)))))

      var wordsV: List[WC] = Nil[WC]()
      for (e <- words){
        wordsV = Cons(e, wordsV)
      }

      val list: List[WC] = wordsV

      val wc: WC = if(parallel) {
        val t1 = System.nanoTime
        val c = fromList(list)
        time_conv += System.nanoTime - t1

        val t2 = System.nanoTime
        val v = foldC(c)(WordCountMonoid())
        time_fold += System.nanoTime - t2
        v
      } else if(sequential) {
        val t1 = System.nanoTime
        val c = fromList(list)
        time_conv += System.nanoTime - t1

        val t2 = System.nanoTime
        val v = foldSequential(c)(WordCountMonoid())
        time_fold += System.nanoTime - t2
        v
      } else {
        val t2 = System.nanoTime
        val v = foldL(list)(WordCountMonoid())
        time_fold += System.nanoTime - t2
        v
      }
      wc
    })

    println("Finish reading file, start fold")


    var wordsV: List[WC] = Nil[WC]()
    for (e <- scalaListOfWC){
      wordsV = Cons(e, wordsV)
    }

    val list: List[WC] = wordsV

    val wc: WC = if(parallel) {
      val t1 = System.nanoTime
      val c = fromList(list)
      time_conv += System.nanoTime - t1

      val t2 = System.nanoTime
      val v = foldC(c)(WordCountMonoid())
      time_fold += System.nanoTime - t2
      v
    } else if(sequential) {
      val t1 = System.nanoTime
      val c = fromList(list)
      time_conv += System.nanoTime - t1

      val t2 = System.nanoTime
      val v = foldSequential(c)(WordCountMonoid())
      time_fold += System.nanoTime - t2
      v
    } else {
      val t2 = System.nanoTime
      val v = foldL(list)(WordCountMonoid())
      time_fold += System.nanoTime - t2
      v
    }

    if(parallel || sequential)
      timeAnalysis += "All conversions took " + time_conv / 1e9 + " seconds\n"
    timeAnalysis += "Folding took " + time_fold / 1e9 + " seconds, start retrieving list\n"

    var uniqueWordsV: List[String] = Nil[String]()
    for(e <- uniqueWordsStd){
      uniqueWordsV = Cons(e, uniqueWordsV)
    }

    // This list is reversed, but it doesn't matter if we count words
    val uniqueWords: List[String] = uniqueWordsV

    val (wordCount: List[(String, BigInt)], s) = time(uniqueWords
      .foldLeft(List[(String, BigInt)]()) { case (acc, w) =>
        Cons((w, wc.words(w)), acc)
      }, "Time to retrieve list:")

    timeAnalysis += s

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

    val output = sortedWordCount.map(x => x._1 + " => " + x._2)

    val bw = new BufferedWriter(new FileWriter(new File(args(1))))

    printOutput(output, bw)

    bw.close()

    println("Finished running word count for file '" + args(0) + "' in " + (if(parallel) "parallel on ConcRope" else if(sequential) "sequential on ConcRope" else "sequential on List") + " with " + (if(noSort) "no" else if(merge) "merge" else "insertion") + " sort.")
    println("Time analysis:")
    println(timeAnalysis)
  }

  @extern
  def printOutput(output: List[String], bw: BufferedWriter): Unit = {
    output match {
      case Nil() => return
      case x :: xs => bw.write(x); bw.newLine(); printOutput(xs, bw)
    }
  }

  @extern
  def time[A](f: => A, message: String): (A, String) = {
    val t = System.nanoTime
    val v = f
    val str: String = message + " " + (System.nanoTime - t) / 1e9 + " seconds\n"
    (v, str)
  }
}
