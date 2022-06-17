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

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Deals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkChipTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(height = 32, width = 100)
  }

  @Test
  fun default() = composed {
    BpkChip(text = "Chip")
  }

  @Test
  fun notSelected() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkChip(text = "Chip", selected = false)
    }
  }

  @Test
  fun selected() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkChip(text = "Chip", selected = true)
    }
  }

  @Test
  fun disabled() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkChip(text = "Chip", enabled = false)
    }
  }

  @Test
  fun notSelected_OnDark() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkChip(text = "Chip", selected = false, style = BpkChipStyle.OnDark)
    }
  }

  @Test
  fun selected_OnDark() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkChip(text = "Chip", selected = true, style = BpkChipStyle.OnDark)
    }
  }

  @Test
  fun disabled_OnDark() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkChip(text = "Chip", enabled = false, style = BpkChipStyle.OnDark)
    }
  }

  @Test
  fun withIcon() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl)
    composed {
      BpkChip(text = "Chip", icon = BpkIcon.Deals)
    }
  }

  @Test
  fun typeSelected() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl)
    composed {
      BpkChip(text = "Chip", type = BpkChipType.Select)
    }
  }

  @Test
  fun typeDismiss() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl)
    composed {
      BpkChip(text = "Chip", type = BpkChipType.Dismiss)
    }
  }

  @Test
  fun typeSelected_WithIcon() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl)
    composed {
      BpkChip(text = "Chip", icon = BpkIcon.Deals, type = BpkChipType.Dismiss)
    }
  }
}
