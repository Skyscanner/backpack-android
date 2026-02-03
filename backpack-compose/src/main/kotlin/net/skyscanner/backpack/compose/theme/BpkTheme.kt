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

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontFamily
import net.skyscanner.backpack.compose.LocalContentColor
import net.skyscanner.backpack.compose.LocalTextStyle
import net.skyscanner.backpack.compose.tokens.BpkColors
import net.skyscanner.backpack.compose.tokens.BpkShapes
import net.skyscanner.backpack.compose.tokens.BpkTypography
import net.skyscanner.backpack.configuration.BpkConfiguration

private val LocalBpkTypography = staticCompositionLocalOf<BpkTypography?> { null }
private val LocalBpkColors = staticCompositionLocalOf<BpkColors?> { null }
private val LocalBpkShapes = staticCompositionLocalOf<BpkShapes?> { null }

@Composable
fun BpkTheme(
    fontFamily: FontFamily = FontFamily.SansSerif,
    content: @Composable () -> Unit,
) {
    val typography = when (BpkConfiguration.typographySet) {
        BpkConfiguration.BpkTypographySet.DEFAULT -> BpkTypography(defaultFontFamily = fontFamily)
        BpkConfiguration.BpkTypographySet.VDL_2 -> BpkTypography.VDL2(defaultFontFamily = fontFamily)
    }
    val colors = if (isSystemInDarkTheme()) BpkColors.dark() else BpkColors.light()
    val shapes = BpkShapes()

    CompositionLocalProvider(
        LocalBpkTypography provides typography,
        LocalBpkColors provides colors,
        LocalBpkShapes provides shapes,
        LocalContentColor provides colors.textPrimary,
        LocalTextStyle provides typography.bodyDefault,
        content = content,
    )
}

object BpkTheme {

    val typography: BpkTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalBpkTypography.current ?: if (LocalInspectionMode.current) {
            // when in preview mode return a default typography object to ensure previews work
            // without wrapping it in another composable
            BpkTypography(defaultFontFamily = FontFamily.SansSerif)
        } else {
            error("BpkTheme not found in composition hierarchy")
        }

    val colors: BpkColors
        @Composable
        @ReadOnlyComposable
        get() = LocalBpkColors.current ?: if (LocalInspectionMode.current) {
            // when in preview mode return a default colour object to ensure previews work
            // without wrapping it in another composable
            if (isSystemInDarkTheme()) BpkColors.dark() else BpkColors.light()
        } else {
            error("BpkTheme not found in composition hierarchy")
        }

    val shapes: BpkShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalBpkShapes.current ?: if (LocalInspectionMode.current) {
            // when in preview mode return a default typography object to ensure previews work
            // without wrapping it in another composable
            BpkShapes()
        } else {
            error("BpkTheme not found in composition hierarchy")
        }
}
