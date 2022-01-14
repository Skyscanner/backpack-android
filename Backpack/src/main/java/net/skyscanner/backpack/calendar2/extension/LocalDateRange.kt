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

package net.skyscanner.backpack.calendar2.extension

import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit
import org.threeten.bp.temporal.TemporalUnit

/**
 * Converts range of local dates to [Iterable].
 * @param amount difference between dates in iterator
 * @param unit units of a difference
 */
fun ClosedRange<LocalDate>.toIterable(
  amount: Long = 1L,
  unit: TemporalUnit = ChronoUnit.DAYS,
): Iterable<LocalDate> =
  object : Iterable<LocalDate> {
    override fun iterator(): Iterator<LocalDate> = object : Iterator<LocalDate> {

      var current = start

      override fun hasNext(): Boolean =
        current <= endInclusive

      override fun next(): LocalDate =
        current.also { current = current.plus(amount, unit) }
    }
  }
