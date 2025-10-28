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

package net.skyscanner.backpack.demo.data

import android.content.Context
import androidx.core.content.edit
import net.skyscanner.backpack.configuration.BpkConfiguration
import net.skyscanner.backpack.demo.R

class SharedPreferences {

    companion object {
        val THEME = "theme"
        val TYPOGRAPHY_SET = "typography_set"

        fun getTheme(context: Context): Int {
            return context
                .getSharedPreferences(
                    context.getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE,
                )
                .getInt(THEME, R.style.AppTheme)
        }

        fun getTypographySet(context: Context): BpkConfiguration.BpkTypographySet {
            val ordinal = context
                .getSharedPreferences(
                    context.getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE,
                )
                .getInt(TYPOGRAPHY_SET, BpkConfiguration.BpkTypographySet.DEFAULT.ordinal)
            return BpkConfiguration.BpkTypographySet.entries[ordinal]
        }

        fun saveTypographySet(context: Context, typographySet: BpkConfiguration.BpkTypographySet) {
            val sharedPref = context
                .getSharedPreferences(
                    context.getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE,
                )

            sharedPref.edit(commit = true) {
                putInt(TYPOGRAPHY_SET, typographySet.ordinal)
            }
        }

        fun saveTheme(context: Context, theme: Int) {
            val sharedPref = context
                .getSharedPreferences(
                    context.getString(R.string.preference_file_key),
                    Context.MODE_PRIVATE,
                )

            sharedPref.edit(commit = true) {
                putInt(THEME, theme)
            }
        }
    }
}
