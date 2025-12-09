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

package net.skyscanner.backpack.demo.figma.cardwrapper

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.card.BpkCardElevation
import net.skyscanner.backpack.compose.card.BpkCardPadding
import net.skyscanner.backpack.compose.card.BpkCardStyle
import net.skyscanner.backpack.compose.cardwrapper.BpkCardWrapper
import net.skyscanner.backpack.compose.theme.BpkTheme

@FigmaConnect("FIGMA_WRAPPER_CARD")
public class BpkCardWrapperDoc {
    @Composable
    public fun ComponentExample() {
        BpkCardWrapper(
            backgroundColor = BpkTheme.colors.coreAccent,
            headerContent = {
                // Header content goes here
            },
            cardContent = {
                // Card content goes here
            },
            cardPadding = BpkCardPadding.None, // example padding
            corner = BpkCardCorner.Small, // example corner
            cardStyle = BpkCardStyle.onDefault, // example style
            elevation = BpkCardElevation.None, // example elevation

        )
    }
}
