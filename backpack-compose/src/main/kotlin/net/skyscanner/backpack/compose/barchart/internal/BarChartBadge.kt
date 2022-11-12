/*
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

package net.skyscanner.backpack.compose.barchart.internal

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.barchart.BpkBarChartModel
import net.skyscanner.backpack.compose.flare.BpkFlarePointerDirection
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.FlareShape
import net.skyscanner.backpack.compose.utils.anchor
import net.skyscanner.backpack.compose.utils.offsetWithSize
import kotlin.math.roundToInt

@Composable
internal fun BarChartBadge(
  anchor: Offset,
  selected: BpkBarChartModel.Item?,
  modifier: Modifier = Modifier,
) {
  if (anchor.isUnspecified || selected == null) return

  val animatable = remember { Animatable(0f) }
  LaunchedEffect(selected) {
    animatable.snapTo(0f)
    animatable.animateTo(1f, animationSpec = spring(stiffness = Spring.StiffnessMediumLow))
  }

  BpkText(
    text = selected.badge,
    color = BpkTheme.colors.textPrimaryInverse,
    style = BpkTheme.typography.label2,
    textAlign = TextAlign.Center,
    maxLines = 1,
    modifier = modifier
      .anchor(anchor, Alignment.Center)
      .padding(bottom = BpkSpacing.Sm)
      .graphicsLayer { alpha = animatable.value }
      .height(36.dp * 2)
      .padding(bottom = 36.dp)
      .offsetWithSize { IntOffset(x = 0, y = ((1f - animatable.value) * it.height).roundToInt()) }
      .widthIn(min = 48.dp)
      .background(
        color = BpkTheme.colors.coreAccent,
        shape = FlareShape(
          borderRadius = BpkBorderRadius.Xs,
          flareHeight = BpkSpacing.Sm,
          pointerDirection = BpkFlarePointerDirection.Down,
        ),
      )
      .padding(bottom = BpkSpacing.Sm)
      .padding(BpkSpacing.Md),
  )
}
