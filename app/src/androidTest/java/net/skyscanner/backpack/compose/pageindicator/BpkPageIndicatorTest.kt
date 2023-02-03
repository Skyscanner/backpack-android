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

package net.skyscanner.backpack.compose.pageindicator

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BpkPageIndicatorTest(val style: BpkPageIndicatorStyle) : BpkSnapshotTest() {

  @Test
  fun lessThan5pages() = snap {
    BpkPageIndicator(
      modifier = Modifier.background(color = Color.Magenta),
      currentIndex = 0,
      totalIndicators = 3,
      style = style,
      indicatorLabel = stringResource(R.string.page_indicator_label_indicator, 1, 3),
    )
  }

  @Test
  fun moreThan5pages() = snap {
    BpkPageIndicator(
      modifier = Modifier.background(color = Color.Magenta),
      currentIndex = 4,
      totalIndicators = 8,
      style = style,
      indicatorLabel = stringResource(R.string.page_indicator_label_indicator, 4, 8),
    )
  }

  companion object {

    @JvmStatic
    @Parameterized.Parameters(name = "{0} Screenshot")
    fun flavours(): List<BpkPageIndicatorStyle> = BpkPageIndicatorStyle.values().toList()
  }
}
