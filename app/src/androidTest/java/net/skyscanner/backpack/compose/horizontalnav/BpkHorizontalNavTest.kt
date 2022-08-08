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

package net.skyscanner.backpack.compose.horizontalnav

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.demo.compose.BpkHorizontalNav_LargeWithIcon_Sample
import net.skyscanner.backpack.demo.compose.BpkHorizontalNav_Large_Sample
import net.skyscanner.backpack.demo.compose.BpkHorizontalNav_SmallWithIcon_Sample
import net.skyscanner.backpack.demo.compose.BpkHorizontalNav_Small_Sample
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkHorizontalNavTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(height = 50, width = 200)
  }

  @Test
  fun default() = composed {
    BpkHorizontalNav_Large_Sample()
  }

  @Test
  fun small() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkHorizontalNav_Small_Sample()
    }
  }

  @Test
  fun smallWithIcon() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    composed {
      BpkHorizontalNav_SmallWithIcon_Sample()
    }
  }

  @Test
  fun large() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkHorizontalNav_Large_Sample()
    }
  }

  @Test
  fun largeWithIcon() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    composed {
      BpkHorizontalNav_LargeWithIcon_Sample()
    }
  }
}
