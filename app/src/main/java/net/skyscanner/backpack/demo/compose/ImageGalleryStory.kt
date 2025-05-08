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

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryChipCategory
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryChipGrid
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryImage
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryImageCategory
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryImageGrid
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryPreviewDefault
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryPreviewHero
import net.skyscanner.backpack.compose.imagegallery.BpkImageGallerySlideshow
import net.skyscanner.backpack.compose.modal.rememberBpkModalState
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.ImageGalleryComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@ImageGalleryComponent
@ComposeStory(name = "Gallery Preview Default")
fun ImageGalleryPreviewDefaultStory(
    modifier: Modifier = Modifier,
) {
    BpkImageGalleryPreviewDefault(
        image = ImageGalleryData.slideshowImages(LocalContext.current)[0],
        buttonText = stringResource(R.string.image_gallery_preview_button),
        onButtonClicked = { Log.d("BpkImageGalleryPreview", "Click on Gallery preview") },
    )
}

@Composable
@ImageGalleryComponent
@ComposeStory(name = "Gallery Preview Hero")
fun ImageGalleryPreviewHeroStory(
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberBpkCarouselState(
        totalImages = 4,
    )

    BpkImageGalleryPreviewHero(
        state = pagerState,
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(
                id = when (it) {
                    0 -> R.drawable.carousel_placeholder_1
                    1 -> R.drawable.carousel_placeholder_2
                    2 -> R.drawable.carousel_placeholder_3
                    3 -> R.drawable.carousel_placeholder_4
                    else -> R.drawable.carousel_placeholder_1
                },
            ),
            contentDescription = "Image $it",
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
@ImageGalleryComponent
@ComposeStory(name = "Image Grid")
fun ImageGalleryImageGridStory(
    modifier: Modifier = Modifier,
    initialPage: Int = 0,
) {
    val context = LocalContext.current
    val showModal = rememberSaveable { mutableStateOf(true) }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BpkButton(
            text = stringResource(R.string.generic_show),
            onClick = { showModal.value = true },
        )

        if (showModal.value) {
            val modalState = rememberBpkModalState()
            val coroutineScope = rememberCoroutineScope()

            BpkImageGalleryImageGrid(
                state = modalState,
                initialCategory = initialPage,
                closeContentDescription = stringResource(R.string.navigation_close),
                onCloseClicked = { coroutineScope.launch { modalState.hide() } },
                onDismiss = { showModal.value = false },
                categories = ImageGalleryData.imageCategories(context),
            )
        }
    }
}

@Composable
@ImageGalleryComponent
@ComposeStory(name = "Chip Grid")
fun ImageGalleryChipGridStory(
    modifier: Modifier = Modifier,
    initialPage: Int = 0,
) {
    val context = LocalContext.current
    val showModal = rememberSaveable { mutableStateOf(true) }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BpkButton(
            text = stringResource(R.string.generic_show),
            onClick = { showModal.value = true },
        )

        if (showModal.value) {
            val modalState = rememberBpkModalState()
            val coroutineScope = rememberCoroutineScope()

            BpkImageGalleryChipGrid(
                state = modalState,
                initialCategory = initialPage,
                closeContentDescription = stringResource(R.string.navigation_close),
                onCloseClicked = { coroutineScope.launch { modalState.hide() } },
                onDismiss = { showModal.value = false },
                categories = ImageGalleryData.chipCategories(context),
            )
        }
    }
}

@Composable
@ImageGalleryComponent
@ComposeStory(name = "Slideshow")
fun ImageGallerySlideshowStory(
    modifier: Modifier = Modifier,
    initialPage: Int = 0,
) {
    val showModal = rememberSaveable { mutableStateOf(true) }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BpkButton(
            text = stringResource(R.string.generic_show),
            onClick = { showModal.value = true },
        )

        if (showModal.value) {
            val modalState = rememberBpkModalState()
            val coroutineScope = rememberCoroutineScope()
            val context = LocalContext.current

            BpkImageGallerySlideshow(
                state = modalState,
                closeContentDescription = stringResource(R.string.navigation_close),
                initialImage = initialPage,
                onCloseClicked = { coroutineScope.launch { modalState.hide() } },
                onDismiss = { showModal.value = false },
                images = ImageGalleryData.slideshowImages(context),
            )
        }
    }
}

