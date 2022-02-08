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

package net.skyscanner.backpack.text

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkTextTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(72, 180)
  }

  @Test
  fun default() {
    val text = BpkText(testContext)
    text.text = "Message"
    snap(text)
  }

  @Test
  fun bodyDefault() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.TextStyle.BODY_DEFAULT
    snap(text)
  }

  @Test
  fun bodyLongform() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.TextStyle.BODY_LONGFORM
    snap(text)
  }

  @Test
  fun caption() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.TextStyle.CAPTION
    snap(text)
  }

  @Test
  fun footnote() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.TextStyle.FOOTNOTE
    snap(text)
  }

  @Test
  fun subheading() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.TextStyle.SUBHEADING
    snap(text)
  }

  @Test
  fun label1() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.TextStyle.LABEL1
    snap(text)
  }

  @Test
  fun label2() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.TextStyle.LABEL2
    snap(text)
  }

  @Test
  fun heading1() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.TextStyle.HEADING1
    snap(text)
  }

  @Test
  fun heading2() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.TextStyle.HEADING2
    snap(text)
  }

  @Test
  fun heading3() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.TextStyle.HEADING3
    snap(text)
  }

  @Test
  fun heading4() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.TextStyle.HEADING4
    snap(text)
  }

  @Test
  fun heading5() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.TextStyle.HEADING5
    snap(text)
  }

  @Test
  fun hero1() {
    setDimensions(150, 400)
    val text = BpkText(testContext)
    text.text = "Hero 1"
    text.textStyle = BpkText.TextStyle.HERO1
    snap(text)
  }

  @Test
  fun hero2() {
    setDimensions(150, 400)
    val text = BpkText(testContext)
    text.text = "Hero 2"
    text.textStyle = BpkText.TextStyle.HERO2
    snap(text)
  }

  @Test
  fun hero3() {
    setDimensions(150, 400)
    val text = BpkText(testContext)
    text.text = "Hero 3"
    text.textStyle = BpkText.TextStyle.HERO3
    snap(text)
  }

  @Test
  fun hero4() {
    setDimensions(150, 400)
    val text = BpkText(testContext)
    text.text = "Hero 4"
    text.textStyle = BpkText.TextStyle.HERO4
    snap(text)
  }

  @Test
  fun hero5() {
    setDimensions(150, 400)
    val text = BpkText(testContext)
    text.text = "Hero 5"
    text.textStyle = BpkText.TextStyle.HERO5
    snap(text)
  }

  @Test
  fun link() {
    val text = BpkText(testContext)
    text.setText(R.string.txt_lorem_ipsum_link)
    snap(text)
  }
}
