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
  fun screenshotTestTextDefault() {
    val text = BpkText(testContext)
    text.text = "Message"
    snap(text)
  }

  @Test
  fun screenshotTestTextLg() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.LG
    snap(text)
  }

  @Test
  fun screenshotTestTextBase() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.BASE
    snap(text)
  }

  @Test
  fun screenshotTestTextSm() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.SM
    snap(text)
  }

  @Test
  fun screenshotTestTextXl() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XL
    snap(text)
  }

  @Test
  fun screenshotTestTextXs() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XS
    snap(text)
  }

  @Test
  fun screenshotTestTextXXL() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XXL
    snap(text)
  }

  @Test
  fun screenshotTestTextXXXL() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XXXL
    snap(text)
  }

  @Test
  fun screenshotTestTextCaps() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.CAPS
    snap(text)
  }

  @Test
  fun screenshotTestTextXXLEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XXL
    text.weight = BpkText.Weight.EMPHASIZED
    snap(text)
  }

  @Test
  fun screenshotTestTextXXXLEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XXXL
    text.weight = BpkText.Weight.EMPHASIZED
    snap(text)
  }

  @Test
  fun screenshotTestTextLgEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.LG
    text.weight = BpkText.Weight.EMPHASIZED
    snap(text)
  }

  @Test
  fun screenshotTestTextBaseEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.BASE
    text.weight = BpkText.Weight.EMPHASIZED
    snap(text)
  }

  @Test
  fun screenshotTestTextSmEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.SM
    text.weight = BpkText.Weight.EMPHASIZED
    snap(text)
  }

  @Test
  fun screenshotTestTextXlEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XL
    text.weight = BpkText.Weight.EMPHASIZED
    snap(text)
  }

  @Test
  fun screenshotTestTextXsEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XS
    text.weight = BpkText.Weight.EMPHASIZED
    snap(text)
  }

  @Test
  fun screenshotTestTextCapsEmphasize() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.CAPS
    text.weight = BpkText.Weight.EMPHASIZED
    snap(text)
  }

  @Test
  fun screenshotTestTextXLHeavy() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XL
    text.weight = BpkText.Weight.HEAVY
    snap(text)
  }

  @Test
  fun screenshotTestTextXXLHeavy() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XXL
    text.weight = BpkText.Weight.HEAVY
    snap(text)
  }

  @Test
  fun screenshotTestTextXXXLHeavy() {
    val text = BpkText(testContext)
    text.text = "Message"
    text.textStyle = BpkText.XXXL
    text.weight = BpkText.Weight.HEAVY
    snap(text)
  }

  @Test
  fun screenshotTestTextLink() {
    val text = BpkText(testContext)
    text.setText(R.string.txt_lorem_ipsum_link)
    snap(text)
  }
}
