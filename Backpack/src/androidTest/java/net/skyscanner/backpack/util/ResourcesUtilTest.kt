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

package net.skyscanner.backpack.util

import android.content.Context
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import net.skyscanner.backpack.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResourcesUtilTest {

  private lateinit var context: Context

  @Before
  fun setUp() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
  }

  @Test
  fun test_getColor() {
    val expected = context.getColor(R.color.bpkBackgroundSecondary)
    Assert.assertEquals(expected, ResourcesUtil.getColor(TextView(context), R.color.bpkBackgroundSecondary))
    Assert.assertEquals(expected, ResourcesUtil.getColor(context.resources, R.color.bpkBackgroundSecondary))
  }

  @Test
  fun test_getColor_extension() {
    val expected = context.getColor(R.color.bpkBackgroundSecondary)
    Assert.assertEquals(expected, TextView(context).getColor(R.color.bpkBackgroundSecondary))
  }
}
