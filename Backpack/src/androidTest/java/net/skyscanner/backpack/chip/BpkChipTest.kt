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

package net.skyscanner.backpack.chip

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.util.TestContext
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkChipTest {

  private val context = TestContext

  @Test
  fun test_toggle() {
    val chip = BpkChip(context).apply {
      isSelected = false
    }

    chip.toggle()
    Assert.assertTrue(chip.isSelected)

    chip.toggle()
    Assert.assertFalse(chip.isSelected)
  }

  @Test
  fun test_toggle_when_disabled() {
    val chip = BpkChip(context).apply {
      isSelected = false
      isEnabled = false
    }

    chip.toggle()
    Assert.assertFalse(chip.isSelected)
  }
}
