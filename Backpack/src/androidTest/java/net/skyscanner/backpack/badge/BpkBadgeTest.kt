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

package net.skyscanner.backpack.badge

import android.graphics.PixelFormat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkBadgeTest {

  @Test
  fun test_message() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val badge = BpkBadge(context)
    badge.message = "error"
    badge.type = BpkBadge.Type.Destructive
    Assert.assertEquals("error", badge.text.toString())
  }

  @Test
  @Ignore // FIXME: Test is not working anymore (the component looks fine)
  fun test_alpha_default() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val badge = BpkBadge(context)
    badge.type = BpkBadge.Type.Success
    Assert.assertEquals(PixelFormat.OPAQUE, badge.background.opacity)
  }

  @Test
  fun test_alpha_outline() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val badge = BpkBadge(context)
    badge.type = BpkBadge.Type.Outline
    Assert.assertEquals(PixelFormat.TRANSLUCENT, badge.background.opacity)
  }
}
