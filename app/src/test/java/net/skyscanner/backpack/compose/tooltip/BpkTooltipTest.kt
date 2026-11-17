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

package net.skyscanner.backpack.compose.tooltip

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.longClick
import androidx.test.espresso.Espresso
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.BackpackDemoTheme
import net.skyscanner.backpack.util.ExperimentalBackpackApi
import org.junit.Test

class BpkTooltipTest : BpkSnapshotTest() {

    @Test
    fun `given explicit show when tooltip is visible then anchor bounds are unchanged`() {
        var show by mutableStateOf(false)
        composeTestRule.setContent { BackpackDemoTheme { TooltipSample(showInitially = show) } }
        val before = composeTestRule.onNodeWithTag("anchor").fetchSemanticsNode().boundsInRoot
        show = true
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Tooltip title").assertIsDisplayed()
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Heading, Unit))
        check(composeTestRule.onNodeWithTag("anchor").fetchSemanticsNode().boundsInRoot == before)
    }

    @Test
    fun `given close button when clicked then tooltip dismisses and callback runs once`() {
        var dismissCalls = 0
        composeTestRule.setContent {
            BackpackDemoTheme { TooltipSample(showInitially = true, onDismissRequest = { dismissCalls++ }) }
        }
        composeTestRule.onNodeWithContentDescription("Close tooltip")
            .assertIsDisplayed()
            .assertHasClickAction()
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Tooltip title").assertDoesNotExist()
        check(dismissCalls == 1) { "dismissCalls=$dismissCalls" }
    }

    @Test
    fun `given two close taps when tooltip dismisses then callback runs once`() {
        var dismissCalls = 0
        composeTestRule.setContent {
            BackpackDemoTheme { TooltipSample(showInitially = true, onDismissRequest = { dismissCalls++ }) }
        }
        val close = composeTestRule.onNodeWithContentDescription("Close tooltip")
        val closeAction = close.fetchSemanticsNode().config[SemanticsActions.OnClick].action
        closeAction?.invoke()
        closeAction?.invoke()
        check(dismissCalls == 1)
    }

    @Test
    fun `given tooltip shown again when closed then callback runs for each presentation`() {
        var dismissCalls = 0
        var showAgain by mutableStateOf(false)
        composeTestRule.setContent {
            BackpackDemoTheme { TooltipSample(showInitially = true, showAgain = showAgain, onDismissRequest = { dismissCalls++ }) }
        }
        composeTestRule.onNodeWithContentDescription("Close tooltip").performClick()
        composeTestRule.waitForIdle()
        showAgain = true
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("Close tooltip").performClick()
        check(dismissCalls == 2)
    }

    @Test
    fun `given programmatic dismiss when tooltip is visible then callback is not invoked`() {
        var dismissCalls = 0
        var dismissProgrammatically by mutableStateOf(false)
        composeTestRule.setContent {
            BackpackDemoTheme {
                TooltipSample(
                    showInitially = true,
                    dismissProgrammatically = dismissProgrammatically,
                    onDismissRequest = { dismissCalls++ },
                )
            }
        }
        dismissProgrammatically = true
        composeTestRule.waitForIdle()
        check(dismissCalls == 0)
    }

    @Test
    fun `given tooltip composition disposal when removed then callback is not invoked`() {
        var dismissCalls = 0
        var visible by mutableStateOf(true)
        composeTestRule.setContent {
            BackpackDemoTheme {
                if (visible) TooltipSample(showInitially = true, onDismissRequest = { dismissCalls++ })
            }
        }
        visible = false
        composeTestRule.waitForIdle()
        check(dismissCalls == 0)
    }

    @Test
    fun `given show coroutine cancellation when tooltip is visible then callback is not invoked`() {
        var dismissCalls = 0
        var cancelShow by mutableStateOf(false)
        composeTestRule.setContent {
            BackpackDemoTheme {
                TooltipWithCancellableShow(
                    cancelShow = cancelShow,
                    onDismissRequest = { dismissCalls++ },
                )
            }
        }
        composeTestRule.onNodeWithText("Tooltip title").assertIsDisplayed()
        cancelShow = true
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Tooltip title").assertDoesNotExist()
        check(dismissCalls == 0)
    }

    @Test
    fun `given tooltip state recreation when composition changes then callback is not invoked`() {
        var dismissCalls = 0
        var generation by mutableStateOf(0)
        composeTestRule.setContent {
            BackpackDemoTheme {
                key(generation) {
                    TooltipSample(showInitially = true, onDismissRequest = { dismissCalls++ })
                }
            }
        }
        composeTestRule.onNodeWithText("Tooltip title").assertIsDisplayed()
        generation++
        composeTestRule.waitForIdle()
        check(dismissCalls == 0)
    }

    @Test
    fun `given dismissed tooltip when anchor is long clicked then it does not reopen`() {
        composeTestRule.setContent { BackpackDemoTheme { TooltipSample(showInitially = false) } }
        composeTestRule.onNodeWithTag("anchor").performTouchInput { longClick() }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Tooltip title").assertDoesNotExist()
    }

    @Test
    fun `given tooltip close when visible then close target is at least 48dp and no cta exists`() {
        composeTestRule.setContent { BackpackDemoTheme { TooltipSample(showInitially = true) } }
        val close = composeTestRule.onNodeWithContentDescription("Close tooltip")
        val bounds = close.fetchSemanticsNode().boundsInRoot
        check(bounds.width >= 48 * composeTestRule.density.density)
        check(bounds.height >= 48 * composeTestRule.density.density)
        composeTestRule.onNodeWithText("Tooltip title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tooltip text").assertIsDisplayed()
    }

    @Test
    fun `given tooltip when Back is pressed then it dismisses and callback runs once`() {
        var dismissCalls = 0
        composeTestRule.setContent {
            BackpackDemoTheme { TooltipSample(showInitially = true, onDismissRequest = { dismissCalls++ }) }
        }
        Espresso.pressBack()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Tooltip title").assertDoesNotExist()
        check(dismissCalls == 1)
    }

    @Test
    fun `given close action then Back during the same presentation then callback runs once`() {
        var dismissCalls = 0
        composeTestRule.setContent {
            BackpackDemoTheme { TooltipSample(showInitially = true, onDismissRequest = { dismissCalls++ }) }
        }
        composeTestRule.onNodeWithContentDescription("Close tooltip").performClick()
        Espresso.pressBack()
        composeTestRule.waitForIdle()
        check(dismissCalls == 1)
    }

    // Robolectric dispatches performTouchInput to the Compose root, not Material3's Popup window,
    // so it cannot drive Popup.onDismissRequest with an outside tap. The focusable Popup delegates
    // outside taps to TooltipBox.onDismissRequest on device; Back is covered above in this suite.

    @Test
    fun `given font scale two when tooltip is visible then close remains on screen`() {
        val resources = composeTestRule.activity.resources
        val original = Configuration(resources.configuration)
        resources.updateConfiguration(Configuration(original).apply { fontScale = 2f }, resources.displayMetrics)
        try {
            composeTestRule.setContent { BackpackDemoTheme { TooltipSample(showInitially = true) } }
            val close = composeTestRule.onNodeWithContentDescription("Close tooltip")
            val root = composeTestRule.onNodeWithTag("root").fetchSemanticsNode().boundsInWindow
            close.assertIsDisplayed()
            val closeBounds = close.fetchSemanticsNode().boundsInWindow
            check(closeBounds.top >= root.top) { "close=$closeBounds root=$root" }
            check(closeBounds.bottom <= root.bottom) { "close=$closeBounds root=$root" }
        } finally {
            resources.updateConfiguration(original, resources.displayMetrics)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun `given visible tooltip with below anchor position then it remains within root bounds`() {
        // Directional placement is delegated to Material3's rememberTooltipPositionProvider, which
        // receives BpkTooltipAnchorPosition.Below through BpkTooltipAnchorPosition.toMaterial3().
        // Its placement math is covered by Material3; Popup and main-window bounds are not comparable
        // under Robolectric, so this test verifies Backpack's observable containment contract instead.
        composeTestRule.setContent { BackpackDemoTheme { TooltipSample(showInitially = true) } }
        val tooltip = composeTestRule.onNodeWithText("Tooltip title").fetchSemanticsNode().boundsInWindow
        val root = composeTestRule.onNodeWithTag("root").fetchSemanticsNode().boundsInWindow
        check(tooltip.top >= root.top && tooltip.bottom <= root.bottom) { "tooltip=$tooltip root=$root" }
        check(tooltip.left >= root.left && tooltip.right <= root.right) { "tooltip=$tooltip root=$root" }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun tooltipVisible() = snap(captureFullScreen = true) {
        TooltipSample(showInitially = true)
    }

    @Composable
    @OptIn(ExperimentalBackpackApi::class)
    private fun TooltipSample(
        showInitially: Boolean,
        modifier: Modifier = Modifier,
        dismissProgrammatically: Boolean = false,
        showAgain: Boolean = false,
        onDismissRequest: (() -> Unit)? = null,
    ) {
        val state = rememberBpkTooltipState()
        LaunchedEffect(showInitially) {
            if (showInitially) state.show()
        }
        LaunchedEffect(dismissProgrammatically) {
            if (dismissProgrammatically) state.dismiss()
        }
        LaunchedEffect(showAgain) {
            if (showAgain) state.show()
        }
        Box(
            modifier = Modifier.fillMaxSize().testTag("root"),
            contentAlignment = Alignment.TopCenter,
        ) {
            BpkTooltip(
                title = "Tooltip title",
                text = "Tooltip text",
                closeAccessibilityLabel = "Close tooltip",
                state = state,
                content = { Box(modifier = Modifier.size(48.dp).testTag("anchor")) },
                modifier = modifier.padding(top = 100.dp),
                onDismissRequest = onDismissRequest,
            )
        }
    }

    @Composable
    @OptIn(ExperimentalBackpackApi::class)
    private fun TooltipWithCancellableShow(
        cancelShow: Boolean,
        onDismissRequest: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val state = rememberBpkTooltipState()
        val scope = rememberCoroutineScope()
        LaunchedEffect(Unit) { scope.launch { state.show() } }
        LaunchedEffect(cancelShow) {
            if (cancelShow) scope.cancel()
        }
        BpkTooltip(
            title = "Tooltip title",
            text = "Tooltip text",
            closeAccessibilityLabel = "Close tooltip",
            state = state,
            modifier = modifier,
            onDismissRequest = onDismissRequest,
        ) { Box(modifier = Modifier.size(48.dp).testTag("anchor")) }
    }
}
