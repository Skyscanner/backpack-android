/**
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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind

internal fun Modifier.registerForBehaviouralEvents(
    item: Any,
    behaviouralCallback: BehaviouralCallback?,
    onClick: () -> Unit,
): Modifier = composed {

    if (behaviouralCallback == null) {
        this
    } else {
        var wasDrawn by rememberSaveable { mutableStateOf(false) }
        drawBehind {
            if (!wasDrawn) {
                wasDrawn = true
                behaviouralCallback.onDrawn(item)
            }
        }
    }
}.clickable {
    behaviouralCallback?.onClick(item)
    onClick.invoke()
}

interface BehaviouralCallback {
    fun onDrawn(element: Any)
    fun onClick(element: Any)
}
