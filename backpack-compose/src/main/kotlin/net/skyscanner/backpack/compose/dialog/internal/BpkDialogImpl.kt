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

package net.skyscanner.backpack.compose.dialog.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkDimension
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BpkDialogImpl(
  onDismissRequest: () -> Unit,
  icon: Dialog.Icon?,
  title: String,
  text: String,
  buttons: List<Dialog.Button>,
  properties: DialogProperties,
) {
  Dialog(onDismissRequest = onDismissRequest, properties = properties) {
    Box(contentAlignment = Alignment.TopCenter) {
      Surface(
        modifier = Modifier.padding(top = IconPadding),
        shape = BpkTheme.shapes.medium,
        color = BpkTheme.colors.backgroundElevation01,
      ) {
        Column(
          modifier = Modifier
            .padding(top = DialogPaddingTop, bottom = BpkDimension.Spacing.Lg)
            .padding(horizontal = BpkDimension.Spacing.Lg),
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          DialogTextContent(title = title, text = text)
          DialogButtons(buttons)
        }
      }
      DialogIcon(icon = icon)
    }
  }
}

@Composable
private fun DialogTextContent(title: String, text: String) {
  BpkText(text = title, style = BpkTheme.typography.heading3, textAlign = TextAlign.Center)
  BpkText(
    modifier = Modifier.padding(top = BpkDimension.Spacing.Base, bottom = BpkDimension.Spacing.Lg),
    text = text,
    textAlign = TextAlign.Center,
  )
}

@Composable
private fun DialogButtons(buttons: List<Dialog.Button>) {
  Column(
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
  ) {
    buttons.forEach {
      BpkButton(
        modifier = Modifier.fillMaxWidth(),
        text = it.button.text,
        onClick = it.button.onClick,
        size = BpkButtonSize.Large,
        type = it.type,
      )
    }
  }
}

@Composable
private fun DialogIcon(icon: Dialog.Icon?) {
  icon?.let {
    Box(
      modifier = Modifier
        .clip(CircleShape)
        .background(BpkTheme.colors.backgroundElevation01)
        .padding(IconBorder)
        .background(icon.backgroundColor, CircleShape)
        .defaultMinSize(minWidth = IconSize, minHeight = IconSize),
      contentAlignment = Alignment.Center,
    ) {
      Icon(painter = icon.icon, contentDescription = null, tint = BpkColor.White)
    }
  }
}

private val CircleShape = RoundedCornerShape(percent = 50)
private val IconSize = 64.dp
private val IconBorder = 4.dp
private val IconPadding = 40.dp
private val DialogPaddingTop = 40.dp
