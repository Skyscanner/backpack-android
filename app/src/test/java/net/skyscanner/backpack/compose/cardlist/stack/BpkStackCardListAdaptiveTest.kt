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

package net.skyscanner.backpack.compose.cardlist.stack

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.utils.TABLET_WIDTH
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(ParameterizedRobolectricTestRunner::class)
@Config(qualifiers = "w700dp-h1000dp-mdpi", sdk = [35])
class BpkStackCardListAdaptiveTest(
    private val stackType: StackType,
) : BpkSnapshotTest(listOf(stackType)) {

    @Test
    @Variants(BpkTestVariant.Default)
    fun columnsAdaptWhenWindowCrossesTabletWidth() {
        val windowWidth = mutableStateOf(TABLET_WIDTH)
        snap(
            width = 650.dp,
            assertion = {
                assertSingleColumn()

                runOnIdle { windowWidth.value = TABLET_WINDOW_WIDTH }
                waitForIdle()
                assertTwoColumns()

                runOnIdle { windowWidth.value = TABLET_WIDTH }
                waitForIdle()
                assertSingleColumn()
            },
        ) { WindowWidth(windowWidth.value) { ListFixture() } }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun staysSingleColumnOnTabletByDefault() = snap(
        width = 650.dp,
        assertion = { assertSingleColumn() },
    ) {
        WindowWidth(TABLET_WINDOW_WIDTH) {
            BpkStackCardList(
                title = "Destinations",
                totalCount = 3,
                isInScrollableContainer = stackType.isInScrollableContainer,
            ) { Card(it) }
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun expansionSurvivesEmptyListAndResize() {
        val count = mutableStateOf(3)
        val windowWidth = mutableStateOf(TABLET_WIDTH)
        snap(
            width = 650.dp,
            assertion = {
                onNodeWithTag("card-2").assertDoesNotExist()
                onNodeWithText("More").performClick()
                onNodeWithTag("card-2").assertExists()

                runOnIdle {
                    count.value = 0
                    windowWidth.value = TABLET_WINDOW_WIDTH
                }
                onNodeWithTag("controls").assertIsDisplayed()
                onNodeWithText("Find a destination").assertIsDisplayed()
                onNodeWithTag("card-0").assertDoesNotExist()
                onNodeWithText("Less").assertDoesNotExist()

                runOnIdle { count.value = 3 }
                onNodeWithTag("card-2").assertExists()
                onNodeWithText("Find a destination").assertDoesNotExist()
                onNodeWithText("Less").performClick()
                onNodeWithTag("card-2").assertDoesNotExist()
            },
        ) {
            WindowWidth(windowWidth.value) { ListFixture(count = count.value, expandable = true) }
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun emptyListShowsOnlyControlsContent() = snap(
        assertion = {
            onNodeWithTag("controls").assertIsDisplayed()
            onNodeWithText("Find a destination").assertDoesNotExist()
            onNodeWithTag("card-0").assertDoesNotExist()
        },
    ) { ListFixture(count = 0, empty = false) }

    @Test
    @Variants(BpkTestVariant.Default)
    fun emptyListShowsOnlyEmptyContent() = snap(
        assertion = {
            onNodeWithText("Find a destination").assertIsDisplayed()
            onNodeWithTag("controls").assertDoesNotExist()
            onNodeWithTag("card-0").assertDoesNotExist()
        },
    ) { ListFixture(count = 0, controls = false) }

    @Test
    @Variants(BpkTestVariant.Default)
    fun controlsContentSpansBothColumns() = snap(
        width = 650.dp,
        assertion = {
            val first = boundsOf("card-0")
            val second = boundsOf("card-1")
            val controls = boundsOf("controls")
            assertTrue(controls.width > first.width + second.width)
            assertTrue(controls.bottom <= first.top)
        },
    ) { WindowWidth(TABLET_WINDOW_WIDTH) { ListFixture() } }

    @Test
    @Variants(BpkTestVariant.Default)
    fun singleCardKeepsColumnWidthOnTablet() = snap(
        width = 650.dp,
        assertion = {
            assertTrue(boundsOf("card-0").width < boundsOf("controls").width / 2)
            onNodeWithTag("card-1").assertDoesNotExist()
        },
    ) { WindowWidth(TABLET_WINDOW_WIDTH) { ListFixture(count = 1) } }

    @Test
    @Variants(BpkTestVariant.Default)
    fun largeFontKeepsContentVisibleOnTablet() = snap(
        width = 650.dp,
        assertion = {
            assertTwoColumns()
            onNodeWithText("Filter destinations").assertIsDisplayed()
            onNodeWithText("Card 0").assertIsDisplayed()
            onNodeWithText("Card 1").assertIsDisplayed()
            onNodeWithText("Card 2").assertIsDisplayed()
        },
    ) {
        CompositionLocalProvider(LocalDensity provides Density(LocalDensity.current.density, fontScale = 2f)) {
            WindowWidth(TABLET_WINDOW_WIDTH) { ListFixture() }
        }
    }

    private fun ComposeTestRule.boundsOf(tag: String) = onNodeWithTag(tag).fetchSemanticsNode().boundsInRoot

    private fun ComposeTestRule.assertSingleColumn() {
        assertTrue(boundsOf("card-1").top > boundsOf("card-0").top)
    }

    private fun ComposeTestRule.assertTwoColumns() {
        val first = boundsOf("card-0")
        val second = boundsOf("card-1")
        val last = boundsOf("card-2")
        assertEquals(first.top, second.top, 1f)
        assertEquals(first.width, second.width, 1f)
        assertEquals(first.width, last.width, 1f)
        assertTrue(last.top > first.top)
    }

    @Composable
    private fun ListFixture(
        count: Int = 3,
        expandable: Boolean = false,
        controls: Boolean = true,
        empty: Boolean = true,
    ) {
        BpkStackCardList(
            title = "Destinations",
            totalCount = count,
            isInScrollableContainer = stackType.isInScrollableContainer,
            useTwoColumnsOnTablet = true,
            accessoryStyle = if (expandable && count > 2) {
                BpkStackCardAccessoryStyle.Expand(collapsedCount = 2, expandText = "More", collapsedText = "Less")
            } else null,
            controlsContent = if (controls) {
                { Box(Modifier.fillMaxWidth().testTag("controls")) { BpkText("Filter destinations") } }
            } else null,
            emptyContent = if (empty) {
                { BpkButton(text = "Find a destination", onClick = {}) }
            } else null,
        ) { Card(it) }
    }

    @Composable
    private fun Card(index: Int) {
        Box(Modifier.fillMaxWidth().height(48.dp).testTag("card-$index")) { BpkText("Card $index") }
    }

    @Composable
    private fun WindowWidth(widthDp: Int, content: @Composable () -> Unit) {
        val original = LocalWindowInfo.current
        val widthPx = with(LocalDensity.current) { widthDp.dp.roundToPx() }
        val window = object : WindowInfo by original {
            override val containerSize = IntSize(widthPx, original.containerSize.height)
        }
        CompositionLocalProvider(LocalWindowInfo provides window, content = content)
    }

    enum class StackType(val isInScrollableContainer: Boolean) {
        Column(isInScrollableContainer = true),
        LazyColumn(isInScrollableContainer = false),
    }

    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "{0}")
        fun stackTypes(): List<StackType> = StackType.entries
    }
}

private const val TABLET_WINDOW_WIDTH = TABLET_WIDTH + 1
