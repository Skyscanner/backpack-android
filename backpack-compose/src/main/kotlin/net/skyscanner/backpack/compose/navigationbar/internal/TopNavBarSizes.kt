/*
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

package net.skyscanner.backpack.compose.navigationbar.internal

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.tokens.BpkSpacing

internal object TopNavBarSizes {
    val ExpandedHeight = 112.dp
    val CollapsedHeight = 56.dp
    val ExpandedTitlePaddingBottom = 20.dp // check this when updating to compose version that disables font padding
    val IconActionSize = 40.dp
    val TopAppBarHorizontalPadding = BpkSpacing.Md
    val TopAppBarTitleInset = BpkSpacing.Base - TopAppBarHorizontalPadding
}

internal val TopTitleAlphaEasing = CubicBezierEasing(.8f, 0f, .8f, .15f)
