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

package net.skyscanner.backpack.compose.calendar.internal.extension

import java.time.LocalDate
import java.time.YearMonth

internal fun YearMonth.firstDay(): LocalDate =
    atDay(1)

internal fun YearMonth.lastDay(): LocalDate =
    atEndOfMonth()

internal fun YearMonth.prevMonth(): YearMonth =
    minusMonths(1)

internal fun YearMonth.nextMonth(): YearMonth =
    plusMonths(1)
