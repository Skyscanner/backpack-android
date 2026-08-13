/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.compose.banneralert

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Airline
import org.junit.Rule
import org.junit.Test

class BpkBannerAlertAccessibilityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenIconContentDescription_whenBpkBannerAlertRendered_thenIconDescriptionIsExposed() {
        val iconContentDescription = "Airline"

        composeTestRule.setContent {
            BpkTheme {
                BpkBannerAlert(
                    message = "Hello world!",
                    alertTypeContentDescription = "Information",
                    icon = BpkIcon.Airline,
                    iconContentDescription = iconContentDescription,
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(iconContentDescription)
            .assertExists()
    }

    @Test
    fun givenNoIconContentDescription_whenBpkBannerAlertRendered_thenIconFallsBackToAlertTypeContentDescription() {
        val alertTypeContentDescription = "Information"

        composeTestRule.setContent {
            BpkTheme {
                BpkBannerAlert(
                    message = "Hello world!",
                    alertTypeContentDescription = alertTypeContentDescription,
                    icon = BpkIcon.Airline,
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription(alertTypeContentDescription)
            .assertExists()
    }
}
