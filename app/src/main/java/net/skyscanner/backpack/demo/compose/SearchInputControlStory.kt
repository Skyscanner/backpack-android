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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.searchinputcontrol.BpkSearchInputControl
import net.skyscanner.backpack.compose.searchinputcontrol.BpkSearchInputControlStyle
import net.skyscanner.backpack.compose.searchinputcontrol.BpkSearchInputControlType
import net.skyscanner.backpack.compose.searchinputcontrol.Docking
import net.skyscanner.backpack.compose.searchinputsummary.Prefix
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.textfield.BpkClearAction
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Hotels
import net.skyscanner.backpack.compose.tokens.Search
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.SearchInputControlComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.meta.StoryKind

@Composable
@SearchInputControlComponent
@ComposeStory(kind = StoryKind.DemoOnly)
fun SearchInputControlExamples(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        DefaultControlExample()
        TextPrefixControlExample()
        IconPrefixControlExample()
        NoPrefixControlExample()
        ReadOnlyControlExample()
        CornerControlExample()
    }
}

@Composable
@SearchInputControlComponent
@ComposeStory(name = "Default prefix", kind = StoryKind.ScreenshotOnly)
internal fun DefaultControlExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        modifier = Modifier.padding(BpkSpacing.Base),
    ) {
        SearchInputControlStory(
            name = "Default, empty",
            inputText = "",
            inputHint = stringResource(id = R.string.text_field_hint),
        )

        SearchInputControlStory(
            name = "Default, filled in",
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
        )
    }
}

@Composable
@SearchInputControlComponent
@ComposeStory(name = "Text prefix", kind = StoryKind.ScreenshotOnly)
internal fun TextPrefixControlExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        modifier = Modifier.padding(BpkSpacing.Base),
    ) {
        SearchInputControlStory(
            name = "Text prefix, empty",
            inputText = "",
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.Text(stringResource(id = R.string.text_field_prefix)),
        )

        SearchInputControlStory(
            name = "Text prefix, filled in",
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.Text(stringResource(id = R.string.text_field_prefix)),
        )
    }
}

@Composable
@SearchInputControlComponent
@ComposeStory(name = "Icon prefix", kind = StoryKind.ScreenshotOnly)
internal fun IconPrefixControlExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        modifier = Modifier.padding(BpkSpacing.Base),
    ) {
        SearchInputControlStory(
            name = "Icon prefix, empty",
            inputText = "",
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.Icon(BpkIcon.Hotels),
        )

        SearchInputControlStory(
            name = "Icon prefix, filled in",
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.Icon(BpkIcon.Hotels),
        )
    }
}

@Composable
@SearchInputControlComponent
@ComposeStory(name = "No prefix", kind = StoryKind.ScreenshotOnly)
internal fun NoPrefixControlExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        modifier = Modifier.padding(BpkSpacing.Base),
    ) {
        SearchInputControlStory(
            name = "No prefix, empty",
            inputText = "",
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.None,
        )

        SearchInputControlStory(
            name = "No prefix, filled in",
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.None,
        )
    }
}

@Composable
@SearchInputControlComponent
@ComposeStory(name = "Read only", kind = StoryKind.ScreenshotOnly)
internal fun ReadOnlyControlExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        modifier = Modifier.padding(BpkSpacing.Base),
    ) {
        SearchInputControlStory(
            name = "Read only, unfocused",
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.None,
            type = BpkSearchInputControlType.ReadOnly(isFocused = false),
        )

        SearchInputControlStory(
            name = "Read only, focused",
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.None,
            type = BpkSearchInputControlType.ReadOnly(isFocused = true),
        )

        var firstFocused by remember { mutableStateOf(false) }

        SearchInputControlStory(
            name = "Read only, focus on click",
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.None,
            type = BpkSearchInputControlType.ReadOnly(isFocused = firstFocused),
            searchInputControlModifier = Modifier.clickable { firstFocused = !firstFocused },
        )

        SearchInputControlStory(
            name = "Read only, focus on click",
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.None,
            type = BpkSearchInputControlType.ReadOnly(isFocused = !firstFocused),
            searchInputControlModifier = Modifier.clickable { firstFocused = !firstFocused },
        )
    }
}

