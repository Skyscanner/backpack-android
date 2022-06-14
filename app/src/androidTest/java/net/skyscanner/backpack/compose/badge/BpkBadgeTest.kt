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

package net.skyscanner.backpack.compose.badge

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.CloseCircle
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkBadgeTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(height = 50, width = 200)
  }

  @Test
  fun default() = composed {
    BpkBadge(text = "Default", type = BpkBadgeType.Destructive)
  }

  @Test
  fun withIcon() = composed {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl)
    BpkBadge(text = "Default", type = BpkBadgeType.Destructive, icon = BpkIcon.CloseCircle)
  }

  @Test
  fun normal() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkBadge(text = BpkBadgeType.Normal.toString(), type = BpkBadgeType.Normal)
    }
  }

  @Test
  fun strong() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkBadge(text = BpkBadgeType.Strong.toString(), type = BpkBadgeType.Strong)
    }
  }

  @Test
  fun success() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkBadge(text = BpkBadgeType.Success.toString(), type = BpkBadgeType.Success)
    }
  }

  @Test
  fun warning() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkBadge(text = BpkBadgeType.Warning.toString(), type = BpkBadgeType.Warning)
    }
  }

  @Test
  fun destructive() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkBadge(text = BpkBadgeType.Destructive.toString(), type = BpkBadgeType.Destructive)
    }
  }

  @Test
  fun inverse() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkBadge(text = BpkBadgeType.Inverse.toString(), type = BpkBadgeType.Inverse)
    }
  }

  @Test
  fun outline() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed(background = BpkColor.SkyBlue) {
      BpkBadge(text = BpkBadgeType.Outline.toString(), type = BpkBadgeType.Outline)
    }
  }
}
