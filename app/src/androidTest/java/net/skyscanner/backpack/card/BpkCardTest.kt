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

package net.skyscanner.backpack.card

import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkCardTest : BpkSnapshotTest() {

  @Test
  fun default() {
    val card = BpkCardView(testContext)
    val text = TextView(testContext)
    text.text = "message"
    card.addView(text)
    snap(card, android.R.color.transparent)
  }

  @Test
  fun withPadding() {
    val card = BpkCardView(testContext)
    val text = TextView(testContext)
    text.text = "message"
    card.addView(text)
    card.padded = true
    snap(card, android.R.color.transparent)
  }

  @Test
  fun withoutPadding() {
    val card = BpkCardView(testContext)
    val text = TextView(testContext)
    text.text = "message"
    card.addView(text)
    card.padded = false
    snap(card, android.R.color.transparent)
  }

  @Test
  fun withFocus() {
    val card = BpkCardView(testContext)
    val text = TextView(testContext)
    text.text = "message"
    card.addView(text)
    card.elevationLevel = BpkCardView.ElevationLevel.FOCUSED
    snap(card, android.R.color.transparent)
  }

  @Test
  fun noElevation() {
    val card = BpkCardView(testContext)
    val text = TextView(testContext)
    text.text = "message"
    card.addView(text)
    card.elevationLevel = BpkCardView.ElevationLevel.NONE
    snap(card,R.color.bpkSurfaceHighlight)
  }

  @Test
  fun withoutPaddingAndFocus() {
    val card = BpkCardView(testContext)
    val text = TextView(testContext)
    text.text = "message"
    card.addView(text)
    card.padded = true
    card.elevationLevel = BpkCardView.ElevationLevel.FOCUSED
    snap(card, android.R.color.transparent)
  }

  @Test
  fun withCornerStyleLarge() {
    val card = BpkCardView(testContext).apply {
      addView(
        TextView(testContext).apply {
          text = "message"
        }
      )
      cornerStyle = BpkCardView.CornerStyle.LARGE
    }
    snap(card, android.R.color.transparent)
  }

  @Test
  fun withCornerStyleLargeAndFocus() {
    val card = BpkCardView(testContext).apply {
      addView(
        TextView(testContext).apply {
          text = "message"
        }
      )
      elevationLevel = BpkCardView.ElevationLevel.FOCUSED
      cornerStyle = BpkCardView.CornerStyle.LARGE
    }
    snap(card, android.R.color.transparent)
  }
}
