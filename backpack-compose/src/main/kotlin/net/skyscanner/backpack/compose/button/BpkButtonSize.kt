package net.skyscanner.backpack.compose.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

sealed class BpkButtonSize {

  @Composable
  internal abstract fun textStyle(): TextStyle

  internal abstract val iconSize: Dp

  internal abstract val minHeight: Dp

  internal open val horizontalPadding: Dp =
    BpkSpacing.Base

  internal open val horizontalSpacing: Dp =
    BpkSpacing.Md

  object Default : BpkButtonSize() {

    override val iconSize = BpkSpacing.Base

    override val minHeight = BpkSpacing.Xl + BpkSpacing.Sm

    @Composable
    override fun textStyle(): TextStyle =
      BpkTheme.typography.label2

  }

  object Large : BpkButtonSize() {

    override val iconSize = BpkSpacing.Lg

    override val minHeight = BpkSpacing.Lg + BpkSpacing.Lg

    @Composable
    override fun textStyle(): TextStyle =
      BpkTheme.typography.label1

  }

}
