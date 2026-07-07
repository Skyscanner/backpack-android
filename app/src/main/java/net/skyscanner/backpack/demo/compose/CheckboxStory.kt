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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import net.skyscanner.backpack.compose.checkbox.BpkCheckbox
import net.skyscanner.backpack.compose.checkbox.BpkCheckboxPosition
import net.skyscanner.backpack.compose.checkbox.BpkCheckboxStyle
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.InformationCircle
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.CheckboxComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@CheckboxComponent
@ComposeStory
fun CheckboxStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(BpkSpacing.Base)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {

        DefaultCheckboxSample()
        IntermediateCheckboxSample()
        UncheckedCheckboxSample()
        CheckedCheckboxSample()
        DisabledUncheckedCheckboxSample()
        DisabledCheckedCheckboxSample()
        CheckboxTrailingSample()
        CheckboxWithIconSample()
        CheckboxWithIconTrailingSample()
        CustomContentCheckboxSample()

        BpkText(stringResource(R.string.toggle_no_label))
        NoLabelCheckboxSample()

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
            OnContrastUncheckedCheckboxSample()
            OnContrastCheckedCheckboxSample()
            OnContrastIntermediateCheckboxSample()
            OnContrastDisabledUncheckedCheckboxSample()
            OnContrastDisabledCheckedCheckboxSample()
            OnContrastCheckboxWithIconSample()
            OnContrastCheckboxWithIconTrailingSample()
        }
    }
}

@Composable
internal fun DefaultCheckboxSample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(true) }

    BpkCheckbox(
        modifier = modifier,
        text = stringResource(id = R.string.toggle_default),
        checked = checked,
        onCheckedChange = { checked = it },
    )
}

@Composable
internal fun IntermediateCheckboxSample(modifier: Modifier = Modifier) {
    BpkCheckbox(
        modifier = modifier,
        text = stringResource(id = R.string.toggle_indeterminate),
        state = ToggleableState.Indeterminate,
        onClick = { },
    )
}

@Composable
internal fun UncheckedCheckboxSample(modifier: Modifier = Modifier) {
    BpkCheckbox(
        modifier = modifier,
        text = stringResource(id = R.string.toggle_default_unchecked),
        checked = false,
        onCheckedChange = {},
    )
}

@Composable
internal fun CheckedCheckboxSample(modifier: Modifier = Modifier) {
    BpkCheckbox(
        modifier = modifier,
        text = stringResource(id = R.string.toggle_default_checked),
        checked = true,
        onCheckedChange = {},
    )
}

@Composable
internal fun DisabledUncheckedCheckboxSample(modifier: Modifier = Modifier) {
    BpkCheckbox(
        modifier = modifier,
        text = stringResource(id = R.string.toggle_disabled_unchecked),
        checked = false,
        enabled = false,
        onCheckedChange = {},
    )
}

@Composable
internal fun DisabledCheckedCheckboxSample(modifier: Modifier = Modifier) {
    BpkCheckbox(
        modifier = modifier,
        text = stringResource(id = R.string.toggle_disabled_checked),
        checked = true,
        enabled = false,
        onCheckedChange = {},
    )
}

@Composable
internal fun OnContrastUncheckedCheckboxSample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(false) }

    BpkCheckbox(
        modifier = modifier,
        text = stringResource(id = R.string.toggle_on_contrast_unchecked),
        checked = checked,
        style = BpkCheckboxStyle.OnContrast,
        onCheckedChange = { checked = it },
    )
}

@Composable
internal fun OnContrastCheckedCheckboxSample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(true) }

    BpkCheckbox(
        modifier = modifier,
        text = stringResource(id = R.string.toggle_on_contrast_checked),
        checked = checked,
        style = BpkCheckboxStyle.OnContrast,
        onCheckedChange = { checked = it },
    )
}

@Composable
internal fun OnContrastIntermediateCheckboxSample(modifier: Modifier = Modifier) {
    BpkCheckbox(
        modifier = modifier,
        text = stringResource(id = R.string.toggle_on_contrast_intermediate),
        state = ToggleableState.Indeterminate,
        style = BpkCheckboxStyle.OnContrast,
        onClick = { },
    )
}

@Composable
internal fun OnContrastDisabledUncheckedCheckboxSample(modifier: Modifier = Modifier) {
    BpkCheckbox(
        modifier = modifier,
        text = stringResource(id = R.string.toggle_on_contrast_disabled_unchecked),
        checked = false,
        enabled = false,
        style = BpkCheckboxStyle.OnContrast,
        onCheckedChange = {},
    )
}

