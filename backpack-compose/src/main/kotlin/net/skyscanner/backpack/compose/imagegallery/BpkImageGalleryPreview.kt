package net.skyscanner.backpack.compose.imagegallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.badge.BpkBadge
import net.skyscanner.backpack.compose.badge.BpkBadgeType
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonIconPosition
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.carousel.BpkCarousel
import net.skyscanner.backpack.compose.carousel.BpkCarouselState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Picture
import net.skyscanner.backpack.compose.utils.invisibleSemantic

@Composable
fun BpkImageGalleryPreviewDefault(
    image: BpkImageGalleryImage,
    buttonText: String,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier
        .aspectRatio(1.73f),
    ) {
        image.content(image.contentDescription(), ContentScale.Crop)
        BpkButton(
            text = buttonText,
            icon = BpkIcon.Picture,
            position = BpkButtonIconPosition.Start,
            size = BpkButtonSize.Default,
            type = BpkButtonType.Secondary,
            contentDescription = buttonText,
            onClick = onButtonClicked,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(all = BpkSpacing.Base),
        )
    }
}

@Composable
fun BpkImageGalleryPreviewHero(
    state: BpkCarouselState,
    modifier: Modifier = Modifier,
    onImageClicked: ((Int) -> Unit)? = null,
    content: @Composable (BoxScope.(Int) -> Unit),
) {
    val interactionSource = remember { MutableInteractionSource() }

    BpkCarousel(
        modifier = modifier.clickable(interactionSource = interactionSource, indication = null) {
            onImageClicked?.invoke(
                state.currentPage,
            )
        },
        state = state,
        content = content,
        overlayContent = { pageIndicator ->
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(
                        horizontal = BpkSpacing.Base,
                        vertical = ImageGalleryPreviewVerticalSpacing,
                    ),
            ) {
                pageIndicator?.invoke()
                BpkBadge(
                    modifier = Modifier
                        .align(Alignment.BottomEnd).invisibleSemantic(),
                    text = "${state.currentPage + 1}/${state.pageCount}",
                    type = BpkBadgeType.Inverse,

                )
            }
        },
    )
}

private val ImageGalleryPreviewVerticalSpacing = 48.dp
