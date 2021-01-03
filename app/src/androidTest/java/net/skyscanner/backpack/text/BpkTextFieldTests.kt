/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkTextFieldTests : BpkSnapshotTest() {

  private val subject = BpkTextField(testContext).also(::init)

  private fun init(it: BpkTextField) {
    it.hint = "Hint"
    it.setText("Text")
  }

  @Before
  fun setup() {
    setDimensions(60, 200)
  }

  @Test
  fun screenshotTestTextField_Default() {
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_Hint() {
    subject.setText("")
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_Disabled() {
    subject.isEnabled = false
    snap(subject)
  }

  @Test
  @Ignore
  fun screenshotTestTextField_IconStart() {
    subject.iconStart = AppCompatResources.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  @Ignore
  fun screenshotTestTextField_IconEnd() {
    subject.iconEnd = AppCompatResources.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  @Ignore
  fun screenshotTestTextField_IconStart_withTint() {
    subject.setText("")
    subject.iconStart = AppCompatResources.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  @Ignore
  fun screenshotTestTextField_IconEnd_withTint() {
    subject.setText("")
    subject.iconEnd = AppCompatResources.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_withTheme() {
    val subject = BpkTextField(createThemedContext(testContext)).also(::init)
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_Default_RTL() {
    subject.layoutDirection = View.LAYOUT_DIRECTION_RTL
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_Hint_RTL() {
    subject.layoutDirection = View.LAYOUT_DIRECTION_RTL
    subject.setText("")
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_Disabled_RTL() {
    subject.layoutDirection = View.LAYOUT_DIRECTION_RTL
    subject.isEnabled = false
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_IconStart_RTL() {
    subject.layoutDirection = View.LAYOUT_DIRECTION_RTL
    subject.iconStart = AppCompatResources.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_IconEnd_RTL() {
    subject.layoutDirection = View.LAYOUT_DIRECTION_RTL
    subject.iconEnd = AppCompatResources.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_IconStart_withTint_RTL() {
    subject.layoutDirection = View.LAYOUT_DIRECTION_RTL
    subject.setText("")
    subject.iconStart = AppCompatResources.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_IconEnd_withTint_RTL() {
    subject.layoutDirection = View.LAYOUT_DIRECTION_RTL
    subject.setText("")
    subject.iconEnd = AppCompatResources.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  fun screenshotTestTextField_withTheme_RTL() {
    val subject = BpkTextField(createThemedContext(testContext)).also(::init)
    subject.layoutDirection = View.LAYOUT_DIRECTION_RTL
    snap(subject)
  }
}
