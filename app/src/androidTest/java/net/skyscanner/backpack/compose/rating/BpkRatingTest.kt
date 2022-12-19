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

import androidx.compose.ui.unit.IntSize
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.SnapshotUtil.assumeVariant
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
    snapshotSize = IntSize(250, 75)
  }

  @Test
  fun default() = snap {
    BpkRatingDefaultSample()
  }

  @Test
  fun titleOnly() {
    assumeVariant(BpkTestVariant.Default)
    snap {
      BpkRatingTitleOnlySample()
    }
  }

  @Test
  fun noScale() {
    assumeVariant(BpkTestVariant.Default)
    snap {
      BpkRatingNoScaleSample()
    }
  }

  @Test
  fun customContent() {
    assumeVariant(BpkTestVariant.Default)
    snap {
      BpkRatingCustomContentSample()
    }
  }

  @Test
  fun large() = snap {
    BpkRatingLargeSample()
  }

  @Test
  fun largeTitleOnly() {
    assumeVariant(BpkTestVariant.Default)
    snap {
      BpkRatingLargeTitleOnlySample()
    }
  }

  @Test
  fun largeNoScale() {
    assumeVariant(BpkTestVariant.Default)
    snap {
      BpkRatingLargeNoScaleSample()
    }
  }

  @Test
  fun largeCustomContent() {
    assumeVariant(BpkTestVariant.Default)
    snap {
      BpkRatingLargeCustomContentSample()
    }
  }

  @Test
  fun largeCustomContentMoSubtitle() {
    assumeVariant(BpkTestVariant.Default)
    snap {
      BpkRatingLargeCustomContentNoSubtitleSample()
    }
  }
}
