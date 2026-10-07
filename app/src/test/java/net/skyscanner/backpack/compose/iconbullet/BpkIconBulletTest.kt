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

package net.skyscanner.backpack.compose.iconbullet

import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.TrendDown
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

@RunWith(ParameterizedRobolectricTestRunner::class)
class BpkIconBulletTest(flavour: Flavor) :
    BpkSnapshotTest(listOfNotNull(flavour.first, flavour.second)) {

    private val type: BpkIconBulletType = flavour.first
    private val size: BpkIconBulletSize = flavour.second

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun default() {
        snap {
            BpkIconBullet(icon = BpkIcon.TrendDown, size = size, type = type)
        }
    }

    companion object {

        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "{0} Screenshot")
        fun flavours(): List<Flavor> = BpkIconBulletType.entries.flatMap { type ->
            BpkIconBulletSize.entries.map { size -> Pair(type, size) }
        }
    }
}

private typealias Flavor = Pair<BpkIconBulletType, BpkIconBulletSize>
