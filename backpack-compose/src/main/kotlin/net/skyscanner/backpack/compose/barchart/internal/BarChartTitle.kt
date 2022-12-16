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

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.barchart.BpkBarChartModel
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.utils.ScrollingDirection
import net.skyscanner.backpack.compose.utils.lastScrollingDirection

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun BarChartTitle(
  model: BpkBarChartModel,
  state: LazyListState,
  modifier: Modifier = Modifier,
) {

  val title by remember { derivedStateOf { model.items[state.firstVisibleItemIndex].group } }
  val scrollingDirection = state.lastScrollingDirection()

  AnimatedContent(
    targetState = title,
    modifier = modifier,
    content = {
      BpkText(
        text = it,
        maxLines = 1,
        style = BpkTheme.typography.heading4,
        color = BpkTheme.colors.textPrimary,
      )
    },
    transitionSpec = {
      when (scrollingDirection) {
        ScrollingDirection.Forward ->
          ContentTransform(
            targetContentEnter = fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.Start),
            initialContentExit = fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.Start),
          )

        ScrollingDirection.Backward ->
          ContentTransform(
            targetContentEnter = fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.End),
            initialContentExit = fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.End),
          )
      }
    },
  )
}
