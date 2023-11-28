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

package net.skyscanner.backpack.compose.appsearchmodal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.appsearchmodal.internal.BpkAppSearchModalImpl
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.modal.BpkModal
import net.skyscanner.backpack.compose.modal.BpkModalState
import net.skyscanner.backpack.compose.modal.rememberBpkModalState
import net.skyscanner.backpack.compose.navigationbar.NavIcon

sealed class BpkAppSearchModalResult {
    data class Content(
        val sections: List<BpkSection>,
        val shortcuts: List<BpkShortcut>? = null,
    ) : BpkAppSearchModalResult()

    data class Loading(val accessibilityLabel: String) : BpkAppSearchModalResult()
    data class Error(
        val title: String,
        val description: String,
        val image: @Composable () -> Unit,
        val action: BpkAction,
    ) : BpkAppSearchModalResult()
}

data class BpkShortcut(
    val text: String,
    val icon: BpkIcon,
    val onShortcutSelected: () -> Unit,
)

data class BpkSection(val headings: BpkSectionHeading? = null, val items: List<BpkItem>)

data class BpkItem(
    val title: AnnotatedString,
    val subtitle: AnnotatedString,
    val icon: BpkIcon,
    val onItemSelected: () -> Unit,
    val type: String,
)

data class BpkSectionHeading(val title: String, val action: BpkAction? = null)

data class BpkAction(val text: String, val onActionSelected: () -> Unit)

@Composable
fun BpkAppSearchModal(
    title: String,
    inputText: String,
    inputHint: String,
    results: BpkAppSearchModalResult,
    closeAccessibilityLabel: String,
    onInputChanged: (String) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    state: BpkModalState = rememberBpkModalState(),
) {
    val coroutineScope = rememberCoroutineScope()
    BpkModal(
        navIcon = NavIcon.Close(
            contentDescription = closeAccessibilityLabel,
            onClick = {
                coroutineScope.launch { state.hide() }
            },
        ),
        modifier = modifier,
        state = state,
        action = null,
        title = title,
        onDismiss = onClose,
    ) {
        BpkAppSearchModalImpl(
            inputText = inputText,
            inputHint = inputHint,
            results = results,
            onInputChanged = onInputChanged,
        )
    }
}
