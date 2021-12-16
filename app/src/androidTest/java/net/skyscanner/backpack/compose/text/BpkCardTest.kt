/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkCardTest : BpkSnapshotTest() {

  @Before
  fun setUp() {
    setDimensions(128, 96)
  }

  @Test
  fun smallCorner() = composed {
    BpkCard(Modifier.padding(BpkSpacing.Base)) {
      Box(Modifier.fillMaxSize())
    }
  }

  @Test
  fun largeCorner() = composed {
    BpkCard(corner = BpkCardCorner.Large, modifier = Modifier.padding(BpkSpacing.Base)) {
      Box(Modifier.fillMaxSize())
    }
  }

  @Test
  fun unfocused() = composed {
    BpkCard(Modifier.padding(BpkSpacing.Base)) {
      Box(Modifier.fillMaxSize())
    }
  }

  @Test
  fun focused() = composed {

    val interactionSource = remember {
      MutableInteractionSource().apply {
        tryEmit(FocusInteraction.Focus())
      }
    }

    BpkCard(interactionSource = interactionSource, modifier = Modifier.padding(BpkSpacing.Base)) {
      Box(Modifier.fillMaxSize())
    }
  }
}
