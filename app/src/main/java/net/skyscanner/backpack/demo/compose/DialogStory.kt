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
import net.skyscanner.backpack.compose.dialog.BpkAlertDialog
import net.skyscanner.backpack.compose.dialog.BpkDestructiveDialog
import net.skyscanner.backpack.compose.dialog.BpkSuccessDialog
import net.skyscanner.backpack.compose.dialog.DialogButton
import net.skyscanner.backpack.compose.icons.BpkIcons
import net.skyscanner.backpack.compose.icons.lg.AlertAdd
import net.skyscanner.backpack.compose.icons.lg.Tick
import net.skyscanner.backpack.compose.icons.lg.Trash
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
fun DialogStory() {
  Column(
    modifier = Modifier.padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
  ) {
    var dialogShown by rememberSaveable { mutableStateOf<Dialog?>(null) }
    Dialog.values().forEach {
      BpkButton(it.buttonText) {
        dialogShown = it
      }
    }

    when (dialogShown) {
      Dialog.SuccessOneButton -> SuccessOneButtonDialogExample { dialogShown = null }
      Dialog.SuccessTwoButtons -> SuccessTwoButtonsDialogExample { dialogShown = null }
      Dialog.SuccessThreeButtons -> SuccessThreeButtonsDialogExample { dialogShown = null }
      Dialog.Alert -> AlertDialogExample { dialogShown = null }
      Dialog.Destructive -> DestructiveDialogExample { dialogShown = null }
      Dialog.NoIcon -> NoIconDialogExample { dialogShown = null }
      null -> {}
    }
  }
}

enum class Dialog(val buttonText: String) {
  SuccessOneButton("Success One Button"),
  SuccessTwoButtons("Success Two Buttons"),
  SuccessThreeButtons("Success Three Buttons"),
  Alert("Alert"),
  Destructive("Destructive"),
  NoIcon("No Icon"),
}

@Preview
@Composable
fun SuccessOneButtonDialogExample(onDismiss: () -> Unit = {}) {
  BackpackPreview {
    BpkSuccessDialog(
      icon = BpkIcons.Lg.Tick,
      title = stringResource(id = R.string.dialog_title),
      text = stringResource(id = R.string.dialog_text),
      confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
      onDismissRequest = onDismiss,
    )
  }
}

@Preview
@Composable
fun SuccessTwoButtonsDialogExample(onDismiss: () -> Unit = {}) {
  BackpackPreview {
    BpkSuccessDialog(
      icon = BpkIcons.Lg.Tick,
      title = stringResource(id = R.string.dialog_title),
      text = stringResource(id = R.string.dialog_text),
      confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
      secondaryButton = DialogButton(stringResource(id = R.string.dialog_skip), onDismiss),
      onDismissRequest = onDismiss,
    )
  }
}

@Preview
@Composable
fun SuccessThreeButtonsDialogExample(onDismiss: () -> Unit = {}) {
  BackpackPreview {
    BpkSuccessDialog(
      icon = BpkIcons.Lg.Tick,
      title = stringResource(id = R.string.dialog_title),
      text = stringResource(id = R.string.dialog_text),
      confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
      secondaryButton = DialogButton(stringResource(id = R.string.dialog_skip), onDismiss),
      linkButton = DialogButton(stringResource(id = R.string.dialog_link_optional), onDismiss),
      onDismissRequest = onDismiss,
    )
  }
}

@Preview
@Composable
fun AlertDialogExample(onDismiss: () -> Unit = {}) {
  BackpackPreview {
    BpkAlertDialog(
      icon = BpkIcons.Lg.AlertAdd,
      title = stringResource(id = R.string.dialog_title),
      text = stringResource(id = R.string.dialog_text),
      confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
      secondaryButton = DialogButton(stringResource(id = R.string.dialog_skip), onDismiss),
      linkButton = DialogButton(stringResource(id = R.string.dialog_link_optional), onDismiss),
      onDismissRequest = onDismiss,
    )
  }
}

@Preview
@Composable
fun DestructiveDialogExample(onDismiss: () -> Unit = {}) {
  BackpackPreview {
    BpkDestructiveDialog(
      icon = BpkIcons.Lg.Trash,
      title = stringResource(id = R.string.dialog_title),
      text = stringResource(id = R.string.dialog_text),
      confirmButton = DialogButton(stringResource(id = R.string.dialog_delete), onDismiss),
      linkButton = DialogButton(stringResource(id = R.string.dialog_cancel), onDismiss),
      onDismissRequest = onDismiss,
    )
  }
}

@Preview
@Composable
fun NoIconDialogExample(onDismiss: () -> Unit = {}) {
  BackpackPreview {
    BpkSuccessDialog(
      icon = null,
      title = stringResource(id = R.string.dialog_title),
      text = stringResource(id = R.string.dialog_text),
      confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
      secondaryButton = DialogButton(stringResource(id = R.string.dialog_skip), onDismiss),
      onDismissRequest = onDismiss,
    )
  }
}
