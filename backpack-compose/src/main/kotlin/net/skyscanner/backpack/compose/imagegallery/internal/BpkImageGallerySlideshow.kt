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

package net.skyscanner.backpack.compose.imagegallery.internal

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import kotlinx.coroutines.flow.distinctUntilChanged
import net.skyscanner.backpack.compose.badge.BpkBadge
import net.skyscanner.backpack.compose.badge.BpkBadgeType
import net.skyscanner.backpack.compose.carousel.BpkCarousel
import net.skyscanner.backpack.compose.carousel.BpkCarouselState
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryImage
import net.skyscanner.backpack.compose.pageindicator.BpkPageIndicator
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Camera
import net.skyscanner.backpack.compose.utils.invisibleSemantic

@Composable
internal fun BpkImageGallerySlideshow(
    images: List<BpkImageGalleryImage>,
    initialImage: Int,
    modifier: Modifier = Modifier,
    onImageChanged: ((Int) -> Unit)? = null,
) {
    val pagerState = rememberBpkCarouselState(totalImages = images.size, initialImage = initialImage)
    if (onImageChanged != null) {
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.distinctUntilChanged().collect { onImageChanged(it) }
        }
    }
    val current = images[pagerState.currentPage]

    val configuration = LocalConfiguration.current
    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Row(
            modifier = modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            ImageCarousel(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f),
                pagerState = pagerState,
                image = current,
                overlayContent = {
                    CarouselBadge(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        pagerState = pagerState,
                    )

                    PageIndicator(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(BpkSpacing.Base),
                        pagerState = pagerState,
                    )
                },
            )
            ImageDescription(
                image = current,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
            )
        }
    } else {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
        ) {
            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                ImageCarousel(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    pagerState = pagerState,
                    image = current,
                    overlayContent = {
                        CarouselBadge(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            pagerState = pagerState,
                        )
                    },
                )

                PageIndicator(
                    modifier = Modifier.padding(top = BpkSpacing.Base),
                    pagerState = pagerState,
                )
            }
            ImageDescription(current)
        }
    }
}

@Composable
private fun ImageCarousel(
    pagerState: BpkCarouselState,
    image: BpkImageGalleryImage,
    modifier: Modifier = Modifier,
    overlayContent: @Composable (BoxScope.((@Composable () -> Unit)?) -> Unit),
) {
    BpkCarousel(
        modifier = modifier,
        state = pagerState,
        overlayContent = overlayContent,
    ) {
        image.content("${image.title}. ${image.description}. ${image.credit}")
    }
}

@Composable
private fun CarouselBadge(
    pagerState: BpkCarouselState,
    modifier: Modifier = Modifier,
) {
    BpkBadge(
        modifier = modifier
            .invisibleSemantic()
            .padding(bottom = BpkSpacing.Base),
        text = "${pagerState.currentPage + 1}/${pagerState.pageCount}",
        type = BpkBadgeType.Strong,
    )
}

@Composable
private fun PageIndicator(
    pagerState: BpkCarouselState,
    modifier: Modifier = Modifier,
) {
    if (pagerState.pageCount > 1) {
        BpkPageIndicator(
            modifier = modifier
                .testTag("pageIndicator"),
            totalIndicators = pagerState.pageCount,
            currentIndex = pagerState.currentPage,
        )
    }
}

@Composable
private fun ImageDescription(image: BpkImageGalleryImage, modifier: Modifier = Modifier) {
    Crossfade(targetState = image, label = "Text crossfade", modifier = modifier.padding(BpkSpacing.Lg)) { image ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BpkText(
                modifier = Modifier
                    .invisibleSemantic()
                    .padding(bottom = BpkSpacing.Md),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(image.title)
                    }
                    image.description?.let {
                        append(". ")
                        append(it)
                    }
                },
                style = BpkTheme.typography.caption,
            )

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
            ) {
                image.credit?.let {
                    BpkIcon(icon = BpkIcon.Camera, contentDescription = null)
                    BpkText(
                        modifier = Modifier.invisibleSemantic(),
                        text = it,
                        style = BpkTheme.typography.caption,
                    )
                }
            }
        }
    }
}
