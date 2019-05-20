import stainless.lang._
import stainless.proof._
import stainless.annotation._
import stainless.collection._

import scala.io.Source
import java.io._

object Main2 {
  import FoldMapConcRope.{fold => foldC}
  import FoldMapConcRope.concRopeFromList
  import FoldMapConcRope.foldSequential
  import FoldMapList.{fold => foldL}
  import MergeSort.{sort => mSort}
  import InsertionSort.{sort => iSort}
  import WordCount._
  import TotalOrderWC._
  import ConcRope._

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

    val t = System.nanoTime

    val scalaListOfWC = wordsStd.zipWithIndex.map(x => {
      val line = x._1

      if (x._2 % 10000 == 0){
        println("Doing line " + x._2)
      }

      val words = line.split(' ').map(s => WC(Bag((s, BigInt(1)))))

      var wordsV: List[WC] = Nil[WC]()
      for (e <- words){
        wordsV = Cons(e, wordsV)
      }

      val list: List[WC] = wordsV

      val wc: WC = if(parallel) {
        val c = concRopeFromList(list)

        val v = foldC(c)(WordCountMonoid())
        v
      } else if(sequential) {
        val c = concRopeFromList(list)

        val v = foldSequential(c)(WordCountMonoid())
        v
      } else {
        val v = foldL(list)(WordCountMonoid())
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
      val c = concRopeFromList(list)

      val v = foldC(c)(WordCountMonoid())
      v
    } else if(sequential) {
      val c = concRopeFromList(list)

      val v = foldSequential(c)(WordCountMonoid())
      v
    } else {
      val v = foldL(list)(WordCountMonoid())
      v
    }

    timeAnalysis += "Finish fold in " + (System.nanoTime - t) / 1e9 + " seconds, start retrieving list\n"

    val scalaWords = wc.words.theMap.toList

    var vv: List[(String, BigInt)] = Nil[(String, BigInt)]()
    for(e <- scalaWords){
      vv = Cons(e, vv)
    }

    val wordCount: List[(String, BigInt)] = vv

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
