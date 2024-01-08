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

package net.skyscanner.backpack.map.internal

import android.view.View
import androidx.annotation.DrawableRes
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.R
import org.junit.Test

class BpkMapMarkerTest : BpkSnapshotTest() {

    @Test
    fun default() {
        val view = createMarker(0, true)
        snap(view)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun selected() {
        val view = createMarker(0, true)
        view.isSelected = true
        snap(view)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun noPointer() {
        val view = createMarker(0, false)
        snap(view)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun withIcon() {
        val view = createMarker(R.drawable.bpk_map, true)
        snap(view)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun withIcon_Selected() {
        val view = createMarker(R.drawable.bpk_map, true)
        view.isSelected = true
        snap(view)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun withIcon_NoPointer() {
        val view = createMarker(R.drawable.bpk_map, false)
        snap(view)
    }

    private fun createMarker(@DrawableRes icon: Int, showPointer: Boolean): View {
        return BpkMapMarkersTestBridge.create(testContext, "Title", icon, showPointer)
    }
}
