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

package net.skyscanner.backpack.compose.price.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import net.skyscanner.backpack.compose.price.BpkPriceSize
import net.skyscanner.backpack.compose.theme.BpkTheme

@Composable
internal fun BpkPriceSize.mainTextStyle(): TextStyle =
    when (this) {
        BpkPriceSize.Large -> BpkTheme.typography.heading2
        BpkPriceSize.Small -> BpkTheme.typography.heading4
        BpkPriceSize.ExtraSmall -> BpkTheme.typography.heading5
    }

@Composable
internal fun BpkPriceSize.secondaryTextStyle(): TextStyle =
    when (this) {
        BpkPriceSize.Large -> BpkTheme.typography.footnote
        BpkPriceSize.Small, BpkPriceSize.ExtraSmall -> BpkTheme.typography.caption
    }
