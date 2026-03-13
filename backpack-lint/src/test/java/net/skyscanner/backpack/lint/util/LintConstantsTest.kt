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

package net.skyscanner.backpack.lint.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LintConstantsTest {

    @Test
    fun `given SLACK_CHANNEL_URL constant, when accessed, then returns correct URL`() {
        assertEquals(
            "https://skyscanner.slack.com/archives/C0JHPDSSU",
            LintConstants.SLACK_CHANNEL_URL,
        )
    }

    @Test
    fun `given SUPPORT_MESSAGE constant, when accessed, then contains Slack channel URL`() {
        assertTrue(LintConstants.SUPPORT_MESSAGE.contains(LintConstants.SLACK_CHANNEL_URL))
    }

    @Test
    fun `given SUPPORT_MESSAGE constant, when accessed, then contains backpack channel mention`() {
        assertTrue(LintConstants.SUPPORT_MESSAGE.contains("#backpack"))
    }
}
