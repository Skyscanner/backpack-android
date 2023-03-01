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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.floatingnotification.rememberBpkFloatingNotificationState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Heart
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.FloatingNotificationComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

// todo
@Composable
@FloatingNotificationComponent
@ComposeStory
fun FloatingNotificationStory(modifier: Modifier = Modifier) {
  Box(modifier) {

    val state = rememberBpkFloatingNotificationState()
    val scope: CoroutineScope = rememberCoroutineScope()

    val text = stringResource(R.string.floating_notification_saved)
    val cta = stringResource(R.string.floating_notification_view)

    Column(
      modifier = Modifier.padding(BpkSpacing.Base),
      verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {

      BpkButton(text = stringResource(R.string.floating_notification_just_text)) {
        scope.launch {
          state.show(
            text = text,
          )
        }
      }

      BpkButton(text = stringResource(R.string.floating_notification_text_with_icon)) {
        scope.launch {
          state.show(
            text = text,
            icon = BpkIcon.Heart,
          )
        }
      }

      BpkButton(text = stringResource(R.string.floating_notification_with_action)) {
        scope.launch {
          state.show(
            text = text,
            cta = cta,
            onClick = {},
          )
        }
      }

      BpkButton(text = stringResource(R.string.floating_notification_with_icon_and_action)) {
        scope.launch {
          state.show(
            text = text,
            icon = BpkIcon.Heart,
            cta = cta,
            onClick = {},
            onExit = {},
          )
        }
      }
    }

    BpkFloatingNotification(state)
  }
}
