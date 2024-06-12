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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicator
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicatorStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.PageIndicatorComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState

@OptIn(ExperimentalFoundationApi::class)
@Composable
@PageIndicatorComponent
@ComposeStory
fun PageIndicatorStory(modifier: Modifier = Modifier) {

    val pagerState1 = rememberPagerState(pageCount = { 3 })
    val pagerState2 = rememberPagerState(pageCount = { 8 })
    val pagerState3 = rememberPagerState(pageCount = { 8 })
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                BpkSpacing.Base,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        BpkText(text = stringResource(id = R.string.page_indicator_less_than_5))
        PageIndicatorSample(style = BpkPageIndicatorStyle.Default, state = pagerState1)

        BpkText(text = stringResource(id = R.string.page_indicator_more_than_5))
        PageIndicatorSample(style = BpkPageIndicatorStyle.Default, state = pagerState2)

        BpkText(text = stringResource(id = R.string.page_indicator_over_image))
        PageIndicatorSample(
            state = pagerState3,
            style = BpkPageIndicatorStyle.OverImage,
        ) {
            Image(
                painter = painterResource(id = R.drawable.canadian_rockies_canada),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PageIndicatorSample(
    style: BpkPageIndicatorStyle,
    state: PagerState,
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.(Int) -> Unit) = {},
) {
    Box(
        modifier = modifier
            .aspectRatio(1.9f)
            .padding(vertical = BpkSpacing.Base),
        contentAlignment = Alignment.BottomCenter,
    ) {
        HorizontalPager(
            modifier = Modifier
                .testTag("pager")
                .fillMaxSize(),
            state = state,
        ) {
            content(state.currentPage)
        }
        BpkPageIndicator(currentIndex = state.currentPage, totalIndicators = state.pageCount, style = style, modifier =
            Modifier.align(Alignment.BottomCenter)
                .testTag("pageIndicator"))
    }
}
