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

package net.skyscanner.backpack.demo.figma.panel

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.figma.code.connect.Figma
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import net.skyscanner.backpack.compose.panel.BpkPanel
import net.skyscanner.backpack.compose.panel.BpkPanelPadding

@FigmaConnect("FIGMA_PANEL")
public class BpkPanelDoc {
    @FigmaProperty(FigmaType.Enum, "Keyline?")
    public val propagateMinConstraints: Boolean = Figma.mapping(
        "Yes" to true,
        "No" to false,
    )

    @Composable
    public fun ComponentExample() {
        BpkPanel(
            propagateMinConstraints = propagateMinConstraints, // example usage of the property
            padding = BpkPanelPadding.Base, // example padding
            contentAlignment = Alignment.TopStart, // example alignment
            content = {
                // Content goes here
            },
        )
    }
}
