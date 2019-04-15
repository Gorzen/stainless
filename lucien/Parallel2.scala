import stainless.lang._
import stainless.lang.StaticChecks._
import stainless.proof._
import stainless.annotation._

import java.util.concurrent._
import scala.util.DynamicVariable

@ignore
object Parallel2 {

  val forkJoinPool = new ForkJoinPool(16)

  abstract class TaskScheduler {
    def schedule[T](body: => T): ForkJoinTask[T]
    def parallel[A, B](taskA: => A, taskB: => B): (A, B) = {
      val right = task {
        taskB
      }
      val left = taskA
      (left, right.join())
    }
  }

  case class DefaultTaskScheduler() extends TaskScheduler {
    def schedule[T](body: => T): ForkJoinTask[T] = {
      val t = new RecursiveTask[T] {
        def compute = body
      }
      Thread.currentThread match {
        case wt: ForkJoinWorkerThread =>
          t.fork()
        case _ =>
          forkJoinPool.execute(t)
      }
      t
    }
  }

  val scheduler = new DynamicVariable[TaskScheduler](DefaultTaskScheduler())

  def task[T](body: => T): ForkJoinTask[T] = {
    scheduler.value.schedule(body)
  }

  def parallel_ignore[A, B](taskA: => A, taskB: => B): (A, B) = {
    scheduler.value.parallel(taskA, taskB)
  } ensuring { res =>
    taskA == res._1 && taskB == res._2
  }
}
