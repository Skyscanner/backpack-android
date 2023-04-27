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

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.dialog.BpkDestructiveDialog
import net.skyscanner.backpack.compose.dialog.BpkFlareDialog
import net.skyscanner.backpack.compose.dialog.BpkImageDialog
import net.skyscanner.backpack.compose.dialog.BpkSuccessDialog
import net.skyscanner.backpack.compose.dialog.BpkWarningDialog
import net.skyscanner.backpack.compose.dialog.DialogButton
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.AlertAdd
import net.skyscanner.backpack.compose.tokens.Tick
import net.skyscanner.backpack.compose.tokens.Trash
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.DialogComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.demo.meta.StoryKind

@Composable
@DialogComponent
@ComposeStory("Success One Button", StoryKind.DemoOnly)
internal fun SuccessOneButtonDialogExample() =
    DialogDemo { onDismiss ->
        BpkSuccessDialog(
            icon = BpkIcon.Tick,
            title = stringResource(id = R.string.dialog_title),
            text = stringResource(id = R.string.dialog_text),
            confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
            onDismissRequest = onDismiss,
        )
    }

@Composable
@DialogComponent
@ComposeStory("Success Two Buttons", StoryKind.DemoOnly)
internal fun SuccessTwoButtonsDialogExample() =
    DialogDemo { onDismiss ->
        BpkSuccessDialog(
            icon = BpkIcon.Tick,
            title = stringResource(id = R.string.dialog_title),
            text = stringResource(id = R.string.dialog_text),
            confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
            secondaryButton = DialogButton(stringResource(id = R.string.dialog_skip), onDismiss),
            onDismissRequest = onDismiss,
        )
    }

@Composable
@DialogComponent
@ComposeStory("Success Three Buttons")
internal fun SuccessThreeButtonsDialogExample() =
    DialogDemo { onDismiss ->
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

@Composable
@DialogComponent
@ComposeStory("Warning")
internal fun WarningDialogExample() =
    DialogDemo { onDismiss ->
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

@Composable
@DialogComponent
@ComposeStory("Destructive")
internal fun DestructiveDialogExample() =
    DialogDemo { onDismiss ->
        BpkDestructiveDialog(
            icon = BpkIcon.Trash,
            title = stringResource(id = R.string.dialog_title),
            text = stringResource(id = R.string.dialog_text),
            confirmButton = DialogButton(stringResource(id = R.string.dialog_delete), onDismiss),
            linkButton = DialogButton(stringResource(id = R.string.dialog_cancel), onDismiss),
            onDismissRequest = onDismiss,
        )
    }

@Composable
@DialogComponent
@ComposeStory("No Icon", StoryKind.DemoOnly)
internal fun NoIconDialogExample() =
    DialogDemo { onDismiss ->
        BpkSuccessDialog(
            icon = null,
            title = stringResource(id = R.string.dialog_title),
            text = stringResource(id = R.string.dialog_text),
            confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
            secondaryButton = DialogButton(stringResource(id = R.string.dialog_skip), onDismiss),
            onDismissRequest = onDismiss,
        )
    }

@Composable
@DialogComponent
@ComposeStory("Flare")
internal fun FlareDialogExample() =
    DialogDemo { onDismiss ->
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
                modifier = Modifier.height(192.dp),
            )
        }
    }

@Composable
@DialogComponent
@ComposeStory(kind = StoryKind.DemoOnly)
internal fun FlareDialogVerticalExample() =
    DialogDemo { onDismiss ->
        BpkFlareDialog(
            title = stringResource(id = R.string.dialog_title),
            text = stringResource(id = R.string.dialog_text_one_line),
            confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
            onDismissRequest = onDismiss,
        ) {
            Image(
                painter = painterResource(R.drawable.sunset),
                contentDescription = stringResource(R.string.image_sunset_content_description),
                contentScale = ContentScale.Crop,
            )
        }
    }

@Composable
@DialogComponent
@ComposeStory("Image Start alignment")
internal fun ImageDialogStartAlignmentExample() =
    DialogDemo { onDismiss ->
        BpkImageDialog(
            title = stringResource(id = R.string.dialog_title),
            text = stringResource(id = R.string.dialog_text),
            confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
            secondaryButton = DialogButton(stringResource(id = R.string.dialog_skip), onDismiss),
            onDismissRequest = onDismiss,
            textAlign = TextAlign.Start,
        ) {
            Image(
                painter = painterResource(R.drawable.canadian_rockies_canada),
                contentDescription = stringResource(R.string.image_rockies_content_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(192.dp),
            )
        }
    }

@Composable
@DialogComponent
@ComposeStory("Image End alignment")
internal fun ImageDialogEndAlignmentExample() =
    DialogDemo { onDismiss ->
        BpkImageDialog(
            title = stringResource(id = R.string.dialog_title),
            text = stringResource(id = R.string.dialog_text),
            confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
            secondaryButton = DialogButton(stringResource(id = R.string.dialog_skip), onDismiss),
            onDismissRequest = onDismiss,
            textAlign = TextAlign.End,
        ) {
            Image(
                painter = painterResource(R.drawable.canadian_rockies_canada),
                contentDescription = stringResource(R.string.image_rockies_content_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(192.dp),
            )
        }
    }

@Composable
@DialogComponent
@ComposeStory(kind = StoryKind.DemoOnly)
internal fun ImageDialogVerticalExample() =
    DialogDemo { onDismiss ->
        BpkImageDialog(
            title = stringResource(id = R.string.dialog_title),
            text = stringResource(id = R.string.dialog_text_one_line),
            confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation), onDismiss),
            onDismissRequest = onDismiss,
            textAlign = TextAlign.Start,
        ) {
            Image(
                painter = painterResource(R.drawable.sunset),
                contentDescription = stringResource(R.string.image_sunset_content_description),
                contentScale = ContentScale.Crop,
            )
        }
    }

@Composable
private fun DialogDemo(
    content: @Composable (onDismiss: () -> Unit) -> Unit,
) {

    val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

    @Suppress("SuspiciousCallableReferenceInLambda")
    val onDismiss: () -> Unit = remember(dispatcher) { dispatcher::onBackPressed }

    content(onDismiss)
}
