/*
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.rating.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.semantics.semantics
import net.skyscanner.backpack.compose.rating.BpkRatingScale
import net.skyscanner.backpack.compose.rating.BpkRatingSize
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BpkRatingLayout(
    value: Float,
    scale: BpkRatingScale,
    size: BpkRatingSize,
    title: @Composable (() -> Unit)?,
    subtitle: String?,
    showScale: Boolean,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier.semantics(mergeDescendants = true) { },
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
    ) {

        BpkRatingNumbers(
            modifier = Modifier.alignByBaseline(),
            value = value,
            scale = scale,
            size = size,
            showScale = showScale,
        )

        when (size) {

            BpkRatingSize.Base -> {
                if (title != null) {
                    BpkRatingTitle(Modifier.alignByBaseline(), title)
                }
                if (subtitle != null) {
                    BpkRatingSubtitle(subtitle, size, Modifier.alignByBaseline())
                }
            }

            BpkRatingSize.Large ->
                Column(
                    modifier = Modifier.alignBy(LastBaseline),
                ) {
                    if (title != null) {
                        BpkRatingTitle(content = title)
                    }
                    if (subtitle != null) {
                        BpkRatingSubtitle(subtitle, size)
                    }
                }
        }
    }
}
