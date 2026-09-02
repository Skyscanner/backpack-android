/*
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

package net.skyscanner.backpack.compose.rating.internal

import net.skyscanner.backpack.compose.rating.BpkRatingScale
import org.junit.Assert.assertEquals
import org.junit.Test

class BpkRatingComponentsTest {

    @Test
    fun formatValuePreservesInputPrecision() {
        assertEquals("3.49", formatValue(3.49f, BpkRatingScale.ZeroToFive))
        assertEquals("4.794", formatValue(4.794f, BpkRatingScale.ZeroToFive))
    }

    @Test
    fun formatValueClampsToScale() {
        assertEquals("0.0", formatValue(-1f, BpkRatingScale.ZeroToFive))
        assertEquals("5.0", formatValue(6f, BpkRatingScale.ZeroToFive))
    }
}
