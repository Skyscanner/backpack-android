/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.icon

import androidx.compose.runtime.CompositionLocalProvider
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.LongArrowLeft
import org.junit.Test

class BpkIconTest : BpkSnapshotTest() {

    private val icon = BpkIcon.LongArrowLeft

    @Test
    fun default() = snap {
        BpkIcon(icon = icon, contentDescription = null)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun small() {
        snap {
            BpkIcon(icon = icon, contentDescription = null, size = BpkIconSize.Small)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun large() {
        snap {
            BpkIcon(icon = icon, contentDescription = null, size = BpkIconSize.Large)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun tinted() {
        snap {
            BpkIcon(icon = icon, contentDescription = null, tint = BpkTheme.colors.statusDangerSpot)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun compositionLocalProviderOverride() {
        snap {
            CompositionLocalProvider(LocalBpkIconSize provides BpkIconSize.Large) {
                BpkIcon(icon = icon, contentDescription = null)
            }
        }
    }
}
