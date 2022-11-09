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

package net.skyscanner.backpack.compose.bottomsheet

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun rememberBpkBottomSheetState(
  initialValue: BpkBottomSheetValue,
  animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
  confirmStateChange: (BpkBottomSheetValue) -> Boolean = { true },
): BpkBottomSheetState =
  rememberSaveable(
    animationSpec,
    saver = BpkBottomSheetState.Saver(
      animationSpec = animationSpec,
      confirmStateChange = confirmStateChange,
    )
  ) {
    BpkBottomSheetState(
      initialValue = initialValue,
      animationSpec = animationSpec,
      confirmStateChange = confirmStateChange,
    )
  }

enum class BpkBottomSheetValue {
  Collapsed,
  Expanded,
}

@Stable
@OptIn(ExperimentalMaterialApi::class)
class BpkBottomSheetState(
  initialValue: BpkBottomSheetValue,
  animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
  internal val confirmStateChange: (BpkBottomSheetValue) -> Boolean = { true },
) : SwipeableState<BpkBottomSheetValue>(
  initialValue = initialValue,
  animationSpec = animationSpec,
  confirmStateChange = confirmStateChange,
) {

  val isExpanded: Boolean
    get() = currentValue == BpkBottomSheetValue.Expanded

  val isCollapsed: Boolean
    get() = currentValue == BpkBottomSheetValue.Collapsed

  suspend fun expand() = animateTo(BpkBottomSheetValue.Expanded)

  suspend fun collapse() = animateTo(BpkBottomSheetValue.Collapsed)

  companion object {

    fun Saver(
      animationSpec: AnimationSpec<Float>,
      confirmStateChange: (BpkBottomSheetValue) -> Boolean,
    ): Saver<BpkBottomSheetState, *> = Saver(
      save = { it.currentValue },
      restore = {
        BpkBottomSheetState(
          initialValue = it,
          animationSpec = animationSpec,
          confirmStateChange = confirmStateChange,
        )
      }
    )
  }
}
