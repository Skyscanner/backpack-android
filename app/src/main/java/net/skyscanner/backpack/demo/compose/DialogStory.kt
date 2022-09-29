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

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.dialog.BpkDestructiveDialog
import net.skyscanner.backpack.compose.dialog.BpkFlareDialog
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
        BpkButton(stringResource(it.buttonText), modifier = Modifier.testTag(it.buttonText.toString())) {
          shownDialog = it
        }
      }
    }

    val onDismiss = { shownDialog = ShownDialog.None }
    when (shownDialog) {
      ShownDialog.SuccessOneButton -> SuccessOneButtonDialogExample(onDismiss)
      ShownDialog.SuccessTwoButtons -> SuccessTwoButtonsDialogExample(onDismiss)
      ShownDialog.SuccessThreeButtons -> SuccessThreeButtonsDialogExample(onDismiss)
      ShownDialog.Warning -> WarningDialogExample(onDismiss)
      ShownDialog.Destructive -> DestructiveDialogExample(onDismiss)
      ShownDialog.NoIcon -> NoIconDialogExample(onDismiss)
      ShownDialog.Flare -> FlareDialogExample(onDismiss)
      ShownDialog.None -> {}
    }
  }
}

enum class ShownDialog(@StringRes val buttonText: Int) {
  SuccessOneButton(R.string.dialog_success_one_button),
  SuccessTwoButtons(R.string.dialog_success_two_buttons),
  SuccessThreeButtons(R.string.dialog_success_three_buttons),
  Warning(R.string.dialog_warning),
  Destructive(R.string.dialog_destructive),
  NoIcon(R.string.dialog_no_icon),
  Flare(R.string.dialog_flare),
  None(R.string.generic_empty),
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

@Preview
@Composable
fun FlareDialogExample(onDismiss: () -> Unit = {}) {
  BpkFlareDialog(
    title = stringResource(id = R.string.dialog_title),
    text = stringResource(id = R.string.dialog_text),
    confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
    secondaryButton = DialogButton(stringResource(id = R.string.dialog_skip), onDismiss),
    onDismissRequest = onDismiss,
  ) {
    Image(
      painter = painterResource(R.drawable.canadian_rockies_canada),
      contentDescription = stringResource(R.string.image_rockies_content_description),
      contentScale = ContentScale.Crop,
    )
  }
}
