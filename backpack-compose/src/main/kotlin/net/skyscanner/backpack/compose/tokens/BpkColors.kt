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
  @Deprecated(DEPRECATION_MESSAGE)
  public val line: Color,
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
  @Deprecated(DEPRECATION_MESSAGE)
  public val systemGreen: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val systemRed: Color,
  public val textDisabled: Color,
  public val textError: Color,
  public val textLink: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val textPrimary: Color,
  public val textPrimaryInverse: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val textQuaternary: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val textSecondary: Color,
  @Deprecated(DEPRECATION_MESSAGE)
  public val textTertiary: Color,
) {
  internal companion object {
    private const val DEPRECATION_MESSAGE: String =
        "This colour is now deprecated. Please switch to the new semantic colours - see internal New Colours documentation"

    public fun light(
      background: Color = Color(0xFFFFFFFF),
      backgroundAlternative: Color = Color(0xFFF1F2F8),
      backgroundAlternativeSecondary: Color = Color(0xFFFFFFFF),
      backgroundElevation01: Color = Color(0xFFFFFFFF),
      backgroundElevation02: Color = Color(0xFFFFFFFF),
      backgroundElevation03: Color = Color(0xFFFFFFFF),
      backgroundSecondary: Color = Color(0xFFF1F2F8),
      backgroundTertiary: Color = Color(0xFFFFFFFF),
      canvas: Color = Color(0xFFFFFFFF),
      canvasContrast: Color = Color(0xFFF1F2F8),
      coreAccent: Color = Color(0xFF0770E3),
      coreEco: Color = Color(0xFF0FA1A9),
      corePrimary: Color = Color(0xFF02122C),
      line: Color = Color(0xFFCDCDD7),
      primary: Color = Color(0xFF0770E3),
      scrim: Color = Color(0xFF02122C),
      statusDangerFill: Color = Color(0xFFF6DDE1),
      statusDangerSpot: Color = Color(0xFFD1435B),
      statusSuccessFill: Color = Color(0xFFD0EEEC),
      statusSuccessSpot: Color = Color(0xFF00A698),
      statusWarningFill: Color = Color(0xFFFFEBD0),
      statusWarningSpot: Color = Color(0xFFFF9400),
      surfaceContrast: Color = Color(0xFF02122C),
      surfaceDefault: Color = Color(0xFFFFFFFF),
      surfaceElevated: Color = Color(0xFFFFFFFF),
      systemGreen: Color = Color(0xFF00A698),
      systemRed: Color = Color(0xFFD1435B),
      textDisabled: Color = Color(0xFFB2B2BF),
      textError: Color = Color(0xFFD1435B),
      textLink: Color = Color(0xFF0770E3),
      textPrimary: Color = Color(0xFF111236),
      textPrimaryInverse: Color = Color(0xFFFFFFFF),
      textQuaternary: Color = Color(0xFF8F90A0),
      textSecondary: Color = Color(0xFF68697F),
      textTertiary: Color = Color(0xFF8F90A0),
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
        systemGreen = systemGreen,
        systemRed = systemRed,
        textDisabled = textDisabled,
        textError = textError,
        textLink = textLink,
        textPrimary = textPrimary,
        textPrimaryInverse = textPrimaryInverse,
        textQuaternary = textQuaternary,
        textSecondary = textSecondary,
        textTertiary = textTertiary,
    )

    public fun dark(
      background: Color = Color(0xFF000000),
      backgroundAlternative: Color = Color(0xFF000000),
      backgroundAlternativeSecondary: Color = Color(0xFF1D1B20),
      backgroundElevation01: Color = Color(0xFF1D1B20),
      backgroundElevation02: Color = Color(0xFF2C2C2E),
      backgroundElevation03: Color = Color(0xFF3A3A3C),
      backgroundSecondary: Color = Color(0xFF1D1B20),
      backgroundTertiary: Color = Color(0xFF2C2C2E),
      canvas: Color = Color(0xFF000000),
      canvasContrast: Color = Color(0xFF1D1B20),
      coreAccent: Color = Color(0xFF6D9FEB),
      coreEco: Color = Color(0xFF0FA1A9),
      corePrimary: Color = Color(0xFF054184),
      line: Color = Color(0xFF48484A),
      primary: Color = Color(0xFF6D9FEB),
      scrim: Color = Color(0xFF02122C),
      statusDangerFill: Color = Color(0xFFFFCADD),
      statusDangerSpot: Color = Color(0xFFD1435B),
      statusSuccessFill: Color = Color(0xFFB1FFE7),
      statusSuccessSpot: Color = Color(0xFF00A698),
      statusWarningFill: Color = Color(0xFFFBF1BB),
      statusWarningSpot: Color = Color(0xFFFFB54D),
      surfaceContrast: Color = Color(0xFF1D1B20),
      surfaceDefault: Color = Color(0xFF1D1B20),
      surfaceElevated: Color = Color(0xFF2C2C2E),
      systemGreen: Color = Color(0xFF00A698),
      systemRed: Color = Color(0xFFD1435B),
      textDisabled: Color = Color(0xFF8E8E93),
      textError: Color = Color(0xFFD1435B),
      textLink: Color = Color(0xFF6D9FEB),
      textPrimary: Color = Color(0xFFFFFFFF),
      textPrimaryInverse: Color = Color(0xFF111236),
      textQuaternary: Color = Color(0xFF8E8E93),
      textSecondary: Color = Color(0xFFB2B2BF),
      textTertiary: Color = Color(0xFF8E8E93),
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
        systemGreen = systemGreen,
        systemRed = systemRed,
        textDisabled = textDisabled,
        textError = textError,
        textLink = textLink,
        textPrimary = textPrimary,
        textPrimaryInverse = textPrimaryInverse,
        textQuaternary = textQuaternary,
        textSecondary = textSecondary,
        textTertiary = textTertiary,
    )
  }
}
