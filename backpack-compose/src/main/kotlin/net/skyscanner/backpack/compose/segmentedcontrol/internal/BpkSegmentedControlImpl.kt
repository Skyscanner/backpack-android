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

package net.skyscanner.backpack.compose.segmentedcontrol.internal

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.segmentedcontrol.BpkSegmentedControlStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.theme.bpkRipple
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkElevation
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.internal.BpkSegmentedControlColors

private const val BpkAnimationDurationMs = 50
private val BpkSegmentedControlHeight = 32.dp
private val ButtonShape = RoundedCornerShape(BpkBorderRadius.Sm)

@Composable
internal fun BpkSegmentedControlImpl(
    buttonContents: List<String>,
    onItemClick: (Int) -> Unit,
    selectedIndex: Int,
    shadow: Boolean,
    type: BpkSegmentedControlStyle,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .applyShadow(shadow)
            .clip(ButtonShape)
            .height(BpkSegmentedControlHeight)
            .fillMaxWidth()
            .background(type.toColor())
            .selectableGroup(),
    ) {
        buttonContents.forEachIndexed { index, content ->
            if (index > 0) {
                VerticalDivider(
                    color = getDividerColor(index, selectedIndex),
                )
            }
            BpkSegmentedControlButton(
                modifier = Modifier.weight(1f),
                isSelected = selectedIndex == index,
                type = type,
                content = content,
                onItemClick = { onItemClick(index) },
            )
        }
    }
}

@Composable
private fun BpkSegmentedControlButton(
    isSelected: Boolean,
    type: BpkSegmentedControlStyle,
    content: String,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(getButtonColor(isSelected, type))
            .padding(horizontal = BpkSpacing.Md)
            .selectable(
                selected = isSelected,
                role = Role.RadioButton,
                interactionSource = remember { MutableInteractionSource() },
                indication = bpkRipple(),
            ) { onItemClick() },
    ) {

        BpkText(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            text = content,
            textAlign = TextAlign.Center,
            color = getTextColor(type, isSelected),
            style = BpkTheme.typography.footnote,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

private fun Modifier.applyShadow(
    shadow: Boolean,
): Modifier {
    return if (shadow)
        this.shadow(
            elevation = BpkElevation.Sm,
            shape = ButtonShape,
        )
    else this
}

@Composable
internal fun getTextColor(
    style: BpkSegmentedControlStyle,
    selected: Boolean,
): Color {

    return animateColorAsState(
        targetValue = when {
            style == BpkSegmentedControlStyle.SurfaceContrast || selected -> BpkTheme.colors.textOnDark
            else -> BpkTheme.colors.textPrimary
        },
        animationSpec = tween(
            durationMillis = BpkAnimationDurationMs,
            easing = FastOutSlowInEasing,
        ),
    ).value
}

@Composable
internal fun getDividerColor(currentIndex: Int, selectedIndex: Int): Color {
    return animateColorAsState(
        targetValue = if (shouldHideDivider(currentIndex, selectedIndex)) {
            Color.Transparent
        } else BpkTheme.colors.line,
        animationSpec = tween(
            durationMillis = BpkAnimationDurationMs,
            easing = FastOutSlowInEasing,
        ),
    ).value
}

private fun shouldHideDivider(currentIndex: Int, selectedIndex: Int): Boolean {
    return selectedIndex == currentIndex || selectedIndex == currentIndex - 1
}

@Composable
internal fun getButtonColor(
    selected: Boolean,
    style: BpkSegmentedControlStyle,
): Color {
    return animateColorAsState(
        targetValue = if (!selected) Color.Transparent else
            when (style) {
                BpkSegmentedControlStyle.SurfaceContrast -> BpkSegmentedControlColors.surfaceContrastOn
                else -> BpkTheme.colors.corePrimary
            },
        animationSpec = tween(
            durationMillis = BpkAnimationDurationMs,
            easing = FastOutSlowInEasing,
        ),
    ).value
}

@Composable
private fun BpkSegmentedControlStyle.toColor(): Color {
    return when (this) {
        BpkSegmentedControlStyle.CanvasDefault -> BpkSegmentedControlColors.canvasDefault
        BpkSegmentedControlStyle.CanvasContrast -> BpkTheme.colors.surfaceDefault
        BpkSegmentedControlStyle.SurfaceDefault -> BpkTheme.colors.canvasContrast
        BpkSegmentedControlStyle.SurfaceContrast -> BpkSegmentedControlColors.surfaceContrast
    }
}
