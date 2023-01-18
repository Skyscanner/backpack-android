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

package net.skyscanner.backpack.horizontalnav

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.tabs.TabLayout
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.horisontalnav.BpkHorizontalNav
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkHorizontalNavTest : BpkSnapshotTest() {

  private val horizontalNav = BpkHorizontalNav(testContext).init()

  @Test
  fun default() {
    snap(horizontalNav)
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun small() {
    val horizontalNav = BpkHorizontalNav(testContext).init()
    horizontalNav.size = BpkHorizontalNav.Size.Small
    snap(horizontalNav)
  }

  @Test
  fun withIcons() {
    val horizontalNav = BpkHorizontalNav(testContext).apply {
      addTab(newTab().setText("Tab 1").setIcon(R.drawable.bpk_cars))
      addTab(newTab().setText("Tab 2").setIcon(R.drawable.bpk_cars))
      addTab(newTab().setText("Tab 3").setIcon(R.drawable.bpk_cars))
    }
    snap(horizontalNav)
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun fixed() {
    horizontalNav.tabMode = TabLayout.MODE_FIXED
    snap(horizontalNav)
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun scrollable() {
    horizontalNav.tabMode = TabLayout.MODE_SCROLLABLE
    snap(horizontalNav)
  }

  private fun BpkHorizontalNav.init() = apply {
    addTab(newTab().setText("Tab 1"))
    addTab(newTab().setText("Tab 2"))
    addTab(newTab().setText("Tab 3"))
  }
}
