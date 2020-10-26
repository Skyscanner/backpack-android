/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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

package net.skyscanner.backpack.demo.data

import android.annotation.SuppressLint
import android.content.Context
import net.skyscanner.backpack.demo.R

class SharedPreferences {

  companion object {
    val SHOULD_HIGHLIGHT = "highlight"
    val THEME = "theme"

    fun shouldHighlight(context: Context): Boolean {
      return context
        .getSharedPreferences(
          context.getString(R.string.preference_file_key),
          Context.MODE_PRIVATE
        )
        .getBoolean(SHOULD_HIGHLIGHT, false)
    }

    @SuppressLint("ApplySharedPref")
    fun saveHighlightState(context: Context, state: Boolean) {
      val sharedPref = context
        .getSharedPreferences(
          context.getString(R.string.preference_file_key),
          Context.MODE_PRIVATE
        )
      with(sharedPref.edit()) {
        putBoolean(SHOULD_HIGHLIGHT, state)
        commit()
      }
    }

    fun getTheme(context: Context): Int {
      return context
        .getSharedPreferences(
          context.getString(R.string.preference_file_key),
          Context.MODE_PRIVATE
        )
        .getInt(THEME, R.style.AppTheme)
    }

    fun saveTheme(context: Context, theme: Int) {
      val sharedPref = context
        .getSharedPreferences(
          context.getString(R.string.preference_file_key),
          Context.MODE_PRIVATE
        )

      with(sharedPref.edit()) {
        putInt(THEME, theme)
        commit()
      }
    }
  }
}
