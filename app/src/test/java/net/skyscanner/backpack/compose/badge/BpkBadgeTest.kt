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

package net.skyscanner.backpack.compose.badge

import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.CloseCircle
import org.junit.Test

class BpkBadgeTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        BpkBadge(text = "Default")
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun withIcon() = snap {
        BpkBadge(text = "Default", icon = BpkIcon.CloseCircle)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun normal() {
        snap {
            BpkBadge(text = BpkBadgeType.Normal.toString(), type = BpkBadgeType.Normal)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun strong() {
        snap {
            BpkBadge(text = BpkBadgeType.Strong.toString(), type = BpkBadgeType.Strong)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun success() {
        snap {
            BpkBadge(text = BpkBadgeType.Success.toString(), type = BpkBadgeType.Success)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun warning() {
        snap {
            BpkBadge(text = BpkBadgeType.Warning.toString(), type = BpkBadgeType.Warning)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun destructive() {
        snap {
            BpkBadge(text = BpkBadgeType.Destructive.toString(), type = BpkBadgeType.Destructive)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun inverse() {
        snap(background = { BpkTheme.colors.surfaceContrast }) {
            BpkBadge(text = BpkBadgeType.Inverse.toString(), type = BpkBadgeType.Inverse)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun outline() {
        snap(background = { BpkTheme.colors.surfaceContrast }) {
            BpkBadge(text = BpkBadgeType.Outline.toString(), type = BpkBadgeType.Outline)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun brand() {
        snap {
            BpkBadge(text = BpkBadgeType.Brand.toString(), type = BpkBadgeType.Brand)
        }
    }
}
