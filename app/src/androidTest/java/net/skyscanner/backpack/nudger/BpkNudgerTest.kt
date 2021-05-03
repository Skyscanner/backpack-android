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

package net.skyscanner.backpack.nudger

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * FIXME
 * TDv
 * The buttons show up without icons. The snapshot test framework is not rendering
 * the icons when using `setCompoundDrawablesRelativeWithIntrinsicBounds` instead of
 * `setCompoundDrawablesWithIntrinsicBounds` on API-21 which is required to support RTL. We can add
 * them when we update the emulators on the CI or move to a device farm for snapshot testing
 */
@RunWith(AndroidJUnit4::class)
class BpkNudgerTest : BpkSnapshotTest() {

  private val nudger = BpkNudger(testContext)

  @Before
  fun setup() {
    setDimensions(50, 150)
  }

  @Test
  fun screenshotTestNudger_minusDisabled() {
    snap(nudger)
  }

  @Test
  fun screenshotTestNudger_plusDisabled() {
    nudger.value = 10
    nudger.maxValue = 10
    snap(nudger)
  }

  @Test
  fun screenshotTestNudger_standard() {
    nudger.value = 5
    snap(nudger)
  }
}
