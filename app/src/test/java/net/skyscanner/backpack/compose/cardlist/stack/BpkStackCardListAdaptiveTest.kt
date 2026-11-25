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
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.text.BpkText
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(ParameterizedRobolectricTestRunner::class)
@Config(qualifiers = "w700dp-h1000dp-mdpi", sdk = [35])
class BpkStackCardListAdaptiveTest(private val embedded: Boolean) : BpkSnapshotTest() {

    @Test
    fun `given opt in when window crosses 600 dp then columns adapt`() {
        val width = mutableStateOf(599)
        snap(
            width = 650.dp,
            assertion = {
                var first = onNodeWithTag("card-0").fetchSemanticsNode().boundsInRoot
                var second = onNodeWithTag("card-1").fetchSemanticsNode().boundsInRoot
                assertTrue(second.top > first.top)
                for (tabletWidth in listOf(600, 601)) {
                    runOnIdle { width.value = tabletWidth }
                    waitForIdle()
                    first = onNodeWithTag("card-0").fetchSemanticsNode().boundsInRoot
                    second = onNodeWithTag("card-1").fetchSemanticsNode().boundsInRoot
                    val last = onNodeWithTag("card-2").fetchSemanticsNode().boundsInRoot
                    assertEquals(first.top, second.top, 1f)
                    assertEquals(first.width, second.width, 1f)
                    assertEquals(first.width, last.width, 1f)
                    assertTrue(last.top > first.top)
                }
                runOnIdle { width.value = 599 }
                waitForIdle()
                assertTrue(
                    onNodeWithTag("card-1").fetchSemanticsNode().boundsInRoot.top >
                        onNodeWithTag("card-0").fetchSemanticsNode().boundsInRoot.top,
                )
            },
        ) { WindowWidth(width.value) { ListFixture() } }
    }

    @Test
    fun `given false opt in when wide then keeps one column`() = snap(
        width = 650.dp,
        assertion = {
            assertTrue(
                onNodeWithTag("card-1").fetchSemanticsNode().boundsInRoot.top >
                    onNodeWithTag("card-0").fetchSemanticsNode().boundsInRoot.top,
            )
        },
    ) { WindowWidth(600) { ListFixture(adaptive = false) } }

    @Test
    fun `given expanded list when empty and resized then expansion survives`() {
        val count = mutableStateOf(3)
        val width = mutableStateOf(599)
        snap(
            width = 650.dp,
            assertion = {
                onNodeWithTag("card-2").assertDoesNotExist()
                onNodeWithText("More").performClick()
                onNodeWithTag("card-2").assertExists()
                runOnIdle {
                    count.value = 0
                    width.value = 600
                }
                onNodeWithTag("header").assertExists()
                onNodeWithText("Find a destination").performClick()
                onNodeWithTag("card-0").assertDoesNotExist()
                onNodeWithText("Less").assertDoesNotExist()
                runOnIdle { count.value = 3 }
                onNodeWithTag("card-2").assertExists()
                onNodeWithText("Less").assertExists()
                onNodeWithText("Find a destination").assertDoesNotExist()
                onNodeWithText("Less").performClick()
                onNodeWithTag("card-2").assertDoesNotExist()
            },
        ) { WindowWidth(width.value) { ListFixture(count = count.value, expandable = true) } }
    }

    @Test
    fun `given independent optional slots when empty then only supplied slots render`() {
        val header = mutableStateOf(false)
        val empty = mutableStateOf(false)
        snap(
            assertion = {
                for (showHeader in listOf(false, true)) {
                    for (showEmpty in listOf(false, true)) {
                        runOnIdle {
                            header.value = showHeader
                            empty.value = showEmpty
                        }
                        if (showHeader) onNodeWithTag("header").assertExists()
                        else onNodeWithTag("header").assertDoesNotExist()
                        if (showEmpty) onNodeWithText("Find a destination").assertExists()
                        else onNodeWithText("Find a destination").assertDoesNotExist()
                        onNodeWithTag("card-0").assertDoesNotExist()
                    }
                }
            },
        ) { ListFixture(count = 0, header = header.value, empty = empty.value) }
    }

    @Test
    fun `given tablet and odd count when rendered then slots span both columns`() = snap(
        width = 650.dp,
        assertion = {
            val first = onNodeWithTag("card-0").fetchSemanticsNode().boundsInRoot
            val second = onNodeWithTag("card-1").fetchSemanticsNode().boundsInRoot
            val header = onNodeWithTag("header").fetchSemanticsNode().boundsInRoot
            assertTrue(header.width > first.width + second.width)
            assertTrue(header.bottom <= first.top)
        },
    ) { WindowWidth(600) { ListFixture() } }

    @Test
    fun `given one tablet card when rendered then keeps half width`() = snap(
        width = 650.dp,
        assertion = {
            val card = onNodeWithTag("card-0").fetchSemanticsNode().boundsInRoot
            val header = onNodeWithTag("header").fetchSemanticsNode().boundsInRoot
            assertTrue(card.width < header.width / 2)
            onNodeWithTag("card-1").assertDoesNotExist()
        },
    ) { WindowWidth(600) { ListFixture(count = 1) } }

    @Test
    fun `given original positional API when wide then remains single column`() = snap(
        width = 650.dp,
        assertion = {
            assertTrue(
                onNodeWithTag("card-1").fetchSemanticsNode().boundsInRoot.top >
                    onNodeWithTag("card-0").fetchSemanticsNode().boundsInRoot.top,
            )
        },
    ) {
        WindowWidth(600) {
            BpkStackCardList("Destinations", 3, Modifier, null, null, null, true, embedded) { Card(it) }
        }
    }

    @Composable
    private fun ListFixture(
        count: Int = 3,
        adaptive: Boolean = true,
        expandable: Boolean = false,
        header: Boolean = true,
        empty: Boolean = true,
    ) {
        BpkStackCardList(
            title = "Destinations",
            totalCount = count,
            isInScrollableContainer = embedded,
            useTwoColumnsOnTablet = adaptive,
            accessoryStyle = if (expandable && count > 2) {
                BpkStackCardAccessoryStyle.Expand(collapsedCount = 2, expandText = "More", collapsedText = "Less")
            } else null,
            headerContent = if (header) {
                { Box(Modifier.fillMaxWidth().testTag("header")) { BpkText("Filter destinations") } }
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

    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "embedded={0}")
        fun parameters() = listOf(arrayOf(true), arrayOf(false))
    }
}
