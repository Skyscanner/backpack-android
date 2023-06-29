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
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.select.BpkSelect
import net.skyscanner.backpack.compose.tokens.BpkSpacing
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
internal fun DefaultSelectSample(modifier: Modifier = Modifier) {
    BpkSelect(
        modifier = modifier.widthIn(min = BpkSpacing.Xxl.times(5)),
        options = arrayListOf("Karachi", "Lahore", "Faisalabad", "Islamabad", "Quetta", "Peshawar", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item"),
        selectedIndex = -1,
        placeHolder = "Select",
        state = BpkFieldStatus.Default,
    )
}

@Composable
internal fun DisabledSelectSample(modifier: Modifier = Modifier) {
    BpkSelect(
        modifier = modifier.widthIn(min = BpkSpacing.Xxl.times(5)),
        options = arrayListOf("Karachi", "Lahore", "Faisalabad", "Islamabad", "Quetta", "Peshawar", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item"),
        selectedIndex = 0,
        placeHolder = "Select",
        state = BpkFieldStatus.Disabled,
    )
}

@Composable
internal fun ErrorSelectSample(modifier: Modifier = Modifier) {
    BpkSelect(
        modifier = modifier.widthIn(min = BpkSpacing.Xxl.times(5)),
        options = arrayListOf("Karachi", "Lahore", "Faisalabad", "Islamabad", "Quetta", "Peshawar", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item", "Menu item"),
        selectedIndex = 0,
        placeHolder = "Select",
        state = BpkFieldStatus.Error("This option not supported yet."),
    )
}
