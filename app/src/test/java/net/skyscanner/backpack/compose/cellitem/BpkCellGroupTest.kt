/**
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

package net.skyscanner.backpack.compose.cellitem

import androidx.compose.foundation.layout.padding
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import org.junit.Test

class BpkCellGroupTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        BpkCellGroup {
            item {
                BpkText(
                    text = "Profile Settings",
                    style = BpkTheme.typography.label1,
                    modifier = androidx.compose.ui.Modifier.padding(BpkSpacing.Base),
                )
            }
            item {
                BpkText(
                    text = "Notifications",
                    style = BpkTheme.typography.label1,
                    modifier = androidx.compose.ui.Modifier.padding(BpkSpacing.Base),
                )
            }
            item {
                BpkText(
                    text = "Language",
                    style = BpkTheme.typography.label1,
                    modifier = androidx.compose.ui.Modifier.padding(BpkSpacing.Base),
                )
            }
        }
    }
}