@Composable
@SearchInputControlComponent
@ComposeStory(name = "Corner", kind = StoryKind.ScreenshotOnly)
internal fun CornerControlExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Xl),
        modifier = Modifier.padding(BpkSpacing.Base),
    ) {
        SearchInputControlDockedStack(
            title = "On default",
            style = BpkSearchInputControlStyle.Default,
            backgroundColor = BpkTheme.colors.surfaceDefault,
            textColor = BpkTheme.colors.textPrimary,
        )
        SearchInputControlDockedStack(
            title = "On contrast",
            style = BpkSearchInputControlStyle.OnContrast,
            backgroundColor = BpkTheme.colors.surfaceContrast,
            textColor = BpkTheme.colors.textOnDark,
        )
    }
}

@Composable
private fun SearchInputControlDockedStack(
    title: String,
    style: BpkSearchInputControlStyle,
    backgroundColor: Color,
    textColor: Color,
) {
    var focusedIndex by remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .padding(BpkSpacing.Base),
    ) {
        BpkText(
            text = title,
            style = BpkTheme.typography.heading4,
            color = textColor,
            modifier = Modifier.padding(vertical = BpkSpacing.Base),
        )
        BpkSearchInputControl(
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.Icon(BpkIcon.Search),
            onInputChanged = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { focusedIndex = 0 },
            type = BpkSearchInputControlType.ReadOnly(isFocused = focusedIndex == 0),
            style = style,
            docking = Docking.Top,
            horizontalPadding = BpkSpacing.Base,
            contentPadding = BpkSpacing.Sm,
            minHeight = BpkSpacing.Xxl + BpkSpacing.Md,
        )
        BpkSearchInputControl(
            inputText = stringResource(id = R.string.city_rome),
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.Icon(BpkIcon.Hotels),
            onInputChanged = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { focusedIndex = 1 },
            type = BpkSearchInputControlType.ReadOnly(isFocused = focusedIndex == 1),
            style = style,
            docking = Docking.Middle,
            horizontalPadding = BpkSpacing.Base,
            contentPadding = BpkSpacing.Sm,
            minHeight = BpkSpacing.Xxl + BpkSpacing.Md,
        )
        BpkSearchInputControl(
            inputText = "",
            inputHint = stringResource(id = R.string.text_field_hint),
            prefix = Prefix.Icon(BpkIcon.Hotels),
            onInputChanged = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { focusedIndex = 2 },
            type = BpkSearchInputControlType.ReadOnly(isFocused = focusedIndex == 2),
            style = style,
            docking = Docking.Bottom,
            horizontalPadding = BpkSpacing.Base,
            contentPadding = BpkSpacing.Sm,
            minHeight = BpkSpacing.Xxl + BpkSpacing.Md,
        )
    }
}

@Composable
internal fun SearchInputControlStory(
    name: String,
    modifier: Modifier = Modifier,
    searchInputControlModifier: Modifier = Modifier,
    inputText: String = stringResource(id = R.string.city_rome),
    inputHint: String = stringResource(id = R.string.text_field_hint),
    prefix: Prefix = Prefix.Icon(BpkIcon.Search),
    type: BpkSearchInputControlType = BpkSearchInputControlType.TextInput,
    style: BpkSearchInputControlStyle = BpkSearchInputControlStyle.Default,
    docking: Docking = Docking.Float,
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
        BpkSearchInputControl(
            inputText = state,
            inputHint = inputHint,
            prefix = prefix,
            onInputChanged = { state = it },
            clearAction = BpkClearAction(stringResource(id = R.string.text_field_clear_action_description)) {
                state = ""
            },
            modifier = searchInputControlModifier.fillMaxWidth(),
            type = type,
            style = style,
            docking = docking,
        )
    }
}
