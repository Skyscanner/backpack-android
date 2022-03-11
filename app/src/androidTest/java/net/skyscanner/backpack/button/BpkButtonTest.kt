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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.viewinterop.AndroidView
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.demo.R
import org.junit.Assume
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
    setDimensions(40, 300)
  }

  @Test
  fun text() = capture {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Themed) // no need to test text on Rtl
    // we want to see colors of all types
    // different sizes have different text style

    BpkButton(testContext, type, size).apply {
      text = "Button"
    }
  }

  @Test
  fun disabled() = capture {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode) // we're testing just colors here – no rtl is needed
    // disabled/loading colors are not theme customisable
    Assume.assumeTrue(size == BpkButton.Size.Standard) // colors will be the same on large size
    Assume.assumeTrue(type == BpkButton.Type.Primary) // colors will be the same on all disabled buttons

    BpkButton(testContext, type, size).apply {
      text = "Button"
      isEnabled = false
    }
  }

  @Test
  fun loading() = capture {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode) // we're testing just colors here – no rtl is needed
    // disabled/loading colors are not theme customisable
    Assume.assumeTrue(type == BpkButton.Type.Primary) // colors will be the same on all loading buttons
    // we need to run it on large size as well and the progress size will be different

    BpkButton(testContext, type, size).apply {
      text = "Button"
      loading = true
    }
  }

  @Test
  fun iconAtStart() = capture {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl) // this just tests layout, so RTL is required
    Assume.assumeTrue(type == BpkButton.Type.Primary) // the layout the same across different button types
    // icon is bigger on large size, so we need to test this

    BpkButton(testContext, type, size).apply {
      text = "Button"
      icon = this@BpkButtonTest.icon
      iconPosition = BpkButton.START
    }
  }

  @Test
  fun iconAtEnd() = capture {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl) // this just tests layout, so RTL is required
    Assume.assumeTrue(type == BpkButton.Type.Primary) // the layout the same across different button types
    // icon is bigger on large size, so we need to test this

    BpkButton(testContext, type, size).apply {
      text = "Button"
      icon = this@BpkButtonTest.icon
      iconPosition = BpkButton.END
    }
  }

  @Test
  fun iconOnly() = capture {
    assumeVariant(BpkTestVariant.Default) // since its only icon, RTL doesn't matter
    Assume.assumeTrue(type == BpkButton.Type.Primary) // the layout the same across different button types
    // icon is bigger on large size, so we need to test this

    BpkButton(testContext, type, size).apply {
      icon = this@BpkButtonTest.icon
      iconPosition = BpkButton.ICON_ONLY
    }
  }

  private fun capture(content: () -> View) {
    composed(
      size = IntSize(160, 64),
      tags = listOf(type, size),
    ) {
      Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AndroidView(factory = { content() })
      }
    }
  }

  companion object {

    private val ButtonTypes = listOf(
      BpkButton.Type.Primary,
      BpkButton.Type.Secondary,
      BpkButton.Type.PrimaryOnDark,
      BpkButton.Type.PrimaryOnLight,
      BpkButton.Type.Featured,
      BpkButton.Type.Destructive,
    )

    @JvmStatic
    @Parameterized.Parameters(name = "{0} Screenshot")
    fun flavours(): List<Flavor> = ButtonTypes.flatMap { type ->
      BpkButton.Size.values().map { size -> Flavor(type, size) }
    }
  }
}

private typealias Flavor = Pair<BpkButton.Type, BpkButton.Size>
