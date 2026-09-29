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
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests for BpkTheme typography selection based on configuration.
 * Typography is always VDL_2 as of VDL25 defaults.
 */
class BpkThemeTest {

    @Test
    fun `Typography factory creates VDL2`() {
        val typography = when (BpkConfiguration.typographySet) {
            BpkConfiguration.BpkTypographySet.DEFAULT -> BpkTypography(FontFamily.SansSerif)
            BpkConfiguration.BpkTypographySet.VDL_2 -> BpkTypography.VDL2(FontFamily.SansSerif)
        }

        assertEquals(FontWeight.Black, typography.hero5.fontWeight)
        assertEquals(BpkLetterSpacing.VdlHero, typography.hero5.letterSpacing)

        assertEquals(FontWeight.Black, typography.heading1.fontWeight)
        assertEquals(BpkLetterSpacing.VdlHeading1, typography.heading1.letterSpacing)
    }
}
