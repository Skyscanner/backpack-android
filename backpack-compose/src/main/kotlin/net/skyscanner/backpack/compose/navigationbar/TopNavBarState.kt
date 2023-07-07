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

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll

enum class TopNavBarStatus {
    Expanded,
    Collapsed,
}

@Stable
sealed interface TopNavBarState

fun Modifier.nestedScroll(state: TopNavBarState): Modifier =
    nestedScroll(state.asInternalState().nestedScrollConnection)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun rememberTopAppBarState(initialStatus: TopNavBarStatus = TopNavBarStatus.Expanded): TopNavBarState {
    val behaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = when (initialStatus) {
            TopNavBarStatus.Expanded -> rememberTopAppBarState(
                initialHeightOffset = 0f,
                initialContentOffset = 0f,
            )

            TopNavBarStatus.Collapsed -> rememberTopAppBarState(
                initialHeightOffset = -Float.MAX_VALUE,
                initialContentOffset = -Float.MAX_VALUE,
            )
        },
    )
    return TopNavBarInternalState(behaviour)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun rememberFixedTopAppBarState(): TopNavBarState {
    val behaviour = TopAppBarDefaults.pinnedScrollBehavior(
        canScroll = { false },
        state = rememberTopAppBarState(
            initialHeightOffset = -Float.MAX_VALUE,
            initialContentOffset = -Float.MAX_VALUE,
        ),
    )
    return TopNavBarInternalState(behaviour)
}

@Stable
@OptIn(ExperimentalMaterial3Api::class)
internal class TopNavBarInternalState(
    private val scrollingBehavior: TopAppBarScrollBehavior,
) : TopNavBarState, TopAppBarScrollBehavior by scrollingBehavior

internal fun TopNavBarState.asInternalState(): TopNavBarInternalState =
    when (this) {
        is TopNavBarInternalState -> this
    }
