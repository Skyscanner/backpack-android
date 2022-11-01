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

import androidx.compose.runtime.LaunchedEffect
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Heart
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkFloatingNotificationTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(height = 110, width = 450)
  }

  @Test
  fun textOnly() {
    composed {
      val state = rememberBpkFloatingNotificationState(initiallyVisible = true)
      BpkFloatingNotification(hostState = state)
      LaunchedEffect(key1 = Unit) {
        state.show(
          message = "Lorem ipsum dolor sit amet"
        )
      }
    }
  }

  @Test
  fun withIcon() {
    composed {
      val state = rememberBpkFloatingNotificationState(initiallyVisible = true)
      BpkFloatingNotification(hostState = state)
      LaunchedEffect(key1 = Unit) {
        state.show(
          message = "Lorem ipsum dolor sit amet",
          icon = BpkIcon.Heart,
        )
      }
    }
  }

  @Test
  fun withCta() {
    composed {
      val state = rememberBpkFloatingNotificationState(initiallyVisible = true)
      BpkFloatingNotification(hostState = state)
      LaunchedEffect(key1 = Unit) {
        state.show(
          message = "Lorem ipsum dolor sit amet",
          action = "Open",
          onClick = {},
        )
      }
    }
  }

  @Test
  fun withIconAndCta() {
    composed {
      val state = rememberBpkFloatingNotificationState(initiallyVisible = true)
      BpkFloatingNotification(hostState = state)
      LaunchedEffect(key1 = Unit) {
        state.show(
          message = "Lorem ipsum dolor sit amet",
          action = "Open",
          onClick = {},
          icon = BpkIcon.Heart,
        )
      }
    }
  }
}
