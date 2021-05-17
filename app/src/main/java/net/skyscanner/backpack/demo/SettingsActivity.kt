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

package net.skyscanner.backpack.demo

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import net.skyscanner.backpack.demo.components.SettingsThemeOption
import net.skyscanner.backpack.demo.data.SharedPreferences
import net.skyscanner.backpack.toggle.BpkSwitch

class SettingsActivity : AppCompatActivity() {
  private lateinit var themes: List<SettingsThemeOption>
  private val themeMapping = mapOf(
    "AppTheme" to R.style.AppTheme,
    "London" to R.style.LondonTheme,
    "Doha" to R.style.DohaTheme
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val theme = SharedPreferences.getTheme(this)
    setTheme(theme)
    val hasCustomTheme = theme != R.style.AppTheme

    setContentView(R.layout.activity_settings)

    val toolbar = findViewById<Toolbar>(R.id.settings_toolbar)
    toolbar.title = "Settings"
    setSupportActionBar(toolbar)

    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    themes = listOf(
      findViewById(R.id.theme_london),
      findViewById(R.id.theme_doha)
    )

    val themeToggle = findViewById<BpkSwitch>(R.id.theme_toggle)

    themeToggle?.apply {
      setOnCheckedChangeListener { _, isChecked ->
        onThemeSwitchClicked(isChecked)
      }
    }

    if (hasCustomTheme) {
      themeToggle?.apply { isChecked = true }
      themes.forEach {
        if (themeMapping[it.text] == theme) {
          it.isCurrent = true
        }
      }
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> this.onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  fun onThemeSelected(view: View) {
    themes.forEach { it.isCurrent = false }

    view as SettingsThemeOption
    view.isCurrent = !view.isCurrent

    updateTheme()
  }

  fun onThemeSwitchClicked(isChecked: Boolean) {
    val themeList = findViewById<LinearLayout>(R.id.themes_list)

    themes.forEach { it.isCurrent = false }

    if (isChecked) {
      themeList.visibility = View.VISIBLE
    } else {
      themeList.visibility = View.GONE
    }

    if (!isChecked) {
      updateTheme()
    }
  }

  private fun updateTheme() {
    var theme = "AppTheme"
    themes.forEach {
      if (it.isCurrent) {
        theme = it.text as String
      }
    }

    themeMapping[theme]?.let {
      SharedPreferences.saveTheme(this, it)
      BackpackDemoApplication.triggerRebirth(this)
    }
  }
}
