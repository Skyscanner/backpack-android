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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import net.skyscanner.backpack.compose.dividedcard.BpkDividedCard
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.CardComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@CardComponent
@ComposeStory("Divided card")
fun DividedCardStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(BpkTheme.colors.line)
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Xxl),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {

        DividedCardExample(Modifier.fillMaxWidth())
    }
}

@Composable
private fun DividedCardExample(modifier: Modifier = Modifier) {
    BpkDividedCard(
        modifier = modifier.fillMaxWidth(),
        primaryContent = {
            Spacer(modifier = Modifier.height(BpkSpacing.Xxl))
            BpkText(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.divided_card_primary_content),
                style = BpkTheme.typography.bodyDefault,
                textAlign = TextAlign.Center,
            )
        },
        secondaryContent = {
            Spacer(modifier = Modifier.height(BpkSpacing.Xxl))
            BpkText(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.divided_card_secondary_content),
                style = BpkTheme.typography.bodyDefault,
                textAlign = TextAlign.Center,
            )
        },
        onClick = {},
    )
}
