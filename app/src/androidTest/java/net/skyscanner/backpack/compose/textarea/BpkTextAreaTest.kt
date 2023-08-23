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

package net.skyscanner.backpack.compose.textarea

import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkTextAreaTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        BpkTextArea(
            value = "Value",
            onValueChange = {},
            placeholder = "Placeholder",
        )
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun readOnly() {
        snap {
            BpkTextArea(
                value = "Value",
                onValueChange = {},
                placeholder = "Placeholder",
                readOnly = true,
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun placeholder() {
        snap {
            BpkTextArea(
                value = "",
                onValueChange = {},
                placeholder = "Placeholder",
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun multiline() {
        snap(width = 200.dp) {
            BpkTextArea(
                value = "Value ".repeat(20),
                onValueChange = {},
                placeholder = "Placeholder ".repeat(20),
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun multilinePlaceholder() {
        snap(width = 200.dp) {
            BpkTextArea(
                value = "",
                onValueChange = {},
                placeholder = "Placeholder ".repeat(20),
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun disabled() {
        snap {
            BpkTextArea(
                value = "Value",
                onValueChange = {},
                placeholder = "Placeholder",
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun disabledPlaceholder() {
        snap {
            BpkTextArea(
                value = "",
                onValueChange = {},
                placeholder = "Placeholder",
            )
        }
    }
}
