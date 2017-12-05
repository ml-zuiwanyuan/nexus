package nexus

import shapeless.Id

/**
 * Represents an assignment of the form `Expr[X] <- X`.
 * @author Tongfei Chen
 * @since 0.1.0
 */
trait Assignment extends ExprValuePair[Id] {

  override def toString = s"$expr <- $value"

}

object Assignment {

  /** Creates an assignment for an symbolic expression. */
  def apply[X](x: Expr[X], v: X): Assignment = new Assignment {
    type Data = X
    val expr = x
    val value = v
  }

}
