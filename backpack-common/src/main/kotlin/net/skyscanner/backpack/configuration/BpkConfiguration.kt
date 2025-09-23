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

object BpkConfiguration {
    sealed class BpkExperimentalComponent {

        data object BpkButton : BpkExperimentalComponent()

        data object BpkCard : BpkExperimentalComponent()

        data object BpkChip : BpkExperimentalComponent()
    }

    enum class BpkTypographySet {
        DEFAULT,
        VDL_2,
    }

    private var _hasSet: Boolean = false

    fun setConfigs(
        chipConfig: Boolean = false,
        buttonConfig: Boolean = false,
        cardConfig: Boolean = false,
        typography: Boolean = false,
    ) {
        if (_hasSet) {
            throw IllegalStateException("BpkConfiguration has already been set")
        }
        _hasSet = true
        if (chipConfig) {
            this.chipConfig = BpkExperimentalComponent.BpkChip
        }
        if (buttonConfig) {
            this.buttonConfig = BpkExperimentalComponent.BpkButton
        }
        if (typography) {
            this.typographySet = BpkTypographySet.VDL_2
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

    var cardConfig: BpkExperimentalComponent.BpkCard? = null
        private set

    var typographySet: BpkTypographySet = BpkTypographySet.DEFAULT
        private set
}
