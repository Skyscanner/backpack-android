/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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
import android.view.View
import androidx.core.content.ContextCompat
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import net.skyscanner.backpack.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkOutlineChipTest {
  private lateinit var context: Context

  @Before
  fun beforeAll() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
  }

  @Test
  fun test_message() {
    // Given

    val block = mock<View.OnClickListener>()
    val chip = BpkOutlineChip(context).apply {
      text = "Message"
      setOnClickListener(block)
    }

    // When
    chip.performClick()

    // Then
    Assert.assertEquals("Message", chip.text.toString())
    Assert.assertEquals(ContextCompat.getColor(context, R.color.bpkWhite), chip.currentTextColor)
    verify(block)
  }
}
