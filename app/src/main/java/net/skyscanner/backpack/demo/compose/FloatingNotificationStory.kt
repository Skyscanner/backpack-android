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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.floatingnotification.Animation
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.floatingnotification.Cta
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Heart
import net.skyscanner.backpack.demo.R

@Composable
fun FloatingNotificationStory() {
  var show by remember { mutableStateOf(false) }
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base)
  ) {
    BpkButton(
      text = stringResource(id = R.string.floating_notification_show),
      enabled = !show
    ) {
      show = true
    }
    TextOnlyExample(show)
    TextWithIconExample(show)
    TextWithCtaExample(show)
    TextWithIconWithCtaExample(show)
    TextWithNoAnimationsExample(show) {
      show = false
    }
  }
}

@Composable
@Preview
fun TextOnlyExample(show: Boolean = false) {
  BpkFloatingNotification(
    text = stringResource(id = R.string.stub_xs),
    show = show
  )
}

@Composable
@Preview
fun TextWithIconExample(show: Boolean = false) {
  BpkFloatingNotification(
    text = stringResource(id = R.string.stub_xs),
    icon = BpkIcon.Heart,
    show = show
  )
}

@Composable
@Preview
fun TextWithCtaExample(show: Boolean = false) {
  BpkFloatingNotification(
    text = stringResource(id = R.string.stub_xs),
    cta = Cta("Open", onClick = {}),
    show = show
  )
}

@Composable
@Preview
fun TextWithIconWithCtaExample(show: Boolean = false) {
  BpkFloatingNotification(
    text = stringResource(id = R.string.stub_xs),
    cta = Cta("Open", onClick = {}),
    icon = BpkIcon.Heart,
    show = show
  )
}

@Composable
@Preview
fun TextWithNoAnimationsExample(show: Boolean = false, onExit: () -> Unit = {}) {
  BpkFloatingNotification(
    text = stringResource(id = R.string.stub_xs),
    animation = Animation(animateOnEnter = false, animateOnExit = false),
    onExit = onExit,
    show = show
  )
}
