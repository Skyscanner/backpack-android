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
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDialog
import androidx.test.platform.app.InstrumentationRegistry
import java.util.Locale
import net.skyscanner.backpack.demo.R

sealed class BpkTestVariant(val id: String, val themeId: Int = R.style.AppTheme) {

  open fun applyToActivity(activity: Activity): Activity =
    activity.apply { setTheme(themeId) }

  open fun newActivity(activity: Activity): Activity =
    activity

  open fun newContext(context: Context): Context =
    context.apply {
      setTheme(themeId)
    }

  object Default : BpkTestVariant("default")

  object Themed : BpkTestVariant("themed", R.style.LondonTheme)

  object DarkMode : BpkTestVariant("dm") {

    override fun newActivity(activity: Activity): Activity {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
      val config = Configuration().apply {
        uiMode = uiMode or Configuration.UI_MODE_NIGHT_YES
      }
      activity.applyOverrideConfiguration(config)
      return activity
    }

    override fun newContext(context: Context): Context {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
      if (Looper.myLooper() == null) {
        Looper.prepare()
      }
      val config = Configuration(context.resources.configuration).apply {
        uiMode = uiMode or Configuration.UI_MODE_NIGHT_YES
      }
      val dialog = AppCompatDialog(super.newContext(context.createConfigurationContext(config)))
      return dialog.context
    }
  }

  object Rtl : BpkTestVariant("rtl") {

    private val rtlLocale = Locale("ar")

    override fun newActivity(activity: Activity): Activity {
      Locale.setDefault(rtlLocale)
      val config = Configuration().apply {
        setLayoutDirection(rtlLocale)
        setLocale(rtlLocale)
      }
      activity.applyOverrideConfiguration(config)
      return activity
    }

    override fun newContext(context: Context): Context {
      Locale.setDefault(rtlLocale)
      val config = Configuration(context.resources.configuration).apply {
        setLayoutDirection(rtlLocale)
        setLocale(rtlLocale)
      }
      return super.newContext(context.createConfigurationContext(config))
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
