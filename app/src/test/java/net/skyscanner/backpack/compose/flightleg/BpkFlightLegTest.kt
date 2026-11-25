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

package net.skyscanner.backpack.compose.flightleg

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.DeviceConfigurationOverride
import androidx.compose.ui.test.FontScale
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Aircraft
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.compose.FlightLegWithBottomContentSample
import net.skyscanner.backpack.demo.compose.BasicFlightLegSample
import net.skyscanner.backpack.demo.compose.CompleteFlightLegLongStopsSample
import net.skyscanner.backpack.demo.compose.CompleteFlightLegShortStopsSample
import net.skyscanner.backpack.util.ExperimentalBackpackApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.robolectric.RuntimeEnvironment

class BpkFlightLegTest : BpkSnapshotTest() {

    @Test
    fun `given ltr bottom content when rendered then it aligns with airport codes after the logo`() {
        assertBottomContentAlignment(LayoutDirection.Ltr)
    }

    @Test
    fun `given rtl bottom content when rendered then it aligns with airport codes after the logo`() {
        assertBottomContentAlignment(LayoutDirection.Rtl)
    }

    @Test
    @OptIn(ExperimentalBackpackApi::class)
    fun `given long slot content at large font scale when rendered then it does not overlap warning`() {
        setComplexLegContent(rtl = false, fontScale = 2f)
        assertComplexLegBounds(rtl = false)
    }

    @Test
    fun `given slot in rtl complex leg when rendered then it aligns with codes before existing notices`() {
        setComplexLegContent(rtl = true)
        assertComplexLegBounds(rtl = true)
    }

    @Test
    @OptIn(ExperimentalBackpackApi::class)
    fun `given absent slot when compared to stable overload then leg bounds are identical`() {
        var useExperimental by androidx.compose.runtime.mutableStateOf(false)
        composeTestRule.setContent {
            net.skyscanner.backpack.demo.BackpackDemoTheme {
                Box(modifier = Modifier.testTag("leg")) {
                    if (useExperimental) {
                        BpkFlightLeg(
                            departureArrivalTime = "19:50 - 22:45",
                            description = AnnotatedString("LHR-SIN, partly operated"),
                            stopsInfo = "2 Stops",
                            duration = "7h 55m",
                            contentDescription = "Flight leg",
                            nextDayArrival = "+1",
                            operatedBy = "Partly operated by WestJet",
                            warning = "Change airports in London",
                            bottomContent = null,
                        )
                    } else {
                        BpkFlightLeg(
                            departureArrivalTime = "19:50 - 22:45",
                            description = AnnotatedString("LHR-SIN, partly operated"),
                            stopsInfo = "2 Stops",
                            duration = "7h 55m",
                            contentDescription = "Flight leg",
                            nextDayArrival = "+1",
                            operatedBy = "Partly operated by WestJet",
                            warning = "Change airports in London",
                        )
                    }
                }
            }
        }
        val stableBounds = composeTestRule.onNodeWithTag("leg", useUnmergedTree = true).fetchSemanticsNode().boundsInRoot
        val stableChildren = legChildBounds()
        useExperimental = true
        composeTestRule.waitForIdle()
        val experimentalBounds = composeTestRule.onNodeWithTag("leg", useUnmergedTree = true).fetchSemanticsNode().boundsInRoot
        assertEquals("Null slot must preserve leg width", stableBounds.width, experimentalBounds.width, 0f)
        assertEquals("Null slot must preserve leg height", stableBounds.height, experimentalBounds.height, 0f)
        assertEquals("Null slot must preserve child bounds", stableChildren, legChildBounds())
    }

