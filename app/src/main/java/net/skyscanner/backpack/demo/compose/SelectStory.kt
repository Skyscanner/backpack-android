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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.select.BpkSelect
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.SelectComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@SelectComponent
@ComposeStory
fun SelectStory(modifier: Modifier = Modifier) {
    Column(modifier) {
        Row(
            modifier = Modifier.padding(BpkSpacing.Base),
            horizontalArrangement = Arrangement.Start,
        ) {
            DefaultSelectSample()
        }
        Row(
            modifier = Modifier.padding(BpkSpacing.Base),
            horizontalArrangement = Arrangement.Start,
        ) {
            DisabledSelectSample()
        }
        Row(
            modifier = Modifier.padding(BpkSpacing.Base),
            horizontalArrangement = Arrangement.Start,
        ) {
            ErrorSelectSample()
        }
    }
}

@Composable
@SelectComponent
@ComposeStory("TextBox")
fun SelectTextBoxStory(modifier: Modifier = Modifier) {
    Column(modifier) {
        Row(
            modifier = Modifier.padding(BpkSpacing.Base),
            horizontalArrangement = Arrangement.Start,
        ) {
            DefaultSelectTextBoxSample()
        }
        Row(
            modifier = Modifier.padding(BpkSpacing.Base),
            horizontalArrangement = Arrangement.Start,
        ) {
            DisabledSelectTextBoxSample()
        }
        Row(
            modifier = Modifier.padding(BpkSpacing.Base),
            horizontalArrangement = Arrangement.Start,
        ) {
            ErrorSelectTextBoxSample()
        }
    }
}

@Composable
internal fun DefaultSelectSample(modifier: Modifier = Modifier) {
    BpkSelect(
        modifier = modifier.widthIn(min = BpkSpacing.Xxl.times(5)),
        options = options(),
        selectedIndex = -1,
        placeHolder = stringResource(id = R.string.input_placeholder),
        state = BpkFieldStatus.Default,
    )
}

@Composable
internal fun DisabledSelectSample(modifier: Modifier = Modifier) {
    BpkSelect(
        modifier = modifier.widthIn(min = BpkSpacing.Xxl.times(5)),
        options = options(),
        selectedIndex = 0,
        placeHolder = stringResource(id = R.string.input_placeholder),
        state = BpkFieldStatus.Disabled,
    )
}

@Composable
internal fun ErrorSelectSample(modifier: Modifier = Modifier) {
    BpkSelect(
        modifier = modifier.widthIn(min = BpkSpacing.Xxl.times(5)),
        options = options(),
        selectedIndex = 0,
        placeHolder = stringResource(id = R.string.input_placeholder),
        state = BpkFieldStatus.Error(stringResource(id = R.string.input_error)),
    )
}

@Composable
internal fun DefaultSelectTextBoxSample(modifier: Modifier = Modifier) {
    BpkSelect(
        modifier = modifier.widthIn(min = BpkSpacing.Xxl.times(5)),
        text = stringResource(R.string.city_london),
        placeHolder = stringResource(id = R.string.input_placeholder),
        state = BpkFieldStatus.Default,
        onClick = {},
    )
}

@Composable
internal fun DisabledSelectTextBoxSample(modifier: Modifier = Modifier) {
    BpkSelect(
        modifier = modifier.widthIn(min = BpkSpacing.Xxl.times(5)),
        text = stringResource(R.string.city_london),
        placeHolder = stringResource(id = R.string.input_placeholder),
        state = BpkFieldStatus.Disabled,
        onClick = {},
    )
}

@Composable
internal fun ErrorSelectTextBoxSample(modifier: Modifier = Modifier) {
    BpkSelect(
        modifier = modifier.widthIn(min = BpkSpacing.Xxl.times(5)),
        text = stringResource(R.string.city_london),
        placeHolder = stringResource(id = R.string.input_placeholder),
        state = BpkFieldStatus.Error(stringResource(id = R.string.input_error)),
        onClick = {},
    )
}

@Composable
internal fun options(): List<String> {
    return arrayListOf(
        stringResource(R.string.city_london),
        stringResource(R.string.city_paris),
        stringResource(R.string.city_algiers),
        stringResource(R.string.city_madrid),
        stringResource(R.string.city_new_york),
        stringResource(R.string.city_shenzhen),
        stringResource(R.string.city_tokyo),
        stringResource(R.string.city_rome),
        stringResource(R.string.city_cairo),
        stringResource(R.string.city_berlin),
        stringResource(R.string.city_dubai),
        stringResource(R.string.city_rio),
        stringResource(R.string.city_long_name),
    )
}
