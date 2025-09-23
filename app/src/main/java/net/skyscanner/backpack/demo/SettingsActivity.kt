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

package net.skyscanner.backpack.demo

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import net.skyscanner.backpack.configuration.BpkConfiguration
import net.skyscanner.backpack.demo.data.SharedPreferences
import net.skyscanner.backpack.demo.ui.SettingsThemeOption
import net.skyscanner.backpack.demo.ui.SettingsTypographyOption
import net.skyscanner.backpack.toggle.BpkSwitch

class SettingsActivity : AppCompatActivity() {
    private lateinit var themes: List<SettingsThemeOption>
    private lateinit var typographyOptions: List<SettingsTypographyOption>
    private val themeMapping = mapOf(
        "AppTheme" to R.style.AppTheme,
        "London" to R.style.LondonTheme,
        "Doha" to R.style.DohaTheme,
    )
    private val typographyMapping = mapOf(
        "Default (Sans Serif)" to BpkConfiguration.BpkTypographySet.DEFAULT,
        "VDL 2" to BpkConfiguration.BpkTypographySet.VDL_2,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val theme = SharedPreferences.getTheme(this)
        setTheme(theme)
        val hasCustomTheme = theme != R.style.AppTheme

        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<Toolbar>(R.id.settings_toolbar)
        toolbar.title = getString(R.string.settings_title)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        themes = listOf(
            findViewById<SettingsThemeOption>(R.id.theme_london).apply { setOnClickListener(::onThemeSelected) },
            findViewById<SettingsThemeOption>(R.id.theme_doha).apply { setOnClickListener(::onThemeSelected) },
        )

        typographyOptions = listOf(
            findViewById<SettingsTypographyOption>(R.id.typography_default).apply { setOnClickListener(::onTypographySelected) },
            findViewById<SettingsTypographyOption>(R.id.typography_alternative1).apply { setOnClickListener(::onTypographySelected) },
        )

        val themeToggle = findViewById<BpkSwitch>(R.id.theme_toggle)
        val themePicker = findViewById<View>(R.id.theme_picker)

        themePicker.setOnClickListener {
            themeToggle.isChecked = !themeToggle.isChecked
            onThemeSwitchClicked(themeToggle.isChecked)
        }

        if (hasCustomTheme) {
            themeToggle.isChecked = true
            onThemeSwitchClicked(themeToggle.isChecked)
            themes.forEach {
                if (themeMapping[it.text] == theme) {
                    it.isCurrent = true
                }
            }
        }

        // Set current typography set
        val currentTypographySet = SharedPreferences.getTypographySet(this)
        typographyOptions.forEach {
            if (typographyMapping[it.text] == currentTypographySet) {
                it.isCurrent = true
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

    fun onTypographySelected(view: View) {
        typographyOptions.forEach { it.isCurrent = false }

        view as SettingsTypographyOption
        view.isCurrent = !view.isCurrent

        updateTypographySet()
    }

    private fun onThemeSwitchClicked(isChecked: Boolean) {
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

    private fun updateTypographySet() {
        var typographySet = BpkConfiguration.BpkTypographySet.DEFAULT
        typographyOptions.forEach {
            if (it.isCurrent) {
                typographySet = typographyMapping[it.text as String] ?: BpkConfiguration.BpkTypographySet.DEFAULT
            }
        }

        SharedPreferences.saveTypographySet(this, typographySet)
        BpkConfiguration.setConfigs(
            typography = typographySet == BpkConfiguration.BpkTypographySet.VDL_2,
        )
        BackpackDemoApplication.triggerRebirth(this)
    }
}
