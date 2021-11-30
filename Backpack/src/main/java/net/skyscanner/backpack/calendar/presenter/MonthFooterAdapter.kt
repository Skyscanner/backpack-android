/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.calendar.presenter

import android.view.View

/**
 * [MonthFooterAdapter] allows the creation of footer views
 * for [net.skyscanner.backpack.calendar.BpkCalendar].
 *
 * Example:
 *
 *     object: MonthFooterAdapter {
 *       override fun hasFooterForMonth(month: Int, year: Int): Boolean {
 *         return month % 2 == 0
 *       }
 *
 *       override fun onCreateView(month: Int, year: Int): BpkText {
 *         return BpkText(context)
 *       }
 *
 *       override fun onBindView(view: View, month: Int, year: Int) {
 *         view as BpkText
 *         view.text = "Footer view for $month and $year"
 *        }
 *     }
 *
 *
 * @see [BpkCalendarController.monthFooterAdapter]
 */
@Deprecated("Use Calendar2 instead")
interface MonthFooterAdapter {
  /**
   * Indicate that a month/year combination has a footer view.
   *
   * If this method returns true for the month/year combination
   * [onCreateView] and [onBindView] will be called to create/bind the view.
   *
   * @param month The current month
   * @param year The current year
   *
   * @return True if the month has a footer or false otherwise.
   */
  fun hasFooterForMonth(month: Int, year: Int): Boolean

  /**
   * Called when a new instance of a footer view should be created.
   *
   * @see hasFooterForMonth
   *
   * @param month The current month
   * @param year The current year
   *
   * @return a View
   */
  fun onCreateView(month: Int, year: Int): View

  /**
   * Called when data should be bind to `View`.
   *
   * @see hasFooterForMonth
   *
   * @param view The View to bind
   * @param month The current month
   * @param year The current year
   *
   */
  fun onBindView(view: View, month: Int, year: Int): Unit
}
