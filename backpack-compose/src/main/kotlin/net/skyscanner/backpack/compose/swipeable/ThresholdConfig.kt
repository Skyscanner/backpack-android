package net.skyscanner.backpack.compose.swipeable

import androidx.compose.animation.core.SpringSpec
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.sign
import kotlin.math.sin

@Stable
@ExperimentalMaterial3Api
internal interface ThresholdConfig {
  fun Density.computeThreshold(fromValue: Float, toValue: Float): Float
}

@Immutable
@ExperimentalMaterial3Api
internal data class FixedThreshold(private val offset: Dp) : ThresholdConfig {
  override fun Density.computeThreshold(fromValue: Float, toValue: Float): Float {
    return fromValue + offset.toPx() * sign(toValue - fromValue)
  }
}

@Immutable
internal class ResistanceConfig(
  /*@FloatRange(from = 0.0, fromInclusive = false)*/
  val basis: Float,
  /*@FloatRange(from = 0.0)*/
  val factorAtMin: Float = SwipeableDefaults.StandardResistanceFactor,
  /*@FloatRange(from = 0.0)*/
  val factorAtMax: Float = SwipeableDefaults.StandardResistanceFactor,
) {
  fun computeResistance(overflow: Float): Float {
    val factor = if (overflow < 0) factorAtMin else factorAtMax
    if (factor == 0f) return 0f
    val progress = (overflow / basis).coerceIn(-1f, 1f)
    return basis / factor * sin(progress * PI.toFloat() / 2)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is ResistanceConfig) return false

    if (basis != other.basis) return false
    if (factorAtMin != other.factorAtMin) return false
    if (factorAtMax != other.factorAtMax) return false

    return true
  }

  override fun hashCode(): Int {
    var result = basis.hashCode()
    result = 31 * result + factorAtMin.hashCode()
    result = 31 * result + factorAtMax.hashCode()
    return result
  }

  override fun toString(): String {
    return "ResistanceConfig(basis=$basis, factorAtMin=$factorAtMin, factorAtMax=$factorAtMax)"
  }
}

internal object SwipeableDefaults {

  internal val AnimationSpec = SpringSpec<Float>()
  internal val VelocityThreshold = 125.dp
  const val StiffResistanceFactor = 20f
  const val StandardResistanceFactor = 10f

  internal fun resistanceConfig(
    anchors: Set<Float>,
    factorAtMin: Float = StandardResistanceFactor,
    factorAtMax: Float = StandardResistanceFactor,
  ): ResistanceConfig? {
    return if (anchors.size <= 1) {
      null
    } else {
      val basis = anchors.maxOrNull()!! - anchors.minOrNull()!!
      ResistanceConfig(basis, factorAtMin, factorAtMax)
    }
  }
}
