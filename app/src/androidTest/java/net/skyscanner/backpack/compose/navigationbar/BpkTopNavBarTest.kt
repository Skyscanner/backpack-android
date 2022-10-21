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
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.demo.compose.ActionsTopNavBar
import net.skyscanner.backpack.demo.compose.BackTopNavBar
import net.skyscanner.backpack.demo.compose.CloseTopNavBar
import net.skyscanner.backpack.demo.compose.CollapsibleNavBar
import net.skyscanner.backpack.demo.compose.NoNavIconTopNavBar
import net.skyscanner.backpack.demo.compose.TextActionTopNavBar
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkTopNavBarTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(height = 50, width = 400)
  }

  @Test
  fun default() = composed {
    ActionsTopNavBar(Modifier.fillMaxWidth())
  }

  @Test
  fun noNavIcon() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl)
    composed {
      NoNavIconTopNavBar(Modifier.fillMaxWidth())
    }
  }

  @Test
  fun back() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BackTopNavBar(Modifier.fillMaxWidth())
    }
  }

  @Test
  fun close() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      CloseTopNavBar(Modifier.fillMaxWidth())
    }
  }

  @Test
  fun textAction() = composed {
    TextActionTopNavBar(Modifier.fillMaxWidth())
  }

  @Test
  fun expanded() = composed(IntSize(100, 400)) {
    CollapsibleNavBar(initialStatus = TopNavBarStatus.Expanded, showList = false)
  }

  @Test
  fun collapsed() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      CollapsibleNavBar(initialStatus = TopNavBarStatus.Collapsed)
    }
  }

  @Test
  fun expandedNoNavIcon() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl)
    composed(IntSize(100, 400)) {
      CollapsibleNavBar(initialStatus = TopNavBarStatus.Expanded, showNav = false)
    }
  }

  @Test
  fun expandedNoActions() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl)
    composed(IntSize(100, 400)) {
      CollapsibleNavBar(initialStatus = TopNavBarStatus.Expanded, showActions = false)
    }
  }

  @Test
  fun windowInsets() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      WindowInsets.setUseTestInsets()
      CollapsibleNavBar(initialStatus = TopNavBarStatus.Expanded, showActions = false)
    }
  }
}
