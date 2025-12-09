/*
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

package net.skyscanner.backpack.demo.figma.card

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

@FigmaConnect("FIGMA_CARD")
public class BpkCardDoc {
    @Composable
    public fun ComponentExample() {
        BpkCard {
            Column() {
                // Example content inside the card
                BpkText(
                    text = "Card content",
                    style = BpkTheme.typography.bodyDefault,
                )
            }
        }
    }
}
