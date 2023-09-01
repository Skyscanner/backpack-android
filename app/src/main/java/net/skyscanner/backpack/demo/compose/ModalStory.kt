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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.button.BpkButton
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
    ModalDemo(title = stringResource(R.string.dialog_title), stringResource(R.string.navigation_text_action))
}

@Composable
@ModalComponent
@ComposeStory("With Back")
internal fun ModalWithBackIcon() {
    ModalDemo(title = stringResource(R.string.dialog_title), navActionType = ActionType.Back)
}

@Composable
@ModalComponent
@ComposeStory("Without Action")
internal fun ModalWithoutAction() {
    ModalDemo(title = stringResource(R.string.dialog_title))
}

@Composable
@ModalComponent
@ComposeStory("Without Action and Title")
internal fun ModalWithoutActionAndTitle() {
    ModalDemo()
}

enum class ActionType {
    Close,
    Back,
}

@Composable
private fun ModalDemo(
    title: String? = null,
    actionText: String? = null,
    navActionType: ActionType = ActionType.Close,
) {
    val showModal = rememberSaveable { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BpkButton(
            text = stringResource(R.string.generic_show),
            onClick = { showModal.value = true },
        )
    }

    if (showModal.value) {
        val modalState = rememberBpkModalState()
        val coroutineScope = rememberCoroutineScope()
        BpkModal(
            state = modalState,
            title = title,
            navIcon = when (navActionType) {
                ActionType.Close -> NavIcon.Close(
                    contentDescription = stringResource(id = R.string.navigation_back),
                    onClick = { coroutineScope.launch { modalState.hide() } },
                )

                ActionType.Back -> NavIcon.Back(
                    contentDescription = stringResource(id = R.string.navigation_back),
                    onClick = { coroutineScope.launch { modalState.hide() } },
                )
            },
            action = actionText?.let {
                TextAction(
                    text = it,
                    onClick = {
                        coroutineScope.launch { modalState.hide() }
                    },
                )
            },
            onDismiss = { showModal.value = false },
        ) {
            TextContent()
        }
    }
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
