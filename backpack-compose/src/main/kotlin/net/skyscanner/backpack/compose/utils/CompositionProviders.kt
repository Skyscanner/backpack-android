package net.skyscanner.backpack.compose.utils

import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkColor

@Composable
internal inline fun BpkToggleableContent(
  enabled: Boolean,
  noinline content: @Composable () -> Unit,
) {

  val contentColor = when {
    enabled -> BpkTheme.colors.textPrimary
    else -> dynamicColorOf(BpkColor.SkyGrayTint04, BpkColor.BlackTint06)
  }

  CompositionLocalProvider(
    LocalContentColor provides contentColor,
    LocalTextStyle provides BpkTheme.typography.footnote,
    content = content,
  )

}
