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

package net.skyscanner.backpack.configuration

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class BpkConfigurationTest {

    @Test
    fun `chipConfig is initialised by default`() {
        assertNotNull(BpkConfiguration.chipConfig)
        assertEquals(BpkConfiguration.chipConfig, BpkConfiguration.BpkExperimentalComponent.BpkChip())
    }

    @Test
    fun `buttonConfig is initialised by default`() {
        assertNotNull(BpkConfiguration.buttonConfig)
        assertEquals(BpkConfiguration.buttonConfig, BpkConfiguration.BpkExperimentalComponent.BpkButton())
    }

    @Test
    fun `cardConfig is initialised by default`() {
        assertNotNull(BpkConfiguration.cardConfig)
        assertEquals(BpkConfiguration.cardConfig, BpkConfiguration.BpkExperimentalComponent.BpkCard())
    }

    @Test
    fun `badgeConfig is initialised by default`() {
        assertNotNull(BpkConfiguration.badgeConfig)
        assertEquals(BpkConfiguration.badgeConfig, BpkConfiguration.BpkExperimentalComponent.BpkBadge())
    }

    @Test
    fun `typographySet is VDL_2 by default`() {
        assertEquals(BpkConfiguration.BpkTypographySet.VDL_2, BpkConfiguration.typographySet)
    }

    @Test
    fun `iconConfig is initialised by default`() {
        assertNotNull(BpkConfiguration.iconConfig)
    }

    @Test
    fun `setting logger and then performing log logs once only`() {
        var logCount = 0
        BpkConfiguration.logger = { logCount++ }
        BpkConfiguration.performLogging()
        BpkConfiguration.performLogging()
        assertEquals(1, logCount)
    }
}
