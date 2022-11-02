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
 *
 */

package net.skyscanner.backpack.compose.rating

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.demo.compose.BpkRatingCustomContentSample
import net.skyscanner.backpack.demo.compose.BpkRatingDefaultSample
import net.skyscanner.backpack.demo.compose.BpkRatingLargeCustomContentNoSubtitleSample
import net.skyscanner.backpack.demo.compose.BpkRatingLargeCustomContentSample
import net.skyscanner.backpack.demo.compose.BpkRatingLargeNoScaleSample
import net.skyscanner.backpack.demo.compose.BpkRatingLargeSample
import net.skyscanner.backpack.demo.compose.BpkRatingLargeTitleOnlySample
import net.skyscanner.backpack.demo.compose.BpkRatingNoScaleSample
import net.skyscanner.backpack.demo.compose.BpkRatingTitleOnlySample
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkRatingTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(height = 100, width = 200)
  }

  @Test
  fun default() = composed {
    BpkRatingDefaultSample()
  }

  @Test
  fun titleOnly() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkRatingTitleOnlySample()
    }
  }

  @Test
  fun noScale() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkRatingNoScaleSample()
    }
  }

  @Test
  fun customContent() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkRatingCustomContentSample()
    }
  }

  @Test
  fun large() = composed {
    BpkRatingLargeSample()
  }

  @Test
  fun largeTitleOnly() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkRatingLargeTitleOnlySample()
    }
  }

  @Test
  fun largeNoScale() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkRatingLargeNoScaleSample()
    }
  }

  @Test
  fun largeCustomContent() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkRatingLargeCustomContentSample()
    }
  }

  @Test
  fun largeCustomContentMoSubtitle() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkRatingLargeCustomContentNoSubtitleSample()
    }
  }
}
