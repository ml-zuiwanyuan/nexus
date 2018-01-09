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
object Map extends ParamPolyOp1 {

  implicit def tensor[T[_], R, A](implicit T: IsRealTensorH[T, R]) = new F[Op1[R, R], T[A], T[A]] {
    def apply(f: Op1[R, R]) = new Op1[T[A], T[A]] {
      import T._
      def name = s"Map[${f.name}]"
      def tag(tx: Type[T[A]]) = tx
      def differentiable = f.differentiable
      def forward(x: T[A]) = map(x)(f.forward)
      def backward(dy: T[A], y: T[A], x: T[A]) = map3(dy, y, x)(f.backward)
    }
  }

}
