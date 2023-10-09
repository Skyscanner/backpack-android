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

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.appsearchmodal.internal.BpkAppSearchModalImpl
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.modal.BpkModal
import net.skyscanner.backpack.compose.modal.rememberBpkModalState
import net.skyscanner.backpack.compose.navigationbar.NavIcon

sealed class AppSearchModalResult {
    data class Content(
        val sections: List<Section>,
        val shortcuts: List<Shortcut>? = null,
    ) : AppSearchModalResult()

    data class Loading(val accessibilityLabel: String) : AppSearchModalResult()
    data class Error(
        val title: String,
        val description: String,
        @DrawableRes val image: Int,
        val action: Action,
    ) : AppSearchModalResult()
}

data class Shortcut(
    val text: String,
    val icon: BpkIcon,
    val onShortcutSelected: () -> Unit,
)

data class Section(val headings: SectionHeading? = null, val items: List<Item>)

data class Item(
    val title: String,
    val subTitle: String,
    val icon: BpkIcon,
    val onItemSelected: () -> Unit,
)

data class SectionHeading(val title: String, val action: Action? = null)

data class Action(val text: String, val onActionSelected: () -> Unit)

@Composable
fun BpkAppSearchModal(
    title: String,
    inputText: String,
    inputHint: String,
    results: AppSearchModalResult,
    closeAccessibilityLabel: String,
    onInputChanged: (String) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {

    BpkModal(
        navIcon = NavIcon.Close(
            contentDescription = closeAccessibilityLabel,
            onClick = onClose,
        ),
        modifier = modifier,
        state = rememberBpkModalState(),
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
