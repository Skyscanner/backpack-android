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

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

internal enum class ScrollingDirection {
    Forward,
    Backward,
}

@Composable
internal fun LazyListState.lastScrollingDirection(): ScrollingDirection {

    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }

    return remember(this) {
        derivedStateOf {
            val direction = when {
                previousIndex != firstVisibleItemIndex -> when {
                    previousIndex <= firstVisibleItemIndex -> ScrollingDirection.Forward
                    else -> ScrollingDirection.Backward
                }

                else -> when {
                    previousScrollOffset < firstVisibleItemScrollOffset -> ScrollingDirection.Forward
                    else -> ScrollingDirection.Backward
                }
            }

            previousIndex = firstVisibleItemIndex
            previousScrollOffset = firstVisibleItemScrollOffset
            return@derivedStateOf direction
        }
    }.value
}
