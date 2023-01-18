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

package net.skyscanner.backpack.bottomnav

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.compose.BpkBottomNavSample
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkBottomNavTest : BpkSnapshotTest() {

  @Test
  fun default() {
    snap(
      BpkBottomNav(testContext).apply {
        addItem(1, R.string.bottom_nav_home, R.drawable.bpk_aircraft)
        addItem(2, R.string.bottom_nav_explore, R.drawable.bpk_navigation)
        addItem(3, R.string.bottom_nav_trips, R.drawable.bpk_star)
        addItem(4, R.string.bottom_nav_profile, R.drawable.bpk_account_circle)
      },
      padding = 0,
    )
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun selectionUpdated() {
    snap(
      BpkBottomNav(testContext).apply {
        addItem(1, R.string.bottom_nav_home, R.drawable.bpk_aircraft)
        addItem(2, R.string.bottom_nav_explore, R.drawable.bpk_navigation)
        addItem(3, R.string.bottom_nav_trips, R.drawable.bpk_star)
        addItem(4, R.string.bottom_nav_profile, R.drawable.bpk_account_circle)
        selectedItemId = 2
      },
      padding = 0,
    )
  }

}
