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

package net.skyscanner.backpack.compose.modal

import android.view.Window
import android.view.WindowManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.navigationbar.TextAction
import net.skyscanner.backpack.compose.theme.BpkTheme

internal const val ModalAnimationDurationMs = 300

@Composable
@Suppress("ModifierMissing")
fun BpkModal(
    navIcon: NavIcon,
    modifier: Modifier = Modifier,
    state: BpkModalState = rememberBpkModalState(),
    action: TextAction? = null,
    title: String? = null,
    onDismiss: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val isVisible = state.isVisible
    if (isVisible.isIdle && !isVisible.currentState) {
        onDismiss?.invoke()
    }

    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = true,
            decorFitsSystemWindows = false,
        ),
        onDismissRequest = { isVisible.targetState = false },
    ) {
        val dialogWindow = getDialogWindow()
        val isSystemInDarkTheme = isSystemInDarkTheme()

        SideEffect {
            dialogWindow?.apply {
                WindowCompat.setDecorFitsSystemWindows(this, false)
                val windowInsetsController = WindowInsetsControllerCompat(this, this.decorView)
                // Below codes are deprecated for Android version 15 or above as it would always be transparent
                statusBarColor = android.graphics.Color.TRANSPARENT
                navigationBarColor = android.graphics.Color.TRANSPARENT
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                )
                windowInsetsController.isAppearanceLightStatusBars = !isSystemInDarkTheme
                windowInsetsController.isAppearanceLightNavigationBars = !isSystemInDarkTheme
            }
        }

        AnimatedVisibility(
            visibleState = isVisible,
            enter = slideInVertically(tween(ModalAnimationDurationMs)) { it },
            exit = slideOutVertically(tween(ModalAnimationDurationMs)) { it },
            modifier = modifier.fillMaxSize(),
        ) {
            Column(modifier = Modifier.background(BpkTheme.colors.surfaceDefault)) {
                if (action != null) {
                    BpkTopNavBar(
                        navIcon = navIcon,
                        title = title.orEmpty(),
                        action = action,
                    )
                } else {
                    BpkTopNavBar(
                        navIcon = navIcon,
                        title = title.orEmpty(),
                    )
                }
                Box(content = content)
            }
        }
    }
}

@Composable
private fun getDialogWindow(): Window? = (LocalView.current.parent as? DialogWindowProvider)?.window
