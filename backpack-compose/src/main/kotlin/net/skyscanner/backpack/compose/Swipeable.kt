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

package net.skyscanner.backpack.compose

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Stable

@Stable
interface BpkSwipeableState<T> {

    val currentValue: T

    val isAnimationRunning: Boolean

    val targetValue: T

    suspend fun snapTo(targetValue: T)

    suspend fun animateTo(targetValue: T)
}

@OptIn(ExperimentalMaterialApi::class)
internal fun <T> BpkSwipeableState(
    wrapped: SwipeableState<T>,
): BpkSwipeableState<T> = object : BpkSwipeableState<T> {

    override val currentValue: T
        get() = wrapped.currentValue

    override val isAnimationRunning: Boolean
        get() = wrapped.isAnimationRunning

    override val targetValue: T
        get() = wrapped.targetValue

    override suspend fun animateTo(targetValue: T) =
        wrapped.animateTo(targetValue)

    override suspend fun snapTo(targetValue: T) =
        wrapped.snapTo(targetValue)
}
