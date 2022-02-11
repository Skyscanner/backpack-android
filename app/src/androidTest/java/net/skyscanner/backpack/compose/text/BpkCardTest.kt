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

package net.skyscanner.backpack.compose.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.card.BpkCardPadding
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkCardTest : BpkSnapshotTest() {

  @Before
  fun setUp() {
    setDimensions(240, 320)
  }

  @Test
  fun smallCorner() = composed {
    CardWrapper {
      BpkCard(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
      ) {
        BpkText("Small corners")
      }
    }
  }

  @Test
  fun largeCorner() = composed {
    CardWrapper {
      BpkCard(
        modifier = Modifier.fillMaxSize(),
        corner = BpkCardCorner.Large,
        contentAlignment = Alignment.Center,
      ) {
        BpkText("Large corners")
      }
    }
  }

  @Test
  fun noPadding() = composed {
    CardWrapper {
      BpkCard(
        modifier = Modifier.fillMaxSize(),
        padding = BpkCardPadding.None,
      ) {
        BpkText("No padding")
      }
    }
  }

  @Test
  fun unfocused() = composed {
    CardWrapper {
      BpkCard(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
      ) {
        BpkText("Unfocused")
      }
    }
  }

  @Test
  fun focused() = composed {
    CardWrapper {
      BpkCard(
        onClick = {},
        modifier = Modifier.fillMaxSize(),
        focused = true,
        contentAlignment = Alignment.Center,
      ) {
        BpkText("Focused")
      }
    }
  }
}

@Composable
private fun CardWrapper(content: @Composable () -> Unit) {
  Box(
    Modifier
      .background(BpkTheme.colors.backgroundAlternative)
      .padding(BpkSpacing.Lg)
  ) {
    content()
  }
}
