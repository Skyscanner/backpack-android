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

package net.skyscanner.backpack.compose.navigationbar

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import net.skyscanner.backpack.compose.navigationbar.internal.TopNavBarStateImpl
import net.skyscanner.backpack.compose.navigationbar.internal.TopNavBarTokens

enum class TopNavBarStatus {
  Expanded,
  Collapsed,
}

@Stable
sealed interface TopNavBarState

fun Modifier.nestedScroll(state: TopNavBarState): Modifier =
  nestedScroll(state.asInternalState().nestedScrollConnection)

@Composable
fun rememberTopAppBarState(initialStatus: TopNavBarStatus = TopNavBarStatus.Expanded): TopNavBarState {
  val offsetRange = with(LocalDensity.current) { (TopNavBarTokens.ExpandedHeight - TopNavBarTokens.CollapsedHeight).toPx() }
  val flingBehavior = ScrollableDefaults.flingBehavior()
  return rememberSaveable(
    offsetRange, flingBehavior,
    saver = TopNavBarStateImpl.saver(offsetRange, flingBehavior),
    init = { TopNavBarStateImpl(initialStatus, offsetRange = offsetRange, flingBehavior = flingBehavior) },
  )
}

@Stable
internal interface TopNavBarInternalState : TopNavBarState {

  val fraction: Float

  val nestedScrollConnection: NestedScrollConnection

}

internal fun TopNavBarState.asInternalState(): TopNavBarInternalState =
  when (this) {
    is TopNavBarInternalState -> this
  }
