package net.skyscanner.backpack.compose.card

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
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
  val pressed by interactionSource.collectIsPressedAsState()
  val elevated = focused || pressed

  val elevation by animateDpAsState(
    when {
      elevated -> BpkElevation.Xl
      else -> BpkElevation.Base
    }
  )

  val backgroundColor by animateColorAsState(
    when {
      elevated -> BpkTheme.colors.backgroundElevation02
      else -> BpkTheme.colors.backgroundElevation01
    }
  )

  val size = when (corner) {
    BpkCardCorner.Small -> BpkBorderRadius.Md
    BpkCardCorner.Large -> BpkBorderRadius.Lg
  }

  Card(
    modifier = modifier,
    shape = RoundedCornerShape(size = size),
    backgroundColor = backgroundColor,
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
