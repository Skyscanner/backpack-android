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

package net.skyscanner.backpack.snackbar

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.AnyRes

inline fun BpkSnackbar.setAction(text: CharSequence, crossinline listener: (View) -> Unit): BpkSnackbar =
  setAction(text, View.OnClickListener { listener(it) })

inline fun BpkSnackbar.setAction(@AnyRes resId: Int, crossinline listener: (View) -> Unit): BpkSnackbar =
  setAction(resId, View.OnClickListener { listener(it) })

inline fun BpkSnackbar.setAction(icon: Drawable, crossinline listener: (View) -> Unit): BpkSnackbar =
  setAction(icon, View.OnClickListener { listener(it) })

fun BpkSnackbar.setOnDismissed(ignoreDismissAfterAction: Boolean = true, callback: () -> Unit) =
  addCallback(object : BpkSnackbar.Callback() {
    override fun onDismissed(transientBottomBar: BpkSnackbar?, event: Int) {
      super.onDismissed(transientBottomBar, event)
      if (ignoreDismissAfterAction && event == DISMISS_EVENT_ACTION) return
      callback()
    }
  })
