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

package net.skyscanner.backpack.horizontalnav

import android.graphics.Color
import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.tabs.TabLayout
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.horisontalnav.BpkHorizontalNav
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HorizontalNavTests : BpkSnapshotTest() {

  private val horizontalNav = BpkHorizontalNav(testContext).init()

  @Before
  fun setup() {
    setDimensions(36, 300)
  }

  @Test
  fun screenshotHorizontalNav_Default() {
    snap(horizontalNav)
  }

  @Test
  fun screenshotHorizontalNav_withTheme() {
    val horizontalNav = BpkHorizontalNav(createThemedContext(testContext)).init()
    snap(horizontalNav)
  }

  @Test
  fun screenshotHorizontalNav_fixed() {
    horizontalNav.tabMode = TabLayout.MODE_FIXED
    snap(horizontalNav)
  }

  @Test
  fun screenshotHorizontalNav_scrollable() {
    horizontalNav.tabMode = TabLayout.MODE_SCROLLABLE
    snap(horizontalNav)
  }

  @Test
  fun screenshotHorizontalNav_small() {
    val horizontalNav = BpkHorizontalNav(testContext).init()
    horizontalNav.size = BpkHorizontalNav.Size.Small
    snap(horizontalNav)
  }

  @Test
  fun screenshotHorizontalNav_alternate() {
    val horizontalNav = BpkHorizontalNav(testContext).init()
    horizontalNav.setBackgroundResource(R.color.bpkSkyBlueTint03)
    horizontalNav.appearance = BpkHorizontalNav.Appearance.Alternate
    snap(horizontalNav)
  }

  @Test
  fun screenshotHorizontalNav_Rtl() {
    horizontalNav.layoutDirection = View.LAYOUT_DIRECTION_RTL
    snap(horizontalNav)
  }

  @Test
  fun screenshotHorizontalNav_notificationDot() {
    horizontalNav.setNotificationDot(0, true)
    snap(horizontalNav)
  }

  @Test
  fun screenshotHorizontalNav_badge() {
    horizontalNav.tabMode = TabLayout.MODE_SCROLLABLE
    horizontalNav.setBadge(0, "Beta")
    snap(horizontalNav)
  }

  private fun BpkHorizontalNav.init() = apply {
    setBackgroundColor(Color.WHITE)
    addTab(newTab().setText("1"))
    addTab(newTab().setText("2"))
    addTab(newTab().setText("3"))
  }
}
