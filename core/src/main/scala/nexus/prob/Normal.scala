package nexus.prob

import java.util._
import nexus._
import nexus.algebra._
import nexus.algebra.syntax._

/**
 * Represents the normal distribution N(μ, σ^2^).
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Normal[R](val μ: R, val σ2: R)(implicit R: IsReal[R]) extends Stochastic[R] {
  import GlobalSettings._
  private[this] val σ = R.sqrt(σ2)

  def mean = μ

  def variance = σ2

  def stdDev = σ

  def sample = {
    val u = random.nextGaussian()
    μ + R.fromDouble(u) * σ
  }

}

object Normal {

  def standard[R](implicit R: IsReal[R]) = apply(R.zero, R.one)

  def apply[R: IsReal](μ: R, σ2: R) = new Normal(μ, σ2)
  def unapply[R](n: Normal[R]) = Some((n.μ, n.σ2))

}
