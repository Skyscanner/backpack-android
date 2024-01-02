/*
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

package net.skyscanner.backpack.demo.stories

import android.content.Context
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.DialogComponent
import net.skyscanner.backpack.meta.StoryKind
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.dialog.BpkDialog

@Composable
@DialogComponent
@ViewStory("Success One Button", StoryKind.DemoOnly)
internal fun SuccessOneButtonDialogExample() = DialogDemo { onDismiss ->
    BpkDialog(this, BpkDialog.Type.Success).apply {
        title = context.getString(R.string.dialog_title)
        description = context.getString(R.string.dialog_text)
        icon = BpkDialog.Icon(R.drawable.bpk_tick)
        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_confirmation)) {
                dismiss()
            },
        )
        setOnDismissListener {
            onDismiss()
        }
    }.show()
}

@Composable
@DialogComponent
@ViewStory("Success Two Buttons", StoryKind.DemoOnly)
internal fun SuccessTwoButtonsDialogExample() = DialogDemo { onDismiss ->
    BpkDialog(this, BpkDialog.Type.Success).apply {
        title = context.getString(R.string.dialog_title)
        description = context.getString(R.string.dialog_text)
        icon = BpkDialog.Icon(R.drawable.bpk_tick)
        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_confirmation)) {
                dismiss()
            },
        )
        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_skip)) {
                dismiss()
            },
        )
        setOnDismissListener {
            onDismiss()
        }
    }.show()
}

@Composable
@DialogComponent
@ViewStory("Success Three Buttons")
internal fun SuccessThreeButtonsDialogExample() = DialogDemo { onDismiss ->
    BpkDialog(this, BpkDialog.Type.Success).apply {
        title = context.getString(R.string.dialog_title)
        description = context.getString(R.string.dialog_text)
        icon = BpkDialog.Icon(R.drawable.bpk_tick)
        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_confirmation)) {
                dismiss()
            },
        )
        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_skip)) {
                dismiss()
            },
        )
        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_link_optional)) {
                dismiss()
            },
        )
        setOnDismissListener {
            onDismiss()
        }
    }.show()
}

@Composable
@DialogComponent
@ViewStory("Warning", StoryKind.DemoOnly)
internal fun WarningDialogExample() = DialogDemo { onDismiss ->
    BpkDialog(this, BpkDialog.Type.Warning).apply {
        title = context.getString(R.string.dialog_title)
        description = context.getString(R.string.dialog_text)
        icon = BpkDialog.Icon(R.drawable.bpk_alert__add)
        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_confirmation)) {
                dismiss()
            },
        )
        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_skip)) {
                dismiss()
            },
        )
        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_link_optional)) {
                dismiss()
            },
        )
        setOnDismissListener {
            onDismiss()
        }
    }.show()
}

@Composable
@DialogComponent
@ViewStory("Destructive")
internal fun DestructiveDialogExample() = DialogDemo { onDismiss ->
    BpkDialog(this, BpkDialog.Type.Destructive).apply {
        title = context.getString(R.string.dialog_title)
        description = context.getString(R.string.dialog_text)
        icon = BpkDialog.Icon(R.drawable.bpk_trash)
        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_delete)) {
                dismiss()
            },
        )
        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_cancel)) {
                dismiss()
            },
        )
        setOnDismissListener {
            onDismiss()
        }
    }.show()
}

@Composable
@DialogComponent
@ViewStory("Long text", StoryKind.DemoOnly)
internal fun LongDialogExample() = DialogDemo { onDismiss ->
    BpkDialog(this, BpkDialog.Type.Success).apply {
        title = context.getString(R.string.dialog_title)
        description = context.getString(R.string.stub).repeat(3)
        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_continue)) {
                dismiss()
            },
        )
        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_skip)) {
                dismiss()
            },
        )
        setOnDismissListener {
            onDismiss()
        }
    }.show()
}

@Composable
@DialogComponent
@ViewStory("No Icon", StoryKind.DemoOnly)
internal fun NoIconDialogExample() = DialogDemo { onDismiss ->
    BpkDialog(this, BpkDialog.Type.Success).apply {
        title = context.getString(R.string.dialog_title)
        description = context.getString(R.string.dialog_text)
        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_confirmation)) {
                dismiss()
            },
        )
        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_skip)) {
                dismiss()
            },
        )
        setOnDismissListener {
            onDismiss()
        }
    }.show()
}

@Composable
@DialogComponent
@ViewStory("Flare")
internal fun FlareDialogExample() = DialogDemo { onDismiss ->
    BpkDialog(this, BpkDialog.Type.Flare).apply {
        title = context.getString(R.string.dialog_title)
        description = context.getString(R.string.dialog_text)
        image!!.setImageResource(R.drawable.dialog_sample)

        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_confirmation)) {
                dismiss()
            },
        )
        addActionButton(
            BpkDialog.Button(context.getString(R.string.dialog_skip)) {
                dismiss()
            },
        )
        setOnDismissListener {
            onDismiss()
        }
    }.show()
}

@Composable
private fun DialogDemo(
    content: Context.(onDismiss: () -> Unit) -> Unit,
) {

    val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

    @Suppress("SuspiciousCallableReferenceInLambda")
    val onDismiss: () -> Unit = remember(dispatcher) { dispatcher::onBackPressed }

    val context = LocalContext.current

    LaunchedEffect(context, onDismiss, content) {
        context.content(onDismiss)
    }
}
