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
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkRatingPillTest : BpkSnapshotTest() {

  private val style = BpkRating.Style.Pill

  @Test
  fun default() {
    val subject = createTestRating(testContext, style = style)
    snap(subject)
  }

  @Test
  fun icon() {
    val subject = createTestRating(testContext, style = style, size = BpkRating.Size.Icon)
    snap(subject)
  }

  @Test
  fun extraSmall() {
    val subject = createTestRating(testContext, style = style, size = BpkRating.Size.ExtraSmall)
    snap(subject)
  }

  @Test
  fun small() {
    val subject = createTestRating(testContext, style = style, size = BpkRating.Size.Small)
    snap(subject)
  }

  @Test
  fun base() {
    val subject = createTestRating(testContext, style = style, size = BpkRating.Size.Base)
    snap(subject)
  }

  @Test
  fun large() {
    val subject = createTestRating(testContext, style = style, size = BpkRating.Size.Large)
    snap(subject)
  }

  @Test
  fun titleOverflow() {
    val subject = createTestRating(testContext, style = style, size = BpkRating.Size.Large)
    subject.title = { "Loooooooooooooooong title" }
    snap(subject, width = 150)
  }

  @Test
  fun subtitleOverflow() {
    val subject = createTestRating(testContext, style = style, size = BpkRating.Size.Large)
    subject.subtitle = { "Looooooooooooooooooooooooooooong subtitle" }
    snap(subject, width = 150)
  }
}
