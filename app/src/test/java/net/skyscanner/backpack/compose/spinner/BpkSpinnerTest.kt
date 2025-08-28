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

package net.skyscanner.backpack.compose.spinner

import androidx.compose.runtime.CompositionLocalProvider
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.LocalContentColor
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Test

class BpkSpinnerTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        BpkSpinner(size = BpkSpinnerSize.Large)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun small() {
        snap {
            BpkSpinner(size = BpkSpinnerSize.Small)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun textPrimary() {
        snap {
            BpkSpinner(size = BpkSpinnerSize.Large, style = BpkSpinnerStyle.TextPrimary)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun disabled() {
        snap {
            BpkSpinner(size = BpkSpinnerSize.Large, style = BpkSpinnerStyle.Disabled)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun onDarkSurface() {
        snap(background = { BpkTheme.colors.surfaceContrast }) {
            BpkSpinner(size = BpkSpinnerSize.Large, style = BpkSpinnerStyle.OnDarkSurface)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun dynamicOnDarkSurface() {
        snap(background = { BpkTheme.colors.surfaceContrast }) {
            CompositionLocalProvider(LocalContentColor provides BpkTheme.colors.textOnDark) {
                BpkSpinner(size = BpkSpinnerSize.Large)
            }
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun dynamicTextPrimary() {
        snap {
            CompositionLocalProvider(LocalContentColor provides BpkTheme.colors.textOnLight) {
                BpkSpinner(size = BpkSpinnerSize.Large)
            }
        }
    }
}
