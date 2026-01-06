package net.skyscanner.backpack.util

import androidx.annotation.StyleRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.configuration.BpkConfiguration

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

object BpkTypographySetManager {

    /**
     * Returns the appropriate theme resource based on the current typography set configuration
     */
    @StyleRes
    fun getTypographyTheme(): Int {
        return when (BpkConfiguration.typographySet) {
            BpkConfiguration.BpkTypographySet.DEFAULT -> R.style.BpkTheme
            BpkConfiguration.BpkTypographySet.VDL_2 -> R.style.BpkTheme_Typography_Vdl2
        }
    }

    /**
     * Creates a theme resource based on a base theme and the current typography set
     */
    @StyleRes
    fun applyTypographySetToTheme(@StyleRes baseTheme: Int): Int {
        return when (BpkConfiguration.typographySet) {
            BpkConfiguration.BpkTypographySet.DEFAULT -> baseTheme
            BpkConfiguration.BpkTypographySet.VDL_2 -> {
                R.style.BpkTheme_Typography_Vdl2
            }
        }
    }
}
