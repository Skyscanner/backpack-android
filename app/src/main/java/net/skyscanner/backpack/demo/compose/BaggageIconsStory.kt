/*
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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BaggageCabinIncludedTick
import net.skyscanner.backpack.compose.tokens.BaggageCabinNotIncludedCross
import net.skyscanner.backpack.compose.tokens.BaggageCabinUncertain
import net.skyscanner.backpack.compose.tokens.BaggageCheckedIncludedTick
import net.skyscanner.backpack.compose.tokens.BaggageCheckedNotIncludedCross
import net.skyscanner.backpack.compose.tokens.BaggageCheckedUncertain
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.components.IconComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@IconComponent
@ComposeStory("Baggage")
fun BaggageIconsStory(modifier: Modifier = Modifier) {
    val icons = listOf(
        "Checked included" to BpkIcon.BaggageCheckedIncludedTick,
        "Cabin included" to BpkIcon.BaggageCabinIncludedTick,
        "Checked not included" to BpkIcon.BaggageCheckedNotIncludedCross,
        "Cabin not included" to BpkIcon.BaggageCabinNotIncludedCross,
        "Checked uncertain" to BpkIcon.BaggageCheckedUncertain,
        "Cabin uncertain" to BpkIcon.BaggageCabinUncertain,
    )

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Lg),
    ) {
        icons.forEach { (label, icon) ->
            Column(verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md)) {
                BpkText(text = label, style = BpkTheme.typography.heading5)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Lg),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BpkText(text = "16dp", style = BpkTheme.typography.caption)
                    BpkIcon(icon = icon, contentDescription = "$label, 16dp", size = BpkIconSize.Small)
                    BpkText(text = "24dp", style = BpkTheme.typography.caption)
                    BpkIcon(icon = icon, contentDescription = "$label, 24dp", size = BpkIconSize.Large)
                }
            }
        }
    }
}
