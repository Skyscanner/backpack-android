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

package net.skyscanner.backpack.compose.navigationbar.internal

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import net.skyscanner.backpack.compose.navigationbar.TopNavBarInternalState
import net.skyscanner.backpack.compose.navigationbar.TopNavBarStatus

@Stable
internal class TopNavBarStateImpl private constructor(
  initialOffset: Float,
  offsetRange: Float,
  private val flingBehavior: FlingBehavior,
) : TopNavBarInternalState {

  constructor(
    initialStatus: TopNavBarStatus,
    offsetRange: Float,
    flingBehavior: FlingBehavior,
  ) : this(
    initialOffset = when (initialStatus) {
      TopNavBarStatus.Expanded -> 0f
      TopNavBarStatus.Collapsed -> -offsetRange
    },
    offsetRange = offsetRange,
    flingBehavior = flingBehavior,
  )

  private val minOffset = -offsetRange
  private var currentOffset by mutableStateOf(initialOffset)

  override val fraction: Float
    get() = 1f - (currentOffset / minOffset)

  override val nestedScrollConnection: NestedScrollConnection =
    object : NestedScrollConnection, ScrollScope {

      override fun scrollBy(pixels: Float): Float {
        val oldValue = currentOffset
        val newValue = (oldValue + pixels).coerceIn(
          minimumValue = minOffset,
          maximumValue = 0f,
        )
        currentOffset = newValue
        return newValue - oldValue
      }

      override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        // Don't intercept if scrolling down.
        if (available.y > 0f) return Offset.Zero

        return if (scrollBy(available.y) != 0f) {
          // We're in the middle of top app bar collapse or expand.
          // Consume only the scroll on the Y axis.
          available.copy(x = 0f)
        } else {
          Offset.Zero
        }
      }

      override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {

        if (available.y < 0f || consumed.y < 0f) {
          // When scrolling up, just update the state's height offset.
          return Offset(0f, scrollBy(consumed.y))
        }

        if (available.y > 0f) {
          // Adjust the height offset in case the consumed delta Y is less than what was
          // recorded as available delta Y in the pre-scroll.
          return Offset(0f, scrollBy(available.y))
        }
        return Offset.Zero
      }

      override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        val velocity = available.y

        // Check if the app bar is completely collapsed/expanded. If so, no need to settle the app bar,
        // and just return Zero Velocity.
        // Note that we don't check for 0f due to float precision with the collapsedFraction
        // calculation.
        if (fraction < 0.01f || fraction == 1f) {
          return Velocity.Zero
        }

        // In case there is an initial velocity that was left after a previous user fling, animate to
        // continue the motion to expand or collapse the app bar.
        val remainingVelocity = with(flingBehavior) {
          performFling(velocity)
        }

        return Velocity(0f, remainingVelocity)
      }
    }

  companion object {

    fun saver(offsetRange: Float, flingBehavior: FlingBehavior): Saver<TopNavBarStateImpl, Float> =
      Saver(
        save = { it.currentOffset },
        restore = { TopNavBarStateImpl(it, offsetRange, flingBehavior) },
      )
  }
}
