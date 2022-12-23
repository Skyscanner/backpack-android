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

package net.skyscanner.backpack.compose.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Deals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkChipTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    snapshotSize = IntSize(100, 32)
  }

  @Test
  fun default() = snap {
    BpkChip(text = "Chip")
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun notSelected() {
    snap {
      BpkChip(text = "Chip", selected = false)
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun selected() {
    snap {
      BpkChip(text = "Chip", selected = true)
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun disabled() {
    snap {
      BpkChip(text = "Chip", enabled = false)
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun notSelected_OnDark() {
    snap {
      OnDarkBox {
        BpkChip(text = "Chip", selected = false, style = BpkChipStyle.OnDark)
      }
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun selected_OnDark() {
    snap {
      OnDarkBox {
        BpkChip(text = "Chip", selected = true, style = BpkChipStyle.OnDark)
      }
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun disabled_OnDark() {
    snap {
      OnDarkBox {
        BpkChip(text = "Chip", enabled = false, style = BpkChipStyle.OnDark)
      }
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun withIcon() {
    snap {
      BpkChip(text = "Chip", icon = BpkIcon.Deals)
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun typeSelected() {
    snap {
      BpkChip(text = "Chip", type = BpkChipType.Select)
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun typeDismiss() {
    snap {
      BpkChip(text = "Chip", type = BpkChipType.Dismiss)
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun typeSelected_WithIcon() {
    snap {
      BpkChip(text = "Chip", icon = BpkIcon.Deals, type = BpkChipType.Dismiss)
    }
  }

  @Composable
  private fun OnDarkBox(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(modifier = modifier.background(BpkTheme.colors.textOnLight).fillMaxSize(), content = content)
  }
}
