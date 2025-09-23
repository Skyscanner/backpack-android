/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.configuration.BpkConfiguration

@Composable
fun BackpackDemoTheme(
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val typographySet = SharedPreferences.getTypographySet(context)

    val fontFamilies = mapOf(
        BpkConfiguration.BpkTypographySet.DEFAULT to FontFamily.SansSerif,
        BpkConfiguration.BpkTypographySet.ALTERNATIVE_1 to FontFamily.Serif,
        BpkConfiguration.BpkTypographySet.ALTERNATIVE_2 to FontFamily.Monospace,
    )

    BpkTheme(
        fontFamilies = fontFamilies,
        content = content,
    )
}
