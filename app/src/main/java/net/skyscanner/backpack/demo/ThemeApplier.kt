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

import android.app.Activity
import android.app.Application
import android.os.Bundle
import net.skyscanner.backpack.demo.data.SharedPreferences
import net.skyscanner.backpack.util.BpkTheme

object ThemeApplier : Application.ActivityLifecycleCallbacks {
  override fun onActivityPaused(activity: Activity) {}
  override fun onActivityResumed(activity: Activity) {}
  override fun onActivityStarted(activity: Activity) {}
  override fun onActivityDestroyed(activity: Activity) {}
  override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
  override fun onActivityStopped(activity: Activity) {}
  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    activity.theme?.applyStyle(SharedPreferences.getTheme(activity), true)
    activity.let { BpkTheme.applyDefaultsToContext(it) }
  }
}
