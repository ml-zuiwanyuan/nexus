package nexus

import shapeless._

/**
 * Contains types and typeclass definitions for tensor operations.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
package object algebra {

  type AdditiveSemigroup[X] = _root_.algebra.ring.AdditiveSemigroup[X]

  // Some alias for HList / HNil: Think `$` as the end of a regex
  private[algebra] type $$ = HList
  private[algebra] type $ = HNil
  private[algebra] val  $: $ = HNil // explicit type annotation to avoid some implicit search bugs

  sealed class ?
  val ? : ? = new ?

  sealed class Batch
  object Batch extends Batch


}
