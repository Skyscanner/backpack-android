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

package net.skyscanner.backpack.compose.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.LongArrowRight
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.compose.rowBackground
import org.junit.Assume.assumeTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BpkButtonTest(flavour: Flavor) : BpkSnapshotTest() {

  private val type: BpkButtonType = flavour.first
  private val size: BpkButtonSize = flavour.second
  private val icon = BpkIcon.LongArrowRight
  private val iconDrawableRes = R.drawable.sample_icon

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun text() {
    capture(background = { type.rowBackground() }) {
      BpkButton("Button", type = type, size = size, onClick = {})
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun disabled() {
    assumeTrue(size == BpkButtonSize.Default) // colors will be the same on large size

    capture(background = { type.rowBackground() }) {
      BpkButton("Button", type = type, size = size, enabled = false, onClick = {})
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun loading() {
    capture(background = { type.rowBackground() }) {
      BpkButton("Button", type = type, size = size, loading = true, onClick = {})
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun iconAtStart() {
    assumeTrue(type == BpkButtonType.Primary) // the layout the same across different button types
    // icon is bigger on large size, so we need to test this

    capture {
      BpkButton("Button", icon, BpkButtonIconPosition.Start, type = type, size = size, onClick = {})
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun iconAtEnd() {
    assumeTrue(type == BpkButtonType.Primary) // the layout the same across different button types
    // icon is bigger on large size, so we need to test this

    capture {
      BpkButton("Button", icon, BpkButtonIconPosition.End, type = type, size = size, onClick = {})
    }
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun iconOnly() {
    assumeTrue(type == BpkButtonType.Primary) // the layout the same across different button types
    // icon is bigger on large size, so we need to test this

    capture {
      BpkButton(icon, "contentDescription", type = type, size = size, onClick = {})
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun drawableAtStart() {
    assumeTrue(type == BpkButtonType.Primary) // the layout the same across different button types
    // icon is bigger on large size, so we need to test this

    capture {
      BpkButton(
        text = "Button",
        icon = painterResource(id = iconDrawableRes),
        position = BpkButtonIconPosition.Start,
        type = type,
        size = size,
        onClick = {}
      )
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun drawableAtEnd() {
    assumeTrue(type == BpkButtonType.Primary) // the layout the same across different button types
    // icon is bigger on large size, so we need to test this

    capture {
      BpkButton(
        text = "Button",
        icon = painterResource(id = iconDrawableRes),
        position = BpkButtonIconPosition.End,
        type = type,
        size = size,
        onClick = {}
      )
    }
  }

  private fun capture(
    background: @Composable () -> Color = { Color.Unspecified },
    content: @Composable () -> Unit,
  ) {
    snap(
      size = IntSize(160, 64),
      tags = listOf(type, size),
    ) {
      Box(
        Modifier
          .fillMaxSize()
          .background(background())
          .padding(BpkSpacing.Md),
        contentAlignment = Alignment.TopStart,
      ) {
        content()
      }
    }
  }

  companion object {

    @JvmStatic
    @Parameterized.Parameters(name = "{0} Screenshot")
    fun flavours(): List<Flavor> = BpkButtonType.values().flatMap { type ->
      BpkButtonSize.values().mapNotNull { size ->
        if (type == BpkButtonType.Primary || size == BpkButtonSize.Default) {
          Flavor(type, size)
        } else {
          null
        }
      }
    }
  }
}

private typealias Flavor = Pair<BpkButtonType, BpkButtonSize>
