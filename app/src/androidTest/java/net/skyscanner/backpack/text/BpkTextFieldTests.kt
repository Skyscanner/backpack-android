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

import androidx.appcompat.content.res.AppCompatResources
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkTextFieldTests : BpkSnapshotTest() {

  private val subject = BpkTextField(testContext).also(::init)

  private fun init(it: BpkTextField) {
    it.hint = "Hint"
    it.setText("Text")
  }

  @Test
  fun default() {
    snap(subject)
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun hint() {
    subject.setText("")
    snap(subject)
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun disabled() {
    subject.isEnabled = false
    snap(subject)
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun iconStart() {
    subject.iconStart = AppCompatResources.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun iconEnd() {
    subject.iconEnd = AppCompatResources.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun iconStart_withTint() {
    subject.setText("")
    subject.iconStart = AppCompatResources.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun iconEnd_withTint() {
    subject.setText("")
    subject.iconEnd = AppCompatResources.getDrawable(testContext, R.drawable.bpk_search)
    snap(subject)
  }
}
