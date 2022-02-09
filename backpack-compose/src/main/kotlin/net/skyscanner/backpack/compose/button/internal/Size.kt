package net.skyscanner.backpack.compose.button.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BpkButtonSize.textStyle(): TextStyle =
  when (this) {
    BpkButtonSize.Default -> BpkTheme.typography.label2
    BpkButtonSize.Large -> BpkTheme.typography.label1
  }

internal val BpkButtonSize.iconSize: Dp
  get() =
    when (this) {
      BpkButtonSize.Default -> BpkSpacing.Base
      BpkButtonSize.Large -> BpkSpacing.Lg
    }

internal val BpkButtonSize.minHeight: Dp
  get() =
    when (this) {
      BpkButtonSize.Default -> 36.dp
      BpkButtonSize.Large -> 48.dp
    }

internal val BpkButtonSize.horizontalPadding: Dp
  get() =
    BpkSpacing.Base

internal val BpkButtonSize.horizontalSpacing: Dp
  get() =
    BpkSpacing.Md
