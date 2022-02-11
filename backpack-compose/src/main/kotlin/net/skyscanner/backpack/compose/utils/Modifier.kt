package net.skyscanner.backpack.compose.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

internal fun Modifier.hideContentIf(hide: Boolean): Modifier =
  layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(placeable.width, placeable.height) {
      if (!hide) {
        placeable.place(0, 0)
      }
    }
  }
