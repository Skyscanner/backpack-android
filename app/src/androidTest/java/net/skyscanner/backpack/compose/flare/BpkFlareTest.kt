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
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
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
    setDimensions(height = 150, width = 200)
  }

  @Test
  fun default() = composed {
    DefaultFlareExample(Modifier.fillMaxSize())
  }

  @Test
  fun up() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      UpFlareExample(Modifier.fillMaxSize())
    }
  }

  @Test
  fun radius() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      RadiusFlareExample(Modifier.fillMaxSize())
    }
  }

  @Test
  fun contentPadding() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      ContentPaddingFlareExample(Modifier.fillMaxSize())
    }
  }
}
