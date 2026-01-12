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

package net.skyscanner.backpack.card

import android.widget.TextView
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.R
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.configuration.BpkConfiguration
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

@RunWith(ParameterizedRobolectricTestRunner::class)
class BpkCardTest(val vdl2Enabled: Boolean) :
    BpkSnapshotTest(if (vdl2Enabled) listOf("VDL2") else listOf("Default")) {

    @Before
    fun setup() {
        // Ensure we start from a known state
        BpkConfiguration.clearConfigs()
        BpkConfiguration.setConfigs(cardConfig = vdl2Enabled)
    }

    @Test
    fun smallCorner() {
        val card = BpkCardView(testContext)
        val text = TextView(testContext)
        text.text = "message"
        card.addView(text)
        snap(card, R.color.bpkSurfaceHighlight)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun largeCorner() {
        val card = BpkCardView(testContext).apply {
            addView(
                TextView(testContext).apply {
                    text = "message"
                },
            )
            cornerStyle = BpkCardView.CornerStyle.LARGE
        }
        snap(card, R.color.bpkSurfaceHighlight)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun noPadding() {
        val card = BpkCardView(testContext)
        val text = TextView(testContext)
        text.text = "message"
        card.addView(text)
        card.padded = false
        snap(card, R.color.bpkSurfaceHighlight)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun onDefault() {
        val card = BpkCardView(testContext)
        val text = TextView(testContext)
        text.text = "message"
        card.addView(text)
        card.cardStyle = BpkCardView.CardStyle.DEFAULT
        snap(card, R.color.bpkSurfaceHighlight)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun onContrast() {
        val card = BpkCardView(testContext)
        val text = TextView(testContext)
        text.text = "message"
        card.addView(text)
        card.cardStyle = BpkCardView.CardStyle.CONTRAST
        snap(card, R.color.bpkSurfaceHighlight)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun noElevation() {
        val card = BpkCardView(testContext)
        val text = TextView(testContext)
        text.text = "message"
        card.addView(text)
        card.elevationLevel = BpkCardView.ElevationLevel.NONE
        snap(card, R.color.bpkSurfaceHighlight)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun unfocused() {
        val card = BpkCardView(testContext)
        val text = TextView(testContext)
        text.text = "message"
        card.addView(text)
        card.elevationLevel = BpkCardView.ElevationLevel.DEFAULT
        snap(card, R.color.bpkSurfaceHighlight)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun focused() {
        val card = BpkCardView(testContext)
        val text = TextView(testContext)
        text.text = "message"
        card.addView(text)
        card.elevationLevel = BpkCardView.ElevationLevel.FOCUSED
        snap(card, R.color.bpkLine)
        snap(card, R.color.bpkSurfaceHighlight)
    }

    companion object {

        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters
        fun flavours(): List<Boolean> = listOf(true, false)
    }
}
