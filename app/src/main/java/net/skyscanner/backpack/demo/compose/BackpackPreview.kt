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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.BackpackDemoTheme

@Composable
fun BackpackPreview(
  modifier: Modifier = Modifier,
  background: Color = Color.Unspecified,
  vararg providers: ProvidedValue<*>,
  content: @Composable () -> Unit,
) {
  BackpackDemoTheme {
    CompositionLocalProvider(*providers) {
      Box(modifier.background(background.takeOrElse { BpkTheme.colors.canvas })) {
        content()
      }
    }
  }
}
