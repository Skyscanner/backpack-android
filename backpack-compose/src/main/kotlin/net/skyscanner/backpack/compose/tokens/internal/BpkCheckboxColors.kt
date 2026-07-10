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

// Temporary: these colors will be replaced when checkbox tokens are added
package net.skyscanner.backpack.compose.tokens.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.compose.utils.dynamicColorOf

internal object BpkCheckboxColors {

    // The onContrast style renders on the dark contrast surface, so its empty and
    // disabled states use fixed white alphas that read in both light and dark mode.
    // The checked / intermediate fill and tick reuse the theme's coreAccent and
    // textPrimaryInverse (matching the default style), so they are not defined here.

    // Component/Checkbox/border-onContrast-notChecked
    internal val onContrastBorder: Color
        @Composable
        get() = dynamicColorOf(Color(0x80FFFFFF), Color(0x80FFFFFF))

    // Component/Checkbox/border-onContrast-disabled
    internal val onContrastDisabled: Color
        @Composable
        get() = dynamicColorOf(Color(0x33FFFFFF), Color(0x33FFFFFF))
}
