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

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.R
import org.junit.Assume.assumeTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BpkButtonTest(flavour: Flavor) : BpkSnapshotTest() {

  private val type: BpkButton.Type = flavour.first
  private val size: BpkButton.Size = flavour.second

  private val icon
    get() = testContext.getDrawable(
      when (size) {
        BpkButton.Size.Standard -> R.drawable.bpk_long_arrow_right_sm
        BpkButton.Size.Large -> R.drawable.bpk_long_arrow_right
      }
    )

  @Before
  fun setup() {
    setDimensions(64, 160)
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Themed)
  fun text() {
    capture(background = type.rowBackground()) {
      BpkButton(testContext, type, size).apply {
        text = "Button"
      }
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun disabled() {
    assumeTrue(size == BpkButton.Size.Standard) // colors will be the same on large size

    capture(background = type.rowBackground()) {
      BpkButton(testContext, type, size).apply {
        text = "Button"
        isEnabled = false
      }
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun loading() {
    capture(background = type.rowBackground()) {
      BpkButton(testContext, type, size).apply {
        text = "Button"
        loading = true
      }
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun loadingWithIcon() {
    assumeTrue(type == BpkButton.Type.Primary) // colors will be the same on all loading buttons
    // we need to run it on large size as well and the progress size will be different

    capture {
      BpkButton(testContext, type, size).apply {
        text = "Button"
        icon = this@BpkButtonTest.icon
        iconPosition = BpkButton.START
        loading = true
      }
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun loadingWithIconOnly() {
    assumeTrue(type == BpkButton.Type.Primary) // colors will be the same on all loading buttons
    // we need to run it on large size as well and the progress size will be different

    capture {
      BpkButton(testContext, type, size).apply {
        icon = this@BpkButtonTest.icon
        iconPosition = BpkButton.ICON_ONLY
        loading = true
      }
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun iconAtStart() {
    assumeTrue(type == BpkButton.Type.Primary) // the layout the same across different button types
    // icon is bigger on large size, so we need to test this

    capture {
      BpkButton(testContext, type, size).apply {
        text = "Button"
        icon = this@BpkButtonTest.icon
        iconPosition = BpkButton.START
      }
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun iconAtEnd() {
    assumeTrue(type == BpkButton.Type.Primary) // the layout the same across different button types
    // icon is bigger on large size, so we need to test this

    capture {
      BpkButton(testContext, type, size).apply {
        text = "Button"
        icon = this@BpkButtonTest.icon
        iconPosition = BpkButton.END
      }
    }
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun iconOnly() {
    assumeTrue(type == BpkButton.Type.Primary) // the layout the same across different button types
    // icon is bigger on large size, so we need to test this

    capture {
      BpkButton(testContext, type, size).apply {
        icon = this@BpkButtonTest.icon
        iconPosition = BpkButton.ICON_ONLY
      }
    }
  }

  private fun capture(
    @ColorRes background: Int? = null,
    content: () -> View,
  ) {
    val wrapper = FrameLayout(testContext).apply {
      layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        .apply { gravity = Gravity.TOP or Gravity.START }
    }
    if (background != null) {
      wrapper.setBackgroundColor(testContext.getColor(background))
    }
    val padding = testContext.resources.getDimensionPixelSize(R.dimen.bpkSpacingMd)
    wrapper.setPaddingRelative(padding, padding, padding, padding)
    wrapper.addView(
      content(),
      FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
    )
    snap(wrapper, tags = listOf(type, size))
  }

  companion object {

    @JvmStatic
    @Parameterized.Parameters(name = "{0} Screenshot")
    fun flavours(): List<Flavor> = BpkButton.Type.values().flatMap { type ->
      BpkButton.Size.values().mapNotNull { size ->
        if (type == BpkButton.Type.Primary || size == BpkButton.Size.Standard) {
          Flavor(type, size)
        } else {
          null
        }
      }
    }
  }
}

private typealias Flavor = Pair<BpkButton.Type, BpkButton.Size>

private fun BpkButton.Type.rowBackground() =
  when (this) {
    BpkButton.Type.SecondaryOnDark,
    BpkButton.Type.PrimaryOnDark,
    BpkButton.Type.LinkOnDark -> R.color.bpkSurfaceContrast
    BpkButton.Type.PrimaryOnLight -> R.color.bpkTextOnDark
    else -> null
  }
