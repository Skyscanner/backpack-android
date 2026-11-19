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

package net.skyscanner.backpack.compose.switch

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.click
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.LayoutDirection
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.BackpackDemoTheme
import net.skyscanner.backpack.demo.compose.CustomContentSwitchExample
import net.skyscanner.backpack.demo.compose.DefaultCheckedSwitchExample
import net.skyscanner.backpack.demo.compose.DefaultDisabledCheckedSwitchExample
import net.skyscanner.backpack.demo.compose.DefaultDisabledUncheckedSwitchExample
import net.skyscanner.backpack.demo.compose.DefaultUncheckedSwitchExample
import net.skyscanner.backpack.demo.compose.OnContrastCheckedSwitchExample
import net.skyscanner.backpack.demo.compose.OnContrastDisabledCheckedSwitchExample
import net.skyscanner.backpack.demo.compose.OnContrastDisabledUncheckedSwitchExample
import net.skyscanner.backpack.demo.compose.OnContrastUncheckedSwitchExample
import net.skyscanner.backpack.demo.compose.SwitchWithHelpExample
import net.skyscanner.backpack.util.ExperimentalBackpackApi
import org.junit.Test

class BpkSwitchTest : BpkSnapshotTest() {

    @Test
    @OptIn(ExperimentalBackpackApi::class)
    fun `given padded full width help switch when physically tapped then help and toggle are independent`() {
        var checked by mutableStateOf(false)
        var helpClicks = 0
        composeTestRule.setContent {
            BackpackDemoTheme {
                BpkSwitch(
                    text = "Include nearby airports",
                    checked = checked,
                    onCheckedChange = { checked = it },
                    helpAction = BpkSwitchHelpAction("Help") { helpClicks++ },
                    shouldTruncate = false,
                    textStyle = BpkTheme.typography.label1,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = BpkSpacing.Lg, vertical = BpkSpacing.Base)
                        .testTag("switch"),
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Help").performTouchInput {
            click(androidx.compose.ui.geometry.Offset(4.dp.toPx(), height / 2f))
        }
        check(helpClicks == 1) { "Info icon tap must open help" }
        check(!checked) { "Info icon tap must not toggle the switch" }
        composeTestRule.onNodeWithTag("switch").performTouchInput {
            click(androidx.compose.ui.geometry.Offset(width - 26.dp.toPx(), height / 2f))
        }
        check(checked) { "Visual switch center tap must toggle" }
        check(helpClicks == 1)
    }

    @Test
    @OptIn(ExperimentalBackpackApi::class)
    fun `given help action when clicked then it is one independent button and does not toggle`() {
        var checked by mutableStateOf(false)
        var helpClicks = 0
        composeTestRule.setContent {
            BackpackDemoTheme {
                BpkSwitch(
                    text = "Wifi",
                    checked = checked,
                    onCheckedChange = { checked = it },
                    helpAction = BpkSwitchHelpAction(
                        contentDescription = "Help",
                        onClick = { helpClicks++ },
                    ),
                )
            }
        }

        composeTestRule.onAllNodesWithContentDescription("Help")
            .assertCountEquals(1)
        val help = composeTestRule.onNodeWithContentDescription("Help")
        help
            .assertIsDisplayed()
            .assertHasClickAction()
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
            .performClick()

        check(helpClicks == 1)
        check(!checked)
        help.assert(SemanticsMatcher.keyNotDefined(SemanticsProperties.ToggleableState))
        composeTestRule.onNodeWithText("Wifi")
            .assert(SemanticsMatcher.keyNotDefined(SemanticsProperties.ContentDescription))
        val bounds = help.fetchSemanticsNode().boundsInRoot
        check(bounds.width >= 48 * composeTestRule.density.density)
        check(bounds.height >= 48 * composeTestRule.density.density)
    }

    @Test
    @OptIn(ExperimentalBackpackApi::class)
    fun `given label or switch action when toggled then it does not invoke help`() {
        var checked by mutableStateOf(false)
        var helpClicks = 0
        composeTestRule.setContent {
            BackpackDemoTheme {
                BpkSwitch(
                    text = "Wifi",
                    checked = checked,
                    onCheckedChange = { checked = it },
                    helpAction = BpkSwitchHelpAction("Help") { helpClicks++ },
                    modifier = Modifier.testTag("switch"),
                )
            }
        }

        composeTestRule.onNodeWithText("Wifi").performClick()
        check(checked)
        check(helpClicks == 0)
        composeTestRule.onNodeWithTag("switch").performTouchInput {
            click(androidx.compose.ui.geometry.Offset(width - 1f, height / 2f))
        }
        check(!checked)
        check(helpClicks == 0)
        composeTestRule.onNodeWithText("Wifi")
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Switch))
        composeTestRule.onNodeWithTag("switch").assertIsDisplayed()
        composeTestRule.onAllNodes(
            SemanticsMatcher.keyIsDefined(SemanticsActions.OnClick) and hasAnyAncestor(hasTestTag("switch")),
        ).assertCountEquals(2)
    }

    @Test
    @OptIn(ExperimentalBackpackApi::class)
    fun `given visual switch handler changes when tapped then latest handler is invoked`() {
        var oldHandlerCalls = 0
        var newHandlerCalls = 0
        var useNewHandler by mutableStateOf(false)
        composeTestRule.setContent {
            BackpackDemoTheme {
                BpkSwitch(
                    text = "Wifi",
                    checked = false,
                    onCheckedChange = if (useNewHandler) {
                        { newHandlerCalls++ }
                    } else {
                        { oldHandlerCalls++ }
                    },
                    helpAction = BpkSwitchHelpAction("Help") {},
                    modifier = Modifier.testTag("switch"),
                )
            }
        }

        useNewHandler = true
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("switch").performTouchInput {
            click(androidx.compose.ui.geometry.Offset(width - 1f, height / 2f))
        }

        check(oldHandlerCalls == 0)
        check(newHandlerCalls == 1)
    }

    @Test
    @OptIn(ExperimentalBackpackApi::class)
    fun `given ltr or rtl label when help is shown then help is adjacent to the label end`() {
        var rtl by mutableStateOf(false)
        composeTestRule.setContent {
            BackpackDemoTheme {
                CompositionLocalProvider(
                    LocalLayoutDirection provides if (rtl) {
                        LayoutDirection.Rtl
                    } else {
                        LayoutDirection.Ltr
                    },
                ) {
                    BpkSwitch(
                        text = "A deliberately long label that wraps",
                        checked = false,
                        onCheckedChange = {},
                        helpAction = BpkSwitchHelpAction("Help") {},
                        modifier = Modifier.width(220.dp),
                        shouldTruncate = false,
                    )
                }
            }
        }
        fun assertAdjacent() {
            val label = composeTestRule.onNodeWithText("A deliberately long label that wraps", useUnmergedTree = true)
                .fetchSemanticsNode().boundsInRoot
            val help = composeTestRule.onNodeWithContentDescription("Help").fetchSemanticsNode().boundsInRoot
            if (rtl) check(help.right <= label.left + 1f) else check(help.left >= label.right - 1f)
        }
        assertAdjacent()
        rtl = true
        composeTestRule.waitForIdle()
        assertAdjacent()
    }

    @Test
    fun switchWithHelp() = snap(width = 320.dp) {
        SwitchWithHelpExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    @OptIn(ExperimentalBackpackApi::class)
    fun switchWithHelpOnContrast() = snap(width = 320.dp) {
        BpkSwitch(
            text = "Help on contrast",
            checked = false,
            onCheckedChange = {},
            helpAction = BpkSwitchHelpAction("Help") {},
            style = BpkSwitchStyle.OnContrast,
            modifier = Modifier.fillMaxWidth().background(BpkTheme.colors.surfaceContrast).padding(BpkSpacing.Base),
        )
    }

    @Test
    fun switchWithHelpShortCopy() = snap(width = 320.dp) {
        SwitchWithHelpExample(longHelpContent = false)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun switchWithHelpShortSheet() = snap(width = 320.dp, captureFullScreen = true) {
        SwitchWithHelpExample(longHelpContent = false, showHelpInitially = true)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun switchWithHelpLongSheet() = snap(width = 320.dp, captureFullScreen = true) {
        SwitchWithHelpExample(showHelpInitially = true)
    }

    @Test
    fun `given short help copy when opened then sheet content fits without scrolling`() {
        composeTestRule.setContent {
            BackpackDemoTheme {
                SwitchWithHelpExample(longHelpContent = false, showHelpInitially = true)
            }
        }
        val scroll = composeTestRule.onNodeWithTag("switchHelpScroll")
        val scrollBounds = scroll.fetchSemanticsNode().boundsInRoot
        val titleBounds = composeTestRule.onNodeWithTag("switchHelpTitle").fetchSemanticsNode().boundsInRoot
        scroll.assert(SemanticsMatcher.keyIsDefined(SemanticsActions.ScrollBy))
        check(scrollBounds.top > 0f)
        check(titleBounds.top >= scrollBounds.top)
        val titleTop = titleBounds.top
        scroll.performSemanticsAction(SemanticsActions.ScrollBy) { action -> action(0f, 100f) }
        composeTestRule.waitForIdle()
        check(composeTestRule.onNodeWithTag("switchHelpTitle").fetchSemanticsNode().boundsInRoot.top == titleTop)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun switchWithHelpLongWrappingLabel() = snap(width = 240.dp) {
        HelpRow(label = "A deliberately long label that wraps over multiple lines")
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun switchWithHelpChecked() = snap(width = 240.dp) {
        HelpRow(checked = true)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun switchWithHelpDisabled() = snap(width = 240.dp) {
        HelpRow(enabled = false)
    }

    @Test
    fun switchWithHelpAtLargeFontScale() {
        withLargeFontScale {
            snap(width = 240.dp) {
                HelpRow(label = "A deliberately long label that wraps over multiple lines")
            }
        }
    }

    @Test
    fun `given font scale two when the help row wraps then all controls stay visible in ltr and rtl`() {
        withLargeFontScale {
            var checked by mutableStateOf(false)
            var rtl by mutableStateOf(false)
            composeTestRule.setContent {
                BackpackDemoTheme {
                    CompositionLocalProvider(
                        LocalLayoutDirection provides if (rtl) LayoutDirection.Rtl else LayoutDirection.Ltr,
                    ) {
                        HelpRow(
                            label = "A deliberately long label that wraps over multiple lines",
                            checked = checked,
                            onCheckedChange = { checked = it },
                        )
                    }
                }
            }
            assertHelpRowBounds(rtl = false) { checked }
            checked = false
            rtl = true
            composeTestRule.waitForIdle()
            assertHelpRowBounds(rtl = true) { checked }
        }
    }

    @Test
    fun `given repeated completed sheet dismissals when toggling then switch remains mounted and stateful`() {
        composeTestRule.setContent {
            BackpackDemoTheme {
                SwitchWithHelpExample()
            }
        }

        composeTestRule.onNodeWithText("Default").performClick()
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.ToggleableState, ToggleableState.On))
        repeat(2) {
            composeTestRule.onNodeWithTag("switchWithHelp").assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription("Help").performClick()
            composeTestRule.onNodeWithContentDescription("Dismiss").assertIsDisplayed().performClick()
            composeTestRule.waitForIdle()
            composeTestRule.onNodeWithContentDescription("Dismiss").assertDoesNotExist()
            composeTestRule.onNodeWithText("Default")
                .assert(SemanticsMatcher.expectValue(SemanticsProperties.ToggleableState, ToggleableState.On))
        }
        composeTestRule.onNodeWithTag("switchWithHelp").assertIsDisplayed()
    }

    @Test
    fun `given font scale two when help sheet opens then title close and scroll content remain visible`() {
        withLargeFontScale {
            composeTestRule.setContent { BackpackDemoTheme { SwitchWithHelpExample() } }
            composeTestRule.onNodeWithText("Default").assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription("Help").performClick()
            composeTestRule.onNodeWithTag("switchHelpTitle").assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription("Dismiss").assertIsDisplayed()
            val scroll = composeTestRule.onNodeWithTag("switchHelpScroll")
                .assert(SemanticsMatcher.keyIsDefined(SemanticsActions.ScrollBy))
            val messageBeforeScroll = composeTestRule.onNodeWithTag("switchHelpMessage")
                .fetchSemanticsNode().boundsInRoot.top
            scroll.performSemanticsAction(SemanticsActions.ScrollBy) { action ->
                action(0f, 100f)
            }
            composeTestRule.waitForIdle()
            val messageAfterScroll = composeTestRule.onNodeWithTag("switchHelpMessage")
                .fetchSemanticsNode().boundsInRoot.top
            check(messageAfterScroll < messageBeforeScroll)
            composeTestRule.onNodeWithContentDescription("Dismiss").assertIsDisplayed()
        }
    }

    @Test
    fun defaultUnchecked() = snap(width = 200.dp) {
        DefaultUncheckedSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun defaultChecked() = snap(width = 200.dp) {
        DefaultCheckedSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun defaultDisabledUnchecked() = snap(width = 200.dp) {
        DefaultDisabledUncheckedSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun defaultDisabledChecked() = snap(width = 200.dp) {
        DefaultDisabledCheckedSwitchExample()
    }

    @Test
    fun onContrastUnchecked() = snap(width = 200.dp, background = { BpkTheme.colors.surfaceContrast }) {
        OnContrastUncheckedSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun onContrastChecked() = snap(width = 200.dp, background = { BpkTheme.colors.surfaceContrast }) {
        OnContrastCheckedSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun onContrastDisabledUnchecked() = snap(width = 200.dp, background = { BpkTheme.colors.surfaceContrast }) {
        OnContrastDisabledUncheckedSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun onContrastDisabledChecked() = snap(width = 200.dp, background = { BpkTheme.colors.surfaceContrast }) {
        OnContrastDisabledCheckedSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun customContent() = snap(width = 200.dp) {
        CustomContentSwitchExample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun announceStateFalseSuppressesStateDescription() {
        composeTestRule.setContent {
            BackpackDemoTheme {
                BpkSwitch(
                    text = "Wifi",
                    checked = true,
                    onCheckedChange = {},
                    announceState = false,
                    modifier = Modifier.testTag("switch"),
                )
            }
        }
        composeTestRule.onNodeWithTag("switch")
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, SUPPRESSED_STATE_DESCRIPTION))
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun announceStateFalseSuppressesStateDescriptionWhenReadOnly() {
        composeTestRule.setContent {
            BackpackDemoTheme {
                BpkSwitch(
                    text = "Wifi",
                    checked = true,
                    onCheckedChange = null,
                    announceState = false,
                    modifier = Modifier.testTag("switch"),
                )
            }
        }
        composeTestRule.onNodeWithTag("switch")
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, SUPPRESSED_STATE_DESCRIPTION))
    }

    @Composable
    @OptIn(ExperimentalBackpackApi::class)
    private fun HelpRow(
        label: String = "Semibold help row",
        checked: Boolean = false,
        enabled: Boolean = true,
        onCheckedChange: (Boolean) -> Unit = {},
    ) {
        BpkSwitch(
            text = label,
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            helpAction = BpkSwitchHelpAction("Help") {},
            modifier = Modifier.testTag("helpRow"),
            shouldTruncate = false,
            textStyle = BpkTheme.typography.label2,
        )
    }

    private fun assertHelpRowBounds(rtl: Boolean, checked: () -> Boolean) {
        val row = composeTestRule.onNodeWithTag("helpRow").fetchSemanticsNode().boundsInRoot
        val label = composeTestRule.onNodeWithText(
            "A deliberately long label that wraps over multiple lines",
            useUnmergedTree = true,
        )
            .fetchSemanticsNode().boundsInRoot
        val help = composeTestRule.onNodeWithContentDescription("Help").fetchSemanticsNode().boundsInRoot
        composeTestRule.onNodeWithText(
            "A deliberately long label that wraps over multiple lines",
            useUnmergedTree = true,
        ).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Help").assertIsDisplayed()
        check(help.left >= row.left && help.right <= row.right)
        check(label.left >= row.left && label.right <= row.right)
        if (rtl) check(help.right <= label.left + 1f) else check(help.left >= label.right - 1f)
        // The touch target abuts the label; centring the icon supplies its visual inset.
        val expectedGap = BpkSpacing.None.value * composeTestRule.density.density
        val actualGap = if (rtl) label.left - help.right else help.left - label.right
        check(kotlin.math.abs(actualGap - expectedGap) <= 1f)
        composeTestRule.onNodeWithTag("helpRow").performTouchInput {
            click(
                androidx.compose.ui.geometry.Offset(
                    x = if (rtl) 1f else width - 1f,
                    y = height / 2f,
                ),
            )
        }
        check(checked())
    }

    private fun withLargeFontScale(block: () -> Unit) {
        val resources = composeTestRule.activity.resources
        val original = Configuration(resources.configuration)
        resources.updateConfiguration(Configuration(original).apply { fontScale = 2f }, resources.displayMetrics)
        try {
            block()
        } finally {
            resources.updateConfiguration(original, resources.displayMetrics)
        }
    }
}
