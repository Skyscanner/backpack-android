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
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import org.junit.Test

class BpkTextAreaTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        BpkTextArea(
            value = "Value",
            onValueChange = {},
            placeholder = "Placeholder",
            status = BpkFieldStatus.Default,
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
                status = BpkFieldStatus.Default,
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
                status = BpkFieldStatus.Default,
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
                status = BpkFieldStatus.Default,
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
                status = BpkFieldStatus.Default,
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
                status = BpkFieldStatus.Disabled,
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

    @Test
    fun validated() {
        snap {
            BpkTextArea(
                value = "Value",
                onValueChange = {},
                placeholder = "Placeholder",
                status = BpkFieldStatus.Validated,
            )
        }
    }

    @Test
    fun error() {
        snap {
            BpkTextArea(
                value = "Value",
                onValueChange = {},
                placeholder = "Placeholder",
                status = BpkFieldStatus.Error("Error text"),
            )
        }
    }
}
