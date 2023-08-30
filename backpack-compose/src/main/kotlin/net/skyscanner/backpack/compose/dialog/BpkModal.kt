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

package net.skyscanner.backpack.compose.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.navigationbar.TextAction
import net.skyscanner.backpack.compose.theme.BpkTheme

private const val ANIMATION_DURATION_MS = 400

@Composable
@Suppress("ModifierMissing")
fun BpkModal(
    closeButtonAccessibilityLabel: String,
    action: TextAction? = null,
    title: String? = null,
    onDismiss: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val animVisibleState = remember {
        MutableTransitionState(false).apply { targetState = true }
    }

    if (animVisibleState.isIdle && !animVisibleState.currentState) {
        onDismiss?.invoke()
        return
    }

    Popup(
        properties = PopupProperties(focusable = true),
        onDismissRequest = { animVisibleState.targetState = false },
    ) {
        AnimatedVisibility(
            visibleState = animVisibleState,
            enter = slideInVertically(tween(ANIMATION_DURATION_MS)) { it },
            exit = slideOutVertically(tween(ANIMATION_DURATION_MS)) { it },
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(modifier = Modifier.background(BpkTheme.colors.surfaceDefault)) {
                if (action != null) {
                    BpkTopNavBar(
                        navIcon = NavIcon.Close(
                            contentDescription = closeButtonAccessibilityLabel,
                            onClick = { animVisibleState.targetState = false },
                        ),
                        title = title.orEmpty(),
                        action = TextAction(text = action.text, onClick = { action.onClick.invoke() }),
                    )
                } else {
                    BpkTopNavBar(
                        navIcon = NavIcon.Close(
                            contentDescription = closeButtonAccessibilityLabel,
                            onClick = { animVisibleState.targetState = false },
                        ),
                        title = title.orEmpty(),
                    )
                }
                Box(content = content)
            }
        }
    }
}
