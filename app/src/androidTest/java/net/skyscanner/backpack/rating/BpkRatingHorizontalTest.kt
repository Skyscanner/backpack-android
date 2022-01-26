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

package net.skyscanner.backpack.rating

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkRatingHorizontalTest : BpkSnapshotTest() {

  private val style = BpkRating.Style.Horizontal

  @Before
  fun setup() {
    setDimensions(100, 200)
  }

  @Test
  fun screenshotTestRating_Default() {
    val subject = createTestRating(testContext, style = style)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_Icon() {
    val subject = createTestRating(testContext, style = style, size = BpkRating.Size.Icon)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_ExtraSmall() {
    val subject = createTestRating(testContext, style = style, size = BpkRating.Size.ExtraSmall)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_Small() {
    val subject = createTestRating(testContext, style = style, size = BpkRating.Size.Small)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_Base() {
    val subject = createTestRating(testContext, style = style, size = BpkRating.Size.Base)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_Large() {
    val subject = createTestRating(testContext, style = style, size = BpkRating.Size.Large)
    snap(subject)
  }
}
