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

package net.skyscanner.backpack.compose.tooltip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.RichTooltipColors
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.NativeAndroidClose
import net.skyscanner.backpack.compose.utils.clickableWithRipple
import net.skyscanner.backpack.util.ExperimentalBackpackApi

@ExperimentalBackpackApi
enum class BpkTooltipType {
    Rich,
}

@ExperimentalBackpackApi
enum class BpkTooltipAnchorPosition {
    Above,
    Below,
    Start,
    End,
}

/** State used to explicitly show and dismiss a [BpkTooltip]. */
@ExperimentalBackpackApi
@Stable
@OptIn(ExperimentalMaterial3Api::class)
class BpkTooltipState internal constructor(internal val delegate: TooltipState) {
    private var userDismissedForPresentation = false
    val isVisible: Boolean
        get() = delegate.isVisible

    suspend fun show() {
        userDismissedForPresentation = false
        delegate.show()
    }

    fun dismiss() = delegate.dismiss()

    internal fun consumeUserDismissal(): Boolean {
        if (userDismissedForPresentation) return false
        userDismissedForPresentation = true
        return true
    }
}

@ExperimentalBackpackApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberBpkTooltipState(): BpkTooltipState {
    val delegate = rememberTooltipState(isPersistent = true)
    return remember(delegate) { BpkTooltipState(delegate) }
}

/**
 * Displays a persistent Rich tooltip for [content]'s anchor.
 *
 * [BpkTooltipState.isVisible] remains true while Material3 runs its exit animation. Therefore,
 * [onDismissRequest] is invoked at most once for each presentation. Material3 also shares a
 * single-tooltip mutex, so showing a second tooltip can dismiss this tooltip without invoking
 * [onDismissRequest].
 */
@ExperimentalBackpackApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BpkTooltip(
    title: String,
    text: String,
    closeAccessibilityLabel: String,
    state: BpkTooltipState,
    modifier: Modifier = Modifier,
    type: BpkTooltipType = BpkTooltipType.Rich,
    anchorPosition: BpkTooltipAnchorPosition = BpkTooltipAnchorPosition.Below,
    onDismissRequest: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val dismissFromUser: () -> Unit = {
        if (state.consumeUserDismissal()) {
            state.dismiss()
            onDismissRequest?.invoke()
        }
    }
    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(anchorPosition.toMaterial3()),
        tooltip = {
            when (type) {
                BpkTooltipType.Rich -> RichTooltip(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            BpkText(
                                text = title,
                                style = BpkTheme.typography.heading5,
                                modifier = Modifier.semantics { heading() },
                            )
                            Box(
                                modifier = Modifier
                                    .size(TooltipCloseTouchTarget)
                                    .clickableWithRipple(role = Role.Button, onClick = dismissFromUser)
                                    .semantics { contentDescription = closeAccessibilityLabel },
                                contentAlignment = Alignment.Center,
                            ) {
                                BpkIcon(
                                    icon = BpkIcon.NativeAndroidClose,
                                    contentDescription = null,
                                    size = BpkIconSize.Large,
                                )
                            }
                        }
                    },
                    text = { BpkText(text = text) },
                    colors = RichTooltipColors(
                        containerColor = BpkTheme.colors.surfaceDefault,
                        contentColor = BpkTheme.colors.textPrimary,
                        titleContentColor = BpkTheme.colors.textPrimary,
                        actionContentColor = BpkTheme.colors.textPrimary,
                    ),
                    action = null,
                )
            }
        },
        state = state.delegate,
        modifier = modifier,
        onDismissRequest = dismissFromUser,
        focusable = true,
        enableUserInput = false,
        content = content,
    )
}

private val TooltipCloseTouchTarget = 48.dp

@OptIn(ExperimentalBackpackApi::class, ExperimentalMaterial3Api::class)
private fun BpkTooltipAnchorPosition.toMaterial3(): TooltipAnchorPosition = when (this) {
    BpkTooltipAnchorPosition.Above -> TooltipAnchorPosition.Above
    BpkTooltipAnchorPosition.Below -> TooltipAnchorPosition.Below
    BpkTooltipAnchorPosition.Start -> TooltipAnchorPosition.Start
    BpkTooltipAnchorPosition.End -> TooltipAnchorPosition.End
}
