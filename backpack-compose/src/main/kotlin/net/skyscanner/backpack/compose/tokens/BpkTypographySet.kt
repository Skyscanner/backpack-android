/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.tokens

import androidx.compose.ui.text.font.FontFamily
import net.skyscanner.backpack.configuration.BpkConfiguration

/**
 * Provider for typography sets in Compose
 */
object BpkTypographySet {

    /**
     * Creates a BpkTypography instance based on the current configuration
     */
    fun create(
        defaultFontFamily: FontFamily = FontFamily.SansSerif,
        alternativeFontFamily1: FontFamily = FontFamily.Serif,
        alternativeFontFamily2: FontFamily = FontFamily.Monospace,
    ): BpkTypography {
        val fontFamily = when (BpkConfiguration.typographySet) {
            BpkConfiguration.BpkTypographySet.DEFAULT -> defaultFontFamily
            BpkConfiguration.BpkTypographySet.VDL_2 -> defaultFontFamily
        }
        return BpkTypography(defaultFontFamily = fontFamily)
    }

    /**
     * Creates a BpkTypography instance with custom font families for each typography set
     */
    fun create(
        fontFamilies: Map<BpkConfiguration.BpkTypographySet, FontFamily>,
    ): BpkTypography {
        val fontFamily = fontFamilies[BpkConfiguration.typographySet]
            ?: fontFamilies[BpkConfiguration.BpkTypographySet.DEFAULT]
            ?: FontFamily.SansSerif
        return BpkTypography(defaultFontFamily = fontFamily)
    }
}