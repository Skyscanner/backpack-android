package net.skyscanner.backpack.compose.utils

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.compose.theme.BpkTheme

@Composable
internal inline fun InteractionSource.animateAsColor(
  default: Color,
  pressed: Color = default,
  focused: Color = pressed,
): Color {
  val isPressed by collectIsPressedAsState()
  val isFocused by collectIsFocusedAsState()
  val target = when {
    isPressed -> pressed
    isFocused -> focused
    else -> default
  }
  return animateColorAsState(target).value
}

@Composable
internal inline fun dynamicColorOf(light: Color, dark: Color): Color =
  if (BpkTheme.colors.isLight) light else dark
