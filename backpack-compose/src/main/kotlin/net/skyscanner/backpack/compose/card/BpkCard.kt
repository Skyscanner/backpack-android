package net.skyscanner.backpack.compose.card

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkElevation

enum class BpkCardCorner {
  Small,
  Large,
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BpkCard(
  modifier: Modifier = Modifier,
  corner: BpkCardCorner = BpkCardCorner.Small,
  onClick: (() -> Unit)? = null,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  onClickLabel: String? = null,
  role: Role? = null,
  content: @Composable () -> Unit,
) {

  val focused by interactionSource.collectIsFocusedAsState()
  val elevation by animateDpAsState(if (focused) BpkElevation.Lg else BpkElevation.Sm)

  CompositionLocalProvider(LocalBpkCardElevation provides LocalBpkCardElevation.current + 1) {

    Card(
      modifier = modifier,
      shape = RoundedCornerShape(
        size = when (corner) {
          BpkCardCorner.Small -> BpkBorderRadius.Md
          BpkCardCorner.Large -> BpkBorderRadius.Lg
        },
      ),
      backgroundColor = when (LocalBpkCardElevation.current) {
        BpkCardElevation.Zero -> BpkTheme.colors.background
        BpkCardElevation.One -> BpkTheme.colors.backgroundElevation01
        BpkCardElevation.Two -> BpkTheme.colors.backgroundElevation02
        BpkCardElevation.Tree -> BpkTheme.colors.backgroundElevation03
        BpkCardElevation.Max -> BpkTheme.colors.backgroundElevation03
      },
      contentColor = BpkTheme.colors.textPrimary,
      elevation = elevation,
      onClick = onClick ?: {},
      onClickLabel = onClickLabel,
      interactionSource = interactionSource,
      enabled = onClick != null,
      role = role,
      content = content,
    )

  }
}

private val LocalBpkCardElevation = compositionLocalOf { BpkCardElevation.Zero }

private enum class BpkCardElevation {
  Zero,
  One,
  Two,
  Tree,
  Max;

  operator fun plus(value: Int) : BpkCardElevation {
    val index = (ordinal + value).coerceIn(0, Max.ordinal)
    return values()[index]
  }

}
