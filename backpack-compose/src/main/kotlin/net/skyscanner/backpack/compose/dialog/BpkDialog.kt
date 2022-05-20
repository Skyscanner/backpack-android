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

package net.skyscanner.backpack.compose.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.window.DialogProperties
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.dialog.internal.BpkDialogImpl
import net.skyscanner.backpack.compose.dialog.internal.Dialog

@Composable
fun BpkSuccessDialog(
  onDismissRequest: () -> Unit,
  icon: Painter?,
  title: String,
  text: String,
  confirmButton: DialogButton,
  secondaryButton: DialogButton? = null,
  properties: DialogProperties = DialogProperties(),
) {
  BpkDialogImpl(
    icon = icon?.let { Dialog.Icon.Success(icon) },
    title = title,
    text = text,
    buttons = listOfNotNull(
      Dialog.Button(BpkButtonType.Primary, confirmButton),
      secondaryButton?.let { Dialog.Button(BpkButtonType.Secondary, secondaryButton) },
    ),
    onDismissRequest = onDismissRequest,
    properties = properties
  )
}

@Composable
fun BpkSuccessDialog(
  onDismissRequest: () -> Unit,
  icon: Painter?,
  title: String,
  text: String,
  confirmButton: DialogButton,
  secondaryButton: DialogButton,
  linkButton: DialogButton? = null,
  properties: DialogProperties = DialogProperties(),
) {
  BpkDialogImpl(
    icon = icon?.let { Dialog.Icon.Success(icon) },
    title = title,
    text = text,
    buttons = listOfNotNull(
      Dialog.Button(BpkButtonType.Primary, confirmButton),
      Dialog.Button(BpkButtonType.Secondary, secondaryButton),
      linkButton?.let { Dialog.Button(BpkButtonType.Link, linkButton) },
    ),
    onDismissRequest = onDismissRequest,
    properties = properties
  )
}

@Composable
fun BpkAlertDialog(
  onDismissRequest: () -> Unit,
  icon: Painter?,
  title: String,
  text: String,
  confirmButton: DialogButton,
  secondaryButton: DialogButton? = null,
  properties: DialogProperties = DialogProperties(),
) {
  BpkDialogImpl(
    icon = icon?.let { Dialog.Icon.Alert(icon) },
    title = title,
    text = text,
    buttons = listOfNotNull(
      Dialog.Button(BpkButtonType.Primary, confirmButton),
      secondaryButton?.let { Dialog.Button(BpkButtonType.Secondary, secondaryButton) },
    ),
    onDismissRequest = onDismissRequest,
    properties = properties
  )
}

@Composable
fun BpkAlertDialog(
  onDismissRequest: () -> Unit,
  icon: Painter?,
  title: String,
  text: String,
  confirmButton: DialogButton,
  secondaryButton: DialogButton,
  linkButton: DialogButton? = null,
  properties: DialogProperties = DialogProperties(),
) {
  BpkDialogImpl(
    icon = icon?.let { Dialog.Icon.Alert(icon) },
    title = title,
    text = text,
    buttons = listOfNotNull(
      Dialog.Button(BpkButtonType.Primary, confirmButton),
      Dialog.Button(BpkButtonType.Secondary, secondaryButton),
      linkButton?.let { Dialog.Button(BpkButtonType.Link, linkButton) },
    ),
    onDismissRequest = onDismissRequest,
    properties = properties
  )
}

@Composable
fun BpkDestructiveDialog(
  onDismissRequest: () -> Unit,
  icon: Painter?,
  title: String,
  text: String,
  confirmButton: DialogButton,
  linkButton: DialogButton? = null,
  properties: DialogProperties = DialogProperties(),
) {
  BpkDialogImpl(
    icon = icon?.let { Dialog.Icon.Destructive(icon) },
    title = title,
    text = text,
    buttons = listOfNotNull(
      Dialog.Button(BpkButtonType.Destructive, confirmButton),
      linkButton?.let { Dialog.Button(BpkButtonType.Link, linkButton) },
    ),
    onDismissRequest = onDismissRequest,
    properties = properties
  )
}

data class DialogButton(internal val text: String, internal val onClick: () -> Unit)
