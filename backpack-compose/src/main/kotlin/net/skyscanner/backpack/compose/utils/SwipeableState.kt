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

package net.skyscanner.backpack.compose.utils

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FixedThreshold
import androidx.compose.material.ResistanceConfig
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.SwipeableState
import androidx.compose.material.ThresholdConfig
import androidx.compose.material.swipeable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp

// to resolve one of the issues in Material Design library, see
// https://github.com/androidx/androidx/blob/133b57f4ff1506abeda479a95c357b8e7bdca2d7/compose/material/material/src/commonMain/kotlin/androidx/compose/material/Swipeable.kt#L845
@OptIn(ExperimentalMaterialApi::class)
internal fun <T> Modifier.nestedScrollFixedSwipeable(
  state: SwipeableState<T>,
  anchors: Map<Float, T>,
  orientation: Orientation,
  enabled: Boolean = true,
  reverseDirection: Boolean = false,
  interactionSource: MutableInteractionSource? = null,
  thresholds: (from: T, to: T) -> ThresholdConfig = { _, _ -> FixedThreshold(56.dp) },
  resistance: ResistanceConfig? = SwipeableDefaults.resistanceConfig(anchors.keys),
  velocityThreshold: Dp = SwipeableDefaults.VelocityThreshold
): Modifier = this
  .nestedScroll(
    connection = state.preUpPostDownNestedScrollConnection(anchors.keys.minOrNull() ?: Float.NEGATIVE_INFINITY)
  )
  .swipeable(
    state = state,
    anchors = anchors,
    orientation = orientation,
    enabled = enabled,
    reverseDirection = reverseDirection,
    interactionSource = interactionSource,
    thresholds = thresholds,
    resistance = resistance,
    velocityThreshold = velocityThreshold,
  )

@OptIn(ExperimentalMaterialApi::class)
private fun <T> SwipeableState<T>.preUpPostDownNestedScrollConnection(
  minBound: Float,
): NestedScrollConnection =
  object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
      val delta = available.toFloat()
      return if (delta < 0 && source == NestedScrollSource.Drag) {
        performDrag(delta).toOffset()
      } else {
        Offset.Zero
      }
    }

    override fun onPostScroll(
      consumed: Offset,
      available: Offset,
      source: NestedScrollSource
    ): Offset {
      return if (source == NestedScrollSource.Drag) {
        performDrag(available.toFloat()).toOffset()
      } else {
        Offset.Zero
      }
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
      val toFling = Offset(available.x, available.y).toFloat()
      return if (toFling < 0 && offset.value > minBound) {
        performFling(velocity = toFling)
        // since we go to the anchor with tween settling, consume all for the best UX
        available
      } else {
        Velocity.Zero
      }
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
      performFling(velocity = Offset(available.x, available.y).toFloat())
      return available
    }

    private fun Float.toOffset(): Offset = Offset(0f, this)

    private fun Offset.toFloat(): Float = this.y
  }
