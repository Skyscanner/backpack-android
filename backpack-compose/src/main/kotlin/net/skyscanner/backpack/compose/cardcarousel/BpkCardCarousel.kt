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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.card.BpkCardPadding
import net.skyscanner.backpack.compose.carousel.BpkCarouselState
import net.skyscanner.backpack.compose.carousel.asInternalState
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
    state: BpkCarouselState,
    cards: List<BpkCardCarouselItem>,
    modifier: Modifier = Modifier,
) {
    val internalState = state.asInternalState()
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
                modifier = Modifier
                    .testTag("pager")
                    .weight(1f),
                state = internalState.delegate,
                contentPadding = PaddingValues(horizontal = contentPadding.dp),
            ) { page ->
                Box(modifier = Modifier
                    .width(itemWidth.dp)
                    .graphicsLayer {
                        with(internalState.delegate) {
                            val pageOffset = (this.currentPage - page + this.currentPageOffsetFraction).absoluteValue
                            with(lerp(UNFOCUSED_CARD_SCALE, 1f, 1f - pageOffset.coerceIn(0f, 1f))) {
                                scaleY = this
                                scaleX = this
                            }
                        }
                    }) {

                    with(cards[internalState.getModdedPageNumber(page, internalState.pageCount)]) {
                        val content = this.imageContent
                        BpkCarouselCard(
                            title = this.title,
                            description = this.description,
                            image = {
                                content(contentDescription)
                            },
                        )
                    }
                }
            }
            if (internalState.delegate.pageCount > 1) {
                BpkPageIndicator(
                    modifier = Modifier.testTag("pageIndicator"),
                    totalIndicators = internalState.pageCount,
                    currentIndex = internalState.getModdedPageNumber(internalState.currentPage, internalState.pageCount),
                    style = BpkPageIndicatorStyle.Default,
                )
            }
        }
    }
}

@Composable
internal fun BpkCarouselCard(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    image: @Composable (BoxScope.() -> Unit),
) {
    BpkCard(
        corner = BpkCardCorner.Small,
        padding = BpkCardPadding.None,
        modifier = modifier.fillMaxWidth(),
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
                description?.let {
                    BpkText(text = it, style = BpkTheme.typography.bodyDefault)
                }
            }
        }
    }
}

private const val UNFOCUSED_CARD_SCALE = 0.85f
private const val CARD_WIDTH_PERCENTAGE = 0.7f

data class BpkCardCarouselItem(
    val title: String,
    val description: String? = null,
    val contentDescription: String? = null,
    val imageContent: @Composable (contentDescription: String?) -> Unit,
)
