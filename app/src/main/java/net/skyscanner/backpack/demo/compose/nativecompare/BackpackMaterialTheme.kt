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

package net.skyscanner.backpack.demo.compose.nativecompare

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkColors
import net.skyscanner.backpack.compose.tokens.BpkTypography

/**
 * Demo-only bridge: hands Backpack's GLOBAL semantic tokens (colours, type scale, corner radii) to
 * Material 3 so a stock Material component renders as Backpack without any Backpack component code
 * in between.
 *
 * Only global tokens are mapped on purpose. No button, badge or chip internal tokens: the point of
 * the comparison is to show what the platform component gives us with global tokens alone.
 * Must stay in the demo module. It is not part of the published library.
 */
@Composable
fun BackpackMaterialTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = BpkTheme.colors.toMaterialScheme(dark = isSystemInDarkTheme()),
        typography = BpkTheme.typography.toMaterialTypography(),
        shapes = bpkShapes,
        content = content,
    )
}

/** Backpack radius tokens on the Material shape scale. Buttons take [Shapes.small], Backpack's button radius. */
private val bpkShapes = Shapes(
    extraSmall = RoundedCornerShape(BpkBorderRadius.Xs),
    small = RoundedCornerShape(BpkBorderRadius.Sm),
    medium = RoundedCornerShape(BpkBorderRadius.Md),
    large = RoundedCornerShape(BpkBorderRadius.Lg),
    extraLarge = RoundedCornerShape(BpkBorderRadius.Xl),
)

/** Backpack type scale on the Material roles. Material buttons read labelLarge, which is Backpack's label1. */
private fun BpkTypography.toMaterialTypography(): Typography = Typography(
    displayLarge = hero1,
    displayMedium = hero2,
    displaySmall = hero3,
    headlineLarge = heading1,
    headlineMedium = heading2,
    headlineSmall = heading3,
    titleLarge = heading4,
    titleMedium = heading5,
    titleSmall = subheading,
    bodyLarge = bodyLongform,
    bodyMedium = bodyDefault,
    bodySmall = footnote,
    labelLarge = label1,
    labelMedium = label2,
    labelSmall = label3,
)

private fun BpkColors.toMaterialScheme(dark: Boolean): ColorScheme {
    val base = if (dark) darkColorScheme() else lightColorScheme()
    return base.copy(
        // Role map shared with iOS: see app-native/docs/semantic-token-mapping.md
        primary = corePrimary,
        onPrimary = textOnDark,
        primaryContainer = surfaceHighlight,
        onPrimaryContainer = textPrimary,
        secondary = coreAccent,
        onSecondary = textOnDark,
        tertiary = coreAccent,
        onTertiary = textOnDark,
        secondaryContainer = surfaceHighlight,
        onSecondaryContainer = textPrimary,
        background = canvas,
        onBackground = textPrimary,
        surface = surfaceDefault,
        onSurface = textPrimary,
        surfaceVariant = surfaceHighlight,
        onSurfaceVariant = textSecondary,
        surfaceContainer = surfaceDefault,
        surfaceContainerLow = surfaceDefault,
        surfaceContainerHigh = surfaceElevated,
        surfaceContainerHighest = surfaceElevated,
        inverseSurface = surfaceContrast,
        inverseOnSurface = textOnDark,
        error = statusDangerSpot,
        onError = textOnDark,
        errorContainer = surfaceHighlight,
        onErrorContainer = textError,
        outline = line,
        outlineVariant = line,
        scrim = scrim,
    )
}
