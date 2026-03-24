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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.searchinputcontrol.BpkSearchInputControl
import net.skyscanner.backpack.compose.searchinputcontrol.BpkSearchInputControlStyle
import net.skyscanner.backpack.compose.searchinputcontrol.Docking
import net.skyscanner.backpack.compose.searchinputsummary.Prefix
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Calendar
import net.skyscanner.backpack.compose.tokens.Family
import net.skyscanner.backpack.compose.tokens.FlightLanding
import net.skyscanner.backpack.compose.tokens.FlightTakeoff
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
        OnDefaultControlExample()
        OnContrastControlExample()
    }
}

@Composable
@SearchInputControlComponent
@ComposeStory(name = "On default", kind = StoryKind.ScreenshotOnly)
internal fun OnDefaultControlExample() {
    Column(
        modifier = Modifier
            .background(BpkTheme.colors.surfaceDefault)
            .padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        SearchInputControlDockedStack(
            style = BpkSearchInputControlStyle.OnDefault,
            focusedIndex = 1,
        )
    }
}

@Composable
@SearchInputControlComponent
@ComposeStory(name = "On contrast", kind = StoryKind.ScreenshotOnly)
internal fun OnContrastControlExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BpkTheme.colors.surfaceContrast)
            .padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        SearchInputControlDockedStack(
            style = BpkSearchInputControlStyle.OnContrast,
            focusedIndex = 1,
        )
    }
}

@Composable
internal fun SearchInputControlDockedStack(
    style: BpkSearchInputControlStyle,
    focusedIndex: Int = -1,
) {
    var currentFocusedIndex by remember { mutableIntStateOf(focusedIndex) }
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        BpkSearchInputControl(
            inputText = "London",
            inputHint = "Where from?",
            prefix = Prefix.Icon(BpkIcon.FlightTakeoff),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { currentFocusedIndex = 0 },
            isFocused = currentFocusedIndex == 0,
            style = style,
            docking = Docking.Top,
        )
        BpkSearchInputControl(
            inputText = "",
            inputHint = "Where to?",
            prefix = Prefix.Icon(BpkIcon.FlightLanding),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { currentFocusedIndex = 1 },
            isFocused = currentFocusedIndex == 1,
            style = style,
            docking = Docking.Middle,
        )
        BpkSearchInputControl(
            inputText = "Thurs 9 May – Fri 29 May, 2025",
            inputHint = "Select dates",
            prefix = Prefix.Icon(BpkIcon.Calendar),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { currentFocusedIndex = 2 },
            isFocused = currentFocusedIndex == 2,
            style = style,
            docking = Docking.Middle,
        )
        BpkSearchInputControl(
            inputText = "1 adult",
            inputHint = "Who's travelling?",
            prefix = Prefix.Icon(BpkIcon.Family),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { currentFocusedIndex = 3 },
            isFocused = currentFocusedIndex == 3,
            style = style,
            docking = Docking.Bottom,
        )
    }
}
