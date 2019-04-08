import stainless.lang._
import stainless.lang.StaticChecks._
import stainless.proof._
import stainless.annotation._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.language.reflectiveCalls

object Parallel {
  @extern
  def parallel[A, B](taskA: => A, taskB: => B): (A, B) = {
    val ta = Future { taskA }
    val tb = taskB

    (Await.result(ta, Duration.Inf), tb)
  } ensuring { res =>
    res._1 == taskA &&
    res._2 == taskB
  }
}
