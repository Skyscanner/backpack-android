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

import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Heart
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class BpkFloatingNotificationTest : BpkSnapshotTest() {

  private val state = BpkFloatingNotificationState()
  private val scope = TestScope(UnconfinedTestDispatcher())

  @Test
  fun textOnly() {
    scope.launch {
      state.show(
        text = "Lorem ipsum dolor sit amet"
      )
    }
    snap(height = 110.dp) { BpkFloatingNotification(state) }
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun withIcon() {
    scope.launch {
      state.show(
        text = "Lorem ipsum dolor sit amet",
        icon = BpkIcon.Heart,
      )
    }
    snap(height = 110.dp) {
      BpkFloatingNotification(state)
    }
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun withCta() {
    scope.launch {
      state.show(
        text = "Lorem ipsum dolor sit amet",
        cta = "Open",
        onClick = {},
      )
    }
    snap(height = 110.dp) {
      BpkFloatingNotification(state)
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
  fun withIconAndCta() {
    scope.launch {
      state.show(
        text = "Lorem ipsum dolor sit amet",
        cta = "Open",
        onClick = {},
        icon = BpkIcon.Heart,
      )
    }
    snap(height = 110.dp) {
      BpkFloatingNotification(state)
    }
  }
}
