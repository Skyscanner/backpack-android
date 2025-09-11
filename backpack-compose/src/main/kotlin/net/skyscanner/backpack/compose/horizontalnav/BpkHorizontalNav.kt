/*
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.horizontalnav

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.LocalContentColor
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

enum class BpkHorizontalNavSize {
    Default,
    Small,
}

data class BpkHorizontalNavTab(
    val title: String,
    val icon: BpkIcon? = null,
)

@Composable
fun BpkHorizontalNav(
    tabs: List<BpkHorizontalNavTab>,
    activeIndex: Int,
    onChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
    size: BpkHorizontalNavSize = BpkHorizontalNavSize.Default,
) {
    TabRow(
        selectedTabIndex = activeIndex,
        tabs = tabs,
        onChanged = onChanged,
        modifier = modifier.height(
            when (size) {
                BpkHorizontalNavSize.Default -> 48.dp
                BpkHorizontalNavSize.Small -> 36.dp
            },
        ),
    ) { tab ->
        if (tab.icon != null) {
            BpkIcon(
                icon = tab.icon,
                contentDescription = null,
            )
        }
        BpkText(
            text = tab.title,
            style = when (size) {
                BpkHorizontalNavSize.Default -> BpkTheme.typography.label1
                BpkHorizontalNavSize.Small -> BpkTheme.typography.label2
            },
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Composable
@UiComposable
private fun TabRow(
    selectedTabIndex: Int,
    onChanged: (Int) -> Unit,
    tabs: List<BpkHorizontalNavTab>,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.(BpkHorizontalNavTab) -> Unit,
) {
    Surface(
        modifier = modifier.selectableGroup(),
        color = BpkTheme.colors.surfaceDefault,
        contentColor = BpkTheme.colors.textLink,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawDivider(
                    dividerColor = if (isSystemInDarkTheme()) Color.Transparent else BpkTheme.colors.line,
                    dividerThickness = 1.dp,
                )
                .drawIndicator(
                    indicatorColor = BpkTheme.colors.textLink,
                    indicatorHeight = 2.dp,
                    tabsCount = tabs.size,
                    indicatorOffset = animateFloatAsState(
                        targetValue = selectedTabIndex.toFloat(),
                        animationSpec = tween(durationMillis = IndicatorAnimationDuration, easing = FastOutSlowInEasing),
                        label = "HorizontalNav indicator offset",
                    ).value,
                ),
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = index == selectedTabIndex,
                    modifier = Modifier.weight(1f),
                    onClick = { onChanged(index) },
                ) {
                    content(tab)
                }
            }
        }
    }
}

private fun Modifier.drawDivider(
    dividerColor: Color,
    dividerThickness: Dp,
): Modifier =
    drawBehind {
        val dividerThicknessPx = dividerThickness.toPx()
        drawRect(
            color = dividerColor,
            topLeft = Offset(0f, size.height - dividerThicknessPx),
            size = Size(size.width, dividerThicknessPx),
        )
    }

private fun Modifier.drawIndicator(
    indicatorColor: Color,
    indicatorHeight: Dp,
    tabsCount: Int,
    indicatorOffset: Float,
): Modifier =
    drawBehind {
        val tabWidth = size.width / tabsCount
        val left = tabWidth * indicatorOffset
        val indicatorHeightPx = indicatorHeight.toPx()
        scale(scaleX = if (layoutDirection == LayoutDirection.Rtl) -1f else 1f, scaleY = 1f) {
            drawRect(
                color = indicatorColor,
                topLeft = Offset(left, size.height - indicatorHeightPx),
                size = Size(tabWidth, indicatorHeightPx),
            )
        }
    }

@Composable
private fun Tab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) {
    val ripple = ripple(bounded = true, color = BpkTheme.colors.textLink)
    val transition = updateTransition(targetState = selected, label = "HorizontalNav.Tab transition")
    val color by transition.animateColor(
        transitionSpec = {
            if (false isTransitioningTo true) {
                tween(
                    durationMillis = TabFadeInAnimationDuration,
                    delayMillis = TabFadeInAnimationDelay,
                    easing = LinearEasing,
                )
            } else {
                tween(
                    durationMillis = TabFadeOutAnimationDuration,
                    easing = LinearEasing,
                )
            }
        },
        targetValueByState = {
            if (it) BpkTheme.colors.textLink else BpkTheme.colors.textPrimary
        },
        label = "HorizontalNav.Tab color",
    )
    CompositionLocalProvider(
        LocalContentColor provides color,
    ) {
        Row(
            modifier = modifier
                .selectable(
                    selected = selected,
                    onClick = onClick,
                    enabled = enabled,
                    role = Role.Tab,
                    interactionSource = interactionSource,
                    indication = ripple,
                )
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }
}

// Tab transition specifications
private const val TabFadeInAnimationDuration = 150
private const val IndicatorAnimationDuration = 250
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100
