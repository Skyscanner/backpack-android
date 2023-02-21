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

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.carousel.internal.BpkCarouselImpl

@Composable
fun BpkCarousel(
  state: BpkCarouselState,
  modifier: Modifier = Modifier,
  onImageChanged: ((Int) -> Unit)? = null,
  imageContent: @Composable (BoxScope.(Int) -> Unit),
) {
  BpkCarouselImpl(
    state = state as BpkCarouselStateImpl,
    modifier = modifier,
    onImageChanged = { onImageChanged?.invoke(it) },
    imageContent = { imageContent(it) },
  )
}
