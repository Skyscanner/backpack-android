/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at./gradlew publishToMavenLocal -Pversion=x.x.x-SNAPSHOT
 *
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.compose.cardcarousel

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.card.BpkCardPadding
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicator
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicatorStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import kotlin.math.absoluteValue
import kotlin.math.max

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BpkCardCarousel(
    cards: List<@Composable BoxScope.() -> Unit>,
    modifier: Modifier = Modifier,
    currentCard: Int = 0,
    onCardChange: ((Int) -> Unit)? = null,
) {
    val totalCard by remember { mutableIntStateOf(cards.size) }
    val pagerState = rememberPagerState(pageCount = { getModdedPageCount(totalCard) })

    LaunchedEffect(pagerState) {
        pagerState.scrollToPage(getModdedCurrentPageNumber(currentCard, totalCard))
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onCardChange?.invoke(page % totalCard)
        }
    }

    BoxWithConstraints(modifier = modifier) {
        val scope = this
        val itemWidth = scope.maxWidth.value * CARD_WIDTH_PERCENTAGE
        val contentPadding = max(0f, (scope.maxWidth.value - itemWidth) / 2)

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalPager(
                modifier = Modifier.testTag("pager").weight(1f),
                state = pagerState,
                contentPadding = PaddingValues(horizontal = contentPadding.dp),
            ) { page ->
                Box(modifier = Modifier
                    .width(itemWidth.dp)
                    .graphicsLayer {
                        val pageOffset =
                            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                        with(lerp(UNFOCUSED_CARD_SCALE, 1f, 1f - pageOffset.coerceIn(0f, 1f))) {
                            scaleY = this
                            scaleX = this
                        }
                    }) {
                    cards[page % totalCard]()
                }
            }
            if (totalCard > 1) {
                BpkPageIndicator(
                    modifier = Modifier.testTag("pageIndicator"),
                    totalIndicators = totalCard,
                    currentIndex = pagerState.currentPage % totalCard,
                    style = BpkPageIndicatorStyle.Default,
                )
            }
        }
    }
}

@Composable
fun BpkCarouselCard(
    imageAccessibilityLabel: String,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    image: @Composable (BoxScope.() -> Unit),
) {
    BpkCard(
        corner = BpkCardCorner.Small,
        padding = BpkCardPadding.None,
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = imageAccessibilityLabel },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Box { image() }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = BpkSpacing.Base, start = BpkSpacing.Lg, end = BpkSpacing.Lg, bottom = BpkSpacing.Lg,
                    ),
            ) {
                BpkText(text = title, style = BpkTheme.typography.heading3)
                BpkText(text = description, style = BpkTheme.typography.bodyDefault)
            }
        }
    }
}

private fun getModdedPageCount(count: Int) = if (count > 1) count * 100 else 1
private fun getModdedCurrentPageNumber(index: Int, count: Int) = if (count > 1) (count * 100) / 2 + index else index

private const val UNFOCUSED_CARD_SCALE = 0.85f
private const val CARD_WIDTH_PERCENTAGE = 0.7f
