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

package net.skyscanner.backpack.compose.barchart.internal

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.barchart.BpkBarChartModel
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BarChartList(
    model: BpkBarChartModel,
    selected: BpkBarChartModel.Item?,
    onSelected: (BpkBarChartModel.Item) -> Unit,
    state: LazyListState,
    modifier: Modifier = Modifier,
) {

    var rootLayoutCoordinates: LayoutCoordinates? = null
    var badgeAnchor by remember { mutableStateOf(Offset.Unspecified) }

    Box(modifier = modifier.onGloballyPositioned { rootLayoutCoordinates = it }) {

        LazyRow(
            state = state,
            modifier = Modifier.drawSelectionLine(
                color = BpkTheme.colors.coreAccent,
                y = if (badgeAnchor.isSpecified) animateFloatAsState(badgeAnchor.y).value else 0f,
                alpha = animateFloatAsState(if (selected != null) 1f else 0f).value,
            ),
            contentPadding = PaddingValues(horizontal = BpkSpacing.Base),
        ) {
            items(
                items = model.items,
                key = { it.key },
            ) { item ->
                BarChartColumn(
                    modifier = Modifier.requiredSize(ItemWidth, ItemHeight),
                    model = item,
                    selected = item == selected,
                    onSelected = onSelected,
                    onSelectedAndPositioned = { bar ->
                        rootLayoutCoordinates?.let { root ->
                            badgeAnchor = root.localPositionOf(bar, Offset(x = bar.size.width / 2f, y = 0f))
                        }
                    },
                )
            }
        }

        if (badgeAnchor.isSpecified && selected != null) {
            BarChartBadge(
                model = model,
                state = state,
                selected = selected,
                anchor = badgeAnchor,
                // we align badge by absolute coordinates, so no auto-RTL is needed
                modifier = Modifier.align { size, space, _ -> Alignment.TopStart.align(size, space, LayoutDirection.Ltr) },
            )
        }
    }
}

private fun Modifier.drawSelectionLine(
    color: Color,
    y: Float,
    alpha: Float,
    strokeWidth: Dp = 1.dp,
): Modifier =
    drawWithContent {
        drawContent()
        drawLine(color.copy(alpha = alpha), start = Offset(0f, y), end = Offset(size.width, y), strokeWidth.toPx())
    }

private val ItemWidth = 32.dp
private val ItemHeight = 168.dp
