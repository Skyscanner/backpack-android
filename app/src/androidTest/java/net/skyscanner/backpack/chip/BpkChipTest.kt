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

package net.skyscanner.backpack.chip

import androidx.appcompat.content.res.AppCompatResources
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkChipTest : BpkSnapshotTest() {
  @Before
  fun setup() {
    setDimensions(36, 100)
  }

  @Test
  fun screenshotTestDefault() {
    val view = BpkChip(testContext)
    view.text = "tag"
    snap(view.wrapInParent())
  }

  @Test
  fun screenshotTestDisabled() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.disabled = true
    snap(view.wrapInParent())
  }

  @Test
  fun screenshotTestSelected() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.isSelected = true
    snap(view.wrapInParent())
  }

  @Test
  fun screenshotTestCustomBackground() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.chipBackgroundColor = testContext.getColor(R.color.bpkErfoud)
    snap(view.wrapInParent())
  }

  @Test
  fun screenshotTestCustomSelectedBackground() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.selectedBackgroundColor = testContext.getColor(R.color.bpkPanjin)
    view.toggle()
    snap(view.wrapInParent())
  }

  @Test
  fun screenshotTestWithIcon() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    snap(view.wrapInParent())
  }

  @Test
  fun screenshotTestSelectedWithIcon() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    view.isSelected = true
    snap(view.wrapInParent())
  }

  @Test
  fun screenshotTestDisabledWithIcon() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    view.disabled = true
    snap(view.wrapInParent())
  }
}
