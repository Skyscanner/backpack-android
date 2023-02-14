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

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkCarouselTest : BpkSnapshotTest() {

  @Test
  fun singleImageDefault() = snap {
    BpkCarousel(
      count = 1,
      imageContent = {
        Image(painter = painterResource(id = imageResAtIndex(it)), contentDescription = "")
      }
    ) {

    }
  }

  @Test
  fun multipleImageDefault() = snap {
    BpkCarousel(
      count = 3,
      imageContent = {
        Image(painter = painterResource(id = imageResAtIndex(it)), contentDescription = "")
      }
    )
  }

  @Test
  fun setCurrentImage() = snap {
    BpkCarousel(
      count = 3,
      currentImage = 1,
      imageContent = {
        Image(painter = painterResource(id = imageResAtIndex(it)), contentDescription = "")
      }
    )
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
  fun multipleImageDarkMode() = snap {
    BpkCarousel(
      count = 2,
      imageContent = {
        Image(painter = painterResource(id = imageResAtIndex(it)), contentDescription = "")
      }
    )
  }

  private fun imageResAtIndex(index: Int) = when (index) {
    0 -> net.skyscanner.backpack.demo.R.drawable.canadian_rockies_canada
    1 -> net.skyscanner.backpack.demo.R.drawable.canadian_rockies_canada
    2 -> net.skyscanner.backpack.demo.R.drawable.canadian_rockies_canada
    else -> net.skyscanner.backpack.demo.R.drawable.canadian_rockies_canada
  }

}
