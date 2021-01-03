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

import android.content.Context
import android.util.LayoutDirection
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Ignore
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
    snap(createChip())
  }

  @Test
  fun screenshotTestDisabled() {
    val view = createChip()
    view.disabled = true
    snap(view)
  }

  @Test
  fun screenshotTestSelected() {
    val view = createChip()
    view.isSelected = true
    snap(view)
  }

  @Test
  fun screenshotTestSelected_withTheme() {
    val view = createChip(createThemedContext(testContext))
    view.isSelected = true
    snap(view)
  }

  @Test
  @Ignore("TODO: relative drawables do not work with the screenshot test lib")
  fun screenshotTestWithIcon() {
    val view = createChip()
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    snap(view)
  }

  @Test
  @Ignore("TODO: relative drawables do not work with the screenshot test lib")
  fun screenshotTestSelectedWithIcon() {
    val view = createChip()
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    view.isSelected = true
    snap(view)
  }

  @Test
  @Ignore("TODO: relative drawables do not work with the screenshot test lib")
  fun screenshotTestDisabledWithIcon() {
    val view = createChip()
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    view.disabled = true
    snap(view)
  }

  @Test
  @Ignore("TODO: relative drawables do not work with the screenshot test lib")
  fun screenshotTestWithIcon_RTL() {
    val view = createChip()
    view.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_account)
    view.layoutDirection = LayoutDirection.RTL
    snap(view)
  }

  private fun createChip(context: Context = testContext): BpkChip =
    BpkChip(context).apply {
      text = "tag"

      chipBackgroundColor = ContextCompat.getColor(context, R.color.bpkTochigi)
      disabledBackgroundColor = ContextCompat.getColor(context, R.color.bpkTochigi)
      selectedBackgroundColor = ContextCompat.getColor(context, R.color.bpkAbisko)
    }
}
