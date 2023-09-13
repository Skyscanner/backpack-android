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

package net.skyscanner.backpack.compose.carousel

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable

sealed interface BpkCarouselState : ScrollableState {

    val interactionSource: InteractionSource

    @get:IntRange(from = 0)
    val pageCount: Int

    @get:IntRange(from = 0)
    val currentPage: Int

    val currentPageOffsetFraction: Float

    suspend fun animateScrollToPage(
        @IntRange(from = 0) page: Int,
        @FloatRange(from = -1.0, to = 1.0) pageOffset: Float = 0f,
    )

    suspend fun scrollToPage(
        @IntRange(from = 0) page: Int,
        @FloatRange(from = -1.0, to = 1.0) pageOffset: Float = 0f,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun rememberBpkCarouselState(
    totalImages: Int,
    initialImage: Int = 0,
): BpkCarouselState {
    val initialPage = (Int.MAX_VALUE / 2) + initialImage

    val pagerState = rememberSaveable(saver = PagerStateImpl.Saver) {
        PagerStateImpl(
            initialPage,
            totalImages,
        )
    }

    return remember(pagerState, totalImages) {
        BpkCarouselInternalState(delegate = pagerState, totalImages = totalImages)
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun BpkCarouselState(
    totalImages: Int,
    initialImage: Int = 0,
): BpkCarouselState {
    val initialPage = (Int.MAX_VALUE / 2) + initialImage
    return BpkCarouselInternalState(
        delegate = PagerStateImpl(initialPage = initialPage, totalPages = totalImages),
        totalImages = totalImages,
    )
}

internal fun BpkCarouselState.asInternalState(): BpkCarouselInternalState =
    when (this) {
        is BpkCarouselInternalState -> this
    }

@OptIn(ExperimentalFoundationApi::class)
internal class BpkCarouselInternalState(
    val delegate: PagerState,
    val totalImages: Int,
) : BpkCarouselState, ScrollableState by delegate {

    override val interactionSource: InteractionSource
        get() = delegate.interactionSource

    override val pageCount: Int
        get() = totalImages

    override val currentPage: Int
        get() = getModdedPageNumber(delegate.currentPage, totalImages)

    override val currentPageOffsetFraction: Float
        get() = delegate.currentPageOffsetFraction

    override suspend fun animateScrollToPage(page: Int, pageOffset: Float) =
        delegate.animateScrollToPage(getTargetPage(page), pageOffset)

    override suspend fun scrollToPage(page: Int, pageOffset: Float) =
        delegate.scrollToPage(getTargetPage(page), pageOffset)

    private fun getTargetPage(page: Int): Int {
        return delegate.currentPage - (currentPage - page)
    }

    fun getModdedPageNumber(index: Int, count: Int) = (index - (Int.MAX_VALUE / 2)).floorMod(count)

    private fun Int.floorMod(other: Int): Int = when (other) {
        0 -> this
        else -> this - floorDiv(other) * other
    }
}

// We cannot create PagerState with new pageCount parameter without remember functions so we temporarily fork it
@OptIn(ExperimentalFoundationApi::class)
private class PagerStateImpl(
    initialPage: Int,
    private val totalPages: Int,
) : PagerState(initialPage, initialPageOffsetFraction = 0f) {

    override val pageCount: Int
        get() = if (totalPages > 1) Int.MAX_VALUE else 1 // if count > 1, set to Int.MAX_VALUE for infinite looping

    companion object {
        val Saver: Saver<PagerStateImpl, *> = listSaver(
            save = {
                listOf(
                    it.currentPage,
                    it.totalPages,
                )
            },
            restore = {
                PagerStateImpl(
                    initialPage = it[0],
                    totalPages = it[1],
                )
            },
        )
    }
}
