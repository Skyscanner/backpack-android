/*
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

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import net.skyscanner.backpack.compose.tokens.BpkLetterSpacing
import net.skyscanner.backpack.compose.tokens.BpkTypography
import net.skyscanner.backpack.configuration.BpkConfiguration
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

/**
 * Tests for BpkTheme typography selection based on configuration.
 * Note: These tests verify the typography selection logic by directly
 * constructing typography objects based on configuration state.
 */
class BpkThemeTest {

    @After
    fun cleanup() {
        BpkConfiguration.clearConfigs()
    }

    @Test
    fun `Typography factory creates VDL2 when configuration is set`() {
        BpkConfiguration.clearConfigs()
        BpkConfiguration.setConfigs(typography = true)

        // Simulate what BpkTheme does
        val typography = when (BpkConfiguration.typographySet) {
            BpkConfiguration.BpkTypographySet.DEFAULT -> BpkTypography(FontFamily.SansSerif)
            BpkConfiguration.BpkTypographySet.VDL_2 -> BpkTypography.VDL2(FontFamily.SansSerif)
        }

        // Verify hero5 has VDL2 characteristics
        assertEquals(FontWeight.Black, typography.hero5.fontWeight)
        assertEquals(BpkLetterSpacing.VdlHero, typography.hero5.letterSpacing)

        // Verify heading1 has VDL2 characteristics
        assertEquals(FontWeight.Black, typography.heading1.fontWeight)
        assertEquals(BpkLetterSpacing.VdlHeading1, typography.heading1.letterSpacing)
    }

    @Test
    fun `Typography factory creates default when configuration is not set`() {
        BpkConfiguration.clearConfigs()
        BpkConfiguration.setConfigs()

        // Simulate what BpkTheme does
        val typography = when (BpkConfiguration.typographySet) {
            BpkConfiguration.BpkTypographySet.DEFAULT -> BpkTypography(FontFamily.SansSerif)
            BpkConfiguration.BpkTypographySet.VDL_2 -> BpkTypography.VDL2(FontFamily.SansSerif)
        }

        // Verify headings use Bold, NOT Black (VDL2 characteristic) in default typography
        assertEquals(FontWeight.Bold, typography.heading1.fontWeight)
        assertEquals(FontWeight.Bold, typography.heading2.fontWeight)

        // Verify headings do NOT have VDL2 letter spacing
        assertNotEquals(BpkLetterSpacing.VdlHeading1, typography.heading1.letterSpacing)
        assertNotEquals(BpkLetterSpacing.VdlHeading2, typography.heading2.letterSpacing)
    }

    @Test
    fun `Typography selection changes reflect configuration changes`() {
        // Start with default
        BpkConfiguration.clearConfigs()
        BpkConfiguration.setConfigs(typography = false)

        val defaultTypography = when (BpkConfiguration.typographySet) {
            BpkConfiguration.BpkTypographySet.DEFAULT -> BpkTypography(FontFamily.SansSerif)
            BpkConfiguration.BpkTypographySet.VDL_2 -> BpkTypography.VDL2(FontFamily.SansSerif)
        }

        // Now set up VDL2
        BpkConfiguration.clearConfigs()
        BpkConfiguration.setConfigs(typography = true)

        val vdl2Typography = when (BpkConfiguration.typographySet) {
            BpkConfiguration.BpkTypographySet.DEFAULT -> BpkTypography(FontFamily.SansSerif)
            BpkConfiguration.BpkTypographySet.VDL_2 -> BpkTypography.VDL2(FontFamily.SansSerif)
        }

        // The weights should be different for heading1 (Bold vs Black)
        assertNotEquals(defaultTypography.heading1.fontWeight, vdl2Typography.heading1.fontWeight)
        assertEquals(FontWeight.Bold, defaultTypography.heading1.fontWeight)
        assertEquals(FontWeight.Black, vdl2Typography.heading1.fontWeight)
    }

    @Test
    fun `Body styles remain consistent between default and VDL2`() {
        BpkConfiguration.clearConfigs()
        BpkConfiguration.setConfigs(typography = false)

        val defaultTypography = when (BpkConfiguration.typographySet) {
            BpkConfiguration.BpkTypographySet.DEFAULT -> BpkTypography(FontFamily.SansSerif)
            BpkConfiguration.BpkTypographySet.VDL_2 -> BpkTypography.VDL2(FontFamily.SansSerif)
        }

        // Now with VDL2
        BpkConfiguration.clearConfigs()
        BpkConfiguration.setConfigs(typography = true)

        val vdl2Typography = when (BpkConfiguration.typographySet) {
            BpkConfiguration.BpkTypographySet.DEFAULT -> BpkTypography(FontFamily.SansSerif)
            BpkConfiguration.BpkTypographySet.VDL_2 -> BpkTypography.VDL2(FontFamily.SansSerif)
        }

        // Body styles should remain the same
        assertEquals(defaultTypography.bodyDefault, vdl2Typography.bodyDefault)
        assertEquals(defaultTypography.bodyLongform, vdl2Typography.bodyLongform)
    }
}