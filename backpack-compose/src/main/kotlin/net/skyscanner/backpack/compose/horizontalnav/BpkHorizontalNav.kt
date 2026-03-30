/*
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.horizontalnav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.horizontalnav.internal.BpkHorizontalNavImpl
import net.skyscanner.backpack.compose.icon.BpkIcon

enum class BpkHorizontalNavSize {
    Default,
    Small,
}

enum class BpkHorizontalNavBackgroundColor {
    SurfaceDefault,
    CanvasContrast,
}

data class BpkHorizontalNavTab(
    val title: String,
    val icon: BpkIcon? = null,
)

@Composable
fun BpkHorizontalNav(
    tabs: List<BpkHorizontalNavTab>,
    activeIndex: Int,
    onChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: BpkHorizontalNavBackgroundColor = BpkHorizontalNavBackgroundColor.SurfaceDefault,
    size: BpkHorizontalNavSize = BpkHorizontalNavSize.Default,
) {
    BpkHorizontalNavImpl(
        tabs = tabs,
        activeIndex = activeIndex,
        onChanged = onChanged,
        modifier = modifier,
        backgroundColor = backgroundColor,
        size = size,
    )
}
