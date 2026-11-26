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
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.test.espresso.Espresso
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.BackpackDemoTheme
import net.skyscanner.backpack.util.ExperimentalBackpackApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

class BpkTooltipTest : BpkSnapshotTest() {

    // Popup windows read Android configuration separately from the anchor composition.
    private var initialFontScale = 1f

    @Before
    fun rememberFontScale() {
        initialFontScale = RuntimeEnvironment.getFontScale()
    }

    @After
    fun restoreFontScale() {
        RuntimeEnvironment.setFontScale(initialFontScale)
    }

    @Test
    fun `given explicit show when tooltip is visible then anchor bounds are unchanged`() {
        var show by mutableStateOf(false)
        composeTestRule.setContent { BackpackDemoTheme { TooltipSample(showInitially = show) } }
        val before = composeTestRule.onNodeWithTag("anchor").fetchSemanticsNode().boundsInRoot
        show = true
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Tooltip title").assertIsDisplayed()
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Heading, Unit))
        assertEquals("Showing the tooltip must not move its anchor", before, composeTestRule.onNodeWithTag("anchor").fetchSemanticsNode().boundsInRoot)
    }

    @Test
    fun `given visible tooltip when semantics are merged then text is grouped and close remains independent`() {
        composeTestRule.setContent { BackpackDemoTheme { TooltipSample(showInitially = true) } }

        composeTestRule.onNode(hasText("Tooltip title") and hasText("Tooltip text")).assertExists()
        composeTestRule.onNodeWithContentDescription("Close tooltip").assertHasClickAction()
    }

    @Test
    fun `given long title when tooltip opens then close button retains its full touch target`() {
        composeTestRule.setContent {
            BackpackDemoTheme {
                TooltipSample(showInitially = true, title = "A very long translated nearby airports explanation title")
            }
        }
        val close = composeTestRule.onNodeWithContentDescription("Close tooltip")
        val bounds = close.fetchSemanticsNode().boundsInRoot
        assertTrue("Close target must be at least 48dp wide: $bounds", bounds.width >= 48 * composeTestRule.density.density)
        close.assertIsDisplayed().performClick()
        composeTestRule.waitForIdle()
        close.assertDoesNotExist()
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
        assertEquals("User dismissal must notify exactly once", 1, dismissCalls)
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
        assertEquals("User dismissal must notify exactly once", 1, dismissCalls)
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
        assertEquals("Each presentation must notify once", 2, dismissCalls)
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
        assertEquals("Non-user dismissal must not invoke the callback", 0, dismissCalls)
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
        assertEquals("Non-user dismissal must not invoke the callback", 0, dismissCalls)
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
        assertEquals("Non-user dismissal must not invoke the callback", 0, dismissCalls)
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
        assertEquals("Non-user dismissal must not invoke the callback", 0, dismissCalls)
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
        assertTrue("Close target must be at least 48dp wide: $bounds", bounds.width >= 48 * composeTestRule.density.density)
        assertTrue("Close target must be at least 48dp high: $bounds", bounds.height >= 48 * composeTestRule.density.density)
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
        assertEquals("User dismissal must notify exactly once", 1, dismissCalls)
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
        assertEquals("User dismissal must notify exactly once", 1, dismissCalls)
    }

    // Robolectric dispatches performTouchInput to the Compose root, not Material3's Popup window,
    // so it cannot drive Popup.onDismissRequest with an outside tap. The focusable Popup delegates
    // outside taps to TooltipBox.onDismissRequest on device; Back is covered above in this suite.

    @Test
    fun `given font scale two when tooltip is visible then close remains on screen`() {
        RuntimeEnvironment.setFontScale(2f)
        composeTestRule.setContent { BackpackDemoTheme { TooltipSample(showInitially = true) } }
        val close = composeTestRule.onNodeWithContentDescription("Close tooltip")
        val root = composeTestRule.onNodeWithTag("root").fetchSemanticsNode().boundsInWindow
        close.assertIsDisplayed()
        val closeBounds = close.fetchSemanticsNode().boundsInWindow
        assertTrue("Close target must not extend above the root: close=$closeBounds root=$root", closeBounds.top >= root.top)
        assertTrue("Close target must not extend below the root: close=$closeBounds root=$root", closeBounds.bottom <= root.bottom)
    }

    @Test
    fun `given a tooltip body when rendered then it uses Backpack body typography`() {
        var expectedStyle = TextStyle.Default
        composeTestRule.setContent {
            BackpackDemoTheme {
                expectedStyle = BpkTheme.typography.bodyDefault
                TooltipSample(showInitially = true)
            }
        }

        val actualStyle = textLayout("Tooltip text").layoutInput.style
        assertEquals("Body font size must use Backpack", expectedStyle.fontSize, actualStyle.fontSize)
        assertEquals("Body font family must use Backpack", expectedStyle.fontFamily, actualStyle.fontFamily)
        assertEquals("Body line height must use Backpack", expectedStyle.lineHeight, actualStyle.lineHeight)
        val range = composeTestRule.onNode(hasScrollAction()).fetchSemanticsNode().config[SemanticsProperties.VerticalScrollAxisRange]
        assertEquals("Short content must fit without scrolling", 0f, range.maxValue(), 0f)
    }

    @Test
    fun `given a long message at font scale two when shown then all text is reachable and close remains fixed`() {
        assertLongMessageIsReadable()
    }

    @Test
    @Config(qualifiers = "w640dp-h360dp-land")
    fun `given a small landscape window at font scale two when shown then all text is reachable and close remains fixed`() {
        assertLongMessageIsReadable(landscape = true)
    }

    private fun assertLongMessageIsReadable(landscape: Boolean = false) {
        val title = "Include nearby airports near your departure and arrival locations"
        val message = List(6) { "Find cheaper options by expanding your search to nearby airports." }.joinToString(" ")
        RuntimeEnvironment.setFontScale(2f)
        composeTestRule.setContent { BackpackDemoTheme { TooltipSample(showInitially = true, title = title, text = message) } }
        val root = composeTestRule.onNodeWithTag("root").fetchSemanticsNode().boundsInWindow
        assertEquals("The test must use the intended window orientation: $root", landscape, root.width > root.height)
        val layout = textLayout(message)
        assertEquals("The test must use 200% font scale", 2f, layout.layoutInput.density.fontScale, 0f)
        assertFalse("Message clipped: height=${layout.size.height}, lastLineBottom=${layout.getLineBottom(layout.lineCount - 1)}", layout.hasVisualOverflow)
        assertFalse("The title must be laid out fully", textLayout(title).hasVisualOverflow)
        val close = composeTestRule.onNodeWithContentDescription("Close tooltip").assertIsDisplayed()
        val closeBefore = close.fetchSemanticsNode().boundsInWindow
        val scroll = composeTestRule.onNode(hasScrollAction()).assertIsDisplayed()
        val viewport = scroll.fetchSemanticsNode().boundsInWindow
        val lastLineHeight = layout.getLineBottom(layout.lineCount - 1) - layout.getLineTop(layout.lineCount - 1)
        assertTrue("The viewport must show at least one complete line: $viewport", viewport.height >= lastLineHeight)
        val range = scroll.fetchSemanticsNode().config[SemanticsProperties.VerticalScrollAxisRange]
        assertTrue("Long text must overflow the viewport and be scrollable", range.maxValue() > 0f)

        scroll.performSemanticsAction(SemanticsActions.ScrollBy) { it(0f, range.maxValue()) }
        composeTestRule.waitForIdle()

        assertEquals("The end of the message must be reachable", range.maxValue(), range.value(), 1f)
        assertEquals("Scrolling must keep the close action fixed", closeBefore, close.fetchSemanticsNode().boundsInWindow)
        close.assertIsDisplayed().performClick()
        composeTestRule.waitForIdle()
        close.assertDoesNotExist()
    }

    private fun textLayout(text: String): TextLayoutResult {
        val results = mutableListOf<TextLayoutResult>()
        composeTestRule.onNodeWithText(text, useUnmergedTree = true).performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(results) }
        assertEquals("The text must expose exactly one layout result", 1, results.size)
        return results.single()
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
        assertTrue("Tooltip must fit vertically: tooltip=$tooltip root=$root", tooltip.top >= root.top && tooltip.bottom <= root.bottom)
        assertTrue("Tooltip must fit horizontally: tooltip=$tooltip root=$root", tooltip.left >= root.left && tooltip.right <= root.right)
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
        title: String = "Tooltip title",
        text: String = "Tooltip text",
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
                title = title,
                text = text,
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
