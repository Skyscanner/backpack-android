/*
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.divider.BpkDivider
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.DividerComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@DividerComponent
@ComposeStory
fun DividerStory(modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base)) {

        Column {
            BpkText(
                text = stringResource(R.string.divider_no_indent),
                modifier = Modifier.padding(BpkSpacing.Base),
            )
            DividerSampleNoIndent()
        }

        Column {
            BpkText(
                text = stringResource(R.string.divider_indent),
                modifier = Modifier.padding(BpkSpacing.Base),
            )
            DividerSample_WithIndent()
        }
    }
}

@Composable
internal fun DividerSampleNoIndent(modifier: Modifier = Modifier) {
    BpkDivider(modifier = modifier)
}

@Composable
internal fun DividerSample_WithIndent(modifier: Modifier = Modifier) {
    BpkDivider(modifier = modifier, startIndent = BpkSpacing.Base)
}
