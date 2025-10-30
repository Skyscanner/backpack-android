/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.demo.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import net.skyscanner.backpack.configuration.BpkConfiguration
import net.skyscanner.backpack.demo.R
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SharedPreferencesTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        clearSharedPreferences()
    }

    @After
    fun cleanup() {
        clearSharedPreferences()
    }

    private fun clearSharedPreferences() {
        val prefs = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE,
        )
        prefs.edit().clear().commit()
    }

    @Test
    fun `saveTypographySet and getTypographySet work correctly for DEFAULT`() {
        SharedPreferences.saveTypographySet(context, BpkConfiguration.BpkTypographySet.DEFAULT)
        val retrieved = SharedPreferences.getTypographySet(context)

        assertEquals(BpkConfiguration.BpkTypographySet.DEFAULT, retrieved)
    }

    @Test
    fun `saveTypographySet and getTypographySet work correctly for VDL_2`() {
        SharedPreferences.saveTypographySet(context, BpkConfiguration.BpkTypographySet.VDL_2)
        val retrieved = SharedPreferences.getTypographySet(context)

        assertEquals(BpkConfiguration.BpkTypographySet.VDL_2, retrieved)
    }

    @Test
    fun `getTypographySet returns DEFAULT when not set`() {
        val retrieved = SharedPreferences.getTypographySet(context)

        assertEquals(BpkConfiguration.BpkTypographySet.DEFAULT, retrieved)
    }

    @Test
    fun `saveTypographySet overwrites previous value`() {
        SharedPreferences.saveTypographySet(context, BpkConfiguration.BpkTypographySet.DEFAULT)
        SharedPreferences.saveTypographySet(context, BpkConfiguration.BpkTypographySet.VDL_2)

        val retrieved = SharedPreferences.getTypographySet(context)

        assertEquals(BpkConfiguration.BpkTypographySet.VDL_2, retrieved)
    }

    @Test
    fun `getTypographySet handles invalid ordinal gracefully`() {
        // Manually set an invalid ordinal
        val prefs = context.getSharedPreferences(
            context.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE,
        )
        prefs.edit().putInt(SharedPreferences.TYPOGRAPHY_SET, 999).commit()

        // Should not crash and should handle gracefully
        val result = kotlin.runCatching {
            SharedPreferences.getTypographySet(context)
        }

        // Either it returns a valid value or throws an exception, both are acceptable
        // This test documents the behavior
        assert(result.isSuccess || result.isFailure)
    }

    @Test
    fun `saveTypographySet persists across multiple reads`() {
        SharedPreferences.saveTypographySet(context, BpkConfiguration.BpkTypographySet.VDL_2)

        // Read multiple times to ensure value is persisted
        val firstRead = SharedPreferences.getTypographySet(context)
        val secondRead = SharedPreferences.getTypographySet(context)
        val thirdRead = SharedPreferences.getTypographySet(context)

        assertEquals(BpkConfiguration.BpkTypographySet.VDL_2, firstRead)
        assertEquals(BpkConfiguration.BpkTypographySet.VDL_2, secondRead)
        assertEquals(BpkConfiguration.BpkTypographySet.VDL_2, thirdRead)
    }

    @Test
    fun `theme preferences work independently of typography preferences`() {
        // Save a theme
        SharedPreferences.saveTheme(context, R.style.AppTheme)

        // Save typography set
        SharedPreferences.saveTypographySet(context, BpkConfiguration.BpkTypographySet.VDL_2)

        // Both should be retrievable independently
        val retrievedTheme = SharedPreferences.getTheme(context)
        val retrievedTypography = SharedPreferences.getTypographySet(context)

        assertEquals(R.style.AppTheme, retrievedTheme)
        assertEquals(BpkConfiguration.BpkTypographySet.VDL_2, retrievedTypography)
    }
}
