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
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.dialog.BpkDestructiveDialog
import net.skyscanner.backpack.compose.dialog.BpkSuccessDialog
import net.skyscanner.backpack.compose.dialog.BpkWarningDialog
import net.skyscanner.backpack.compose.dialog.DialogButton
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.AlertAdd
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Tick
import net.skyscanner.backpack.compose.tokens.Trash
import net.skyscanner.backpack.demo.R

@Composable
fun DialogStory() {
  Column(
    modifier = Modifier.padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
  ) {
    var shownDialog by rememberSaveable { mutableStateOf(ShownDialog.None) }
    ShownDialog.values().forEach {
      if (it != ShownDialog.None) {
        BpkButton(it.buttonText) {
          shownDialog = it
        }
      }
    }

    when (shownDialog) {
      ShownDialog.SuccessOneButton -> SuccessOneButtonDialogExample { shownDialog = ShownDialog.None }
      ShownDialog.SuccessTwoButtons -> SuccessTwoButtonsDialogExample { shownDialog = ShownDialog.None }
      ShownDialog.SuccessThreeButtons -> SuccessThreeButtonsDialogExample { shownDialog = ShownDialog.None }
      ShownDialog.Warning -> WarningDialogExample { shownDialog = ShownDialog.None }
      ShownDialog.Destructive -> DestructiveDialogExample { shownDialog = ShownDialog.None }
      ShownDialog.NoIcon -> NoIconDialogExample { shownDialog = ShownDialog.None }
      ShownDialog.None -> {}
    }
  }
}

enum class ShownDialog(val buttonText: String) {
  SuccessOneButton("Success One Button"),
  SuccessTwoButtons("Success Two Buttons"),
  SuccessThreeButtons("Success Three Buttons"),
  Warning("Warning"),
  Destructive("Destructive"),
  NoIcon("No Icon"),
  None(""),
}

@Preview
@Composable
fun SuccessOneButtonDialogExample(onDismiss: () -> Unit = {}) {
  BpkSuccessDialog(
    icon = BpkIcon.Tick,
    title = stringResource(id = R.string.dialog_title),
    text = stringResource(id = R.string.dialog_text),
    confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
    onDismissRequest = onDismiss,
  )
}

@Preview
@Composable
fun SuccessTwoButtonsDialogExample(onDismiss: () -> Unit = {}) {
  BpkSuccessDialog(
    icon = BpkIcon.Tick,
    title = stringResource(id = R.string.dialog_title),
    text = stringResource(id = R.string.dialog_text),
    confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
    secondaryButton = DialogButton(stringResource(id = R.string.dialog_skip), onDismiss),
    onDismissRequest = onDismiss,
  )
}

@Preview
@Composable
fun SuccessThreeButtonsDialogExample(onDismiss: () -> Unit = {}) {
  BpkSuccessDialog(
    icon = BpkIcon.Tick,
    title = stringResource(id = R.string.dialog_title),
    text = stringResource(id = R.string.dialog_text),
    confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
    secondaryButton = DialogButton(stringResource(id = R.string.dialog_skip), onDismiss),
    linkButton = DialogButton(stringResource(id = R.string.dialog_link_optional), onDismiss),
    onDismissRequest = onDismiss,
  )
}

@Preview
@Composable
fun WarningDialogExample(onDismiss: () -> Unit = {}) {
  BpkWarningDialog(
    icon = BpkIcon.AlertAdd,
    title = stringResource(id = R.string.dialog_title),
    text = stringResource(id = R.string.dialog_text),
    confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
    secondaryButton = DialogButton(stringResource(id = R.string.dialog_skip), onDismiss),
    linkButton = DialogButton(stringResource(id = R.string.dialog_link_optional), onDismiss),
    onDismissRequest = onDismiss,
  )
}

@Preview
@Composable
fun DestructiveDialogExample(onDismiss: () -> Unit = {}) {
  BpkDestructiveDialog(
    icon = BpkIcon.Trash,
    title = stringResource(id = R.string.dialog_title),
    text = stringResource(id = R.string.dialog_text),
    confirmButton = DialogButton(stringResource(id = R.string.dialog_delete), onDismiss),
    linkButton = DialogButton(stringResource(id = R.string.dialog_cancel), onDismiss),
    onDismissRequest = onDismiss,
  )
}

@Preview
@Composable
fun NoIconDialogExample(onDismiss: () -> Unit = {}) {
  BpkSuccessDialog(
    icon = null,
    title = stringResource(id = R.string.dialog_title),
    text = stringResource(id = R.string.dialog_text),
    confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
    secondaryButton = DialogButton(stringResource(id = R.string.dialog_skip), onDismiss),
    onDismissRequest = onDismiss,
  )
}