@Composable
internal fun OnContrastDisabledCheckedCheckboxSample(modifier: Modifier = Modifier) {
    BpkCheckbox(
        modifier = modifier,
        text = stringResource(id = R.string.toggle_on_contrast_disabled_checked),
        checked = true,
        enabled = false,
        style = BpkCheckboxStyle.OnContrast,
        onCheckedChange = {},
    )
}

@Composable
internal fun OnContrastCheckboxWithIconSample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(true) }

    BpkCheckbox(
        modifier = modifier,
        checked = checked,
        style = BpkCheckboxStyle.OnContrast,
        onCheckedChange = { checked = it },
    ) {
        BpkText(text = stringResource(id = R.string.toggle_on_contrast_with_icon))
        BpkIcon(
            icon = BpkIcon.InformationCircle,
            contentDescription = stringResource(id = R.string.toggle_with_icon_content_description),
            modifier = Modifier
                .padding(start = BpkSpacing.Sm)
                .clickable { },
        )
    }
}

@Composable
internal fun OnContrastCheckboxWithIconTrailingSample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(true) }

    BpkCheckbox(
        modifier = modifier.fillMaxWidth(),
        checked = checked,
        checkboxPosition = BpkCheckboxPosition.Trailing,
        style = BpkCheckboxStyle.OnContrast,
        onCheckedChange = { checked = it },
    ) {
        // Group the label and icon so they stay together while the trailing
        // checkbox is pushed to the opposite edge.
        Row(verticalAlignment = Alignment.CenterVertically) {
            BpkText(text = stringResource(id = R.string.toggle_on_contrast_with_icon_trailing))
            BpkIcon(
                icon = BpkIcon.InformationCircle,
                contentDescription = stringResource(id = R.string.toggle_with_icon_content_description),
                modifier = Modifier
                    .padding(start = BpkSpacing.Sm)
                    .clickable { },
            )
        }
    }
}

@Composable
internal fun CheckboxTrailingSample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(true) }

    BpkCheckbox(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(id = R.string.toggle_checkbox_trailing),
        checked = checked,
        checkboxPosition = BpkCheckboxPosition.Trailing,
        onCheckedChange = { checked = it },
    )
}

@Composable
internal fun CheckboxWithIconSample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(true) }

    BpkCheckbox(
        modifier = modifier,
        checked = checked,
        onCheckedChange = { checked = it },
    ) {
        BpkText(text = stringResource(id = R.string.toggle_with_icon))
        BpkIcon(
            icon = BpkIcon.InformationCircle,
            contentDescription = stringResource(id = R.string.toggle_with_icon_content_description),
            modifier = Modifier
                .padding(start = BpkSpacing.Sm)
                .clickable { },
        )
    }
}

@Composable
internal fun CheckboxWithIconTrailingSample(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(true) }

    // The same label + icon content slot, but with the checkbox on the trailing side.
    BpkCheckbox(
        modifier = modifier.fillMaxWidth(),
        checked = checked,
        checkboxPosition = BpkCheckboxPosition.Trailing,
        onCheckedChange = { checked = it },
    ) {
        // Group the label and icon so they stay together while the trailing
        // checkbox is pushed to the opposite edge.
        Row(verticalAlignment = Alignment.CenterVertically) {
            BpkText(text = stringResource(id = R.string.toggle_with_icon_trailing))
            BpkIcon(
                icon = BpkIcon.InformationCircle,
                contentDescription = stringResource(id = R.string.toggle_with_icon_content_description),
                modifier = Modifier
                    .padding(start = BpkSpacing.Sm)
                    .clickable { },
            )
        }
    }
}

@Composable
internal fun CustomContentCheckboxSample(modifier: Modifier = Modifier) {
    BpkCheckbox(
        modifier = modifier,
        checked = true,
        onCheckedChange = {},
    ) {
        Column {
            BpkText(text = stringResource(id = R.string.toggle_custom_title), style = BpkTheme.typography.heading5)
            BpkText(text = stringResource(id = R.string.toggle_custom_subtitle))
        }
    }
}

@Composable
internal fun NoLabelCheckboxSample(modifier: Modifier = Modifier) {
    BpkCheckbox(
        modifier = modifier,
        checked = true,
        onCheckedChange = {},
    ) {}
}
