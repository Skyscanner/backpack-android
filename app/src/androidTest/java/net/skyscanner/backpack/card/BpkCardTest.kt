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
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkCardTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(150, 150)
    setBackground(android.R.color.transparent)
  }

  @Test
  fun screenshotTestCardDefault() {
    val card = BpkCardView(testContext)
    val text = TextView(testContext)
    text.text = "message"
    card.addView(text)
    snap(card)
  }

  @Test
  fun screenshotTestCardWithPadding() {
    val card = BpkCardView(testContext)
    val text = TextView(testContext)
    text.text = "message"
    card.addView(text)
    card.padded = true
    snap(card)
  }

  @Test
  fun screenshotTestCardWithoutPadding() {
    val card = BpkCardView(testContext)
    val text = TextView(testContext)
    text.text = "message"
    card.addView(text)
    card.padded = false
    snap(card)
  }

  @Test
  fun screenshotTestCardWithFocus() {
    val card = BpkCardView(testContext)
    val text = TextView(testContext)
    text.text = "message"
    card.addView(text)
    card.focused = true
    snap(card)
  }

  @Test
  fun screenshotTestCardWithoutPaddingAndFocus() {
    val card = BpkCardView(testContext)
    val text = TextView(testContext)
    text.text = "message"
    card.addView(text)
    card.padded = true
    card.focused = true
    snap(card)
  }

  @Test
  fun screenshotTestCardWithCornerStyleLarge() {
    val card = BpkCardView(testContext).apply {
      addView(
        TextView(testContext).apply {
          text = "message"
        }
      )
      cornerStyle = BpkCardView.CornerStyle.LARGE
    }
    snap(card)
  }

  @Test
  fun screenshotTestCardWithCornerStyleLargeAndFocus() {
    val card = BpkCardView(testContext).apply {
      addView(
        TextView(testContext).apply {
          text = "message"
        }
      )
      focused = true
      cornerStyle = BpkCardView.CornerStyle.LARGE
    }
    snap(card)
  }
}
