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

import android.os.Looper
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.SnapshotUtil.assumeVariant
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
  fun text() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Themed) // no need to test text on Rtl
    // we want to see colors of all types
    // we want to test 1 large button type
    assumeTrue(size == BpkButton.Size.Standard || type == BpkButton.Type.Primary)

    capture(background = type.rowBackground()) {
      BpkButton(testContext, type, size).apply {
        text = "Button"
      }
    }
  }

  @Test
  fun disabled() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode) // we're testing just colors here – no rtl is needed
    // disabled/loading colors are not theme customisable
    assumeTrue(size == BpkButton.Size.Standard) // colors will be the same on large size

    capture(background = type.rowBackground()) {
      BpkButton(testContext, type, size).apply {
        text = "Button"
        isEnabled = false
      }
    }
  }

  @Test
  fun loading() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode) // we're testing just colors here – no rtl is needed
    // disabled/loading colors are not theme customisable
    // we want to test 1 large button type
    assumeTrue(size == BpkButton.Size.Standard || type == BpkButton.Type.Primary)

    capture(background = type.rowBackground()) {
      BpkButton(testContext, type, size).apply {
        text = "Button"
        loading = true
      }
    }
  }

  @Test
  fun loadingWithIcon() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl) // this just tests layout, so RTL is required
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
  fun loadingWithIconOnly() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl) // this just tests layout, so RTL is required
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
  fun iconAtStart() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl) // this just tests layout, so RTL is required
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
  fun iconAtEnd() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl) // this just tests layout, so RTL is required
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
  fun iconOnly() {
    assumeVariant(BpkTestVariant.Default) // since its only icon, RTL doesn't matter
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
    content: () -> View
  ) {
    if (Looper.myLooper() == null) {
      Looper.prepare()
    }
    background?.let { setBackground(background) }
    val wrapper = FrameLayout(testContext)
    val padding = testContext.resources.getDimensionPixelSize(R.dimen.bpkBorderRadiusMd)
    wrapper.setPadding(padding, padding, padding, padding)
    val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
    wrapper.addView(content(), params)
    snap(wrapper, tags = listOf(type, size))
  }

  companion object {

    @JvmStatic
    @Parameterized.Parameters(name = "{0} Screenshot")
    fun flavours(): List<Flavor> = BpkButton.Type.values().flatMap { type ->
      BpkButton.Size.values().map { size -> Flavor(type, size) }
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
