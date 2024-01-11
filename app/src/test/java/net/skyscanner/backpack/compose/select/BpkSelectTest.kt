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

package net.skyscanner.backpack.compose.select

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isPopup
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.DefaultSelectSample
import net.skyscanner.backpack.demo.compose.DefaultSelectTextOnlySample
import net.skyscanner.backpack.demo.compose.DisabledSelectSample
import net.skyscanner.backpack.demo.compose.DisabledSelectTextOnlySample
import net.skyscanner.backpack.demo.compose.ErrorSelectSample
import net.skyscanner.backpack.demo.compose.ErrorSelectTextOnlySample
import org.junit.Test

class BpkSelectTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        DefaultSelectSample()
    }

    @Test
    fun disabled() = snap {
        DisabledSelectSample()
    }

    @Test
    fun error() = snap {
        ErrorSelectSample()
    }

    @Test
    fun defaultSelectTextOnly() = snap {
        DefaultSelectTextOnlySample()
    }

    @Test
    fun disabledSelectTextOnly() = snap {
        DisabledSelectTextOnlySample()
    }

    @Test
    fun errorSelectTextOnly() = snap {
        ErrorSelectTextOnlySample()
    }

    @Test
    fun dropdownlist() = snap(assertion = {
        onNodeWithText("Placeholder").performClick()
        onNode(isPopup()).assertIsDisplayed()
    }, captureFullScreen = true) {
        DefaultSelectSample(selectedIndex = 0)
    }
}
