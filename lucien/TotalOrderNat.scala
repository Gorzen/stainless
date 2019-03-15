import stainless.lang._
import stainless.proof._
import stainless.annotation._

object TotalOrderNat {
  import TotalOrderLaws._

  def law_antisymmetric_Nat(x: Nat, y: Nat): Boolean = {
    ((x <= y && y <= x) ==> (x == y)) because {
      (x, y) match {
        case (Succ(n), Succ(m)) =>
          assert(law_antisymmetric_Nat(n, m))
          check((x <= y && y <= x) ==> (x == y))
        case _ => true
      }
    }
  }.holds

  def law_transitive_Nat(x: Nat, y: Nat, z: Nat): Boolean = {
    ((x <= y && y <= z) ==> (x <= z)) because {
      (x, y, z) match {
        case (Succ(n), Succ(m), Succ(l)) =>
          assert(law_transitive_Nat(n, m, l))
          check((x <= y && y <= z) ==> (x <= z))
        case _ => true
      }
    }
  }.holds

  def law_connex_Nat(x: Nat, y: Nat): Boolean = {
    (x <= y || y <= x) because {
      (x, y) match {
        case (Succ(n), Succ(m)) =>
          assert(law_connex_Nat(n, m))
          check(x <= y || y <= x)
        case _ => true
      }
    }
  }.holds

  implicit def natTotalOrder: TotalOrder[Nat] = NatTotalOrder()

  case class NatTotalOrder() extends TotalOrder[Nat] {
    def eqv(x: Nat, y: Nat): Boolean = {
      x == y
    }

    def lteqv(x: Nat, y: Nat): Boolean = {
      x <= y
    }

    override def law_connex_total_order(x: Nat, y: Nat): Boolean = {
      super.law_connex_total_order(x, y) because law_connex_Nat(x, y)
    }

    override def law_transitive_partial_order(x: Nat, y: Nat, z: Nat): Boolean = {
      super.law_transitive_partial_order(x, y, z) because law_transitive_Nat(x, y, z)
    }

    override def law_antisymmetric_partial_order(x: Nat, y: Nat): Boolean = {
      super.law_antisymmetric_partial_order(x, y) because law_antisymmetric_Nat(x, y)
    }
  }
}