    @Test
    @OptIn(ExperimentalBackpackApi::class)
    fun `given described leg with slot when rendered then it has one merged accessibility node`() {
        composeTestRule.setContent {
            net.skyscanner.backpack.demo.BackpackDemoTheme {
                BpkFlightLeg(
                    departureArrivalTime = "19:50 - 22:45",
                    description = AnnotatedString("LHR-SIN"),
                    stopsInfo = "Direct",
                    duration = "7h 55m",
                    contentDescription = "Flight leg. Nearby airport",
                    bottomContent = {
                        net.skyscanner.backpack.compose.text.BpkText(
                            text = "Nearby airport",
                            modifier = Modifier.testTag("bottomContent"),
                        )
                    },
                    modifier = Modifier.testTag("leg"),
                )
            }
        }
        val leg = composeTestRule.onNodeWithTag("leg").fetchSemanticsNode()
        val slot = composeTestRule.onNodeWithTag("bottomContent", useUnmergedTree = true).fetchSemanticsNode()
        assertTrue(
            "Bottom content must stay inside the leg: slot=${slot.boundsInRoot}, leg=${leg.boundsInRoot}",
            slot.boundsInRoot.left >= leg.boundsInRoot.left &&
                slot.boundsInRoot.right <= leg.boundsInRoot.right &&
                slot.boundsInRoot.top >= leg.boundsInRoot.top &&
                slot.boundsInRoot.bottom <= leg.boundsInRoot.bottom,
        )
        composeTestRule.onNodeWithTag("leg")
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.ContentDescription, listOf("Flight leg. Nearby airport")))
        composeTestRule.onNodeWithText("Nearby airport", useUnmergedTree = true)
            .assert(SemanticsMatcher.keyNotDefined(SemanticsProperties.ContentDescription))
    }

    @Test
    @OptIn(ExperimentalBackpackApi::class)
    fun `given undescribed leg with slot when rendered then it is hidden from accessibility`() {
        composeTestRule.setContent {
            net.skyscanner.backpack.demo.BackpackDemoTheme {
                BpkFlightLeg(
                    departureArrivalTime = "19:50 - 22:45",
                    description = AnnotatedString("LHR-SIN"),
                    stopsInfo = "Direct",
                    duration = "7h 55m",
                    contentDescription = null,
                    bottomContent = { net.skyscanner.backpack.compose.text.BpkText(text = "Nearby airport") },
                    modifier = Modifier.testTag("leg"),
                )
            }
        }
        composeTestRule.onNodeWithTag("leg", useUnmergedTree = true)
            .assert(SemanticsMatcher.keyIsDefined(SemanticsProperties.HideFromAccessibility))
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun nearbyAirportBadge() = snap(width = 320.dp) {
        FlightLegWithBottomContentSample()
    }

    @Test
    fun simpleLegTest() = snap {
        BasicFlightLegSample()
    }

    @Test
    fun completeLegLongStopsTest() = snap {
        CompleteFlightLegLongStopsSample()
    }

    @Test
    fun completeLegShortStopsTest() = snap {
        CompleteFlightLegShortStopsSample()
    }

    @Test
    fun carrierLogoFillsWholeSpace() = snap {
        BpkFlightLeg(
            departureArrivalTime = "19:50 - 22:45",
            description = AnnotatedString("LHR-SIN, SwissAir"),
            stopsInfo = "Direct",
            duration = "7h 55m",
            contentDescription = null,
            carrierLogoContent = {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BpkTheme.colors.canvas),
                    painter = painterResource(id = R.drawable.sample_icon),
                    contentDescription = null,
                )
            },
        )
    }

    @Test
    fun carrierLogoConstrained() = snap {
        BpkFlightLeg(
            departureArrivalTime = "19:50 - 22:45",
            description = AnnotatedString("LHR-SIN, SwissAir"),
            stopsInfo = "Direct",
            duration = "7h 55m",
            contentDescription = null,
            carrierLogoContent = {
                BpkIcon(
                    icon = BpkIcon.Aircraft,
                    tint = BpkTheme.colors.textOnLight,
                    contentDescription = null,
                )
            },
        )
    }

    @Test
    fun noCarrierLogo() = snap {
        BpkFlightLeg(
            departureArrivalTime = "19:50 - 22:45",
            description = AnnotatedString("LHR-SIN, SwissAir"),
            stopsInfo = "Direct",
            duration = "7h 55m",
            contentDescription = null,
            carrierLogoContent = null,
        )
    }

    @Test
    fun simpleLegTest_landscape() {
        RuntimeEnvironment.setQualifiers("+land")
        snap {
            BasicFlightLegSample()
        }
    }

    @Test
    fun carrierLogoFillsWholeSpace_landscape() {
        RuntimeEnvironment.setQualifiers("+land")
        snap {
            BpkFlightLeg(
                departureArrivalTime = "19:50 - 22:45",
                description = AnnotatedString("LHR-SIN, SwissAir"),
                stopsInfo = "Direct",
                duration = "7h 55m",
                contentDescription = null,
                carrierLogoContent = {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(BpkTheme.colors.canvas),
                        painter = painterResource(id = R.drawable.sample_icon),
                        contentDescription = null,
                    )
                },
            )
        }
    }

    private fun assertBottomContentAlignment(layoutDirection: LayoutDirection) {
        composeTestRule.setContent {
            net.skyscanner.backpack.demo.BackpackDemoTheme {
                CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                    FlightLegWithBottomContentForTest()
                }
            }
        }
        val codes = composeTestRule.onNodeWithText("LHR-SIN, SwissAir", useUnmergedTree = true)
            .fetchSemanticsNode().boundsInRoot
        val slot = composeTestRule.onNodeWithTag("bottomContent", useUnmergedTree = true)
            .fetchSemanticsNode().boundsInRoot
        assertTrue("Bottom content must not overlap airport codes", slot.top >= codes.bottom)
        val startDifference = if (layoutDirection == LayoutDirection.Rtl) slot.right - codes.right else slot.left - codes.left
        assertEquals("Bottom content must align with airport codes at the logical start", 0f, startDifference, 1f)
    }

    @Composable
    @OptIn(ExperimentalBackpackApi::class)
    private fun FlightLegWithBottomContentForTest() {
        BpkFlightLeg(
            departureArrivalTime = "19:50 - 22:45",
            description = AnnotatedString("LHR-SIN, SwissAir"),
            stopsInfo = "Direct",
            duration = "7h 55m",
            contentDescription = null,
            carrierLogoContent = { BpkIcon(icon = BpkIcon.Aircraft, contentDescription = null) },
            bottomContent = {
                net.skyscanner.backpack.compose.text.BpkText(
                    text = "Nearby airport",
                    modifier = Modifier.testTag("bottomContent"),
                )
            },
        )
    }

    @OptIn(ExperimentalBackpackApi::class)
    private fun setComplexLegContent(rtl: Boolean, fontScale: Float = 1f) {
        composeTestRule.setContent {
            net.skyscanner.backpack.demo.BackpackDemoTheme {
                DeviceConfigurationOverride(DeviceConfigurationOverride.FontScale(fontScale)) {
                    CompositionLocalProvider(LocalLayoutDirection provides if (rtl) LayoutDirection.Rtl else LayoutDirection.Ltr) {
                        BpkFlightLeg(
                            departureArrivalTime = "19:50 - 22:45",
                            description = AnnotatedString("LHR-SIN, partly operated overnight direct flight"),
                            stopsInfo = "2 Stops",
                            duration = "7h 55m",
                            contentDescription = null,
                            nextDayArrival = "+1",
                            operatedBy = "Partly operated by WestJet",
                            warning = "Change airports in London",
                            carrierLogoContent = { BpkIcon(icon = BpkIcon.Aircraft, contentDescription = null) },
                            bottomContent = {
                                net.skyscanner.backpack.compose.text.BpkText(
                                    text = "Nearby airport information with deliberately long content",
                                    modifier = Modifier.testTag("bottomContent"),
                                )
                            },
                            modifier = Modifier.width(320.dp).testTag("flightLeg"),
                        )
                    }
                }
            }
        }
    }

    private fun assertComplexLegBounds(rtl: Boolean) {
        fun bounds(text: String) = composeTestRule.onNodeWithText(text, useUnmergedTree = true)
            .fetchSemanticsNode().boundsInRoot
        val codes = bounds("LHR-SIN, partly operated overnight direct flight")
        val operatedBy = bounds("Partly operated by WestJet")
        val warning = bounds("Change airports in London")
        val slot = composeTestRule.onNodeWithTag("bottomContent", useUnmergedTree = true)
            .fetchSemanticsNode().boundsInRoot
        val expectedGap = net.skyscanner.backpack.compose.tokens.BpkSpacing.Sm.value * composeTestRule.density.density
        val leg = composeTestRule.onNodeWithTag("flightLeg").fetchSemanticsNode().boundsInRoot
        assertEquals("Bottom content gap must use Backpack spacing", expectedGap, slot.top - codes.bottom, 1f)
        assertEquals(
            "Bottom content must align with airport codes at the logical start",
            if (rtl) codes.right else codes.left,
            if (rtl) slot.right else slot.left,
            1f,
        )
        assertTrue("Bottom content must not overlap the operator notice", slot.bottom <= operatedBy.top)
        assertTrue("Operator notice must not overlap the warning", operatedBy.bottom <= warning.top)
        assertTrue("Bottom content must stay within the leg width", slot.left >= leg.left && slot.right <= leg.right)
        composeTestRule.onNodeWithTag("bottomContent", useUnmergedTree = true).assertIsDisplayed()
    }

    private fun legChildBounds() = listOf("19:50 - 22:45", "+1", "2 Stops", "7h 55m", "Change airports in London")
        .map { composeTestRule.onNodeWithText(it, useUnmergedTree = true).fetchSemanticsNode().boundsInRoot }
}
