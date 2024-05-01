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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.searchinputsummary.BpkSearchInputSummary
import net.skyscanner.backpack.compose.searchinputsummary.Prefix
import net.skyscanner.backpack.compose.textfield.BpkClearAction
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Hotels
import net.skyscanner.backpack.compose.tokens.Search
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.SearchInputSummaryComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@SearchInputSummaryComponent
@ComposeStory
fun SearchInputSummary(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        SearchInputSummaryStory(
            inputText = "",
            inputHint = stringResource(id = R.string.text_field_hint),
        )

        SearchInputSummaryStory(
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
        )

        SearchInputSummaryStory(
            inputText = "",
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.Text(stringResource(id = R.string.text_field_prefix)),
        )

        SearchInputSummaryStory(
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.Text(stringResource(id = R.string.text_field_prefix)),
        )

        SearchInputSummaryStory(
            inputText = "",
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.Icon(BpkIcon.Hotels),
        )

        SearchInputSummaryStory(
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.Icon(BpkIcon.Hotels),
        )
    }
}

@Composable
internal fun SearchInputSummaryStory(
    inputText: String = stringResource(id = R.string.city_rome),
    inputHint: String = stringResource(id = R.string.text_field_hint),
    prefix: Prefix = Prefix.Icon(BpkIcon.Search),
) {

    var state by remember { mutableStateOf(inputText) }
    BpkSearchInputSummary(
        inputText = inputText,
        inputHint = inputHint,
        prefix = prefix,
        onInputChanged = {
            state = it
        },
        clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {
            state = ""
        },
        modifier = Modifier.fillMaxWidth(),
    )
}
