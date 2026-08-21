/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import net.skyscanner.backpack.configuration.BpkConfiguration
import net.skyscanner.backpack.demo.data.SharedPreferences

/**
 * Application class registered in AndroidManifest.xml
 */

class BackpackDemoApplication : Application() {

    companion object {
        /** BpkConfiguration can be set once per process; Robolectric creates this Application once per test. */
        @Volatile
        private var configurationApplied = false

        private lateinit var instance: BackpackDemoApplication

        fun triggerRebirth(context: Context) {
            val packageManager = context.packageManager
            val intent = packageManager.getLaunchIntentForPackage(context.packageName)
            val componentName = intent!!.component
            val mainIntent = Intent.makeRestartActivityTask(componentName)
            context.startActivity(mainIntent)
            Runtime.getRuntime().exit(0)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = applicationContext!! as BackpackDemoApplication
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        applyBpkConfigurationOnce()
    }

    /**
     * Applies the saved configuration once per process. Activities are recreated on configuration
     * changes (dark mode, rotation) and must never call setConfigs themselves: the second call throws.
     */
    private fun applyBpkConfigurationOnce() {
        if (configurationApplied) return
        val typographySet = SharedPreferences.getTypographySet(this)
        BpkConfiguration.setConfigs(
            typography = typographySet == BpkConfiguration.BpkTypographySet.VDL_2,
        )
        configurationApplied = true
    }
}
