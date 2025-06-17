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

package net.skyscanner.backpack.compose.rating.internal

import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import net.skyscanner.backpack.compose.LocalContentColor
import net.skyscanner.backpack.compose.LocalTextStyle
import net.skyscanner.backpack.compose.rating.BpkRatingScale
import net.skyscanner.backpack.compose.rating.BpkRatingSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BpkRatingNumbers(
    value: Float,
    scale: BpkRatingScale,
    size: BpkRatingSize,
    showScale: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {

        val numberFormat = remember(LocalConfiguration.current.locales) { DecimalFormat("#0.0") }

        BpkText(
            modifier = Modifier.alignByBaseline(),
            text = formatValue(value, scale, numberFormat),
            style = when (size) {
                BpkRatingSize.Base -> BpkTheme.typography.label1
                BpkRatingSize.Large -> BpkTheme.typography.hero5
            },
            color = BpkTheme.colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        if (showScale) {
            BpkText(
                modifier = Modifier.alignByBaseline(),
                text = "/${scale.range.endInclusive.toInt()}",
                style = when (size) {
                    BpkRatingSize.Base -> BpkTheme.typography.caption
                    BpkRatingSize.Large -> BpkTheme.typography.bodyDefault
                },
                color = BpkTheme.colors.textSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
internal fun BpkRatingTitle(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalTextStyle provides BpkTheme.typography.heading5,
        LocalContentColor provides BpkTheme.colors.textPrimary,
    ) {
        val density = LocalDensity.current
        Layout(
            content = content,
            modifier = modifier.heightIn(max = BpkSpacing.Lg),
        ) { measurables, constraints ->
            if (measurables.isEmpty()) {
                layout(0, 0) {}
            } else {
                val placeable = measurables.first().measure(constraints)
                val maxHeightPx = with(density) { BpkSpacing.Lg.roundToPx() }
                val height = minOf(placeable.height, maxHeightPx)
                val firstBaseline = if (placeable[FirstBaseline] != AlignmentLine.Unspecified) {
                    placeable[FirstBaseline] + (height - placeable.height) / 2
                } else {
                    (height * 0.9f).toInt()
                }
                val lastBaseline = if (placeable[LastBaseline] != AlignmentLine.Unspecified) {
                    placeable[LastBaseline] + (height - placeable.height) / 2
                } else {
                    firstBaseline
                }

                layout(
                    width = placeable.width,
                    height = height,
                    alignmentLines = mapOf(
                        FirstBaseline to firstBaseline,
                        LastBaseline to lastBaseline,
                    ),
                ) {
                    placeable.place(0, (height - placeable.height) / 2)
                }
            }
        }
    }
}

@Composable
internal fun BpkRatingSubtitle(
    subtitle: String,
    size: BpkRatingSize,
    modifier: Modifier = Modifier,
) {
    BpkText(
        modifier = modifier,
        text = subtitle,
        style = when (size) {
            BpkRatingSize.Base -> BpkTheme.typography.caption
            BpkRatingSize.Large -> BpkTheme.typography.bodyDefault
        },
        color = BpkTheme.colors.textSecondary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

private fun formatValue(value: Float, scale: BpkRatingScale, format: DecimalFormat): String {
    val coerced = value.coerceIn(scale.range)
    val rounded = (coerced * 10).toInt() / 10f // rounding to one decimal
    return format.format(rounded)
}

private val BpkRatingScale.range: ClosedFloatingPointRange<Float>
    get() = when (this) {
        BpkRatingScale.ZeroToFive -> 0f..5f
        BpkRatingScale.ZeroToTen -> 0f..10f
    }
