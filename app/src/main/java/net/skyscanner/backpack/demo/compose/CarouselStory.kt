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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.carousel.BpkCarousel
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
@Preview
fun CarouselStory(
  modifier: Modifier = Modifier,
) {
  var currentIndex by remember { mutableStateOf(0) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Base)
      .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {

    BpkText(text = stringResource(id = R.string.carousel_only_1_item))
    CarouselSample(totalItems = 1)

    BpkText(text = stringResource(id = R.string.carousel_multiple_items))
    CarouselSample(totalItems = 3)

    BpkText(text = stringResource(id = R.string.carousel_start_at_different_index))
    CarouselSample(totalItems = 3, currentImage = 2)

    BpkText(text = stringResource(id = R.string.carousel_image_change_callback))
    BpkText(
      text = stringResource(id = R.string.carousel_current_image).format(currentIndex),
      style = BpkTheme.typography.caption,
    )
    CarouselSample(totalItems = 3, currentImage = 1, onImageChange = { currentIndex = it })
  }
}

@Composable
private fun CarouselSample(
  totalItems: Int,
  modifier: Modifier = Modifier,
  currentImage: Int = 0,
  onImageChange: ((Int) -> Unit)? = null,
) {
  BpkCarousel(
    modifier = modifier
      .aspectRatio(1.9f)
      .padding(vertical = BpkSpacing.Md),
    count = totalItems,
    currentImage = currentImage,
    imageContent = {
      Image(
        painter = painterResource(
          id = when (it) {
            0 -> R.drawable.canadian_rockies_canada
            1 -> R.drawable.beach
            2 -> R.drawable.city
            else -> R.drawable.canadian_rockies_canada
          },
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
      )
    },
    onImageChanged = {
      onImageChange?.invoke(it)
    },
  )
}
