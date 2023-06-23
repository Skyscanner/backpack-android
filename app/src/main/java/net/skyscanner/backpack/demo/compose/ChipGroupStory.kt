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

import android.content.res.Resources
import android.widget.Toast
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.chip.BpkChipStyle
import net.skyscanner.backpack.compose.chip.BpkChipType
import net.skyscanner.backpack.compose.chipgroup.multiple.BpkMultiChipGroupType
import net.skyscanner.backpack.compose.chipgroup.multiple.BpkMultiChipItem
import net.skyscanner.backpack.compose.chipgroup.multiple.BpkMultiSelectChipGroup
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleChipGroupType
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleChipItem
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleSelectChipGroup
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Beach
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Deals
import net.skyscanner.backpack.compose.tokens.Filter
import net.skyscanner.backpack.compose.tokens.Heart
import net.skyscanner.backpack.compose.tokens.View
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.ChipGroupComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@ChipGroupComponent
@ComposeStory("Single Select Rail")
fun SingleSelectChipGroupStoryRail(modifier: Modifier = Modifier) =
    ChipGroupDemo(
        modifier,
    ) { style -> SingleSelectChipGroupSample(type = BpkSingleChipGroupType.Rail, style = style) }

@Composable
@ChipGroupComponent
@ComposeStory("Single Select Wrap")
fun SingleSelectChipGroupStoryWrap(modifier: Modifier = Modifier) =
    ChipGroupDemo(
        modifier,
    ) { style -> SingleSelectChipGroupSample(type = BpkSingleChipGroupType.Wrap, style = style) }

@Composable
@ChipGroupComponent
@ComposeStory("Multi Select Rail")
fun MultiSelectChipGroupStoryRail(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    ChipGroupDemo(
        modifier,
    ) { style ->
        MultiSelectChipGroupSample(
            type =
            BpkMultiChipGroupType.Rail(
                BpkMultiChipItem(
                    text = stringResource(R.string.sticky_chip),
                    icon = BpkIcon.Filter,
                ) {
                    Toast.makeText(context, context.getString(R.string.sticky_chip_action), Toast.LENGTH_SHORT).show()
                },
            ),
            style = style,
        )
    }
}

@Composable
@ChipGroupComponent
@ComposeStory("Multi Select Rail With No Sticky Chip")
fun MultiSelectChipGroupStoryRailNoStickyChip(modifier: Modifier = Modifier) {
    ChipGroupDemo(
        modifier,
    ) { style -> MultiSelectChipGroupSample(type = BpkMultiChipGroupType.Rail(), style = style) }
}

@Composable
@ChipGroupComponent
@ComposeStory("Multi Select Wrap")
fun MultiSelectChipGroupStoryWrap(modifier: Modifier = Modifier) =
    ChipGroupDemo(
        modifier,
    ) { style -> MultiSelectChipGroupSample(type = BpkMultiChipGroupType.Wrap, style = style) }

@Composable
private fun ChipGroupDemo(modifier: Modifier = Modifier, content: @Composable (BpkChipStyle) -> Unit) {
    Column(modifier) {
        ChipGroupBox(
            modifier = Modifier
                .weight(1f)
                .background(Color.Transparent),
            content = { content.invoke(BpkChipStyle.Default) },
            contentImage = {},
        )
        ChipGroupBox(
            modifier = Modifier
                .weight(1f)
                .background(BpkTheme.colors.surfaceContrast),
            content = { content.invoke(BpkChipStyle.OnDark) },
            contentImage = {},
        )
        ChipGroupBox(
            modifier = Modifier
                .weight(1f)
                .background(Color.Transparent),
            content = { content.invoke(BpkChipStyle.OnImage) },
            contentImage = {
                Image(
                    painter = painterResource(R.drawable.city),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun ChipGroupBox(
    content: @Composable () -> Unit,
    contentImage: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        contentImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(BpkSpacing.Base),
            verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        ) {
            content()
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

@Composable
internal fun MultiSelectChipGroupSample(
    type: BpkMultiChipGroupType,
    modifier: Modifier = Modifier,
    style: BpkChipStyle = BpkChipStyle.Default,
) {
    // This is just for demonstration purposes, you should not create view model manually :)
    val scope = rememberCoroutineScope()
    val resources = LocalContext.current.resources
    val viewModel = remember { MultiChipViewModel(scope, resources = resources) }
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    BpkMultiSelectChipGroup(
        chips = state.value,
        type = type,
        modifier = modifier,
        style = style,
    )
}

private class MultiChipViewModel(private val scope: CoroutineScope, resources: Resources) {
    private val _uiState = MutableStateFlow(emptyList<BpkMultiChipItem>())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.value = listOf(
            getChip(resources.getString(R.string.city_london)),
            getChip(resources.getString(R.string.city_paris), selected = true),
            getChip(resources.getString(R.string.city_algiers), icon = BpkIcon.View),
            getChip(text = resources.getString(R.string.city_madrid), type = BpkChipType.Dropdown),
            getChip(resources.getString(R.string.city_shenzhen)),
            getChip(resources.getString(R.string.city_berlin), type = BpkChipType.Dismiss),
            getChip(resources.getString(R.string.city_rome)),
            getChip(resources.getString(R.string.city_dubai), icon = BpkIcon.Heart),
            getChip(resources.getString(R.string.city_long_name)),
            getChip(resources.getString(R.string.city_rio), type = BpkChipType.Dismiss),
            getChip(resources.getString(R.string.city_cairo), icon = BpkIcon.Beach),

        )
    }

    private fun getChip(
        text: String,
        icon: BpkIcon? = null,
        selected: Boolean = false,
        type: BpkChipType = BpkChipType.Selectable,
    ) = BpkMultiChipItem(text = text, type = type, icon = icon, selected = selected) {
        if (type == BpkChipType.Dismiss) scope.launch {
            _uiState.value = _uiState.value.filter { it.text != text }
        }
        else toggleSelected(text)
    }

    private fun toggleSelected(city: String) {
        scope.launch {
            _uiState.value = _uiState.value.map { chip ->
                if (chip.text == city) {
                    BpkMultiChipItem(chip.text, chip.icon, chip.type, !chip.selected) { toggleSelected(chip.text) }
                } else chip
            }
        }
    }
}
