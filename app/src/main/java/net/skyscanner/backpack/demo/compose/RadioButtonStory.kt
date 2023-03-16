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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.radiobutton.BpkRadioButton
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.RadioButtonComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@RadioButtonComponent
@ComposeStory
fun RadioButtonStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {

        var selectedIndex by remember { mutableStateOf(1) }
        DefaultUncheckedRadioButtonExample(selected = selectedIndex == 0, onClick = { selectedIndex = 0 })
        DefaultCheckedRadioButtonExample(selected = selectedIndex == 1, onClick = { selectedIndex = 1 })

        DisabledUnCheckedRadioButtonExample()
        DisabledCheckedRadioButtonExample()
        CustomContentRadioButtonExample()
    }
}

@Composable
internal fun DefaultUncheckedRadioButtonExample(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    BpkRadioButton(
        modifier = modifier,
        text = stringResource(id = R.string.toggle_default_unchecked),
        selected = selected,
        onClick = onClick,
    )
}

@Composable
internal fun DefaultCheckedRadioButtonExample(
    modifier: Modifier = Modifier,
    selected: Boolean = true,
    onClick: (() -> Unit)? = null,
) {
    BpkRadioButton(
        modifier = modifier,
        text = stringResource(id = R.string.toggle_default_checked),
        selected = selected,
        onClick = onClick,
    )
}

@Composable
internal fun DisabledUnCheckedRadioButtonExample(modifier: Modifier = Modifier) {
    BpkRadioButton(
        modifier = modifier,
        text = stringResource(id = R.string.toggle_disabled_unchecked),
        enabled = false,
        selected = false,
        onClick = null,
    )
}

@Composable
internal fun DisabledCheckedRadioButtonExample(modifier: Modifier = Modifier) {
    BpkRadioButton(
        modifier = modifier,
        text = stringResource(id = R.string.toggle_disabled_checked),
        enabled = false,
        selected = true,
        onClick = null,
    )
}

@Composable
internal fun CustomContentRadioButtonExample(modifier: Modifier = Modifier) {
    var selected by remember { mutableStateOf(false) }
    BpkRadioButton(
        modifier = modifier,
        selected = selected,
        onClick = { selected = !selected },
    ) {
        Column {
            BpkText(text = stringResource(id = R.string.toggle_custom_title), style = BpkTheme.typography.heading5)
            BpkText(text = stringResource(id = R.string.toggle_custom_subtitle))
        }
    }
}
