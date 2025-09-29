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
import net.skyscanner.backpack.util.InternalBackpackApi

object BpkConfiguration {
    sealed class BpkExperimentalComponent {

        data class BpkButton(
            val cornerRadius: Dp = 999.dp,
            val secondaryBackgroundColor: Color = Color(0xFFE3F0FF),
            val secondaryPressedBackgroundColor: Color = Color(0xFFB4D7FF),
            val secondaryTextColor: Color = Color(0xFF024DAF),
            val largeHeight: Dp = 56.dp,
            val defaultPaddingHorizontal: Dp = 16.dp, // Based on Figma design
            val largePaddingHorizontal: Dp = 24.dp, // Based on Figma design
        ) : BpkExperimentalComponent()

        data object BpkText : BpkExperimentalComponent()

        data object BpkCard : BpkExperimentalComponent()

        data object BpkChip : BpkExperimentalComponent()
    }

    private var _hasSet: Boolean = false

    // To allow testing, but shouldn't be used in production
    @InternalBackpackApi
    fun clearConfigs() {
        chipConfig = null
        buttonConfig = null
        textConfig = null
        cardConfig = null

        _hasSet = false
    }

    fun setConfigs(
        chipConfig: Boolean = false,
        buttonConfig: Boolean = false,
        textConfig: Boolean = false,
        cardConfig: Boolean = false,
    ) {
        if (_hasSet) {
            throw IllegalStateException("BpkConfiguration has already been set")
        }
        _hasSet = true
        if (chipConfig) {
            this.chipConfig = BpkExperimentalComponent.BpkChip
        }
        if (buttonConfig) {
            this.buttonConfig = BpkExperimentalComponent.BpkButton()
        }
        if (textConfig) {
            this.textConfig = BpkExperimentalComponent.BpkText
        }
        if (cardConfig) {
            this.cardConfig = BpkExperimentalComponent.BpkCard
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

    var textConfig: BpkExperimentalComponent.BpkText? = null
        private set

    var cardConfig: BpkExperimentalComponent.BpkCard? = null
        private set
}
