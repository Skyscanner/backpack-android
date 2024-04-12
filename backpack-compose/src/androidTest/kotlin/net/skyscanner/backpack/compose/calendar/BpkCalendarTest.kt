/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.calendar

import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import java.util.Locale
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.CalendarSelection
import net.skyscanner.backpack.compose.calendar.internal.CALENDAR_GRID_TEST_TAG
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class BpkCalendarTest {

    private val initialStartDate = LocalDate.of(2019, 1, 2)
    private val initialEndDate = LocalDate.of(2019, 12, 31)
    private val initialRange = initialStartDate..initialEndDate
    private val now = initialStartDate

    private val DefaultSingle = CalendarParams(
        locale = Locale.UK,
        selectionMode = CalendarParams.SelectionMode.Single(
            startSelectionHint = "startSelectionHint",
            noSelectionState = "noSelectionState",
            startSelectionState = "startSelectionState",
        ),
        range = initialRange,
        now = now,
    )

    private val DefaultRange = DefaultSingle.copy(
        selectionMode = CalendarParams.SelectionMode.Range(
            startSelectionHint = "startSelectionHint",
            endSelectionHint = "endSelectionHint",
            noSelectionState = "noSelectionState",
            startSelectionState = "startSelectionState",
            startAndEndSelectionState = "startAndEndSelectionState",
            endSelectionState = "endSelectionState",
            betweenSelectionState = "betweenSelectionState",
        ),
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun withSameStartAndEndDateSelected() = runTest {
        val expected = CalendarSelection.Dates(
            start = LocalDate.of(2019, 1, 17),
            end = LocalDate.of(2019, 1, 17),
        )
        val controller = createController(DefaultRange)

        composeTestRule.setContent { BpkTheme { BpkCalendar(controller) } }

        composeTestRule.onAllNodesWithText("17")
            .onFirst()
            .assertOnClickLabelEquals("startSelectionHint")
            .performClick()

        composeTestRule.onAllNodesWithText("17")
            .onFirst()
            .assertOnClickLabelEquals("endSelectionHint")
            .assertStateDescriptionEquals("startSelectionState")
            .performClick()

        val state = controller.state.first()

        assertEquals(expected, state.selection)
    }

    @Test
    fun withSingleDaySelected() = runTest {
        val expected = CalendarSelection.Single(LocalDate.of(2019, 2, 14))
        val controller = createController(DefaultSingle)

        composeTestRule.setContent { BpkTheme { BpkCalendar(controller) } }

        composeTestRule.onNodeWithTag(CALENDAR_GRID_TEST_TAG)
            .performScrollToIndex(8)

        composeTestRule.onAllNodesWithText("13")
            .onLast()
            .assertOnClickLabelEquals("startSelectionHint")
            .performClick()
            .assertStateDescriptionEquals("startSelectionState")

        composeTestRule.onAllNodesWithText("14")
            .onLast()
            .assertOnClickLabelEquals("startSelectionHint")
            .performClick()
            .assertStateDescriptionEquals("startSelectionState")

        val state = controller.state.first()

        assertEquals(expected, state.selection)
    }

    @Test
    fun withStartAndEndDateSelected() = runTest {
        val expected = CalendarSelection.Dates(
            start = LocalDate.of(2019, 1, 17),
            end = LocalDate.of(2019, 2, 14),
        )
        val controller = createController(DefaultRange)

        composeTestRule.setContent { BpkTheme { BpkCalendar(controller) } }

        composeTestRule.onNodeWithTag(CALENDAR_GRID_TEST_TAG)
            .performScrollToIndex(8)

        composeTestRule.onAllNodesWithText("17")
            .onFirst()
            .assertOnClickLabelEquals("startSelectionHint")
            .performClick()
            .assertStateDescriptionEquals("startSelectionState")

        composeTestRule.onAllNodesWithText("14")
            .onLast()
            .assertOnClickLabelEquals("endSelectionHint")
            .performClick()
            .assertStateDescriptionEquals("endSelectionState")

        val state = controller.state.first()

        assertEquals(expected, state.selection)
    }

    @Test
    fun withStartDateSelected() = runTest {
        val expected = CalendarSelection.Dates(
            start = LocalDate.of(2019, 1, 17),
            end = null,
        )

        val controller = createController(DefaultRange)

        composeTestRule.setContent { BpkTheme { BpkCalendar(controller) } }

        composeTestRule.onAllNodesWithText("17")
            .onFirst()
            .assertOnClickLabelEquals("startSelectionHint")
            .performClick()
            .assertStateDescriptionEquals("startSelectionState")

        val state = controller.state.first()

        assertEquals(expected, state.selection)
    }

    private fun createController(params: CalendarParams): BpkCalendarController =
        BpkCalendarController(initialParams = params, coroutineScope = TestScope(UnconfinedTestDispatcher()))
}

fun SemanticsNodeInteraction.assertOnClickLabelEquals(value: String): SemanticsNodeInteraction = assert(
    SemanticsMatcher("${SemanticsActions.OnClick.name} = [$value]") {
        it.config.getOrNull(SemanticsActions.OnClick)?.label == value
    })

fun SemanticsNodeInteraction.assertStateDescriptionEquals(value: String): SemanticsNodeInteraction = assert(
    SemanticsMatcher("${SemanticsProperties.StateDescription.name} = [$value]") {
        it.config.getOrNull(SemanticsProperties.StateDescription) == value
    })
