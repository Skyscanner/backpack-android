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
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.chip.BpkChipStyle
import net.skyscanner.backpack.compose.chipgroup.BpkSingleSelectChipGroup
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleChipGroupType
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleChipItem
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Deals
import net.skyscanner.backpack.compose.tokens.Heart
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.ChipGroupComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@ChipGroupComponent
@ComposeStory("Single Select Rail")
fun SingleSelectChipGroupStoryRail(modifier: Modifier = Modifier) =
    SingleSelectChipGroupDemo(BpkSingleChipGroupType.RAIL, modifier)

@Composable
@ChipGroupComponent
@ComposeStory("Single Select Wrap")
fun SingleSelectChipGroupStoryWrap(modifier: Modifier = Modifier) =
    SingleSelectChipGroupDemo(BpkSingleChipGroupType.WRAP, modifier)

@Composable
private fun SingleSelectChipGroupDemo(type: BpkSingleChipGroupType, modifier: Modifier = Modifier) {
    Column(modifier) {
        Box(modifier = Modifier.weight(1f)) {
            Image(
                painter = painterResource(R.drawable.city),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(BpkSpacing.Base)
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
            ) {

                SingleSelectChipGroupSample(type = type, style = BpkChipStyle.OnImage)
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BpkTheme.colors.surfaceContrast)
                    .padding(BpkSpacing.Base),
                verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
            ) {

                SingleSelectChipGroupSample(type = type, style = BpkChipStyle.OnDark)
            }
        }
        Box(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(BpkSpacing.Base),
                verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
            ) {

                SingleSelectChipGroupSample(type = type, style = BpkChipStyle.Default)
            }
        }
    }
}

@Composable
internal fun SingleSelectChipGroupSample(
    type: BpkSingleChipGroupType,
    modifier: Modifier = Modifier,
    style: BpkChipStyle = BpkChipStyle.Default,
) {
    val chips = listOf(
        BpkSingleChipItem(stringResource(R.string.city_london)),
        BpkSingleChipItem(stringResource(R.string.city_paris)),
        BpkSingleChipItem(stringResource(R.string.city_algiers)),
        BpkSingleChipItem(stringResource(R.string.city_madrid)),
        BpkSingleChipItem(stringResource(R.string.city_new_york)),
        BpkSingleChipItem(stringResource(R.string.city_shenzhen)),
        BpkSingleChipItem(stringResource(R.string.city_tokyo)),
        BpkSingleChipItem(stringResource(R.string.city_rome), BpkIcon.Deals),
        BpkSingleChipItem(stringResource(R.string.city_cairo)),
        BpkSingleChipItem(stringResource(R.string.city_berlin)),
        BpkSingleChipItem(stringResource(R.string.city_dubai), BpkIcon.Heart),
        BpkSingleChipItem(stringResource(R.string.city_long_name)),
        BpkSingleChipItem(stringResource(R.string.city_rio)),

    )
    var selectedIndex by remember { mutableStateOf(0) }

    BpkSingleSelectChipGroup(
        modifier = modifier,
        chips = chips,
        selectedIndex = selectedIndex,
        onItemClicked = { selectedIndex = chips.indexOf(it) },
        style = style,
        type = type,
    )
}
