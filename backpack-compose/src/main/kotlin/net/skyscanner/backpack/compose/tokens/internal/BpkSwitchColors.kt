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

// Temporary: these colors will be replaced when switch tokens are added
package net.skyscanner.backpack.compose.tokens.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.compose.utils.dynamicColorOf

internal object BpkSwitchColors {

    internal val onContrastOff: Color
        @Composable
        get() = dynamicColorOf(Color(0xCCFFFFFF), Color(0xCCFFFFFF))

    internal val defaultDisabled: Color
        @Composable
        get() = dynamicColorOf(Color(0x33000000), Color(0x1AFFFFFF))

    internal val onContrastFillDisabled: Color
        @Composable
        get() = dynamicColorOf(Color(0x1AFFFFFF), Color(0x1AFFFFFF))

    internal val knobDisabled: Color
        @Composable
        get() = dynamicColorOf(Color(0x80FFFFFF), Color(0x33FFFFFF))
}
