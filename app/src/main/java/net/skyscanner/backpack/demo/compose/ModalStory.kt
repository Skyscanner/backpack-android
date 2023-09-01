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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.modal.BpkModal
import net.skyscanner.backpack.compose.modal.rememberBpkModalState
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.navigationbar.TextAction
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.ModalComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@ModalComponent
@ComposeStory
internal fun ModalStory() {
    val modalState = rememberBpkModalState()
    ModalDemo { onDismiss ->
        BpkModal(
            state = modalState,
            navIcon = NavIcon.Close(
                contentDescription = stringResource(id = R.string.navigation_accessibility),
                onClick = { modalState.hide() },
            ),
            title = stringResource(id = R.string.dialog_title),
            action = TextAction(
                text = stringResource(R.string.navigation_text_action),
                onClick = { modalState.hide() },
            ),
            onDismiss = onDismiss,
        ) {
            TextContent()
        }
    }
}

@Composable
@ModalComponent
@ComposeStory("Back Icon Without Action Button")
internal fun ModalWithoutActionStory() {
    val modalState = rememberBpkModalState()
    ModalDemo { onDismiss ->
        BpkModal(
            state = modalState,
            navIcon = NavIcon.Back(
                contentDescription = stringResource(id = R.string.navigation_accessibility),
                onClick = { modalState.hide() },
            ),
            title = stringResource(id = R.string.dialog_title),
            onDismiss = onDismiss,
        ) {
            TextContent()
        }
    }
}

@Composable
@ModalComponent
@ComposeStory("Without Action and Title")
internal fun ModalWithoutActionAndTitleStory() {
    val modalState = rememberBpkModalState()
    ModalDemo { onDismiss ->
        BpkModal(
            state = modalState,
            navIcon = NavIcon.Close(
                contentDescription = stringResource(id = R.string.navigation_accessibility),
                onClick = { modalState.hide() },
            ),
            onDismiss = onDismiss,
        ) {
            TextContent()
        }
    }
}

@Composable
@ModalComponent
@ComposeStory("Without Icon, Action and Title")
internal fun ModalWithoutIconActionAndTitleStory() {
    val modalState = rememberBpkModalState()
    ModalDemo { onDismiss ->
        BpkModal(
            state = modalState,
            navIcon = NavIcon.None,
            onDismiss = onDismiss,
        ) {
            TextContent()
        }
    }
}

@Composable
private fun ModalDemo(
    content: @Composable (onDismiss: () -> Unit) -> Unit,
) {
    val dispatcher = LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

    @Suppress("SuspiciousCallableReferenceInLambda")
    val onDismiss: () -> Unit = remember(dispatcher) { dispatcher::onBackPressed }
    content(onDismiss)
}

@Composable
private fun TextContent() {
    BpkText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(BpkSpacing.Base),
        text = stringResource(R.string.dialog_text),
        style = BpkTheme.typography.bodyDefault,
        color = BpkTheme.colors.textPrimary,
    )
}
