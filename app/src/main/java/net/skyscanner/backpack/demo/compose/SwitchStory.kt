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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.bottomsheet.BpkModalBottomSheet
import net.skyscanner.backpack.compose.bottomsheet.BpkModalBottomSheetCloseAction
import net.skyscanner.backpack.compose.bottomsheet.rememberBpkModalBottomSheetState
import net.skyscanner.backpack.compose.switch.BpkSwitch
import net.skyscanner.backpack.compose.switch.BpkSwitchHelpAction
import net.skyscanner.backpack.compose.switch.BpkSwitchStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.InformationCircle
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.SwitchComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.util.ExperimentalBackpackApi

@Composable
@SwitchComponent
@ComposeStory
fun SwitchStoryDefaultDefault(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(BpkSpacing.Base)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {

        DefaultUncheckedSwitchExample()
        DefaultCheckedSwitchExample()

        DefaultDisabledUncheckedSwitchExample()
        DefaultDisabledCheckedSwitchExample()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = BpkTheme.colors.surfaceContrast,
                    shape = RoundedCornerShape(BpkBorderRadius.Md),
                )
                .padding(BpkSpacing.Base),
            verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        ) {
            OnContrastUncheckedSwitchExample()
            OnContrastCheckedSwitchExample()

            OnContrastDisabledUncheckedSwitchExample()
            OnContrastDisabledCheckedSwitchExample()
        }

        LongTextNoTruncationSwitchExample()
        LongTextWithTopSwitchAlignmentSwitchExample()
        LongTextWithTruncationSwitchExample()

        AnnotatedStringSwitchExample()
        CustomContentSwitchExample()
        SwitchWithIconExample()
        EmptyContentWithSwitchExample()
        SwitchWithHelpExample()
    }
}

@Composable
@OptIn(ExperimentalBackpackApi::class)
internal fun SwitchWithHelpExample(
    modifier: Modifier = Modifier,
    longHelpContent: Boolean = true,
    showHelpInitially: Boolean = false,
) {
    var checked by remember { mutableStateOf(false) }
    var showHelp by remember { mutableStateOf(showHelpInitially) }
    val sheetState = rememberBpkModalBottomSheetState(skipPartiallyExpanded = true)
    BpkSwitch(
        text = stringResource(R.string.toggle_default),
        checked = checked,
        onCheckedChange = { checked = it },
        helpAction = BpkSwitchHelpAction(
            contentDescription = stringResource(R.string.toggle_help),
            onClick = { showHelp = true },
        ),
        modifier = modifier
            .fillMaxWidth()
            .testTag("switchWithHelp"),
        shouldTruncate = false,
        textStyle = BpkTheme.typography.label2,
    )
    if (showHelp) {
        BpkModalBottomSheet(
            onDismissRequest = { showHelp = false },
            state = sheetState,
            closeButton = BpkModalBottomSheetCloseAction.Close(
                contentDescription = stringResource(R.string.generic_dismiss),
                onClick = { showHelp = false },
            ),
        ) {
            Column(
                modifier = Modifier
                    .heightIn(max = BpkSpacing.Xxl * 8)
                    .verticalScroll(rememberScrollState())
                    .testTag("switchHelpScroll"),
            ) {
                BpkText(
                    text = stringResource(R.string.toggle_help_title),
                    style = BpkTheme.typography.heading5,
                    modifier = Modifier
                        .padding(BpkSpacing.Base)
                        .testTag("switchHelpTitle"),
                )
                BpkText(
                    text = stringResource(
                        if (longHelpContent) R.string.toggle_help_long_message else R.string.toggle_help_message,
                    ),
                    modifier = Modifier
                        .padding(BpkSpacing.Base)
                        .testTag("switchHelpMessage"),
                )
            }
        }
    }
}

@Composable
internal fun DefaultUncheckedSwitchExample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(false) }
    BpkSwitch(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(id = R.string.toggle_default_unchecked),
        checked = checked,
        onCheckedChange = { checked = it },
    )
}

@Composable
internal fun DefaultCheckedSwitchExample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(true) }
    BpkSwitch(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(id = R.string.toggle_default_checked),
        checked = checked,
        onCheckedChange = { checked = it },
    )
}

