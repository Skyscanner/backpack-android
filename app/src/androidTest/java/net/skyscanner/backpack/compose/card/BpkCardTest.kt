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

package net.skyscanner.backpack.compose.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.compose.FocusableCardExample
import net.skyscanner.backpack.demo.compose.LargeCornersCardExample
import net.skyscanner.backpack.demo.compose.NoElevationCardExample
import net.skyscanner.backpack.demo.compose.NoPaddingCardExample
import net.skyscanner.backpack.demo.compose.NonClickableCardExample
import net.skyscanner.backpack.demo.compose.SmallCornersCardExample
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkCardTest : BpkSnapshotTest() {

  @Test
  fun smallCorner() = capture {
    SmallCornersCardExample(Modifier.fillMaxSize())
  }

  @Test
  fun largeCorner() {
    assumeVariant(BpkTestVariant.Default)

    capture {
      LargeCornersCardExample(Modifier.fillMaxSize())
    }
  }

  @Test
  fun noPadding() {
    assumeVariant(BpkTestVariant.Default)

    capture {
      NoPaddingCardExample(Modifier.fillMaxSize())
    }
  }

  @Test
  fun unfocused() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)

    capture {
      NonClickableCardExample(Modifier.fillMaxSize())
    }
  }

  @Test
  fun focused() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)

    capture {
      FocusableCardExample(Modifier.fillMaxSize())
    }
  }

  @Test
  fun noElevation() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)

    capture {
      NoElevationCardExample(Modifier.fillMaxSize())
    }
  }

  private fun capture(content: @Composable () -> Unit) {
    composed(
      size = IntSize(320, 240),
    ) {
      Box(
        Modifier
          .background(BpkTheme.colors.textSecondary)
          .padding(BpkSpacing.Lg)
      ) {
        content()
      }
    }
  }
}
