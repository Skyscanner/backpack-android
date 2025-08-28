/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.cardwrapper.BpkCardWrapper
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.CardComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@CardComponent
@ComposeStory("Card wrapper")
fun CardWrapperStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Xxl),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        CardWrapperExample(Modifier.fillMaxWidth())
    }
}

@Composable
private fun CardWrapperExample(modifier: Modifier = Modifier) {
    BpkCardWrapper(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = BpkTheme.colors.coreAccent,
        headerContent = {
            Column() {
                BpkText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(BpkSpacing.Base, BpkSpacing.Md),
                    color = BpkTheme.colors.textPrimaryInverse,
                    text = stringResource(id = R.string.card_wrapper_title),
                    style = BpkTheme.typography.label1,
                )
            }
        },
        cardContent = {
            Column() {
                BpkText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = BpkSpacing.Md),
                    text = stringResource(id = R.string.lets_explore_title),
                    style = BpkTheme.typography.heading5,
                )
                BpkText(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.lets_explore_text),
                    style = BpkTheme.typography.bodyDefault,
                )
            }
        },
    )
}
