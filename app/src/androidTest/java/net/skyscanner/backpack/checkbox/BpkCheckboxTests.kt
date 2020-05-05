/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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

package net.skyscanner.backpack.checkbox

import android.graphics.Color
import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkCheckboxTests : BpkSnapshotTest() {

  private val checkbox = BpkCheckbox(testContext).apply {
    text = "Checkbox"
    setBackgroundColor(Color.WHITE)
  }

  @Before
  fun setup() {
    setDimensions(40, 120)
  }

  @Test
  fun screenshotTestCheckbox_Default() {
    snap(checkbox)
  }

  @Test
  fun screenshotTestCheckbox_Checked() {
    checkbox.isChecked = true
    snap(checkbox)
  }

  @Test
  fun screenshotTestCheckbox_Disabled() {
    checkbox.isEnabled = false
    snap(checkbox)
  }

  @Test
  fun screenshotTestCheckbox_CheckedDisabled() {
    checkbox.isEnabled = false
    checkbox.isChecked = true
    snap(checkbox)
  }

  @Test
  fun screenshotTestCheckbox_rtl() {
    checkbox.layoutDirection = View.LAYOUT_DIRECTION_RTL
    snap(checkbox)
  }

  @Test
  fun screenshotTestCheckbox_withTheme() {
    val checkbox = BpkCheckbox(createThemedContext(testContext)).apply {
      setBackgroundColor(Color.WHITE)
      text = "Checkbox"
    }
    checkbox.isChecked = true
    snap(checkbox)
  }
}
