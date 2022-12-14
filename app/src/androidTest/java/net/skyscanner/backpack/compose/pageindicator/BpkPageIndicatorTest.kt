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

import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BpkPageIndicatorTest(val style: BpkPageIndicatorStyle) : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(50, 150)
  }

  @Test
  fun lessThan5pages() = composed {
    val state = rememberBpkPageIndicatorState()
    BpkPageIndicator(
      state = state,
      totalIndicators = 3,
      style = style,
      indicatorLabel = "",
    )
  }

  @Test
  fun moreThan5pages() = composed {
    val state = rememberBpkPageIndicatorState()
    BpkPageIndicator(
      state = state,
      totalIndicators = 8,
      style = style,
      indicatorLabel = "",
    )
  }

  companion object {

    @JvmStatic
    @Parameterized.Parameters(name = "{0} Screenshot")
    fun flavours(): List<BpkPageIndicatorStyle> = BpkPageIndicatorStyle.values().toList()
  }
}
