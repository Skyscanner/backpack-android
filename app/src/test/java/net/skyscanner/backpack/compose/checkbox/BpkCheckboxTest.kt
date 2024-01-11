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

package net.skyscanner.backpack.compose.checkbox

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.compose.CheckedCheckboxSample
import net.skyscanner.backpack.demo.compose.CustomContentCheckboxSample
import net.skyscanner.backpack.demo.compose.DefaultCheckboxSample
import net.skyscanner.backpack.demo.compose.DisabledCheckedCheckboxSample
import net.skyscanner.backpack.demo.compose.DisabledUncheckedCheckboxSample
import net.skyscanner.backpack.demo.compose.IntermediateCheckboxSample
import net.skyscanner.backpack.demo.compose.UncheckedCheckboxSample
import org.junit.Test

class BpkCheckboxTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        DefaultCheckboxSample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun intermediate() {
        snap {
            IntermediateCheckboxSample()
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun unchecked() {
        snap {
            UncheckedCheckboxSample()
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun checked() {
        snap {
            CheckedCheckboxSample()
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun disabledUnchecked() {
        snap {
            DisabledUncheckedCheckboxSample()
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun disabledChecked() {
        snap {
            DisabledCheckedCheckboxSample()
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun customContent() {
        snap {
            CustomContentCheckboxSample()
        }
    }
}
