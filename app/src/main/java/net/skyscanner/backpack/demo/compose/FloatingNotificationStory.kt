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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.floatingnotification.Animation
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.floatingnotification.Cta
import net.skyscanner.backpack.compose.floatingnotification.rememberBpkFloatingNotificationState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Heart
import net.skyscanner.backpack.demo.R

@Composable
fun FloatingNotificationStory() {
  val scope = rememberCoroutineScope()
  val textOnlyState = rememberBpkFloatingNotificationState()
  val longTextWithIconState = rememberBpkFloatingNotificationState()
  val textWithCtaState = rememberBpkFloatingNotificationState()
  val longTextWithIconWithCtaState = rememberBpkFloatingNotificationState()
  val textWithNoAnimationState = rememberBpkFloatingNotificationState()

  BpkButton(
    modifier = Modifier
      .fillMaxWidth()
      .padding(BpkSpacing.Base),
    text = stringResource(id = R.string.floating_notification_show),
    enabled = !textOnlyState.visible && !longTextWithIconState.visible && !textWithCtaState.visible &&
      !longTextWithIconWithCtaState.visible && !textWithNoAnimationState.visible
  ) {
    scope.launch {
      textOnlyState.show(
        text = "Lorem ipsum dolor sit amet."
      )
      longTextWithIconState.show(
        text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.",
        icon = BpkIcon.Heart,
      )
      textWithCtaState.show(
        text = "Lorem ipsum dolor sit amet.",
        cta = Cta("Open", onClick = {})
      )
      longTextWithIconWithCtaState.show(
        text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.",
        icon = BpkIcon.Heart,
        cta = Cta("Open", onClick = {})
      )
      textWithNoAnimationState.show(
        text = "Lorem ipsum dolor sit amet.",
        animation = Animation(animateOnEnter = false, animateOnExit = false)
      )
    }
  }
  BpkFloatingNotification(hostState = textOnlyState)
  BpkFloatingNotification(hostState = longTextWithIconState)
  BpkFloatingNotification(hostState = textWithCtaState)
  BpkFloatingNotification(hostState = longTextWithIconWithCtaState)
  BpkFloatingNotification(hostState = textWithNoAnimationState)
}
