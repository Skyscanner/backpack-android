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

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.viewinterop.AndroidView
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import org.junit.Assume.assumeTrue
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

  @Test
  fun text() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Themed) // no need to test text on Rtl
    // we want to see colors of all types
    // different sizes have different text style

    capture(background = { type.rowBackground() }) {
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

    capture(background = { type.rowBackground() }) {
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
    // we need to run it on large size as well and the progress size will be different

    capture(background = { type.rowBackground() }) {
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
    background: @Composable () -> Color = { Color.Unspecified },
    content: () -> View
  ) {
    composed(
      size = IntSize(160, 64),
      tags = listOf(type, size),
    ) {
      Box(
        Modifier
          .fillMaxSize()
          .background(background())
          .padding(BpkSpacing.Md),
        contentAlignment = Alignment.TopStart
      ) {
        AndroidView(factory = { content() })
      }
    }
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

@Composable
private fun BpkButton.Type.rowBackground() =
  when (this) {
    BpkButton.Type.SecondaryOnDark,
    BpkButton.Type.PrimaryOnDark,
    BpkButton.Type.LinkOnDark -> BpkTheme.colors.surfaceContrast
    BpkButton.Type.PrimaryOnLight -> BpkTheme.colors.textOnDark
    else -> Color.Unspecified
  }
