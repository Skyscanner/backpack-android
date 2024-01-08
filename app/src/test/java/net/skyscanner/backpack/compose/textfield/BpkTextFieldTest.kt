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

package net.skyscanner.backpack.compose.textfield

import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Accessibility
import org.junit.Test

class BpkTextFieldTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        BpkTextField(
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
            BpkTextField(
                value = "Value",
                onValueChange = {},
                placeholder = "Placeholder",
                status = BpkFieldStatus.Default,
                readOnly = true,
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun placeholder() {
        snap {
            BpkTextField(
                value = "",
                onValueChange = {},
                placeholder = "Placeholder",
                status = BpkFieldStatus.Default,
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun withLeadingIcon() {
        snap {
            BpkTextField(
                value = "Value",
                onValueChange = {},
                placeholder = "Placeholder",
                status = BpkFieldStatus.Default,
                icon = BpkIcon.Accessibility,
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun withClearStatus() {
        snap {
            BpkTextField(
                value = "Value",
                onValueChange = {},
                placeholder = "Placeholder",
                status = BpkFieldStatus.Default,
                icon = BpkIcon.Accessibility,
                clearAction = BpkClearAction("Clear") {},
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun singleLine() {
        snap(width = 200.dp) {
            BpkTextField(
                value = "Value ".repeat(20),
                onValueChange = {},
                placeholder = "Placeholder ".repeat(20),
                status = BpkFieldStatus.Default,
                maxLines = 1,
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun singleLinePlaceholder() {
        snap(width = 200.dp) {
            BpkTextField(
                value = "",
                onValueChange = {},
                placeholder = "Placeholder ".repeat(20),
                status = BpkFieldStatus.Default,
                maxLines = 1,
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun multiline() {
        snap(width = 200.dp) {
            BpkTextField(
                value = "Value ".repeat(20),
                onValueChange = {},
                placeholder = "Placeholder ".repeat(20),
                status = BpkFieldStatus.Default,
                maxLines = 3,
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun multilinePlaceholder() {
        snap(width = 200.dp) {
            BpkTextField(
                value = "",
                onValueChange = {},
                placeholder = "Placeholder ".repeat(20),
                status = BpkFieldStatus.Default,
                maxLines = 3,
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun disabled() {
        snap {
            BpkTextField(
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
            BpkTextField(
                value = "",
                onValueChange = {},
                placeholder = "Placeholder",
                status = BpkFieldStatus.Disabled,
            )
        }
    }

    @Test
    fun validated() {
        snap {
            BpkTextField(
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
            BpkTextField(
                value = "Value",
                onValueChange = {},
                placeholder = "Placeholder",
                status = BpkFieldStatus.Error("Error text"),
            )
        }
    }
}
