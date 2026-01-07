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

package net.skyscanner.backpack.util

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.R
import net.skyscanner.backpack.configuration.BpkConfiguration
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkTypographySetManagerTest {

    @After
    fun cleanup() {
        BpkConfiguration.clearConfigs()
    }

    @Test
    fun getTypographyTheme_returnsBpkTheme_forDefaultSet() {
        BpkConfiguration.clearConfigs()
        BpkConfiguration.setConfigs()

        val result = BpkTypographySetManager.getTypographyTheme()

        assertEquals(R.style.BpkTheme, result)
    }

    @Test
    fun getTypographyTheme_returnsBpkThemeTypographyVdl2_forVdl2Set() {
        BpkConfiguration.clearConfigs()
        BpkConfiguration.setConfigs(typography = true)

        val result = BpkTypographySetManager.getTypographyTheme()

        assertEquals(R.style.BpkTheme_Typography_Vdl2, result)
    }

    @Test
    fun applyTypographySetToTheme_returnsBaseTheme_forDefaultSet() {
        BpkConfiguration.clearConfigs()
        BpkConfiguration.setConfigs()

        val baseTheme = R.style.BpkTheme
        val result = BpkTypographySetManager.applyTypographySetToTheme(baseTheme)

        assertEquals(baseTheme, result)
    }

    @Test
    fun applyTypographySetToTheme_returnsVdl2Theme_forVdl2Set() {
        BpkConfiguration.clearConfigs()
        BpkConfiguration.setConfigs(typography = true)

        val baseTheme = R.style.BpkTheme
        val result = BpkTypographySetManager.applyTypographySetToTheme(baseTheme)

        assertEquals(R.style.BpkTheme_Typography_Vdl2, result)
    }

    @Test
    fun applyTypographySetToTheme_ignoresBaseTheme_whenVdl2IsActive() {
        BpkConfiguration.clearConfigs()
        BpkConfiguration.setConfigs(typography = true)

        val customBaseTheme = R.style.BpkTheme
        val result = BpkTypographySetManager.applyTypographySetToTheme(customBaseTheme)

        // Should return VDL2 theme regardless of base theme
        assertEquals(R.style.BpkTheme_Typography_Vdl2, result)
    }
}
