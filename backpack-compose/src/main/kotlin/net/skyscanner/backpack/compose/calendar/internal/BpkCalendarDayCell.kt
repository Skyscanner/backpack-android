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

package net.skyscanner.backpack.compose.calendar.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.calendar2.CellLabel
import net.skyscanner.backpack.calendar2.CellStatus
import net.skyscanner.backpack.calendar2.CellStatusStyle
import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.calendar2.data.CalendarCell.Selection
import net.skyscanner.backpack.compose.LocalContentColor
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.findBySmall
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.RelativeRectangleShape

@Composable
internal fun BpkCalendarDayCell(
    model: CalendarCell.Day,
    onClick: (CalendarCell.Day) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selection = model.selection
    val inactive = model.inactive

    val status = model.info.status
    val style = model.info.style
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(bottom = BpkSpacing.Lg)
            .clickable(
                indication = null,
                enabled = !inactive,
                onClick = { onClick(model) },
                onClickLabel = model.onClickLabel,
                interactionSource = remember { MutableInteractionSource() },
            )
            .semantics {
                if (model.stateDescription != null) {
                    stateDescription = model.stateDescription!!
                } else {
                    selected = selection != null && selection != Selection.Middle
                }
            },
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .cellSelectionBackground(
                    surfaceSubtle = BpkTheme.colors.surfaceSubtle,
                    selection = selection,
                ),
        ) {

            Spacer(
                Modifier
                    .size(BpkCalendarSizes.SelectionHeight)
                    .cellDayBackground(
                        coreAccent = BpkTheme.colors.coreAccent,
                        surfaceSubtle = BpkTheme.colors.surfaceSubtle,
                        selection = selection,
                    ),
            )

            BpkText(
                text = model.text.toString(),
                modifier = Modifier.semantics {
                    contentDescription = model.contentDescription
                },
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = BpkTheme.typography.label1,
                color = dateColor(selection, status, inactive, style),
            )
        }

        if (!inactive) {
            when (val cellLabel = model.info.label) {
                is CellLabel.Text -> {
                    if (cellLabel.text.isNotBlank()) {
                        BpkText(
                            text = cellLabel.text,
                            modifier = Modifier
                                .padding(horizontal = BpkSpacing.Sm),
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            style = BpkTheme.typography.caption,
                            color = labelColor(status, style),
                        )
                    }
                }

                is CellLabel.Icon -> {
                    cellLabel.resId.let { resId ->
                        BpkIcon.findBySmall(resId)?.let { bpkIcon ->
                            val iconTint = cellLabel.tint
                                ?.let { colorRes -> ContextCompat.getColor(LocalContext.current, colorRes) }
                                ?.let { Color(it) } ?: LocalContentColor.current
                            BpkIcon(
                                icon = bpkIcon,
                                tint = iconTint,
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun Modifier.cellSelectionBackground(
    surfaceSubtle: Color,
    selection: Selection?,
): Modifier =
    when (selection) {
        Selection.Start,
        Selection.StartMonth,
        -> background(surfaceSubtle, EndSemiRect)

        Selection.End,
        Selection.EndMonth,
        -> background(surfaceSubtle, StartSemiRect)

        Selection.Middle -> background(surfaceSubtle, RectangleShape)

        Selection.Single,
        Selection.Double,
        null,
        -> this
    }

private fun Modifier.cellDayBackground(
    coreAccent: Color,
    surfaceSubtle: Color,
    selection: Selection?,
): Modifier =
    when {
        selection != null ->
            when (selection) {
                Selection.Double -> this
                    .border(1.dp, coreAccent, CircleShape)
                    .padding(3.dp)
                    .background(coreAccent, CircleShape)

                Selection.StartMonth,
                Selection.Middle,
                Selection.EndMonth,
                -> background(surfaceSubtle, CircleShape)

                Selection.Single,
                Selection.Start,
                Selection.End,
                -> background(coreAccent, CircleShape)
            }

        else -> this
    }

@Composable
private fun dateColor(
    selection: Selection?,
    status: CellStatus?,
    inactive: Boolean,
    style: CellStatusStyle?,
): Color =
    when {
        selection != null ->
            when (selection) {
                Selection.Single,
                Selection.Double,
                Selection.Start,
                Selection.End,
                -> BpkTheme.colors.textPrimaryInverse

                Selection.StartMonth,
                Selection.Middle,
                Selection.EndMonth,
                -> BpkTheme.colors.textPrimary
            }

        inactive -> BpkTheme.colors.textDisabled

        else -> BpkTheme.colors.textPrimary
    }

@Composable
private fun labelColor(status: CellStatus?, style: CellStatusStyle?): Color =
    when {

        style == CellStatusStyle.Label && status != null ->
            when (status) {
                CellStatus.Positive -> BpkTheme.colors.statusSuccessSpot
                CellStatus.Neutral -> BpkTheme.colors.textSecondary
                CellStatus.Negative -> BpkTheme.colors.textSecondary
                CellStatus.Empty -> BpkTheme.colors.textDisabled
            }

        else -> BpkTheme.colors.textSecondary
    }

private val StartSemiRect = RelativeRectangleShape(0f..0.5f)
private val EndSemiRect = RelativeRectangleShape(0.5f..1f)
