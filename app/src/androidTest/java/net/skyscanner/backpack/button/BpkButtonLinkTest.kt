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

package net.skyscanner.backpack.button

import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkButtonLinkTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(50, 100)
  }

  @Test
  fun screenshotTestButtonLinkDefault() {
    val button = BpkButtonLink(testContext)
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonLinkNotUppercase() {
    val button = BpkButtonLink(testContext).apply {
      uppercase = false
      text = "Message"
    }
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonLinkDisabled() {
    val button = BpkButtonLink(testContext).apply {
      text = "Message"
      isEnabled = false
    }
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonLinkWithLeadingIcon() {
    val button = BpkButtonLink(testContext).apply {
      iconPosition = BpkButton.START
      text = "Message"
      icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick)
    }
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonLinkWithTrailingIcon() {
    val button = BpkButtonLink(testContext).apply {
      iconPosition = BpkButton.END
      text = "Message"
      icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick)
    }
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonLinkWithIcon() {
    val button = BpkButtonLink(testContext).apply {
      icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick)
      text = "Message"
    }
    snap(wrap(button))
  }

  private fun wrap(button: BpkButtonLink, with: Int = FrameLayout.LayoutParams.WRAP_CONTENT): FrameLayout {
    return FrameLayout(testContext).apply {
      button.layoutParams = FrameLayout.LayoutParams(with, FrameLayout.LayoutParams.WRAP_CONTENT)
      addView(button)
    }
  }
}
