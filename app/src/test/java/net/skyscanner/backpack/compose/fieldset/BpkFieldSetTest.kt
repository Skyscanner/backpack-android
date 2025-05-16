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

package net.skyscanner.backpack.compose.fieldset

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.textfield.BpkTextField
import org.junit.Test

class BpkFieldSetTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        BpkFieldSet(
            label = "Title",
            description = "Description",
            status = BpkFieldStatus.Default,
        ) {
            BpkTextField(
                value = "",
                onValueChange = { },
                placeholder = "Placeholder",
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun noTitle() {
        snap {
            BpkFieldSet(
                description = "Description",
                status = BpkFieldStatus.Default,
            ) {
                BpkTextField(
                    value = "",
                    onValueChange = { },
                    placeholder = "Placeholder",
                )
            }
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun noDescription() {
        snap {
            BpkFieldSet(
                description = "Description",
                status = BpkFieldStatus.Default,
            ) {
                BpkTextField(
                    value = "",
                    onValueChange = { },
                    placeholder = "Placeholder",
                )
            }
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun noTitleAndDescription() {
        snap {
            BpkFieldSet(
                status = BpkFieldStatus.Default,
            ) {
                BpkTextField(
                    value = "",
                    onValueChange = { },
                    placeholder = "Placeholder",
                )
            }
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun disabled() {
        snap {
            BpkFieldSet(
                label = "Title",
                description = "Description",
                status = BpkFieldStatus.Disabled,
            ) {
                BpkTextField(
                    value = "",
                    onValueChange = { },
                    status = BpkFieldStatus.Disabled,
                    placeholder = "Placeholder",
                )
            }
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun validated() {
        snap {
            BpkFieldSet(
                label = "Title",
                description = "Description",
                status = BpkFieldStatus.Validated,
            ) {
                BpkTextField(
                    value = "",
                    onValueChange = { },
                    placeholder = "Placeholder",
                )
            }
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun error() {
        snap {
            BpkFieldSet(
                label = "Title",
                description = "Description",
                status = BpkFieldStatus.Error("Error text"),
            ) {
                BpkTextField(
                    value = "",
                    onValueChange = { },
                    placeholder = "Placeholder",
                )
            }
        }
    }
}
