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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.compose.chip.BpkChipStyle
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.singleselectchipgroup.BpkSingleSelectChipGroup
import net.skyscanner.backpack.compose.singleselectchipgroup.SingleSelectChipGroupData.ChipGroupType
import net.skyscanner.backpack.compose.singleselectchipgroup.SingleSelectChipGroupData.ChipItem
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Deals
import net.skyscanner.backpack.compose.tokens.Heart
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.SingleSelectChipGroupComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@SingleSelectChipGroupComponent
@ComposeStory("Default")
fun SingleSelectChipGroupStoryDefault(modifier: Modifier = Modifier) =
    SingleSelectChipGroupDemo(BpkChipStyle.Default, modifier)

@Composable
@SingleSelectChipGroupComponent
@ComposeStory("On Dark")
fun SingleSelectChipGroupStoryOnDark(modifier: Modifier = Modifier) =
    SingleSelectChipGroupDemo(BpkChipStyle.OnDark, modifier)

@Composable
@SingleSelectChipGroupComponent
@ComposeStory("On Image")
fun SingleSelectChipGroupStoryOnImage(modifier: Modifier = Modifier) =
    SingleSelectChipGroupDemo(BpkChipStyle.OnImage, modifier)

@Composable
@SingleSelectChipGroupComponent
internal fun SingleSelectChipGroupDemo(style: BpkChipStyle, modifier: Modifier = Modifier) {

    Box(modifier) {
        if (style == BpkChipStyle.OnImage) {
            Image(
                painter = painterResource(R.drawable.city),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }
        val background = when (style) {
            BpkChipStyle.Default -> Color.Transparent
            BpkChipStyle.OnDark -> BpkTheme.colors.surfaceContrast
            BpkChipStyle.OnImage -> Color.Transparent
        }
        val textColor = when (style) {
            BpkChipStyle.Default -> BpkTheme.colors.textPrimary
            BpkChipStyle.OnDark, BpkChipStyle.OnImage -> BpkTheme.colors.textOnDark
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(background)
                .padding(BpkSpacing.Base),
            verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        ) {
            BpkText(text = ChipGroupType.RAIL.name, color = textColor, style = BpkTheme.typography.subheading)
            SingleSelectChipGroupSample(type = ChipGroupType.RAIL, style = style)
            Spacer(modifier = Modifier.height(BpkSpacing.Xxl))
            BpkText(text = ChipGroupType.WRAP.name, color = textColor, style = BpkTheme.typography.subheading)
            SingleSelectChipGroupSample(type = ChipGroupType.WRAP, style = style)
        }
    }
}

@Composable
@SingleSelectChipGroupComponent

internal fun SingleSelectChipGroupSample(
    type: ChipGroupType,
    modifier: Modifier = Modifier,
    defaultIndex: Int = -1,
    style: BpkChipStyle = BpkChipStyle.Default,
) {
    val chips = listOf(
        ChipItem("London"),
        ChipItem("Paris"),
        ChipItem("Algiers"),
        ChipItem("Madrid"),
        ChipItem("New York"),
        ChipItem("Shenzhen"),
        ChipItem("Tokyo"),
        ChipItem("Rome", BpkIcon.Deals),
        ChipItem("Cairo"),
        ChipItem("Berlin"),
        ChipItem("Moscow", BpkIcon.Heart),
        ChipItem("Bangkok"),
        ChipItem("Toronto"),
        ChipItem("Dubai"),
        ChipItem("Amsterdam"),
        ChipItem("Sao Paulo"),
        ChipItem("Sydney"),
        ChipItem("A city with long name in mars"),
        ChipItem("Rio de janeiro"),
    )
    var selectedIndex by remember { mutableStateOf(defaultIndex) }

    BpkSingleSelectChipGroup(
        modifier = modifier,
        chips = chips,
        selectedIndex = selectedIndex,
        onItemClicked = { selectedIndex = chips.indexOf(it) },
        style = style,
        type = type,
    )
}
