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

package net.skyscanner.backpack.compose.checkbox

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.SnapshotUtil.assumeVariant
import net.skyscanner.backpack.demo.compose.CheckedCheckboxSample
import net.skyscanner.backpack.demo.compose.CustomContentCheckboxSample
import net.skyscanner.backpack.demo.compose.DefaultCheckboxSample
import net.skyscanner.backpack.demo.compose.DisabledCheckedCheckboxSample
import net.skyscanner.backpack.demo.compose.DisabledUncheckedCheckboxSample
import net.skyscanner.backpack.demo.compose.IntermediateCheckboxSample
import net.skyscanner.backpack.demo.compose.UncheckedCheckboxSample
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkCheckboxTest : BpkSnapshotTest() {

  @Test
  fun default() = snap {
    DefaultCheckboxSample()
  }

  @Test
  fun intermediate() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    snap {
      IntermediateCheckboxSample()
    }
  }

  @Test
  fun unchecked() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    snap {
      UncheckedCheckboxSample()
    }
  }

  @Test
  fun checked() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    snap {
      CheckedCheckboxSample()
    }
  }

  @Test
  fun disabledUnchecked() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    snap {
      DisabledUncheckedCheckboxSample()
    }
  }

  @Test
  fun disabledChecked() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    snap {
      DisabledCheckedCheckboxSample()
    }
  }

  @Test
  fun customContent() {
    assumeVariant(BpkTestVariant.Default)
    snap {
      CustomContentCheckboxSample()
    }
  }
}
