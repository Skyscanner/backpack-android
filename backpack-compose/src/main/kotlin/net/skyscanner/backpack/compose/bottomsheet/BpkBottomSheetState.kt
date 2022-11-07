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

@file:OptIn(ExperimentalMaterialApi::class)

package net.skyscanner.backpack.compose.bottomsheet

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import net.skyscanner.backpack.compose.utils.SwipeableState

@Composable
fun rememberBpkBottomSheetState(
  initialValue: BpkBottomSheetValue,
  animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
  confirmStateChange: (BpkBottomSheetValue) -> Boolean = { true }
): BpkBottomSheetState {
  val delegate = rememberBottomSheetState(
    initialValue = initialValue.map(),
    animationSpec = animationSpec,
    confirmStateChange = { confirmStateChange(it.unmap()) },
  )
  return remember(delegate) { BpkBottomSheetState(delegate) }
}

enum class BpkBottomSheetValue {
  Collapsed,
  Expanded,
}

class BpkBottomSheetState internal constructor(
  internal val delegate: BottomSheetState,
) : SwipeableState<BpkBottomSheetValue> by SwipeableState(
  delegate = delegate,
  map = BpkBottomSheetValue::map,
  unmap = BottomSheetValue::unmap,
) {

  constructor(
    initialValue: BpkBottomSheetValue,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    confirmStateChange: (BpkBottomSheetValue) -> Boolean = { true }
  ) : this(
    delegate = BottomSheetState(
      initialValue = initialValue.map(),
      animationSpec = animationSpec,
      confirmStateChange = { confirmStateChange(it.unmap()) },
    )
  )

  val isExpanded: Boolean
    get() = delegate.isExpanded

  val isCollapsed: Boolean
    get() = delegate.isCollapsed

  suspend fun expand() = delegate.expand()

  suspend fun collapse() = delegate.collapse()

}


private fun BottomSheetValue.unmap() = when (this) {
  BottomSheetValue.Collapsed -> BpkBottomSheetValue.Collapsed
  BottomSheetValue.Expanded -> BpkBottomSheetValue.Expanded
}

private fun BpkBottomSheetValue.map() = when (this) {
  BpkBottomSheetValue.Collapsed -> BottomSheetValue.Collapsed
  BpkBottomSheetValue.Expanded -> BottomSheetValue.Expanded
}
