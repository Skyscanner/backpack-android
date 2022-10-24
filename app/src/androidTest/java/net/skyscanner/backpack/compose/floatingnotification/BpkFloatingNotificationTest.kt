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

package net.skyscanner.backpack.compose.floatingnotification

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Heart
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkFloatingNotificationTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(height = 52, width = 288)
  }

  @Test
  fun textOnly() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkFloatingNotification(
        text = "Lorem ipsum dolor sit amet",
        show = true
      )
    }
  }

  @Test
  fun withIcon() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkFloatingNotification(
        text = "Lorem ipsum dolor sit amet",
        icon = BpkIcon.Heart,
        show = true
      )
    }
  }

  @Test
  fun withCTA() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkFloatingNotification(
        text = "Lorem ipsum dolor sit amet",
        cta = CTA("Open", onClick = {}),
        show = true
      )
    }
  }

  @Test
  fun withIconAndCTA() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    composed {
      BpkFloatingNotification(
        text = "Lorem ipsum dolor sit amet",
        cta = CTA("Open", onClick = {}),
        icon = BpkIcon.Heart,
        show = true
      )
    }
  }
}
