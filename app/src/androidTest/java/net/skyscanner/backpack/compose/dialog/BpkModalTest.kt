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

package net.skyscanner.backpack.compose.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.createComposeRule
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.compose.TextContentModalExample
import net.skyscanner.backpack.demo.compose.TextContentWithoutActionModalExample
import org.junit.Rule
import org.junit.Test

class BpkModalTest : BpkSnapshotTest() {

    @get:Rule
    val rule = createComposeRule()

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun textContentModal() = record {
        TextContentModalExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun textContentWithoutActionModal() = record {
        TextContentWithoutActionModalExample()
    }

    private fun record(content: @Composable () -> Unit) {
        rule.setContent { BpkTheme { content() } }

        compareScreenshot(rule.onNode(isDialog()))
    }
}
