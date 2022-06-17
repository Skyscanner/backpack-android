/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.compose.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.LayoutDirection
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

internal fun Modifier.hideContentIf(hide: Boolean): Modifier =
  layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(placeable.width, placeable.height) {
      if (!hide) {
        placeable.place(0, 0)
      }
    }
  }

@OptIn(ExperimentalContracts::class)
internal inline fun Modifier.applyIf(predicate: Boolean, block: Modifier.() -> Modifier): Modifier {
  contract {
    callsInPlace(block, InvocationKind.AT_MOST_ONCE)
  }
  return if (predicate) block() else this
}

internal fun Modifier.autoMirror(enabled: Boolean = true): Modifier =
  drawWithContent {
    val scaleX = if (enabled && layoutDirection == LayoutDirection.Rtl) -1f else 1f

    scale(scaleX = scaleX, scaleY = 1f) {
      this@drawWithContent.drawContent()
    }
  }

internal fun Modifier.unboundClickable(role: Role? = null, onClick: () -> Unit): Modifier =
  composed {
    clickable(
      interactionSource = remember { MutableInteractionSource() },
      indication = rememberRipple(bounded = false),
      role = role,
      onClick = onClick,
    )
  }


