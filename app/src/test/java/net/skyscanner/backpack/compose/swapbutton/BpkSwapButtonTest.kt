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

package net.skyscanner.backpack.compose.swapbutton

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

@RunWith(ParameterizedRobolectricTestRunner::class)
class BpkSwapButtonTest(flavour: String) :
    BpkSnapshotTest(listOf(flavour)) {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun canvasDefault() {
        snap {
            BpkSwapButton(
                onClick = {},
                style = BpkSwapButtonStyle.CanvasDefault,
                contentDescription = "Swap",
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun canvasContrast() {
        snap {
            BpkSwapButton(
                onClick = {},
                style = BpkSwapButtonStyle.CanvasContrast,
                contentDescription = "Swap",
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun surfaceContrast() {
        snap {
            BpkSwapButton(
                onClick = {},
                style = BpkSwapButtonStyle.SurfaceContrast,
                contentDescription = "Swap",
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun disabled() {
        snap {
            BpkSwapButton(
                onClick = {},
                enabled = false,
                contentDescription = "Swap",
            )
        }
    }

    @Test
    fun clickInteraction() {
        composeTestRule.setContent {
            BpkSwapButton(
                onClick = {},
                contentDescription = "Swap button",
            )
        }

        composeTestRule.onNodeWithContentDescription("Swap button").performClick()
    }

    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "{0} Screenshot")
        fun flavours(): List<String> = listOf("Default", "DarkMode")
    }
}