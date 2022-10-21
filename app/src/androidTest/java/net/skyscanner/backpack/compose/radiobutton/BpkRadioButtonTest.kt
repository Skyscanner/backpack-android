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

package net.skyscanner.backpack.compose.radiobutton

import androidx.compose.ui.unit.IntSize
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkComposeSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.SnapshotUtil.assumeVariant
import net.skyscanner.backpack.demo.compose.CustomContentRadioButtonExample
import net.skyscanner.backpack.demo.compose.DefaultCheckedRadioButtonExample
import net.skyscanner.backpack.demo.compose.DefaultUncheckedRadioButtonExample
import net.skyscanner.backpack.demo.compose.DisabledCheckedRadioButtonExample
import net.skyscanner.backpack.demo.compose.DisabledUnCheckedRadioButtonExample
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkRadioButtonTest : BpkComposeSnapshotTest() {

  @Before
  fun setup() {
    snapshotSize = IntSize(height = 50, width = 200)
  }

  @Test
  fun defaultUnchecked() = composed {
    DefaultUncheckedRadioButtonExample()
  }

  @Test
  fun defaultChecked() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      DefaultCheckedRadioButtonExample()
    }
  }

  @Test
  fun disabledUnchecked() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      DisabledUnCheckedRadioButtonExample()
    }
  }

  @Test
  fun disabledChecked() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      DisabledCheckedRadioButtonExample()
    }
  }

  @Test
  fun customContent() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      CustomContentRadioButtonExample()
    }
  }
}
