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

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeProgress
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State

@Stable
interface SwipeableState<T> {

  val currentValue: T

  val isAnimationRunning: Boolean

  val offset: State<Float>

  val overflow: State<Float>

  val targetValue: T

  @OptIn(ExperimentalMaterialApi::class)
  val progress: SwipeProgress<T>

  val direction: Float

  suspend fun snapTo(targetValue: T)

  suspend fun animateTo(targetValue: T, anim: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec)

  suspend fun performFling(velocity: Float)

  fun performDrag(delta: Float): Float

}

@OptIn(ExperimentalMaterialApi::class)
@Stable
internal fun <F, T> SwipeableState(
  delegate: SwipeableState<F>,
  map: (T) -> F,
  unmap: (F) -> T,
): net.skyscanner.backpack.compose.utils.SwipeableState<T> =

  @Stable
  object : net.skyscanner.backpack.compose.utils.SwipeableState<T> {

    override val currentValue: T
      get() = delegate.currentValue.let(unmap)

    override val isAnimationRunning: Boolean
      get() = delegate.isAnimationRunning

    override val offset: State<Float>
      get() = delegate.offset

    override val overflow: State<Float>
      get() = delegate.overflow

    override val targetValue: T
      get() = delegate.targetValue.let(unmap)

    override val progress: SwipeProgress<T>
      get() = delegate.progress.let {
        SwipeProgress(
          from = it.from.let(unmap),
          to = it.to.let(unmap),
          fraction = it.fraction,
        )
      }

    override val direction: Float
      get() = delegate.direction

    override suspend fun performFling(velocity: Float) =
      delegate.performFling(velocity)

    override fun performDrag(delta: Float): Float =
      delegate.performDrag(delta)

    override suspend fun animateTo(targetValue: T, anim: AnimationSpec<Float>) =
      delegate.animateTo(targetValue.let(map))

    override suspend fun snapTo(targetValue: T) =
      delegate.snapTo(targetValue.let(map))

  }
