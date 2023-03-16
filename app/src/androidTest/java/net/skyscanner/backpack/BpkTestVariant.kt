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

package net.skyscanner.backpack

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.test.platform.app.InstrumentationRegistry
import net.skyscanner.backpack.demo.R
import java.util.Locale

enum class BpkTestVariant(val id: String, private val themeId: Int = R.style.AppTheme) {

    Default("default"),
    Themed("themed", R.style.LondonTheme),
    DarkMode("dm"),
    Rtl("rtl"),
    ;

    fun applyToActivity(activity: Activity): Activity =
        activity.apply { setTheme(themeId) }

    fun newActivity(activity: Activity): Activity {
        activity.applyOverrideConfiguration(setup(Configuration()))
        return activity
    }

    fun newContext(context: Context): Context =
        context.createConfigurationContext(setup(Configuration(context.resources.configuration))).apply {
            setTheme(themeId)
        }

    private fun setup(configuration: Configuration) =
        when (this) {
            Default, Themed -> configuration
            DarkMode -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                configuration.apply {
                    uiMode = (uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()) or Configuration.UI_MODE_NIGHT_YES
                }
            }
            Rtl -> {
                val rtlLocale = Locale("ar")
                Locale.setDefault(rtlLocale)
                configuration.apply {
                    setLayoutDirection(rtlLocale)
                    setLocale(rtlLocale)
                }
            }
        }

    companion object {

        val current: BpkTestVariant
            get() = when (InstrumentationRegistry.getArguments().getString("variant")) {
                Default.id -> Default
                Themed.id -> Themed
                DarkMode.id -> DarkMode
                Rtl.id -> Rtl
                else -> Default
            }
    }
}
