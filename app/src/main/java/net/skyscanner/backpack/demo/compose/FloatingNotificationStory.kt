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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.floatingnotification.Animation
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.floatingnotification.CTA
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Heart

@Composable
fun FloatingNotificationStory() {
  val (show, setShow) = remember { mutableStateOf(false) }
  Column(
    modifier = Modifier.fillMaxWidth().padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base)
  ) {
    BpkButton(
      text = "Show Notifications",
      enabled = !show
    ) {
      setShow(true)
    }
    TextOnlyExample(show)
    TextWithIconExample(show)
    TextWithCTAExample(show)
    TextWithIconWithCTAExample(show)
    TextWithNoAnimationsExample(show) {
      setShow(false)
    }
  }
}

@Composable
@Preview
fun TextOnlyExample(show: Boolean = false) {
  BpkFloatingNotification(
    text = "Lorem ipsum dolor sit amet",
    show = show
  )
}

@Composable
@Preview
fun TextWithIconExample(show: Boolean = false) {
  BpkFloatingNotification(
    text = "Lorem ipsum dolor sit amet",
    icon = BpkIcon.Heart,
    show = show
  )
}

@Composable
@Preview
fun TextWithCTAExample(show: Boolean = false) {
  BpkFloatingNotification(
    text = "Lorem ipsum dolor sit amet",
    cta = CTA("Open", onClick = {}),
    show = show
  )
}

@Composable
@Preview
fun TextWithIconWithCTAExample(show: Boolean = false) {
  BpkFloatingNotification(
    text = "Lorem ipsum dolor sit amet",
    cta = CTA("Open", onClick = {}),
    icon = BpkIcon.Heart,
    show = show
  )
}

@Composable
@Preview
fun TextWithNoAnimationsExample(show: Boolean = false, onFinished: () -> Unit = {}) {
  BpkFloatingNotification(
    text = "Lorem ipsum dolor sit amet",
    animation = Animation(enabled = false, onFinished = onFinished),
    show = show
  )
}
