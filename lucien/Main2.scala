import scala.io.Source

object Main2 {
  def main2(args: Array[String]): Unit = {
    assert(args.length >= 1 && args.size <= 3)

    val parallel = if((args.length >= 2 && args(1) == "p") || (args.length >= 3 && args(2) == "p")) true else false
    val merge = if((args.length >= 2 && args(1) == "m") || (args.length >= 3 && args(2) == "m")) true else false

    val words: List[String] = Source.fromFile(args(0)).getLines.flatMap(line => line.split(' ')).toList

    println("Starting word count for file '" + args(0) + "' in " + (if(parallel) "parallel" else "sequential") + " mode with " + (if(merge) "merge" else "insertion") + " sort.")

    val list: List[Map[String, BigInt]] = words.map(s => (Map(s -> BigInt(1))))

    val wc: Map[String, BigInt] = if(parallel){
      list.foldLeft(Map.empty[String, BigInt])((x, y) => x ++ y.map{ case (s, i) => (s, i + x.getOrElse(s, BigInt(0)))})
    }else{
      list.foldLeft(Map.empty[String, BigInt])((x, y) => x ++ y.map{ case (s, i) => (s, i + x.getOrElse(s, BigInt(0)))})
    }

    val wordCount: List[(String, BigInt)] = words.distinct.map(s => (s, wc.get(s).get))

    val sortedWordCount = if(merge){
      wordCount.sorted((x: (String, BigInt), y: (String, BigInt)) => -x._2.compareTo(y._2))
    }else{
      wordCount.sorted((x: (String, BigInt), y: (String, BigInt)) => -x._2.compareTo(y._2))
    }

    sortedWordCount.foreach(x => println(x._1 + " => " + x._2))
  }
}
