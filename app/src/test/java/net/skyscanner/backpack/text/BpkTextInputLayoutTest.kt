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

package net.skyscanner.backpack.text

import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import org.junit.Test

class BpkTextInputLayoutTest : BpkSnapshotTest() {

    private val textField = BpkTextField(testContext).apply {
        hint = "Hint"
        setText("Text")
    }

    private val subject = BpkTextInputLayout(testContext).apply {
        label = "Label"
        addView(textField)
    }

    @Test
    fun default() {
        snap(subject)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun disabled() {
        textField.isEnabled = false
        snap(subject)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun error() {
        subject.error = "Error"
        snap(subject)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun helper() {
        subject.helperText = "Helper"
        snap(subject)
    }

    @Test
    fun helperAndError() {
        subject.error = "Error"
        subject.helperText = "Helper"
        snap(subject)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun errorNoLabel() {
        subject.label = null
        subject.error = "Error"
        snap(subject)
    }
}
