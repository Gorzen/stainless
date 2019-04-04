import stainless.lang._
import stainless.proof._
import stainless.annotation._

object MonoidMap {
  import MonoidLaws._

  case class MMap[A, B](f: A => B) {

    def apply(k: A): B = {
      f(k)
    }

    def updated(k: A, v: B): MMap[A, B] = {
      MMap((x: A) => if (x == k) v else f(x))
    }

    def merge(that: MMap[A, B])(implicit M: Monoid[B]): MMap[A, B] = {
      MMap[A, B] { i =>
        val x = this.apply(i)
        val y = that.apply(i)
        M.append(x, y)
      }
    }
  }

  object MMap {
    def empty[A, B](implicit M: Monoid[B]): MMap[A, B] = MMap((k: A) => M.empty)
  }
}
