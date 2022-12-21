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

package net.skyscanner.backpack.compose.nudger

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.SnapshotUtil.assumeVariant
import net.skyscanner.backpack.demo.compose.NudgerExample
import net.skyscanner.backpack.demo.compose.NudgerStoryAvg
import net.skyscanner.backpack.demo.compose.NudgerStoryMax
import net.skyscanner.backpack.demo.compose.NudgerStoryMin
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkNudgerTest : BpkSnapshotTest() {

  @Test
  fun default() = snap {
    NudgerExample(initialValue = NudgerStoryAvg)
  }

  @Test
  fun disabled() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    snap {
      NudgerExample(initialValue = NudgerStoryAvg, enabled = false)
    }
  }

  @Test
  fun minusDisabled() {
    assumeVariant(BpkTestVariant.Default)
    snap {
      NudgerExample(initialValue = NudgerStoryMin)
    }
  }

  @Test
  fun plusDisabled() {
    assumeVariant(BpkTestVariant.Default)
    snap {
      NudgerExample(initialValue = NudgerStoryMax)
    }
  }

  @Test
  fun positiveOverflow() {
    assumeVariant(BpkTestVariant.Default)
    snap {
      NudgerExample(initialValue = NudgerStoryMax + NudgerStoryMax)
    }
  }

  @Test
  fun negativeOverflow() {
    assumeVariant(BpkTestVariant.Default)
    snap {
      NudgerExample(initialValue = NudgerStoryMin - NudgerStoryMax)
    }
  }
}
