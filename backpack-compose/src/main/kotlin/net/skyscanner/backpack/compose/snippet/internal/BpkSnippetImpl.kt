/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import net.skyscanner.backpack.compose.snippet.ImageOrientation
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.clickableWithRipple

@Composable
internal fun BpkSnippetImpl(
    imageOrientation: ImageOrientation,
    headline: String?,
    subHeading: String?,
    bodyText: String?,
    onClick: (() -> Unit)?,
    accessibilityHeaderTagEnabled: Boolean?,
    modifier: Modifier = Modifier,
    content: @Composable (() -> Unit),
) {
    val imageRoundedCornerShape = RoundedCornerShape(BpkBorderRadius.Md)
    Column(
        modifier = onClick?.let {
            modifier
                .clip(RoundedCornerShape(topStart = BpkBorderRadius.Md, topEnd = BpkBorderRadius.Md))
                .clickableWithRipple(onClick = onClick)
        } ?: modifier,
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(getAspectRatio(imageOrientation))
                .background(BpkTheme.colors.surfaceHighlight, imageRoundedCornerShape)
                .clip(imageRoundedCornerShape),
        ) {
            content()
        }
        if (!headline.isNullOrBlank() || !subHeading.isNullOrBlank() || !bodyText.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(BpkSpacing.Base))
        }
        if (!headline.isNullOrBlank()) {
            BpkText(
                text = headline,
                style = BpkTheme.typography.heading4,
                color = BpkTheme.colors.textPrimary,
                modifier = Modifier.semantics {
                    if (accessibilityHeaderTagEnabled == true) {
                        heading()
                    }
                },
            )
        }
        if (!subHeading.isNullOrBlank()) {
            if (!headline.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(BpkSpacing.Sm))
            }
            BpkText(
                text = subHeading,
                style = BpkTheme.typography.subheading,
                color = BpkTheme.colors.textPrimary,
            )
        }
        if (!bodyText.isNullOrBlank()) {
            if (!headline.isNullOrBlank() && subHeading.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(BpkSpacing.Sm))
            } else if (!subHeading.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(BpkSpacing.Xl))
            }
            BpkText(
                text = bodyText,
                style = BpkTheme.typography.bodyDefault,
                color = BpkTheme.colors.textPrimary,
            )
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
