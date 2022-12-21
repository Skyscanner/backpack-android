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

package net.skyscanner.backpack.compose.flare

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.compose.ContentPaddingFlareExample
import net.skyscanner.backpack.demo.compose.DefaultFlareExample
import net.skyscanner.backpack.demo.compose.RadiusFlareExample
import net.skyscanner.backpack.demo.compose.UpFlareExample
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkFlareTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    snapshotSize = IntSize(200, 150)
  }

  @Test
  fun default() = snap {
    DefaultFlareExample(Modifier.fillMaxSize())
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun up() {
    snap {
      UpFlareExample(Modifier.fillMaxSize())
    }
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun radius() {
    snap {
      RadiusFlareExample(Modifier.fillMaxSize())
    }
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun contentPadding() {
    snap {
      ContentPaddingFlareExample(Modifier.fillMaxSize())
    }
  }
}
