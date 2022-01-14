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

package net.skyscanner.backpack.util

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.ContextWrapper
import android.view.accessibility.AccessibilityManager

internal fun Context.isInEditMode(): Boolean =
  unwrapped()::class.qualifiedName == "com.android.layoutlib.bridge.android.BridgeContext"

private fun Context.unwrapped(): Context {
  var context = this
  while (context is ContextWrapper) {
    context = context.baseContext
  }
  return context
}

internal fun Context.isScreenReaderOn(): Boolean {
  val am = getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager
  if (am != null && am.isEnabled) {
    val serviceInfoList =
      am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_SPOKEN)
    return serviceInfoList.isNotEmpty()
  }
  return false
}
