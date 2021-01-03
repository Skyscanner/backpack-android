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

package net.skyscanner.backpack.toast

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkToastTests : BpkSnapshotTest() {

  @Before
  fun setUp() {
    setDimensions(42, 120)
  }

  @Test
  fun screenshotTestToast_Default() {
    val toast = BpkToast.makeText(testContext, "Test", BpkToast.LENGTH_SHORT)
    toast.show()
    snap(toast.view)
  }

  @Test
  fun screenshotTestToast_Themed() {
    val toast = BpkToast.makeText(createThemedContext(testContext), "Test", BpkToast.LENGTH_SHORT)
    toast.show()
    snap(toast.view)
  }
}
