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

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen

/**
 * Application class registered in AndroidManifest.xml
 */

class BackpackDemoApplication : Application() {

  companion object {

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
    AndroidThreeTen.init(this)
    instance = applicationContext!! as BackpackDemoApplication
    Stetho.initializeWithDefaults(this)
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

    this.registerActivityLifecycleCallbacks(ThemeApplier)
  }
}
