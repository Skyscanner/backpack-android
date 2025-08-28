/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.demo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.floatingnotification.rememberBpkFloatingNotificationState
import net.skyscanner.backpack.demo.BackpackDemoTheme

@Composable
fun DemoScaffold(
    modifier: Modifier = Modifier,
    automationMode: Boolean = false,
    content: @Composable () -> Unit,
) {
    BackpackDemoTheme {
        val floatingNotificationState = rememberBpkFloatingNotificationState()
        CompositionLocalProvider(
            LocalAutomationMode provides automationMode,
            LocalFloatingNotification provides floatingNotificationState,
        ) {
            Box(modifier) {
                content()
                BpkFloatingNotification(state = floatingNotificationState)
            }
        }
    }
}
