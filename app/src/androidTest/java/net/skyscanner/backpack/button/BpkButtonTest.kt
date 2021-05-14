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
class BpkButtonTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(40, 300)
  }

  @Test
  fun screenshotTestButtonBasicPrimary() {
    val button = BpkButton(testContext, BpkButton.Type.Primary)
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicPrimaryWithIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Primary)
    button.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicPrimaryOnlyIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Primary)
    button.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.ICON_ONLY
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicSecondary() {
    val button = BpkButton(testContext, BpkButton.Type.Secondary)
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicSecondaryWithIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Secondary)
    button.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.END
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicSecondaryOnlyIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Secondary)
    button.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.ICON_ONLY
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicFeatured() {
    val button = BpkButton(testContext, BpkButton.Type.Featured)
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicFeaturedWithIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Featured)
    button.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicFeaturedOnlyIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Featured)
    button.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.ICON_ONLY
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicDestructive() {
    val button = BpkButton(testContext, BpkButton.Type.Destructive)
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicDestructiveWithIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Destructive)
    button.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicDestructiveOnlyIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Destructive)
    button.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.ICON_ONLY
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicOutline() {
    setBackground(R.color.bpkPrimary)
    val button = BpkButton(testContext, BpkButton.Type.Outline)
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicOutlineWithIcon() {
    setBackground(R.color.bpkPrimary)
    val button = BpkButton(testContext, BpkButton.Type.Outline)
    button.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicOutlineOnlyIcon() {
    setBackground(R.color.bpkPrimary)
    val button = BpkButton(testContext, BpkButton.Type.Outline)
    button.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.ICON_ONLY
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonLargeWithIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Primary)
    button.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(wrap(button, 500))
  }

  @Test
  fun screenshotTestButtonLargeWithIconTrailing() {
    val button = BpkButton(testContext, BpkButton.Type.Primary)
    button.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.END
    button.text = "Message"
    snap(wrap(button, 500))
  }

  @Test
  fun screenshotTestButtonTypeChange() {
    val button = BpkButton(testContext, BpkButton.Type.Primary)
    button.icon = AppCompatResources.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.END
    button.text = "Message"
    button.type = BpkButton.Type.Secondary
    snap(wrap(button, 500))
  }

  @Test
  fun screenshotTestButton_TextIncreasing() {
    val button = BpkButton(testContext, BpkButton.Type.Primary)
    button.text = "Message"
    button.text = "Long long long long long long long long long long long text"
    snap(wrap(button, 500))
  }

  @Test
  fun screenshotTestButton_TextDecreasing() {
    val button = BpkButton(testContext, BpkButton.Type.Primary)
    button.text = "Long long long long long long long long long long long text"
    button.text = "Message"
    snap(wrap(button, 500))
  }

  private fun wrap(
    button: BpkButton,
    width: Int = FrameLayout.LayoutParams.WRAP_CONTENT,
    height: Int = FrameLayout.LayoutParams.WRAP_CONTENT
  ): FrameLayout {
    return FrameLayout(testContext).apply {
      button.layoutParams = FrameLayout.LayoutParams(width, height)
      addView(button)
    }
  }
}
