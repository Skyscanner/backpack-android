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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Heart
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class BpkFloatingNotificationTest : BpkSnapshotTest() {

  private val state = BpkFloatingNotificationState()
  private val scope = TestScope(UnconfinedTestDispatcher())

  @Before
  fun setup() {
    setDimensions(height = 110, width = 450)
  }

  @Test
  fun textOnly() {
    scope.launch {
      state.show(
        text = "Lorem ipsum dolor sit amet"
      )
    }
    composed { BpkFloatingNotification(state) }
  }

  @Test
  fun withIcon() {
    scope.launch {
      state.show(
        text = "Lorem ipsum dolor sit amet",
        icon = BpkIcon.Heart,
      )
    }
    composed {
      BpkFloatingNotification(state)
    }
  }

  @Test
  fun withCta() {
    scope.launch {
      state.show(
        text = "Lorem ipsum dolor sit amet",
        action = "Open",
        onClick = {},
      )
    }
    composed {
      BpkFloatingNotification(state)
    }
  }

  @Test
  fun withIconAndCta() {
    scope.launch {
      state.show(
        text = "Lorem ipsum dolor sit amet",
        action = "Open",
        onClick = {},
        icon = BpkIcon.Heart,
      )
    }
    composed {
      BpkFloatingNotification(state)
    }
  }
}
