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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.navigationbar.TextAction

@Composable
fun BpkModal(
    closeButtonAccessibilityLabel: String,
    modifier: Modifier = Modifier,
    title: String? = null,
    action: TextAction? = null,
    onDismiss: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { onDismiss?.invoke() },
    ) {
        Surface(modifier = modifier) {
            Column {
                if (action != null) {
                    BpkTopNavBar(
                        navIcon = NavIcon.Close(
                            contentDescription = closeButtonAccessibilityLabel,
                            onClick = { onDismiss?.invoke() },
                        ),
                        title = title.orEmpty(),
                        action = TextAction(text = action.text, onClick = { action.onClick.invoke() }),
                    )
                } else {
                    BpkTopNavBar(
                        navIcon = NavIcon.Close(
                            contentDescription = closeButtonAccessibilityLabel,
                            onClick = { onDismiss?.invoke() },
                        ),
                        title = title.orEmpty(),
                    )
                }

                Box(
                    content = content,
                )
            }
        }
    }
}