@Composable
internal fun DefaultDisabledUncheckedSwitchExample(modifier: Modifier = Modifier) {
    BpkSwitch(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(id = R.string.toggle_disabled_unchecked),
        enabled = false,
        checked = false,
        onCheckedChange = null,
    )
}

@Composable
internal fun DefaultDisabledCheckedSwitchExample(modifier: Modifier = Modifier) {
    BpkSwitch(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(id = R.string.toggle_disabled_checked),
        enabled = false,
        checked = true,
        onCheckedChange = null,
    )
}

@Composable
internal fun LongTextNoTruncationSwitchExample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(false) }
    BpkSwitch(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(id = R.string.toggle_long_text),
        checked = checked,
        onCheckedChange = { checked = it },
        shouldTruncate = false,
    )
}

@Composable
internal fun LongTextWithTopSwitchAlignmentSwitchExample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(false) }
    BpkSwitch(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(id = R.string.toggle_long_text),
        checked = checked,
        onCheckedChange = { checked = it },
        switchAlignment = Alignment.Top,
        shouldTruncate = false,
    )
}

@Composable
internal fun EmptyContentWithSwitchExample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(false) }
    BpkSwitch(
        modifier = modifier.fillMaxWidth(),
        content = {},
        checked = checked,
        onCheckedChange = { checked = it },
        switchAlignment = Alignment.Top,
    )
}

@Composable
internal fun LongTextWithTruncationSwitchExample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(false) }
    BpkSwitch(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(id = R.string.toggle_long_text),
        checked = checked,
        onCheckedChange = { checked = it },
        shouldTruncate = true,
    )
}

@Composable
internal fun AnnotatedStringSwitchExample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(false) }
    BpkSwitch(
        modifier = modifier.fillMaxWidth(),
        text = buildAnnotatedString {
            append(stringResource(R.string.toggle_long_text))
            withStyle(
                style = SpanStyle(
                    color = BpkTheme.colors.textPrimary,
                    fontFamily = BpkTheme.typography.bodyDefault.fontFamily,
                ),
            ) {
                append(" styled text")
            }
        },
        checked = checked,
        onCheckedChange = { checked = it },
        shouldTruncate = false,
    )
}

@Composable
internal fun CustomContentSwitchExample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(false) }
    BpkSwitch(
        modifier = modifier.fillMaxWidth(),
        checked = checked,
        onCheckedChange = { checked = it },
    ) {
        Column {
            BpkText(text = stringResource(id = R.string.toggle_custom_title), style = BpkTheme.typography.heading5)
            BpkText(text = stringResource(id = R.string.toggle_custom_subtitle))
        }
    }
}

@Composable
internal fun SwitchWithIconExample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(true) }
    BpkSwitch(
        modifier = modifier.fillMaxWidth(),
        checked = checked,
        onCheckedChange = { checked = it },
    ) {
        BpkText(text = stringResource(id = R.string.toggle_with_icon))
        BpkIcon(
            icon = BpkIcon.InformationCircle,
            contentDescription = stringResource(id = R.string.toggle_with_icon_content_description),
            modifier = Modifier.padding(start = BpkSpacing.Sm),
        )
    }
}

@Composable
internal fun OnContrastUncheckedSwitchExample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(false) }
    BpkSwitch(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(R.string.toggle_on_contrast_unchecked),
        checked = checked,
        onCheckedChange = { checked = it },
        style = BpkSwitchStyle.OnContrast,
    )
}

@Composable
internal fun OnContrastCheckedSwitchExample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(true) }
    BpkSwitch(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(R.string.toggle_on_contrast_checked),
        checked = checked,
        onCheckedChange = { checked = it },
        style = BpkSwitchStyle.OnContrast,
    )
}

@Composable
internal fun OnContrastDisabledUncheckedSwitchExample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(false) }
    BpkSwitch(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(R.string.toggle_on_contrast_disabled_unchecked),
        enabled = false,
        checked = checked,
        onCheckedChange = { checked = it },
        style = BpkSwitchStyle.OnContrast,
    )
}
@Composable
internal fun OnContrastDisabledCheckedSwitchExample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(true) }
    BpkSwitch(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(R.string.toggle_on_contrast_disabled_checked),
        enabled = false,
        checked = checked,
        onCheckedChange = { checked = it },
        style = BpkSwitchStyle.OnContrast,
    )
}
