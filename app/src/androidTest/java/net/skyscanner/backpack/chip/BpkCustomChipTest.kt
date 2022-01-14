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

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkCustomChipTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(36, 100)
  }

  @Test
  fun screenshotTestDefault() {
    snap(createChip().wrapInParent())
  }

  @Test
  fun screenshotTestDisabled() {
    val view = createChip()
    view.disabled = true
    snap(view.wrapInParent())
  }

  @Test
  fun screenshotTestSelected() {
    val view = createChip()
    view.isSelected = true
    snap(view.wrapInParent())
  }

  @Test
  fun screenshotTestWithIcon() {
    val view = createChip()
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    snap(view.wrapInParent())
  }

  @Test
  fun screenshotTestSelectedWithIcon() {
    val view = createChip()
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    view.isSelected = true
    snap(view.wrapInParent())
  }

  @Test
  fun screenshotTestDisabledWithIcon() {
    val view = createChip()
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    view.disabled = true
    snap(view.wrapInParent())
  }

  private fun createChip(context: Context = testContext): BpkChip =
    BpkChip(context).apply {
      text = "tag"

      chipBackgroundColor = context.getColor(R.color.bpkTochigi)
      disabledBackgroundColor = context.getColor(R.color.bpkTochigi)
      selectedBackgroundColor = context.getColor(R.color.bpkAbisko)
    }
}