object ImageGalleryData {

    fun slideshowImages(context: Context) =
        listOf(
            BpkImageGalleryImage(
                title = context.getString(R.string.image_gallery_title_1),
                description = context.getString(R.string.image_gallery_description_1),
                credit = context.getString(R.string.image_gallery_photographer_1),
                content = { contentDescription, contentScale ->
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = placeholder(1),
                        contentDescription = contentDescription,
                        contentScale = contentScale,
                    )
                },
            ),
            galleryImageItem(context, 2),
            galleryImageItem(context, 3),
            galleryImageItem(context, 4),
        )

    fun imageCategories(context: Context) =
        listOf(
            imageCategory(context, context.getString(R.string.image_gallery_category_1), 1),
            imageCategory(context, context.getString(R.string.image_gallery_category_2), 2),
            imageCategory(context, context.getString(R.string.image_gallery_category_3), 3),
            imageCategory(context, context.getString(R.string.image_gallery_category_4), 4),
        )

    fun chipCategories(context: Context) =
        listOf(
            chipCategory(context, context.getString(R.string.image_gallery_category_1), 1),
            chipCategory(context, context.getString(R.string.image_gallery_category_2), 2),
            chipCategory(context, context.getString(R.string.image_gallery_category_3), 3),
            chipCategory(context, context.getString(R.string.image_gallery_category_4), 4),
        )

    fun galleryImageItem(context: Context, index: Int) =
        BpkImageGalleryImage(
            title = context.getString(R.string.image_gallery_title_x, index),
            content = { contentDescription, contentScale ->
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = placeholder(index),
                    contentDescription = contentDescription,
                    contentScale = contentScale,
                )
            },
        )

    private fun imageCategory(context: Context, title: String, position: Int) =
        BpkImageGalleryImageCategory(
            title = title,
            images = listOf(
                galleryImageItem(context, 1 * position),
                galleryImageItem(context, 2 * position),
                galleryImageItem(context, 3 * position),
                galleryImageItem(context, 4 * position),
                galleryImageItem(context, 5 * position),
                galleryImageItem(context, 6 * position),
                galleryImageItem(context, 7 * position),
                galleryImageItem(context, 8 * position),
                galleryImageItem(context, 9 * position),
            ),
            content = {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = placeholder(position),
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                )
            },
        )

    private fun chipCategory(context: Context, title: String, position: Int) =
        BpkImageGalleryChipCategory(
            title = title,
            images = listOf(
                galleryImageItem(context, 1 * position),
                galleryImageItem(context, 2 * position),
                galleryImageItem(context, 3 * position),
                galleryImageItem(context, 4 * position),
                galleryImageItem(context, 5 * position),
                galleryImageItem(context, 6 * position),
                galleryImageItem(context, 7 * position),
                galleryImageItem(context, 8 * position),
                galleryImageItem(context, 9 * position),
            ),
        )

    @Composable
    private fun placeholder(index: Int) =
        painterResource(
            when (index % 10) {
                0 -> R.drawable.carousel_placeholder_1
                1 -> R.drawable.carousel_placeholder_2
                2 -> R.drawable.carousel_placeholder_3
                3 -> R.drawable.carousel_placeholder_4
                4 -> R.drawable.canadian_rockies_canada
                5 -> R.drawable.beach
                6 -> R.drawable.city
                7 -> R.drawable.sea
                8 -> R.drawable.sunset
                9 -> R.drawable.graphic_promo
                else -> R.drawable.carousel_placeholder_1
            },
        )
}
