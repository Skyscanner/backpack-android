/**
 * Backpack for Android - Skyscanner's Design System
 *
 *
 * Copyright 2018 Skyscanner Ltd
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.map.internal

import android.content.Context
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.NONE

// This class is used for screenshots testing only.
// It's not a part of public API and may be changed at any moment without being considered as a breaking change.
@VisibleForTesting(otherwise = NONE)
object BpkMapMarkersTestBridge {
  fun create(context: Context, title: String, icon: Int, showPointer: Boolean): View {
    return createBpkMarkerView(context, title, icon, showPointer)
  }
}
