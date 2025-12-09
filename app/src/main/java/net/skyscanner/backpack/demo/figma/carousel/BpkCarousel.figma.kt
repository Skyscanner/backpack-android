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

package net.skyscanner.backpack.demo.figma.carousel

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.figma.code.connect.FigmaConnect
import net.skyscanner.backpack.compose.carousel.BpkCarousel
import net.skyscanner.backpack.compose.carousel.rememberBpkCarouselState
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@FigmaConnect("FIGMA_CAROUSEL")
public class BpkCarouselDoc {
    @Composable
    public fun ComponentExample() {
        val pagerState = rememberBpkCarouselState(
            totalImages = 5, // number of images in the carousel
            initialImage = 0, // starting image index
        )
        BpkCarousel(
            modifier = Modifier
                .aspectRatio(1.9f)
                .padding(vertical = BpkSpacing.Base),
            state = pagerState,
        ) {
            // content goes here, could be glide image, painterResource, depending on source of images
        }
    }
}
