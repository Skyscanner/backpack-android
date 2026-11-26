/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.modal

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.modal.internal.BpkModalImpl
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.navigationbar.TextAction
import net.skyscanner.backpack.compose.navigationbar.TopNavBarState
import net.skyscanner.backpack.compose.navigationbar.rememberFixedTopAppBarState

internal const val ModalAnimationDurationMs = 300

enum class ModalStyle {
    Default,
    SurfaceContrast,
}

/**
 * A fullscreen modal.
 *
 * @param animated whether the modal slides in and out vertically. Set to `false` when the host already animates the
 * screen the modal belongs to (e.g. a navigation transition) and the modal must not animate on top of it. When `false`
 * the modal appears and disappears immediately: [BpkModalState.hide] and [onDismiss] are still invoked, but right away
 * instead of after the exit animation. The dialog window's own enter/exit animation is disabled too.
 * @param dimBackground whether the content behind the modal is dimmed while it is shown. Set to `false` when the modal
 * covers the screen and the dim would only show as a flash while it appears or disappears (e.g. together with
 * `animated = false`).
 */
@Composable
@Suppress("ModifierMissing")
fun BpkModal(
    navIcon: NavIcon,
    modifier: Modifier = Modifier,
    state: BpkModalState = rememberBpkModalState(),
    navBarState: TopNavBarState = rememberFixedTopAppBarState(),
    modalStyle: ModalStyle = ModalStyle.Default,
    action: TextAction? = null,
    title: String? = null,
    onDismiss: (() -> Unit)? = null,
    animated: Boolean = true,
    dimBackground: Boolean = true,
    content: @Composable BoxScope.() -> Unit,
) {
    BpkModalImpl(
        navIcon = navIcon,
        modifier = modifier,
        state = state,
        navBarState = navBarState,
        modalStyle = modalStyle,
        action = action,
        title = title,
        onDismiss = onDismiss,
        animated = animated,
        dimBackground = dimBackground,
        content = content,
    )
}
