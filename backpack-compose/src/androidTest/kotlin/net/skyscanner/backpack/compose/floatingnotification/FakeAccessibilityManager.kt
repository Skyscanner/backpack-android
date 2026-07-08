/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.floatingnotification

import androidx.compose.ui.platform.AccessibilityManager

internal class FakeAccessibilityManager(
    private val fixedTimeout: Long = 4000L,
    private val captureArgs: Boolean = false,
) : AccessibilityManager {

    var lastOriginalTimeout: Long = -1L
        private set
    var lastContainsIcons: Boolean = false
        private set
    var lastContainsText: Boolean = false
        private set
    var lastContainsControls: Boolean = false
        private set

    override fun calculateRecommendedTimeoutMillis(
        originalTimeoutMillis: Long,
        containsIcons: Boolean,
        containsText: Boolean,
        containsControls: Boolean,
    ): Long {
        if (captureArgs) {
            lastOriginalTimeout = originalTimeoutMillis
            lastContainsIcons = containsIcons
            lastContainsText = containsText
            lastContainsControls = containsControls
        }
        return fixedTimeout
    }
}
