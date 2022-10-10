/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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

// Auto-generated: do not edit
@file:Suppress("RedundantVisibilityModifier","unused")

package net.skyscanner.backpack.compose.tokens

import androidx.compose.ui.graphics.Color
import kotlin.Boolean
import kotlin.Deprecated
import kotlin.String

public class BpkColors private constructor(
  public val isLight: Boolean,
  @Deprecated(DEPRECATION_MESSAGE)
  public val background: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val backgroundAlternative: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val backgroundAlternativeSecondary: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val backgroundElevation01: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val backgroundElevation02: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val backgroundElevation03: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val backgroundSecondary: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val backgroundTertiary: Color,
  public val canvas: Color,
  public val canvasContrast: Color,
  public val coreAccent: Color,
  public val coreEco: Color,
  public val corePrimary: Color,
  public val line: Color,
  public val lineOnDark: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val primary: Color,
  public val scrim: Color,
  public val statusDangerFill: Color,
  public val statusDangerSpot: Color,
  public val statusSuccessFill: Color,
  public val statusSuccessSpot: Color,
  public val statusWarningFill: Color,
  public val statusWarningSpot: Color,
  public val surfaceContrast: Color,
  public val surfaceDefault: Color,
  public val surfaceElevated: Color,
  public val surfaceHighlight: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val systemGreen: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val systemRed: Color,
  public val textDisabled: Color,
  public val textError: Color,
  public val textLink: Color,
  public val textOnDark: Color,
  public val textOnLight: Color,
  public val textPrimary: Color,
  public val textPrimaryInverse: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val textQuaternary: Color,
  public val textSecondary: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val textTertiary: Color,
) {
  internal companion object {
    private const val DEPRECATION_MESSAGE: String =
        "This colour is now deprecated. Please switch to the new semantic colours - see internal New Colours documentation"

    public fun light(
      background: Color = Color(0xFFFFFFFF),
      backgroundAlternative: Color = Color(0xFFEFF1F2),
      backgroundAlternativeSecondary: Color = Color(0xFFFFFFFF),
      backgroundElevation01: Color = Color(0xFFFFFFFF),
      backgroundElevation02: Color = Color(0xFFFFFFFF),
      backgroundElevation03: Color = Color(0xFFFFFFFF),
      backgroundSecondary: Color = Color(0xFFEFF1F2),
      backgroundTertiary: Color = Color(0xFFFFFFFF),
      canvas: Color = Color(0xFFFFFFFF),
      canvasContrast: Color = Color(0xFFEFF1F2),
      coreAccent: Color = Color(0xFF0062E3),
      coreEco: Color = Color(0xFF0FA1A9),
      corePrimary: Color = Color(0xFF05203C),
      line: Color = Color(0xFFC2C9CD),
      lineOnDark: Color = Color(0x80FFFFFF),
      primary: Color = Color(0xFF0062E3),
      scrim: Color = Color(0xB3000000),
      statusDangerFill: Color = Color(0xFFFFE9F9),
      statusDangerSpot: Color = Color(0xFFE70866),
      statusSuccessFill: Color = Color(0xFFD4FFF2),
      statusSuccessSpot: Color = Color(0xFF0C838A),
      statusWarningFill: Color = Color(0xFFFFF7CF),
      statusWarningSpot: Color = Color(0xFFF55D42),
      surfaceContrast: Color = Color(0xFF05203C),
      surfaceDefault: Color = Color(0xFFFFFFFF),
      surfaceElevated: Color = Color(0xFFFFFFFF),
      surfaceHighlight: Color = Color(0xFFE0E3E5),
      systemGreen: Color = Color(0xFF0C838A),
      systemRed: Color = Color(0xFFE70866),
      textDisabled: Color = Color(0x33000000),
      textError: Color = Color(0xFFE70866),
      textLink: Color = Color(0xFF0062E3),
      textOnDark: Color = Color(0xFFFFFFFF),
      textOnLight: Color = Color(0xFF161616),
      textPrimary: Color = Color(0xFF161616),
      textPrimaryInverse: Color = Color(0xFFFFFFFF),
      textQuaternary: Color = Color(0x33000000),
      textSecondary: Color = Color(0xFF545860),
      textTertiary: Color = Color(0x33000000),
    ) = BpkColors(
        isLight = true,
        background = background,
        backgroundAlternative = backgroundAlternative,
        backgroundAlternativeSecondary = backgroundAlternativeSecondary,
        backgroundElevation01 = backgroundElevation01,
        backgroundElevation02 = backgroundElevation02,
        backgroundElevation03 = backgroundElevation03,
        backgroundSecondary = backgroundSecondary,
        backgroundTertiary = backgroundTertiary,
        canvas = canvas,
        canvasContrast = canvasContrast,
        coreAccent = coreAccent,
        coreEco = coreEco,
        corePrimary = corePrimary,
        line = line,
        lineOnDark = lineOnDark,
        primary = primary,
        scrim = scrim,
        statusDangerFill = statusDangerFill,
        statusDangerSpot = statusDangerSpot,
        statusSuccessFill = statusSuccessFill,
        statusSuccessSpot = statusSuccessSpot,
        statusWarningFill = statusWarningFill,
        statusWarningSpot = statusWarningSpot,
        surfaceContrast = surfaceContrast,
        surfaceDefault = surfaceDefault,
        surfaceElevated = surfaceElevated,
        surfaceHighlight = surfaceHighlight,
        systemGreen = systemGreen,
        systemRed = systemRed,
        textDisabled = textDisabled,
        textError = textError,
        textLink = textLink,
        textOnDark = textOnDark,
        textOnLight = textOnLight,
        textPrimary = textPrimary,
        textPrimaryInverse = textPrimaryInverse,
        textQuaternary = textQuaternary,
        textSecondary = textSecondary,
        textTertiary = textTertiary,
    )

    public fun dark(
      background: Color = Color(0xFF010913),
      backgroundAlternative: Color = Color(0xFF131D2B),
      backgroundAlternativeSecondary: Color = Color(0xFF010913),
      backgroundElevation01: Color = Color(0xFF131D2B),
      backgroundElevation02: Color = Color(0xFF243346),
      backgroundElevation03: Color = Color(0xFF243346),
      backgroundSecondary: Color = Color(0xFF131D2B),
      backgroundTertiary: Color = Color(0xFF010913),
      canvas: Color = Color(0xFF010913),
      canvasContrast: Color = Color(0xFF131D2B),
      coreAccent: Color = Color(0xFF84E9FF),
      coreEco: Color = Color(0xFF0FA1A9),
      corePrimary: Color = Color(0xFF054184),
      line: Color = Color(0xFF44505F),
      lineOnDark: Color = Color(0xFF44505F),
      primary: Color = Color(0xFF84E9FF),
      scrim: Color = Color(0xB3000000),
      statusDangerFill: Color = Color(0xFFFFCADD),
      statusDangerSpot: Color = Color(0xFFFF649C),
      statusSuccessFill: Color = Color(0xFFB1FFE7),
      statusSuccessSpot: Color = Color(0xFF62F1C6),
      statusWarningFill: Color = Color(0xFFFBF1BB),
      statusWarningSpot: Color = Color(0xFFFEEB87),
      surfaceContrast: Color = Color(0xFF131D2B),
      surfaceDefault: Color = Color(0xFF131D2B),
      surfaceElevated: Color = Color(0xFF243346),
      surfaceHighlight: Color = Color(0xFF243346),
      systemGreen: Color = Color(0xFF0C838A),
      systemRed: Color = Color(0xFFE70866),
      textDisabled: Color = Color(0x33FFFFFF),
      textError: Color = Color(0xFFFF649C),
      textLink: Color = Color(0xFF84E9FF),
      textOnDark: Color = Color(0xFFFFFFFF),
      textOnLight: Color = Color(0xFF161616),
      textPrimary: Color = Color(0xFFFFFFFF),
      textPrimaryInverse: Color = Color(0xFF161616),
      textQuaternary: Color = Color(0x33FFFFFF),
      textSecondary: Color = Color(0xFFBDC4CB),
      textTertiary: Color = Color(0x33FFFFFF),
    ) = BpkColors(
        isLight = false,
        background = background,
        backgroundAlternative = backgroundAlternative,
        backgroundAlternativeSecondary = backgroundAlternativeSecondary,
        backgroundElevation01 = backgroundElevation01,
        backgroundElevation02 = backgroundElevation02,
        backgroundElevation03 = backgroundElevation03,
        backgroundSecondary = backgroundSecondary,
        backgroundTertiary = backgroundTertiary,
        canvas = canvas,
        canvasContrast = canvasContrast,
        coreAccent = coreAccent,
        coreEco = coreEco,
        corePrimary = corePrimary,
        line = line,
        lineOnDark = lineOnDark,
        primary = primary,
        scrim = scrim,
        statusDangerFill = statusDangerFill,
        statusDangerSpot = statusDangerSpot,
        statusSuccessFill = statusSuccessFill,
        statusSuccessSpot = statusSuccessSpot,
        statusWarningFill = statusWarningFill,
        statusWarningSpot = statusWarningSpot,
        surfaceContrast = surfaceContrast,
        surfaceDefault = surfaceDefault,
        surfaceElevated = surfaceElevated,
        surfaceHighlight = surfaceHighlight,
        systemGreen = systemGreen,
        systemRed = systemRed,
        textDisabled = textDisabled,
        textError = textError,
        textLink = textLink,
        textOnDark = textOnDark,
        textOnLight = textOnLight,
        textPrimary = textPrimary,
        textPrimaryInverse = textPrimaryInverse,
        textQuaternary = textQuaternary,
        textSecondary = textSecondary,
        textTertiary = textTertiary,
    )
  }
}
