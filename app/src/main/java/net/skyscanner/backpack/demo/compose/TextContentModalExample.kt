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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.dialog.BpkModal
import net.skyscanner.backpack.compose.navigationbar.TextAction
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.ModalComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@ModalComponent
@ComposeStory("Text Content Modal Dialog")
internal fun TextContentModalExample(modifier: Modifier = Modifier) {
    DialogDemo { onDismiss ->
        BpkModal(
            title = stringResource(id = R.string.dialog_title),
            closeButtonAccessibilityLabel = stringResource(id = R.string.navigation_accessibility),
            action = TextAction(
                text = stringResource(R.string.navigation_text_action),
                onClick = onDismiss,
            ),
            onDismiss = onDismiss,
            modifier = modifier.fillMaxSize(),
        ) {
            BpkText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(BpkSpacing.Base),
                text = stringResource(R.string.dialog_text),
                style = BpkTheme.typography.heading3,
                color = BpkTheme.colors.textPrimary,
            )
        }
    }
}

@Composable
@ModalComponent
@ComposeStory("Text Content Modal Dialog Without Action Button")
internal fun TextContentWithoutActionModalExample(modifier: Modifier = Modifier) {
    DialogDemo { onDismiss ->
        BpkModal(
            title = stringResource(id = R.string.dialog_title),
            closeButtonAccessibilityLabel = stringResource(id = R.string.navigation_accessibility),
            action = null,
            onDismiss = onDismiss,
            modifier = modifier.fillMaxSize(),
        ) {
            BpkText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(BpkSpacing.Base),
                text = stringResource(R.string.dialog_text),
                style = BpkTheme.typography.heading3,
                color = BpkTheme.colors.textPrimary,
            )
        }
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
