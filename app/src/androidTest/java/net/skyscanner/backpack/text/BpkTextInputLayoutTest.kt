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

package net.skyscanner.backpack.text

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkTextInputLayoutTest : BpkSnapshotTest() {

  private val textField = BpkTextField(testContext).apply {
    hint = "Hint"
    setText("Text")
  }

  private val subject = BpkTextInputLayout(testContext).apply {
    label = "Label"
    addView(textField)
  }

  @Before
  fun setup() {
    setBackground(android.R.color.transparent)
    setDimensions(120, 200)
  }

  @Test
  fun screenshotTestTextInputLayout_Default() {
    snap(subject)
  }

  @Test
  fun screenshotTestTextInputLayout_Disabled() {
    textField.isEnabled = false
    snap(subject)
  }

  @Test
  fun screenshotTestTextInputLayout_Error() {
    subject.error = "Error"
    snap(subject)
  }

  @Test
  fun screenshotTestTextInputLayout_Helper() {
    subject.helperText = "Helper"
    snap(subject)
  }

  @Test
  fun screenshotTestTextInputLayout_HelperAndError() {
    subject.error = "Error"
    subject.helperText = "Helper"
    snap(subject)
  }

  @Test
  fun screenshotTestTextInputLayout_ErrorNoLabel() {
    subject.label = null
    subject.error = "Error"
    snap(subject)
  }
}
