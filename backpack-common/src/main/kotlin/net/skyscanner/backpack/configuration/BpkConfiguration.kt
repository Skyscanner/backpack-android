/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2025 Skyscanner Ltd
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

import androidx.annotation.ColorInt
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import net.skyscanner.backpack.common.R

object BpkConfiguration {
    sealed class BpkExperimentalComponent {

        data class BpkButton(
            val legacyStyle: LegacyStyle? = null,
            val backgroundColor: Color? = null,
        ) : BpkExperimentalComponent() {
            data class LegacyStyle(
                @ColorInt val bgColor: Int,
                @ColorInt val bgPressedColor: Int,
                @ColorInt val bgLoadingColor: Int,
                @ColorInt val bgDisabledColor: Int,
                @ColorInt val contentColor: Int,
                @ColorInt val contentPressedColor: Int,
                @ColorInt val contentDisabledColor: Int,
                @ColorInt val contentLoadingColor: Int,
                @ColorInt val rippleColor: Int,
            )
        }

        data object BpkText : BpkExperimentalComponent()

        data object BpkCard : BpkExperimentalComponent()

        data object BpkChip : BpkExperimentalComponent()
    }

    private var _hasSet: Boolean = false

    var logging: (() -> Unit)? = null

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
            this.buttonConfig = BpkExperimentalComponent.BpkButton(
                null,
                colorResource(R.color.bpkTextSuccess),
            )
        }
        if (textConfig) {
            this.textConfig = BpkExperimentalComponent.BpkText
        }
        if (cardConfig) {
            this.cardConfig = BpkExperimentalComponent.BpkCard
        }
    }

    var chipConfig: BpkExperimentalComponent.BpkChip? = null
        private set

    fun performLogging() {
        logging?.invoke()
        logging = null
    }

    var buttonConfig: BpkExperimentalComponent.BpkButton? = null
        private set

    var textConfig: BpkExperimentalComponent.BpkText? = null
        private set

    var cardConfig: BpkExperimentalComponent.BpkCard? = null
        private set
}
