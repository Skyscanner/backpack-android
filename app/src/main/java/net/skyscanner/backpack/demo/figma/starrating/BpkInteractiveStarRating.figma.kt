/*
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

package net.skyscanner.backpack.demo.figma.starrating

import androidx.compose.runtime.Composable
import com.figma.code.connect.Figma
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import net.skyscanner.backpack.compose.starrating.BpkInteractiveStarRating
import net.skyscanner.backpack.compose.starrating.BpkStarRatingSize

@FigmaConnect("FIGMA_INTERACTIVE_STAR_RATING")
class BpkInteractiveStarRatingDoc {

    @FigmaProperty(FigmaType.Enum, "Rating")
    val rating: Int = Figma.mapping(
        "1 star" to 1,
        "2 stars" to 2,
        "3 stars" to 3,
        "4 stars" to 4,
        "5 stars" to 5,
    )

    @FigmaProperty(FigmaType.Enum, "Size")
    val size: BpkStarRatingSize = Figma.mapping(
        "Large" to BpkStarRatingSize.Large,
        "Small" to BpkStarRatingSize.Small,
        "Extra-large" to BpkStarRatingSize.Large,
    )

    @Composable
    fun ComponentExample() {
        BpkInteractiveStarRating(
            rating = rating,
            size = size,
            onRatingChanged = { /* When rating changes */ },
            contentDescription = { _, _ -> "$rating stars" },
        )
    }
}
