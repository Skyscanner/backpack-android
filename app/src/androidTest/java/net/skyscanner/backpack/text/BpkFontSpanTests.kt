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

package net.skyscanner.backpack.text

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkFontSpanTests : BpkSnapshotTest() {

  private val textView = TextView(testContext).apply { setBackgroundColor(Color.WHITE) }

  @Before
  fun setup() {
    setDimensions(40, 120)
  }

  @Test
  fun screenshotTestFontSpan_Default() {
    textView.text = "Test"
    snap(textView)
  }

  @Test
  fun screenshotTestFontSpan_Custom() {
    val span = BpkFontSpan(testContext, BpkText.XXL, BpkText.Weight.EMPHASIZED)
    textView.text = SpannableStringBuilder().append("Test", span, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    snap(textView)
  }

  @Test
  fun screenshotTestFontSpan_Injected() {
    val font = BpkText.getFont(testContext, BpkText.XXL, BpkText.Weight.EMPHASIZED)
    textView.text = SpannableStringBuilder().append("Test", BpkFontSpan(font), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    snap(textView)
  }
}
