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

package net.skyscanner.backpack.map.internal

import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkMapMarkerTest : BpkSnapshotTest() {

  @get:Rule
  val rule = activityScenarioRule<AppCompatActivity>()

  @Before
  fun setup() {
    setBackground(android.R.color.transparent)
    setDimensions(56, 128)
  }

  @Test
  fun screenshotTestMapMarker_Default() {
    val view = createMarker(0, true)
    snap(view)
  }

  @Test
  fun screenshotTestMapMarker_Default_Selected() {
    val view = createMarker(0, true)
    view.isSelected = true
    snap(view)
  }

  @Test
  fun screenshotTestMapMarker_Default_NoPointer() {
    val view = createMarker(0, false)
    snap(view)
  }

  @Test
  fun screenshotTestMapMarker_WithIcon() {
    val view = createMarker(R.drawable.bpk_map, true)
    snap(view)
  }

  @Test
  fun screenshotTestMapMarker_WithIcon_Selected() {
    val view = createMarker(R.drawable.bpk_map, true)
    view.isSelected = true
    snap(view)
  }

  @Test
  fun screenshotTestMapMarker_WithIcon_NoPointer() {
    val view = createMarker(R.drawable.bpk_map, false)
    snap(view)
  }

  private fun createMarker(@DrawableRes icon: Int, showPointer: Boolean): View {
    var view: View? = null
    rule.scenario.onActivity { activity ->
      view = BpkMapMarkersTestBridge.create(activity, "Title", icon, showPointer)
    }
    return view!!
  }
}
