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

package net.skyscanner.backpack.configuration

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.util.ColorPair
import net.skyscanner.backpack.util.InternalBackpackApi

object BpkConfiguration {
    sealed class BpkExperimentalComponent {

        data class BpkButton(
            val cornerRadius: Dp = 999.dp,
            val secondaryBackgroundColorLight: Color = Color(0xFFE3F0FF),
            val secondaryBackgroundColorDark: Color = Color(0xFF243346),
            val secondaryPressedBackgroundColorLight: Color = Color(0xFFB4D7FF),
            val secondaryPressedBackgroundColorDark: Color = Color(0xFF010913),
            val secondaryTextColorLight: Color = Color(0xFF024DAF),
            val secondaryTextColorDark: Color = Color(0xFFFFFFFF),
            val largeHeight: Dp = 56.dp,
            val defaultPaddingHorizontal: Dp = 16.dp, // Based on Figma design
            val largePaddingHorizontal: Dp = 24.dp, // Based on Figma design
        ) : BpkExperimentalComponent()

        data class BpkCard(
            val defaultElevation: Float = 0.0f,
        ) : BpkExperimentalComponent()

        data class BpkChip(
            val cornerRadius: Dp = 999.dp,
            val height: Dp = 36.dp,

            // Default
            val contentColorDefault: ColorPair = ColorPair(0xFF161616, 0xFFFFFFFF),
            val backgroundColorDefault: ColorPair = ColorPair(0x00000000, 0x00000000),
            val strokeColorDefault: ColorPair = ColorPair(0xFFC1C7CF, 0x33FFFFFF),
            val disabledBackgroundColorDefault: ColorPair = ColorPair(0xFFE0E3E5, 0xFF0B121D),
            val pressedBackgroundColorDefault: ColorPair = ColorPair(0x00000000, 0xFF131D2B),
            val pressedStrokeColorDefault: ColorPair = ColorPair(0xFF0062E3, 0xFF84E9FF),
            val selectedBackgroundColorDefault: ColorPair = ColorPair(0xFF0062E3, 0xFF84E9FF),
            val selectedContentColorDefault: ColorPair = ColorPair(0xFFFFFFFF, 0xFF010913),
            val dismissibleTrailingIconColorDefault: ColorPair = ColorPair(0xFFFFFFFF, 0xFF010913),
            val dismissibleTrailingIconPressedColorDefault: ColorPair = ColorPair(0xFFFFFFFF, 0xFF010913),

            // On Dark
            val contentColorOnDark: ColorPair = ColorPair(0xFFFFFFFF, 0xFFFFFFFF),
            val backgroundColorOnDark: ColorPair = ColorPair(0xFF154679, 0xFF024DAF),
            val strokeColorOnDark: ColorPair = ColorPair(0x00000000, 0x00000000),
            val disabledBackgroundColorOnDark: ColorPair = ColorPair(0xFFE0E3E5, 0xFF0B121D),
            val pressedBackgroundColorOnDark: ColorPair = ColorPair(0xFF154679, 0xFF024DAF),
            val pressedStrokeColorOnDark: ColorPair = ColorPair(0xFFFFFFFF, 0xFFFFFFFF),
            val pressedTrailingIconColorOnDark: ColorPair = ColorPair(0xFFFFFFFF, 0xFFFFFFFF),
            val selectedBackgroundColorOnDark: ColorPair = ColorPair(0xFFFFFFFF, 0xFFFFFFFF),
            val selectedContentColorOnDark: ColorPair = ColorPair(0xFF161616, 0xFF161616),
            val dismissibleTrailingIconColorOnDark: ColorPair = ColorPair(0xFF161616, 0xFF161616),
            val dismissibleTrailingIconPressedColorOnDark: ColorPair = ColorPair(0xFF161616, 0xFF010913),

            // On Image
            val contentColorOnImage: ColorPair = ColorPair(0xFF161616, 0xFFFFFFFF),
            val backgroundColorOnImage: ColorPair = ColorPair(0xFFFFFFFF, 0xFF131D2B),
            val strokeColorOnImage: ColorPair = ColorPair(0x00000000, 0x00000000),
            val disabledBackgroundColorOnImage: ColorPair = ColorPair(0xFFE0E3E5, 0xFF0B121D),
            val pressedBackgroundColorOnImage: ColorPair = ColorPair(0xFFEFF3F8, 0xFF131D2B),
            val pressedStrokeColorOnImage: ColorPair = ColorPair(0x00000000, 0x00000000),
            val selectedBackgroundColorOnImage: ColorPair = ColorPair(0xFF05203C, 0xFF054184),
            val selectedContentColorOnImage: ColorPair = ColorPair(0xFFFFFFFF, 0xFFFFFFFF),
            val dismissibleTrailingIconColorOnImage: ColorPair = ColorPair(0xFFFFFFFF, 0xFFFFFFFF),
        ) : BpkExperimentalComponent()

        data class BpkBadge(
            val backgroundColor: Color = Color.Transparent,
            val endPadding: Dp = 8.dp,
        ) : BpkExperimentalComponent()
    }

    enum class BpkTypographySet {
        DEFAULT,
        VDL_2,
    }

    private var _hasSet: Boolean = false

    // To allow testing, but shouldn't be used in production
    @InternalBackpackApi
    fun clearConfigs() {
        chipConfig = null
        buttonConfig = null
        cardConfig = null
        typographySet = BpkTypographySet.DEFAULT
        badgeConfig = null

        _hasSet = false
    }

    fun setConfigs(
        chipConfig: Boolean = false,
        buttonConfig: Boolean = false,
        cardConfig: Boolean = false,
        badgeConfig: Boolean = false,
        typography: Boolean = false,
    ) {
        if (_hasSet) {
            throw IllegalStateException("BpkConfiguration has already been set")
        }
        _hasSet = true
        if (chipConfig) {
            this.chipConfig = BpkExperimentalComponent.BpkChip()
        }
        if (buttonConfig) {
            this.buttonConfig = BpkExperimentalComponent.BpkButton()
        }
        if (typography) {
            this.typographySet = BpkTypographySet.VDL_2
        }
        if (cardConfig) {
            this.cardConfig = BpkExperimentalComponent.BpkCard()
        }
        if (badgeConfig) {
            this.badgeConfig = BpkExperimentalComponent.BpkBadge()
        }
    }

    var logger: (() -> Unit)? = null

    fun performLogging() {
        logger?.invoke()
        logger = null
    }

    var chipConfig: BpkExperimentalComponent.BpkChip? = null
        private set

    var buttonConfig: BpkExperimentalComponent.BpkButton? = null
        private set

    var cardConfig: BpkExperimentalComponent.BpkCard? = null
        private set
    var badgeConfig: BpkExperimentalComponent.BpkBadge? = null
        private set

    var typographySet: BpkTypographySet = BpkTypographySet.DEFAULT
        private set
}
