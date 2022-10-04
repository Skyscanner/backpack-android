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

package net.skyscanner.backpack.compose.icon

import androidx.compose.runtime.CompositionLocalProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.LongArrowLeft
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkIconTest : BpkSnapshotTest() {

  private val icon = BpkIcon.LongArrowLeft

  @Before
  fun setup() {
    setDimensions(height = 24, width = 24)
  }

  @Test
  fun default() = composed {
    BpkIcon(icon = icon, contentDescription = null)
  }

  @Test
  fun small() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkIcon(icon = icon, contentDescription = null, size = BpkIconSize.Small)
    }
  }

  @Test
  fun large() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkIcon(icon = icon, contentDescription = null, size = BpkIconSize.Large)
    }
  }

  @Test
  fun tinted() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkIcon(icon = icon, contentDescription = null, tint = BpkTheme.colors.statusDangerSpot)
    }
  }

  @Test
  fun compositionLocalProviderOverride() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      CompositionLocalProvider(LocalBpkIconSize provides BpkIconSize.Large) {
        BpkIcon(icon = icon, contentDescription = null)
      }
    }
  }
}
