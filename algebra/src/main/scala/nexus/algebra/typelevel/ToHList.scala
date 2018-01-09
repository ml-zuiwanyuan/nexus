package nexus.algebra.typelevel

import shapeless._

/**
 * Typelevel function that transforms tuples / single types to HLists.
 * @author Tongfei Chen
 */
trait ToHList[A] {

  type Out <: HList

  def apply(x: A): Out
  def inverse(x: Out): A

}

object ToHList extends ToHListLowPriority {

  // Unit (N == 0)
  implicit val unit: ToHList.Aux[Unit, HNil] =
    new ToHList[Unit] {
      type Out = HNil
      def apply(x: Unit) = HNil
      def inverse(x: HNil) = ()
    }

  // Single type (N == 1)
  implicit def single[A: Label]: ToHList.Aux[A, A :: HNil] =
    new ToHList[A] {
      type Out = A :: HNil
      def apply(x: A) = x :: HNil
      def inverse(x: A :: HNil) = x.head
    }

}

trait ToHListLowPriority {

  def apply[A](implicit t: ToHList[A]): Aux[A, t.Out] = t
  type Aux[A, B <: HList] = ToHList[A] { type Out = B }

  // Tuple2+ (N >= 2)
  implicit def generic[A, B <: HList](implicit g: Generic.Aux[A, B], n: A <:!< HList): ToHList.Aux[A, B] =
    new ToHList[A] {
      type Out = B
      def apply(x: A) = g.to(x)
      def inverse(x: B) = g.from(x)
    }

}
