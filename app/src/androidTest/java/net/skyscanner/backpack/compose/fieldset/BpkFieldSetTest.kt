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

package net.skyscanner.backpack.compose.fieldset

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkFieldSetTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(height = 128, width = 200)
  }

  @Test
  fun default() = composed {
    BpkFieldSet(
      label = "Title",
      description = "Description",
      status = BpkFieldStatus.Default,
    ) {
      DemoContent(status = it)
    }
  }

  @Test
  fun noTitle() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkFieldSet(
        description = "Description",
        status = BpkFieldStatus.Default,
      ) {
        DemoContent(status = it)
      }
    }
  }

  @Test
  fun noDescription() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkFieldSet(
        description = "Description",
        status = BpkFieldStatus.Default,
      ) {
        DemoContent(status = it)
      }
    }
  }

  @Test
  fun noTitleAndDescription() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkFieldSet(
        status = BpkFieldStatus.Default,
      ) {
        DemoContent(status = it)
      }
    }
  }

  @Test
  fun disabled() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkFieldSet(
        label = "Title",
        description = "Description",
        status = BpkFieldStatus.Disabled,
      ) {
        DemoContent(status = it)
      }
    }
  }

  @Test
  fun validated() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkFieldSet(
        label = "Title",
        description = "Description",
        status = BpkFieldStatus.Validated,
      ) {
        DemoContent(status = it)
      }
    }
  }

  @Test
  fun error() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    composed {
      BpkFieldSet(
        label = "Title",
        description = "Description",
        status = BpkFieldStatus.Error("Error text"),
      ) {
        DemoContent(status = it)
      }
    }
  }

  @Composable
  private fun DemoContent(
    status: BpkFieldStatus,
    modifier: Modifier = Modifier,
  ) {
    Surface(
      modifier = modifier,
      color = when (status) {
        is BpkFieldStatus.Default -> BpkTheme.colors.primary
        is BpkFieldStatus.Disabled -> BpkTheme.colors.backgroundSecondary
        is BpkFieldStatus.Error -> BpkTheme.colors.systemRed
        is BpkFieldStatus.Validated -> BpkColor.Monteverde
      },
    ) {
      Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth().height(BpkSpacing.Xxl)) {
        BpkText(
          text = when (status) {
            is BpkFieldStatus.Default -> "Default"
            is BpkFieldStatus.Disabled -> "Disabled"
            is BpkFieldStatus.Error -> "Error(text=${status.text})"
            is BpkFieldStatus.Validated -> "Validated"
          }
        )
      }
    }
  }
}
