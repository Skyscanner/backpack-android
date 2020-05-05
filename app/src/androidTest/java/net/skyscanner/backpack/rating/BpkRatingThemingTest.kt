/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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

package net.skyscanner.backpack.rating

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import net.skyscanner.backpack.util.unsafeLazy
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkRatingThemingTest : BpkSnapshotTest() {

  private val themedContext by unsafeLazy { createThemedContext(testContext) }

  @Before
  fun setup() {
    setDimensions(150, 150)
  }

  @Test
  fun screenshotTestRating_Low() {
    val subject = createTestRating(themedContext, size = BpkRating.Size.Icon, value = 3.0f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_Medium() {
    val subject = createTestRating(themedContext, size = BpkRating.Size.Icon, value = 6.0f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_High() {
    val subject = createTestRating(themedContext, size = BpkRating.Size.Icon, value = 8.0f)
    snap(subject)
  }
}
