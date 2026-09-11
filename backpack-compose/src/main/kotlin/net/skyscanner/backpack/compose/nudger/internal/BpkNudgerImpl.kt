/*
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

package net.skyscanner.backpack.compose.nudger.internal

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.fieldset.LocalFieldStatus
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Minus
import net.skyscanner.backpack.compose.tokens.Plus
import kotlin.math.roundToInt

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun BpkNudgerImpl(
    value: Int,
    onValueChange: (Int) -> Unit,
    min: Int,
    max: Int,
    modifier: Modifier = Modifier,
    testTag: String? = null,
    allowSemantics: Boolean = true,
    enabled: Boolean = LocalFieldStatus.current != BpkFieldStatus.Disabled,
) {
    val range = min..max
    val coerced = value.coerceIn(range)

    fun setValue(value: Int) =
        onValueChange(value.coerceIn(range))

    val decrementEnabled = enabled && coerced > range.first
    val incrementEnabled = enabled && coerced < range.last

    // A disabled button loses its focus target, which would drop keyboard focus to the root.
    // Hand focus to the opposite button instead (DON-3704).
    val decrementFocusRequester = remember { FocusRequester() }
    val incrementFocusRequester = remember { FocusRequester() }
    var decrementHasFocus by remember { mutableStateOf(false) }
    var incrementHasFocus by remember { mutableStateOf(false) }
    var pendingFocusMove by remember { mutableStateOf<NudgerButton?>(null) }

    // The move has to wait for recomposition: when both buttons change enabled state on the same
    // click the receiving button has no focus target yet while onClick is running.
    LaunchedEffect(pendingFocusMove) {
        val target = pendingFocusMove ?: return@LaunchedEffect
        pendingFocusMove = null
        when (target) {
            // Only move once the button really did become disabled - the value is hoisted, so the
            // host is free to ignore onValueChange and leave it enabled and focused.
            NudgerButton.Increment -> if (!decrementEnabled && incrementEnabled) {
                incrementFocusRequester.requestFocus()
            }
            NudgerButton.Decrement -> if (!incrementEnabled && decrementEnabled) {
                decrementFocusRequester.requestFocus()
            }
        }
        // When min == max both buttons are disabled and there is nowhere to move focus to.
    }

    Row(
        modifier = if (allowSemantics) modifier.nudgerSemantics(value, ::setValue, range, enabled) else modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        BpkButton(
            icon = BpkIcon.Minus,
            contentDescription = "", // handled by semantics modifier
            enabled = decrementEnabled,
            size = BpkButtonSize.Default,
            type = BpkButtonType.Secondary,
            onClick = {
                // Recorded here because this is the last moment the button is still focused.
                if (decrementHasFocus && coerced - 1 <= range.first) {
                    pendingFocusMove = NudgerButton.Increment
                }
                setValue(coerced - 1)
            },
            modifier = Modifier
                .focusRequester(decrementFocusRequester)
                .onFocusChanged { decrementHasFocus = it.hasFocus }
                .generateNudgerTestTag(testTag, "Decrement"),
        )

        BpkText(
            text = coerced.toString(),
            style = BpkTheme.typography.heading5,
            maxLines = 1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .semantics { invisibleToUser() }
                .padding(horizontal = BpkSpacing.Md)
                .widthIn(min = BpkSpacing.Lg),
            color = animateColorAsState(
                when {
                    enabled -> BpkTheme.colors.textPrimary
                    else -> BpkTheme.colors.textDisabled
                },
            ).value,
        )

        BpkButton(
            icon = BpkIcon.Plus,
            contentDescription = "", // handled by semantics modifier
            enabled = incrementEnabled,
            size = BpkButtonSize.Default,
            type = BpkButtonType.Secondary,
            onClick = {
                // Recorded here because this is the last moment the button is still focused.
                if (incrementHasFocus && coerced + 1 >= range.last) {
                    pendingFocusMove = NudgerButton.Decrement
                }
                setValue(coerced + 1)
            },
            modifier = Modifier
                .focusRequester(incrementFocusRequester)
                .onFocusChanged { incrementHasFocus = it.hasFocus }
                .generateNudgerTestTag(testTag, "Increment"),
        )
    }
}

internal fun Modifier.nudgerSemantics(
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange,
    enabled: Boolean,
): Modifier =
    semantics(mergeDescendants = true) {

        // this is needed to use volume keys
        setProgress { targetValue ->
            // without this rounding the values will only decrease
            val newValue = targetValue
                .roundToInt()
                .coerceIn(range)
            if (newValue != value) {
                onValueChange(newValue)
                true
            } else {
                false
            }
        }

        // override describing percents
        stateDescription = value.toString()

        if (!enabled) disabled()
    }
        .progressSemantics(
            // this is needed to use volume keys
            value = value.toFloat(),
            valueRange = range.first.toFloat()..range.last.toFloat(),
            steps = range.last - range.first,
        )

private enum class NudgerButton { Decrement, Increment }

@OptIn(ExperimentalComposeUiApi::class)
private fun Modifier.generateNudgerTestTag(testTag: String?, action: String): Modifier {
    return testTag?.let {
        semantics {
            contentDescription = "" // handled by semantics modifier
            stateDescription = "" // handled by semantics modifier
            testTagsAsResourceId = true
        }.testTag("${testTag}$action")
    } ?: this
}
