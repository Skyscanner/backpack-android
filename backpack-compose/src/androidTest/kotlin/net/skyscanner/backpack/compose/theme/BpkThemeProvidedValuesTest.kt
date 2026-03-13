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

package net.skyscanner.backpack.compose.theme

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.font.FontFamily
import net.skyscanner.backpack.compose.tokens.BpkColors
import net.skyscanner.backpack.compose.tokens.BpkShapes
import net.skyscanner.backpack.compose.tokens.BpkTypography
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

class BpkThemeProvidedValuesTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenBpkTheme_whenTypographyAccessed_thenProvidedTypographyReturned() {
        var capturedTypography: BpkTypography? = null

        composeTestRule.setContent {
            BpkTheme {
                capturedTypography = BpkTheme.typography
            }
        }

        composeTestRule.waitForIdle()
        assertNotNull("Typography should be provided by BpkTheme", capturedTypography)
    }

    @Test
    fun givenBpkTheme_whenColorsAccessed_thenProvidedColorsReturned() {
        var capturedColors: BpkColors? = null

        composeTestRule.setContent {
            BpkTheme {
                capturedColors = BpkTheme.colors
            }
        }

        composeTestRule.waitForIdle()
        assertNotNull("Colors should be provided by BpkTheme", capturedColors)
    }

    @Test
    fun givenBpkTheme_whenShapesAccessed_thenProvidedShapesReturned() {
        var capturedShapes: BpkShapes? = null

        composeTestRule.setContent {
            BpkTheme {
                capturedShapes = BpkTheme.shapes
            }
        }

        composeTestRule.waitForIdle()
        assertNotNull("Shapes should be provided by BpkTheme", capturedShapes)
    }

    @Test
    fun givenBpkThemeProvidedValues_whenInInspectionMode_thenProvidedValuesTakePrecedenceOverDefaults() {
        var capturedTypography: BpkTypography? = null
        var capturedColors: BpkColors? = null
        var capturedShapes: BpkShapes? = null

        composeTestRule.setContent {
            CompositionLocalProvider(LocalInspectionMode provides true) {
                // Wrap with BpkTheme which provides specific values
                BpkTheme(fontFamily = FontFamily.Cursive) {
                    capturedTypography = BpkTheme.typography
                    capturedColors = BpkTheme.colors
                    capturedShapes = BpkTheme.shapes
                }
            }
        }

        composeTestRule.waitForIdle()

        // Verify provided values are used, not defaults
        assertNotNull("Typography should be provided", capturedTypography)
        assertNotNull("Colors should be provided", capturedColors)
        assertNotNull("Shapes should be provided", capturedShapes)

        // Verify it's using the custom font family (Cursive), not default (SansSerif)
        assertEquals(FontFamily.Cursive, capturedTypography?.bodyDefault?.fontFamily)
    }

    @Test
    fun givenNoValues_whenInInspectionMode_thenUsesDefaultValues() {
        var capturedTypography: BpkTypography? = null
        var capturedColors: BpkColors? = null
        var capturedShapes: BpkShapes? = null

        composeTestRule.setContent {
            CompositionLocalProvider(LocalInspectionMode provides true) {
                capturedTypography = BpkTheme.typography
                capturedColors = BpkTheme.colors
                capturedShapes = BpkTheme.shapes
            }
        }

        composeTestRule.waitForIdle()

        // Verify default values are used
        assertNotNull("Typography should be provided", capturedTypography)
        assertNotNull("Colors should be provided", capturedColors)
        assertNotNull("Shapes should be provided", capturedShapes)

        assertEquals(FontFamily.SansSerif, capturedTypography!!.bodyDefault.fontFamily)
        assertEquals(BpkColors.light().canvas, capturedColors!!.canvas)
        assertEquals(BpkShapes().medium, capturedShapes!!.medium)
    }

    @Test(expected = IllegalStateException::class)
    fun givenNoThemeWrapper_whenOutsideInspectionMode_thenTypographyAccessThrowsError() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalInspectionMode provides false) {
                // This should throw an error
                BpkTheme.typography
            }
        }

        composeTestRule.waitForIdle()
    }

    @Test(expected = IllegalStateException::class)
    fun givenNoThemeWrapper_whenOutsideInspectionMode_thenColorsAccessThrowsError() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalInspectionMode provides false) {
                // This should throw an error
                BpkTheme.colors
            }
        }

        composeTestRule.waitForIdle()
    }

    @Test(expected = IllegalStateException::class)
    fun givenNoThemeWrapper_whenOutsideInspectionMode_thenShapesAccessThrowsError() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalInspectionMode provides false) {
                // This should throw an error
                BpkTheme.shapes
            }
        }

        composeTestRule.waitForIdle()
    }
}
