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

package net.skyscanner.backpack.compose.navigationbar

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.compose.ActionsTopNavBar
import net.skyscanner.backpack.demo.compose.BackTopNavBar
import net.skyscanner.backpack.demo.compose.CloseTopNavBar
import net.skyscanner.backpack.demo.compose.CollapsibleNavigationBarStory
import net.skyscanner.backpack.demo.compose.NoNavIconTopNavBar
import net.skyscanner.backpack.demo.compose.TextActionTopNavBar
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkTopNavBarTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    snapshotSize = IntSize(400, 50)
  }

  @Test
  fun default() = snap {
    ActionsTopNavBar(Modifier.fillMaxWidth())
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun noNavIcon() {
    snap {
      NoNavIconTopNavBar(Modifier.fillMaxWidth())
    }
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun back() {
    snap {
      BackTopNavBar(Modifier.fillMaxWidth())
    }
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun close() {
    snap {
      CloseTopNavBar(Modifier.fillMaxWidth())
    }
  }

  @Test
  fun textAction() = snap {
    TextActionTopNavBar(Modifier.fillMaxWidth())
  }

  @Test
  fun expanded() = snap(IntSize(400, 100)) {
    CollapsibleNavigationBarStory(initialStatus = TopNavBarStatus.Expanded, showList = false)
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun collapsed() {
    snap {
      CollapsibleNavigationBarStory(initialStatus = TopNavBarStatus.Collapsed, showList = false)
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun expandedNoNavIcon() {
    snap(IntSize(400, 100)) {
      CollapsibleNavigationBarStory(initialStatus = TopNavBarStatus.Expanded, showNav = false)
    }
  }

  @Test
  @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
  fun expandedNoActions() {
    snap(IntSize(400, 100)) {
      CollapsibleNavigationBarStory(initialStatus = TopNavBarStatus.Expanded, showActions = false, showList = false)
    }
  }

  @Test
  @Variants(BpkTestVariant.Default)
  fun windowInsets() {
    snap(IntSize(400, 100)) {
      CollapsibleNavigationBarStory(
        initialStatus = TopNavBarStatus.Expanded,
        showActions = false,
        showList = false,
        insets = WindowInsets(top = BpkSpacing.Md),
      )
    }
  }
}
