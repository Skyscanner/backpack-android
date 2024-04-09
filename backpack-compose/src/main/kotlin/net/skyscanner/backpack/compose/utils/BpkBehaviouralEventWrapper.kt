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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * This is used for registering components for behavioural events. Right now this supports `onClick` events, while any other
 * events must be handled by the consumer.
 */

typealias BpkBehaviouralEventWrapper = @Composable (item: Any, modifier: Modifier, content: @Composable BpkClickHandleScope.() -> Unit) -> Unit

interface BpkClickHandleScope {
    fun notifyClick()
}
