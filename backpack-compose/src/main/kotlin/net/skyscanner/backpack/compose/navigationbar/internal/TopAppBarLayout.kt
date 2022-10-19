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

package net.skyscanner.backpack.compose.navigationbar.internal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp

private enum class TopAppBarLayoutId {
  Nav, Title, Actions,
}

@Composable
internal fun TopAppBarLayout(
  fraction: Float,
  navIcon: @Composable BoxScope.() -> Unit,
  title: @Composable BoxScope.() -> Unit,
  actions: @Composable RowScope.() -> Unit,
  modifier: Modifier = Modifier,
) {
  Layout(
    measurePolicy = TopNavBarMeasuringPolicy(fraction),
    modifier = modifier
      .requiredHeight(lerp(TopNavBarTokens.CollapsedHeight, TopNavBarTokens.ExpandedHeight, fraction)),
    content = {
      Box(
        content = navIcon,
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .layoutId(TopAppBarLayoutId.Nav)
          .padding(vertical = TopNavBarTokens.InternalSpacing)
          .padding(start = TopNavBarTokens.InternalSpacing),
      )
      Box(
        content = title,
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .layoutId(TopAppBarLayoutId.Title)
          .padding(all = TopNavBarTokens.InternalSpacing),
      )
      Row(
        content = actions,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .layoutId(TopAppBarLayoutId.Actions)
          .padding(vertical = TopNavBarTokens.InternalSpacing)
          .padding(end = TopNavBarTokens.InternalSpacing),
      )
    },
  )
}

private fun TopNavBarMeasuringPolicy(fraction: Float): MeasurePolicy =
  MeasurePolicy { measurables, constraints ->

    val navIcon = measurables.first { it.layoutId == TopAppBarLayoutId.Nav }.measure(iconConstrains())
    val actions = measurables.first { it.layoutId == TopAppBarLayoutId.Actions }.measure(iconConstrains())

    val title = measurables.first { it.layoutId == TopAppBarLayoutId.Title }.measure(
      constraints = constraints.copy(
        minWidth = 0,
        maxWidth = constraints.maxWidth - titlePaddingStart(navIcon, fraction) - titlePaddingEnd(actions, fraction),
      ),
    )

    layout(constraints.maxWidth, constraints.maxHeight) {
      navIcon.placeRelative(0, 0)
      actions.placeRelative(constraints.maxWidth - actions.measuredWidth, 0)
      title.placeRelative(titlePaddingStart(navIcon, fraction), 0)
    }
  }

private fun Density.titlePaddingStart(navIcon: Placeable, fraction: Float) : Int {
  val isNavIconSet = navIcon.measuredWidth >= TopNavBarTokens.InternalSpacing.toPx()
  val extraPadding = when {
      isNavIconSet -> TopNavBarTokens.ExpandedTitlePaddingStartWithNavIcon
      else -> TopNavBarTokens.ExpandedTitlePaddingStartWithoutNavIcon
  }.roundToPx()

  return lerp(navIcon.measuredWidth, navIcon.measuredWidth + extraPadding, fraction)
}

private fun Density.titlePaddingEnd(actions: Placeable, fraction: Float) : Int =
  lerp(actions.measuredWidth, TopNavBarTokens.ExpandedTitlePaddingEnd.roundToPx(), fraction)

private fun Density.iconConstrains(): Constraints =
  Constraints.fixedHeight(TopNavBarTokens.CollapsedHeight.roundToPx())
