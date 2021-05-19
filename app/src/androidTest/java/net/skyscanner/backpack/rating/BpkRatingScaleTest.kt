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

package net.skyscanner.backpack.rating

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkRatingScaleTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(100, 200)
  }

  @Test
  fun screenshotTestRating_ZeroToFive_Zero() {
    val subject = createTestRating(testContext, scale = BpkRating.Scale.ZeroToFive, value = 0.0f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_ZeroToFive_Low() {
    val subject = createTestRating(testContext, scale = BpkRating.Scale.ZeroToFive, value = 1.5f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_ZeroToFive_LowBoundary() {
    val subject = createTestRating(testContext, scale = BpkRating.Scale.ZeroToFive, value = 2.999999f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_ZeroToFive_Medium() {
    val subject = createTestRating(testContext, scale = BpkRating.Scale.ZeroToFive, value = 3.0f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_ZeroToFive_MediumBoundary() {
    val subject = createTestRating(testContext, scale = BpkRating.Scale.ZeroToFive, value = 3.999999f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_ZeroToFive_High() {
    val subject = createTestRating(testContext, scale = BpkRating.Scale.ZeroToFive, value = 4.0f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_ZeroToFive_Max() {
    val subject = createTestRating(testContext, scale = BpkRating.Scale.ZeroToFive, value = 5.0f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_ZeroToFive_clampsDown() {
    val subject = createTestRating(testContext, scale = BpkRating.Scale.ZeroToFive, value = -10.0f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_ZeroToFive_clampsUp() {
    val subject = createTestRating(testContext, scale = BpkRating.Scale.ZeroToFive, value = 10.0f)
    snap(subject)
  }
}
