import stainless.lang._
import stainless.proof._
import stainless.annotation._

object MonoidOption {
  import MonoidLaws.Monoid

  implicit def optionMonoid[T](implicit ev: Monoid[T]): Monoid[Option[T]] = OptionMonoid(ev)

  case class OptionMonoid[T](monoid: Monoid[T]) extends Monoid[Option[T]] {
    def empty: Option[T] = None[T]()
    def append(x: Option[T], y: Option[T]): Option[T] = {
      (x, y) match {
        case (Some(a), Some(b)) => Some(monoid.append(a, b))
        case (Some(a), None()) => Some(a)
        case (None(), Some(b)) => Some(b)
        case (None(), None()) => None[T]()
      }
    }

    override def law_associativity(x: Option[T], y: Option[T], z: Option[T]): Boolean = {
      super.law_associativity(x, y, z) because {
        (x, y, z) match {
          case(Some(a), Some(b), Some(c)) =>
            check(monoid.law_associativity(a, b, c))
          case _ => true
        }
      }
    }
  }

}
