// Backpack for Android - Skyscanner's Design System
// Copyright 2018-2021 Skyscanner Ltd
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// Auto-generated: do not edit
@file:Suppress("RedundantVisibilityModifier","unused")

package net.skyscanner.backpack.compose.tokens

import androidx.compose.ui.graphics.Color
import kotlin.Boolean

public class BpkColors private constructor(
  public val isLight: Boolean,
  public val background: Color,
  public val backgroundAlternative: Color,
  public val backgroundAlternativeSecondary: Color,
  public val backgroundElevation01: Color,
  public val backgroundElevation02: Color,
  public val backgroundElevation03: Color,
  public val backgroundSecondary: Color,
  public val backgroundTertiary: Color,
  public val line: Color,
  public val primary: Color,
  public val systemGreen: Color,
  public val systemRed: Color,
  public val textPrimary: Color,
  public val textQuaternary: Color,
  public val textSecondary: Color,
  public val textTertiary: Color
) {
  internal companion object {
    public fun light(
      background: Color = BpkColor.White,
      backgroundAlternative: Color = BpkColor.SkyGrayTint07,
      backgroundAlternativeSecondary: Color = BpkColor.White,
      backgroundElevation01: Color = BpkColor.White,
      backgroundElevation02: Color = BpkColor.White,
      backgroundElevation03: Color = BpkColor.White,
      backgroundSecondary: Color = BpkColor.SkyGrayTint07,
      backgroundTertiary: Color = BpkColor.White,
      line: Color = BpkColor.SkyGrayTint05,
      primary: Color = BpkColor.SkyBlue,
      systemGreen: Color = BpkColor.Monteverde,
      systemRed: Color = BpkColor.Panjin,
      textPrimary: Color = BpkColor.SkyGray,
      textQuaternary: Color = BpkColor.SkyGrayTint03,
      textSecondary: Color = BpkColor.SkyGrayTint02,
      textTertiary: Color = BpkColor.SkyGrayTint03
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
        line = line,
        primary = primary,
        systemGreen = systemGreen,
        systemRed = systemRed,
        textPrimary = textPrimary,
        textQuaternary = textQuaternary,
        textSecondary = textSecondary,
        textTertiary = textTertiary,
    )

    public fun dark(
      background: Color = BpkColor.Black,
      backgroundAlternative: Color = BpkColor.Black,
      backgroundAlternativeSecondary: Color = BpkColor.BlackTint01,
      backgroundElevation01: Color = BpkColor.BlackTint01,
      backgroundElevation02: Color = BpkColor.BlackTint02,
      backgroundElevation03: Color = BpkColor.BlackTint03,
      backgroundSecondary: Color = BpkColor.BlackTint01,
      backgroundTertiary: Color = BpkColor.BlackTint02,
      line: Color = BpkColor.BlackTint04,
      primary: Color = BpkColor.SkyBlueTint01,
      systemGreen: Color = BpkColor.Monteverde,
      systemRed: Color = BpkColor.Panjin,
      textPrimary: Color = BpkColor.White,
      textQuaternary: Color = BpkColor.BlackTint06,
      textSecondary: Color = BpkColor.BlackTint06,
      textTertiary: Color = BpkColor.BlackTint06
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
        line = line,
        primary = primary,
        systemGreen = systemGreen,
        systemRed = systemRed,
        textPrimary = textPrimary,
        textQuaternary = textQuaternary,
        textSecondary = textSecondary,
        textTertiary = textTertiary,
    )
  }
}
