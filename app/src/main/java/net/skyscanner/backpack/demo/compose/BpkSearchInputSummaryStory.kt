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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.searchinputsummary.BpkSearchInputSummary
import net.skyscanner.backpack.compose.searchinputsummary.BpkSearchInputSummaryType
import net.skyscanner.backpack.compose.searchinputsummary.Prefix
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.textfield.BpkClearAction
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Hotels
import net.skyscanner.backpack.compose.tokens.Search
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.SearchInputSummaryComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.meta.StoryKind

@Composable
@SearchInputSummaryComponent
@ComposeStory(kind = StoryKind.DemoOnly)
fun SearchInputSummaryExamples(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .systemBarsPadding()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        DefaultExample()
        TextPrefixExample()
        IconPrefixExample()
        NoPrefixExample()
        ReadOnlyExample()
    }
}

@Composable
@SearchInputSummaryComponent
@ComposeStory(name = "Default prefix", kind = StoryKind.ScreenshotOnly)
internal fun DefaultExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        modifier = Modifier.padding(BpkSpacing.Base),
    ) {
        SearchInputSummaryStory(
            name = "Default, empty",
            inputText = "",
            inputHint = stringResource(id = R.string.text_field_hint),
        )

        SearchInputSummaryStory(
            name = "Default, filled in",
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
        )
    }
}

@Composable
@SearchInputSummaryComponent
@ComposeStory(name = "Text prefix", kind = StoryKind.ScreenshotOnly)
internal fun TextPrefixExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        modifier = Modifier.padding(BpkSpacing.Base),
    ) {
        SearchInputSummaryStory(
            name = "Text prefix, empty",
            inputText = "",
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.Text(stringResource(id = R.string.text_field_prefix)),
        )

        SearchInputSummaryStory(
            name = "Text prefix, filled in",
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.Text(stringResource(id = R.string.text_field_prefix)),
        )
    }
}

@Composable
@SearchInputSummaryComponent
@ComposeStory(name = "Icon prefix", kind = StoryKind.ScreenshotOnly)
internal fun IconPrefixExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        modifier = Modifier.padding(BpkSpacing.Base),
    ) {
        SearchInputSummaryStory(
            name = "Icon prefix, empty",
            inputText = "",
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.Icon(BpkIcon.Hotels),
        )

        SearchInputSummaryStory(
            name = "Icon prefix, filled in",
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.Icon(BpkIcon.Hotels),
        )
    }
}

@Composable
@SearchInputSummaryComponent
@ComposeStory(name = "No prefix", kind = StoryKind.ScreenshotOnly)
internal fun NoPrefixExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        modifier = Modifier.padding(BpkSpacing.Base),
    ) {
        SearchInputSummaryStory(
            name = "No prefix, empty",
            inputText = "",
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.None,
        )

        SearchInputSummaryStory(
            name = "No prefix, filled in",
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.None,
        )
    }
}

@Composable
@SearchInputSummaryComponent
@ComposeStory(name = "Read only", kind = StoryKind.ScreenshotOnly)
internal fun ReadOnlyExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        modifier = Modifier.padding(BpkSpacing.Base),
    ) {
        SearchInputSummaryStory(
            name = "Read only, unfocused",
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.None,
            type = BpkSearchInputSummaryType.ReadOnly(isFocused = false),
        )

        SearchInputSummaryStory(
            name = "Read only, focused",
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.None,
            type = BpkSearchInputSummaryType.ReadOnly(isFocused = true),
        )

        var firstFocused by remember { mutableStateOf(false) }

        SearchInputSummaryStory(
            name = "Read only, focus on click",
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.None,
            type = BpkSearchInputSummaryType.ReadOnly(isFocused = firstFocused),
            searchInputSummaryModifier = Modifier.clickable { firstFocused = !firstFocused },
        )

        SearchInputSummaryStory(
            name = "Read only, focus on click",
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.None,
            type = BpkSearchInputSummaryType.ReadOnly(isFocused = !firstFocused),
            searchInputSummaryModifier = Modifier.clickable { firstFocused = !firstFocused },
        )
    }
}

@Composable
internal fun SearchInputSummaryStory(
    name: String,
    modifier: Modifier = Modifier,
    searchInputSummaryModifier: Modifier = Modifier,
    inputText: String = stringResource(id = R.string.city_rome),
    inputHint: String = stringResource(id = R.string.text_field_hint),
    prefix: Prefix = Prefix.Icon(BpkIcon.Search),
    type: BpkSearchInputSummaryType = BpkSearchInputSummaryType.TextInput,
) {
    Column(
        modifier = modifier,
    ) {
        BpkText(
            text = name,
            style = BpkTheme.typography.heading4,
            modifier = Modifier.padding(vertical = BpkSpacing.Base),
        )
        var state by remember { mutableStateOf(inputText) }
        BpkSearchInputSummary(
            inputText = state,
            inputHint = inputHint,
            prefix = prefix,
            onInputChanged = {
                state = it
            },
            clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {
                state = ""
            },
            modifier = searchInputSummaryModifier.fillMaxWidth(),
            type = type,
        )
    }
}
