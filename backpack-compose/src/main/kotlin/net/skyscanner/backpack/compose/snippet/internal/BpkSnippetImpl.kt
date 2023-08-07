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

package net.skyscanner.backpack.compose.snippet.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import net.skyscanner.backpack.compose.snippet.ImageOrientation
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.clickable

@Composable
internal fun BpkSnippetImpl(
    content: @Composable (() -> Unit),
    imageOrientation: ImageOrientation,
    headline: String?,
    subHeading: String?,
    bodyText: String?,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)?,
) {
    val roundedCornerShape = RoundedCornerShape(BpkBorderRadius.Sm)
    Column(
        modifier = modifier
            .apply {
                padding(horizontal = BpkSpacing.Lg)
                if (onClick != null) {
                    clickable(onClick = onClick)
                }
            },
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = BpkSpacing.Base)
                .aspectRatio(getAspectRatio(imageOrientation))
                .background(BpkTheme.colors.surfaceHighlight, roundedCornerShape)
                .clip(roundedCornerShape),
        ) {
            content()
        }
        if (!headline.isNullOrBlank()) {
            BpkText(
                text = headline,
                style = BpkTheme.typography.heading4,
                color = BpkTheme.colors.textPrimary,
            )
            Spacer(modifier = Modifier.height(BpkSpacing.Sm))
        }
        if (!subHeading.isNullOrBlank()) {
            BpkText(
                text = subHeading,
                style = BpkTheme.typography.subheading,
                color = BpkTheme.colors.textPrimary,
            )
            Spacer(modifier = Modifier.height(BpkSpacing.Xl))
        }
        if (!bodyText.isNullOrBlank()) {
            BpkText(
                text = bodyText,
                style = BpkTheme.typography.bodyDefault,
                color = BpkTheme.colors.textPrimary,
            )
            Spacer(modifier = Modifier.height(BpkSpacing.Base))
        }
    }
}

fun getAspectRatio(imageOrientation: ImageOrientation): Float =
    when (imageOrientation) {
        ImageOrientation.Landscape -> IMAGE_RATIO_LANDSCAPE
        ImageOrientation.Square -> IMAGE_RATIO_SQUARE
        ImageOrientation.Portrait -> IMAGE_RATIO_PORTRAIT
    }

const val IMAGE_RATIO_LANDSCAPE: Float = 3 / 2f
const val IMAGE_RATIO_SQUARE: Float = 1f
const val IMAGE_RATIO_PORTRAIT: Float = 5 / 7f
