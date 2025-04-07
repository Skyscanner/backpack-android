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

import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.calendar2.BpkCalendarTestCases
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.compose.BpkSnapshotTest
import org.junit.Test

class BpkCalendarTest : BpkSnapshotTest() {

    @Test
    fun default() {
        val controller = createController(BpkCalendarTestCases.Params.Default)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun labeled() {
        val controller = createController(BpkCalendarTestCases.Params.Labeled)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun past() {
        val controller = createController(BpkCalendarTestCases.Params.Past)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun leapFebruary() {
        val controller = createController(BpkCalendarTestCases.Params.LeapFebruary)
        controller.setSelection(BpkCalendarTestCases.Selection.LeapFebruary)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun nonLeapFebruary() {
        val controller = createController(BpkCalendarTestCases.Params.NonLeapFebruary)
        controller.setSelection(BpkCalendarTestCases.Selection.NonLeapFebruary)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun todayIsLastDayOfMonth() {
        val controller = createController(BpkCalendarTestCases.Params.TodayIsLastDayOfMonth)
        controller.setSelection(BpkCalendarTestCases.Selection.TodayIsLastDayOfMonth)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun todayIsNewYear() {
        val controller = createController(BpkCalendarTestCases.Params.TodayIsNewYear)
        controller.setSelection(BpkCalendarTestCases.Selection.TodayIsNewYear)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun withRangeSet() {
        val controller = createController(BpkCalendarTestCases.Params.WithRangeSetProgrammatically)
        controller.setSelection(BpkCalendarTestCases.Selection.WithRangeSetProgrammatically)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun withSingleDaySet() {
        val controller = createController(BpkCalendarTestCases.Params.WithSingleDaySetProgrammatically)
        controller.setSelection(BpkCalendarTestCases.Selection.WithSingleDaySetProgrammatically)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun withDisabledDates() {
        val controller = createController(BpkCalendarTestCases.Params.WithDisabledDates)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun withDisabledDates_SelectSingle() {
        val controller = createController(BpkCalendarTestCases.Params.WithDisabledDates_SelectSingle)
        controller.setSelection(BpkCalendarTestCases.Selection.WithDisabledDates_SelectSingle)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun withDisabledDates_SelectRange() {
        val controller = createController(BpkCalendarTestCases.Params.WithDisabledDates_SelectRange)
        controller.setSelection(BpkCalendarTestCases.Selection.WithDisabledDates_SelectRange)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun withDisabledDates_SelectDisabledDate() {
        val controller = createController(BpkCalendarTestCases.Params.WithDisabledDates_SelectDisabledDate)
        controller.setSelection(BpkCalendarTestCases.Selection.WithDisabledDates_SelectDisabledDate)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun withHighlightedDates() {
        val controller = createController(BpkCalendarTestCases.Params.WithHighLightedDates)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun withHighlightedDates_SelectSingle() {
        val controller = createController(BpkCalendarTestCases.Params.WithHighLightedDates)
        controller.setSelection(BpkCalendarTestCases.Selection.WithHighlightedDates_SelectSingle)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun withHighlightedDates_SelectHighLightedDate() {
        val controller = createController(BpkCalendarTestCases.Params.WithHighLightedDates)
        controller.setSelection(BpkCalendarTestCases.Selection.WithHighlightedDates_SelectHighLightedDate)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun withWholeMonthButtonEnabled() {
        val controller = createController(BpkCalendarTestCases.Params.WithWholeMonthButtonEnabled)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun withWholeMonthSet() {
        val controller = createController(BpkCalendarTestCases.Params.WithWholeMonthSetProgrammatically)
        controller.setSelection(BpkCalendarTestCases.Selection.WithWholeMonthSetProgrammatically)
        snap(controller)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun withYearInMonthLabel() {
        val controller = createController(BpkCalendarTestCases.Params.WithYearInMonthLabel)
        snap(controller)
    }

    private fun snap(controller: BpkCalendarController) =
        snap(padding = 0.dp) {
            BpkCalendar(controller = controller)
        }

    private fun createController(params: CalendarParams): BpkCalendarController =
        BpkCalendarController(
            initialParams = params,
            onSelectionChanged = {},
        )
}
