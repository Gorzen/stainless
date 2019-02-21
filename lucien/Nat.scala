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
