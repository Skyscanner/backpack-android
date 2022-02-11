package net.skyscanner.backpack.compose.card.internal

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.card.BpkCardPadding
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkElevation
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal inline fun CardConent(
  padding: BpkCardPadding,
  contentAlignment: Alignment,
  content: @Composable BoxScope.() -> Unit,
) {
  Box(
    modifier = Modifier.padding(
      all = when (padding) {
        BpkCardPadding.None -> 0.dp
        BpkCardPadding.Small -> BpkSpacing.Base
      },
    ),
    contentAlignment = contentAlignment,
    content = content,
  )
}

@Composable
internal inline fun cardBackgroundColor(focused: Boolean): Color =
  animateColorAsState(
    when {
      focused -> BpkTheme.colors.backgroundElevation02
      else -> BpkTheme.colors.backgroundElevation01
    }
  ).value


@Composable
internal inline fun cardElevation(focused: Boolean): Dp =
  animateDpAsState(
    when {
      focused -> BpkElevation.Xl
      else -> BpkElevation.Sm
    }
  ).value

internal fun cardShape(corner: BpkCardCorner) =
  RoundedCornerShape(
    size = when (corner) {
      BpkCardCorner.Small -> BpkBorderRadius.Md
      BpkCardCorner.Large -> BpkBorderRadius.Lg
    }
  )
