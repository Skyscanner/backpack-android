/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import net.skyscanner.backpack.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkChipTest {
  private lateinit var context: Context

  @Before
  fun beforeAll() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
  }

  @Test
  fun test_message() {
    val chip = BpkChip(context).apply {
      text = "Message"
    }
    Assert.assertEquals("Message", chip.text.toString())
    Assert.assertEquals(ContextCompat.getColor(context, R.color.bpkTextPrimary), chip.currentTextColor)
  }

  @Test
  fun test_selected_state() {
    val chip = BpkChip(context).apply {
      isSelected = true
    }
    Assert.assertEquals(ContextCompat.getColor(context, R.color.bpkWhite), chip.currentTextColor)
  }

  @Test
  fun test_disabled_state() {
    val chip = BpkChip(context).apply {
      disabled = true
    }
    Assert.assertEquals(ContextCompat.getColor(context, R.color.bpkSkyGrayTint04), chip.currentTextColor)
  }

  @Test
  fun test_toggle() {
    val chip = BpkChip(context).apply {
      isSelected = false
    }

    chip.toggle()
    Assert.assertEquals(ContextCompat.getColor(context, R.color.bpkWhite), chip.currentTextColor)

    chip.toggle()
    Assert.assertEquals(ContextCompat.getColor(context, R.color.bpkTextPrimary), chip.currentTextColor)
  }

  @Test
  fun test_toggle_when_disabled() {
    val chip = BpkChip(context).apply {
      isSelected = false
      disabled = true
    }

    chip.toggle()
    Assert.assertEquals(ContextCompat.getColor(context, R.color.bpkSkyGrayTint04), chip.currentTextColor)
  }

  @Test
  fun test_set_chipBackgroundColor() {

    val chip = BpkChip(context).apply {
      Assert.assertEquals(ContextCompat.getColor(context, R.color.bpkBackgroundSecondary), chipBackgroundColor)
      Assert.assertNotEquals(ContextCompat.getColor(context, R.color.bpkTextSecondary), chipBackgroundColor)
      chipBackgroundColor = ContextCompat.getColor(context, R.color.bpkTextSecondary)
    }

    Assert.assertEquals(ContextCompat.getColor(context, R.color.bpkTextSecondary), chip.chipBackgroundColor)
  }

  @Test
  fun chipSelectedBackgroundColor() {

    val chip = BpkChip(context).apply {
      Assert.assertEquals(ContextCompat.getColor(context, R.color.bpkSkyBlue), selectedBackgroundColor)
      selectedBackgroundColor = ContextCompat.getColor(context, R.color.bpkErfoud)
    }

    Assert.assertEquals(ContextCompat.getColor(context, R.color.bpkErfoud), chip.selectedBackgroundColor)
  }
}
