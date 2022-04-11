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
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BpkButtonLinkTest(private val size: BpkButton.Size) : BpkSnapshotTest() {

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

    capture {
      BpkButtonLink(testContext, size).apply {
        text = "Button"
      }
    }
  }

  @Test
  fun noUppercase() {
    assumeVariant(BpkTestVariant.Default) // this just tests text size

    capture {
      BpkButtonLink(testContext, size).apply {
        text = "Button"
      }
    }
  }

  @Test
  fun disabled() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode) // we're testing just colors here – no rtl is needed
    // disabled colors are not theme customisable
    Assume.assumeTrue(size == BpkButton.Size.Standard) // colors will be the same on large size

    capture {
      BpkButtonLink(testContext, size).apply {
        text = "Button"
        isEnabled = false
      }
    }
  }

  @Test
  fun iconAtStart() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl) // this just tests layout, so RTL is required
    // icon is bigger on large size, so we need to test this

    capture {
      BpkButtonLink(testContext, size).apply {
        text = "Button"
        icon = this@BpkButtonLinkTest.icon
        iconPosition = BpkButton.START
      }
    }
  }

  @Test
  fun iconAtEnd() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl) // this just tests layout, so RTL is required
    // icon is bigger on large size, so we need to test this

    capture {
      BpkButtonLink(testContext, size).apply {
        text = "Button"
        icon = this@BpkButtonLinkTest.icon
        iconPosition = BpkButton.END
      }
    }
  }

  private fun capture(content: () -> View) {
    composed(
      size = IntSize(160, 64),
      tags = listOf(size),
    ) {
      Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AndroidView(factory = { content() })
      }
    }
  }

  companion object {

    @JvmStatic
    @Parameterized.Parameters(name = "{0} Screenshot")
    fun flavours(): List<BpkButton.Size> = BpkButton.Size.values().toList()
  }
}
