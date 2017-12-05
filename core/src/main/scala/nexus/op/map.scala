package nexus.op

import nexus._
import nexus.algebra._
import scala.annotation._

/**
 * Applies an arbitrary differentiable function to all elements in a specific tensor.
 * @note This operation might be slow! Use with caution.
 * @author Tongfei Chen
 * @since 0.1.0
 */
case class Map[X](parameter: DOp1[X, X]) extends ParaPolyDOp1[DOp1[X, X]] {

  type POp[P, X, Y] = Map.POp[P, X, Y]

}

object Map {
  @implicitNotFound("Cannot apply Map[${P}] to ${X}.")
  trait POp[P, X, Y] extends (P => DOp1[X, Y])

  object POp {

    implicit def tensor[T[_ <: $$], R, A <: $$](implicit T: IsTypedRealTensor[T, R]) = new POp[DOp1[R, R], T[A], T[A]] {
      def apply(f: DOp1[R, R]) = new DOp1[T[A], T[A]] {
        import T._
        def name = s"Map[${f.name}]"
        def tag = T.ground[A]
        def forward(x: T[A]) = map(x)(f.forward)
        def backward(dy: T[A], y: T[A], x: T[A]) = map3(dy, y, x)(f.backward)
      }
    }

  }

}
