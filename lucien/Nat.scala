sealed abstract class Nat {
  def <(m: Nat): Boolean = {
    (this, m) match {
      case (Succ(ts), Succ(ms)) => ts < ms
      case (Zero, Succ(ms)) => true
      case _ => false
    }
  }

  def <=(m: Nat): Boolean = {
    this == m || this < m
  }
}

final case object Zero extends Nat
final case class Succ(prev: Nat) extends Nat
