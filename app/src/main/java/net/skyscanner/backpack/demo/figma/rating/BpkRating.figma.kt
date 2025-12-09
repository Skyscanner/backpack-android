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

package net.skyscanner.backpack.demo.figma.rating

import androidx.compose.runtime.Composable
import com.figma.code.connect.Figma
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import net.skyscanner.backpack.compose.rating.BpkRating
import net.skyscanner.backpack.compose.rating.BpkRatingSize

@FigmaConnect("FIGMA_RATING")
class BpkRatingDoc {

    @FigmaProperty(FigmaType.Enum, "Size")
    val size: BpkRatingSize = Figma.mapping(
        "Default" to BpkRatingSize.Base,
        "Large" to BpkRatingSize.Large,
    )

    @FigmaProperty(FigmaType.Enum, "Arrangement")
    val subtitle: String? = Figma.mapping(
        "Title only" to null,
        "Title • Subtitle" to "1,532 reviews",
    )

    @Composable
    fun RatingExample() {
        BpkRating(
            title = "Excellent",
            value = 4.5f,
            size = size,
            subtitle = subtitle,
        )
    }
}
