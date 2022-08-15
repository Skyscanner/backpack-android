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

package net.skyscanner.backpack.skeleton

import android.graphics.drawable.ColorDrawable
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import net.skyscanner.backpack.R
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkCircleSkeletonTest {

  @Test
  @UiThreadTest
  fun test_circleSkeleton() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val skeleton = BpkCircleSkeleton(context)
    var background = skeleton.background as ColorDrawable
    Assert.assertEquals(context.getColor(R.color.__skeletonBackground), background.color)

    Assert.assertEquals(true, skeleton.clipToOutline)
  }
}
