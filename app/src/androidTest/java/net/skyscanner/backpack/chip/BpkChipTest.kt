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

package net.skyscanner.backpack.chip

import androidx.appcompat.content.res.AppCompatResources
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkChipTest : BpkSnapshotTest() {

  @Test
  fun default() {
    val view = BpkChip(testContext)
    view.text = "tag"
    snap(view)
  }

  @Test
  fun disabled() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.disabled = true
    snap(view)
  }

  @Test
  fun selected() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.isSelected = true
    snap(view)
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun onDark() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.style = BpkChip.Style.OnDark
    snap(view, background = R.color.bpkTextOnLight)
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun onDarkSelected() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.style = BpkChip.Style.OnDark
    view.isSelected = true
    snap(view, R.color.bpkTextOnLight)
  }

  @Test
  fun select() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.type = BpkChip.Type.Select
    snap(view)
  }

  @Test
  fun dismiss() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.type = BpkChip.Type.Dismiss
    snap(view)
  }

  @Test
  fun withIcon() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    snap(view)
  }

  @Test
  fun withIconAndSelect() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    view.type = BpkChip.Type.Select
    snap(view)
  }

  @Test
  fun selectedWithIcon() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    view.isSelected = true
    snap(view)
  }

  @Test
  fun selectedWithIconAndSelect() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    view.type = BpkChip.Type.Select
    view.isSelected = true
    snap(view)
  }

  @Test
  fun disabledWithIcon() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    view.disabled = true
    snap(view)
  }
}
