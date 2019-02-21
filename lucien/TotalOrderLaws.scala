import stainless.lang._
import stainless.proof._
import stainless.annotation._

object TotalOrderLaws {

  // Laws: connex, antisymmetric, transitive
  abstract class TotalOrder[A] /*extends PartialOrder[A]*/ {

    /**
     * Result of comparison of x and y
     * The Int sign of the retrurned value is
     * - negative iff 'x < y'
     * - zero     iff 'x == y'
     * - positive iff 'x > y'
     */
    def compare(x: A, y: A): Int

    final def partialComparison(x: A, y: A): Option[Int] = {
      Some(compare(x, y))
    }

    // Antisymmetric and transitive laws are already ensured by PartialOrder since partialComparison is defined by compare
    @law
    def law_connex(x: A, y: A) = {
      lteqv(x, y) || lteqv(y, x)
    }
  }

  def bigIntPartialOrder: TotalOrder[BigInt] = new TotalOrder[BigInt] {
    def compare(x: BigInt, y: BigInt): Int = {
      if(x < y)
        -1
      else if (x == y)
        0
      else
        1
    }
  }

  sealed abstract class Nat {
    def ==(m: Nat): Boolean = {
      (this, m) match {
        case (Succ(ts), Succ(ms)) => ts == ms
        case (Zero, Zero) => true
        case _ => false
      }
    }

    def <(m: Nat): Boolean = {
      (this, m) match {
        case (Succ(ts), Succ(ms)) => ts < ms
        case (Zero, Succ(ms)) => true
        case _ => false
      }
    }

    def compare(m: Nat): Int = {
      if(this < m)
        -1
      else if (this == m)
        0
      else
        1
    }
  }

  final case object Zero extends Nat
  final case class Succ(prev: Nat) extends Nat

  def natTotalOrder: TotalOrder[Nat] = new TotalOrder[Nat] {
    def compare(x: Nat, y: Nat): Int = {
      x compare y
    }
  }
}
