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

package net.skyscanner.backpack.compose.spinner

import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.CompositionLocalProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.compose.tokens.BpkColor
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkSpinnerTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(height = 24, width = 24)
  }

  @Test
  fun default() = composed {
    BpkSpinner(size = BpkSpinnerSize.Large)
  }

  @Test
  fun small() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkSpinner(size = BpkSpinnerSize.Small)
    }
  }

  @Test
  fun textPrimary() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkSpinner(size = BpkSpinnerSize.Large, style = BpkSpinnerStyle.TextPrimary)
    }
  }

  @Test
  fun disabled() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkSpinner(size = BpkSpinnerSize.Large, style = BpkSpinnerStyle.Disabled)
    }
  }

  @Test
  fun onDarkSurface() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed(background = BpkColor.Black) {
      BpkSpinner(size = BpkSpinnerSize.Large, style = BpkSpinnerStyle.OnDarkSurface)
    }
  }

  @Test
  fun dynamicOnDarkSurface() {
    assumeVariant(BpkTestVariant.Default)
    composed(background = BpkColor.Black) {
      CompositionLocalProvider(LocalContentColor provides BpkColor.White) {
        BpkSpinner(size = BpkSpinnerSize.Large)
      }
    }
  }

  @Test
  fun dynamicTextPrimary() {
    assumeVariant(BpkTestVariant.Default)
    composed(background = BpkColor.White) {
      CompositionLocalProvider(LocalContentColor provides BpkColor.Black) {
        BpkSpinner(size = BpkSpinnerSize.Large)
      }
    }
  }
}
