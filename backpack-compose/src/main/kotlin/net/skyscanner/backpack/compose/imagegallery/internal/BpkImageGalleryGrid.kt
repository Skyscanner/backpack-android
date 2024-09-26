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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleChipItem
import net.skyscanner.backpack.compose.chipgroup.single.BpkSingleSelectChipGroup
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryCategories
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryChipCategory
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryImage
import net.skyscanner.backpack.compose.imagegallery.BpkImageGalleryImageCategory
import net.skyscanner.backpack.compose.overlay.BpkOverlay
import net.skyscanner.backpack.compose.overlay.BpkOverlayType
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.clickableWithRipple

@Composable
internal fun BpkImageGalleryGrid(
    categories: BpkImageGalleryCategories,
    currentCategory: Int,
    onCategoryChanged: (BpkImageGalleryCategories.Category) -> Unit,
    onImageClicked: (BpkImageGalleryCategories.Category, BpkImageGalleryImage) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(top = BpkSpacing.Lg)) {
        when (categories) {
            is BpkImageGalleryCategories.Chip -> ChipCategories(
                categories = categories.categories,
                currentCategory = currentCategory,
                onCategoryChanged = onCategoryChanged,
            )

            is BpkImageGalleryCategories.Image -> ImageCategories(
                categories = categories.categories,
                currentCategory = currentCategory,
                onCategoryChanged = onCategoryChanged,
            )
        }
        CategoryGrid(categories.categories[currentCategory], onImageClicked)
    }
}

@Composable
private fun ChipCategories(
    categories: List<BpkImageGalleryChipCategory>,
    currentCategory: Int,
    onCategoryChanged: (BpkImageGalleryCategories.Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    val chipItems = categories.map { BpkSingleChipItem(it.title) }
    BpkSingleSelectChipGroup(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = BpkSpacing.Lg),
        chips = chipItems,
        selectedIndex = currentCategory,
        onItemClicked = {
            onCategoryChanged(categories[chipItems.indexOf(it)])
        },
    )
}

@Composable
private fun ImageCategories(
    categories: List<BpkImageGalleryImageCategory>,
    currentCategory: Int,
    onCategoryChanged: (BpkImageGalleryCategories.Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.selectableGroup(),
        contentPadding = PaddingValues(horizontal = BpkSpacing.Lg),
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
    ) {
        itemsIndexed(categories) { index, category ->
            ImageCategory(
                selected = index == currentCategory,
                onCategoryChanged = onCategoryChanged,
                category = category,
            )
        }
    }
}

@Composable
private fun ImageCategory(
    selected: Boolean,
    onCategoryChanged: (BpkImageGalleryCategories.Category) -> Unit,
    category: BpkImageGalleryImageCategory,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(CategoryImageSize)
            .selectable(
                role = Role.RadioButton,
                selected = selected,
            ) { onCategoryChanged(category) },
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
    ) {
        Box(
            modifier = Modifier
                .size(CategoryImageSize)
                .clip(RoundedCornerShape(BpkBorderRadius.Md)),
        ) {
            if (selected) {
                category.content()
            } else {
                BpkOverlay(
                    overlayType = BpkOverlayType.SolidLow,
                ) {
                    category.content()
                }
            }
        }

        BpkText(
            text = category.title,
            style = if (selected) BpkTheme.typography.label3 else BpkTheme.typography.caption,
            color = if (selected) BpkTheme.colors.textPrimary else BpkTheme.colors.textSecondary,
            maxLines = 2,
            minLines = 2,
        )
    }
}

@Composable
private fun CategoryGrid(
    category: BpkImageGalleryCategories.Category,
    onImageClicked: (BpkImageGalleryCategories.Category, BpkImageGalleryImage) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberLazyGridState()
    LaunchedEffect(category) {
        state.scrollToItem(0)
    }
    LazyVerticalGrid(
        modifier = modifier.padding(top = BpkSpacing.Md),
        contentPadding = PaddingValues(horizontal = BpkSpacing.Lg, vertical = BpkSpacing.Base),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
        state = state,
    ) {
        items(
            items = category.images,
            span = { GridItemSpan(if (category.images.indexOf(it) % 3 == 0) 2 else 1) },
        ) { image ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ImageHeight)
                    .clip(RoundedCornerShape(BpkBorderRadius.Md))
                    .clickableWithRipple { onImageClicked(category, image) },
            ) {
                image.content(image.contentDescription(), ContentScale.Crop)
            }
        }
        item(
            span = { GridItemSpan(2) },
        ) {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
        }
    }
}

private val CategoryImageSize = 90.dp
private val ImageHeight = 192.dp
