/*
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

package net.skyscanner.backpack.compose.floatingnotification.internal

import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Stable
import kotlinx.coroutines.CancellableContinuation
import net.skyscanner.backpack.compose.icon.BpkIcon

@Stable
internal data class BpkFloatingNotificationData(
  val text: String,
  val icon: BpkIcon?,
  val cta: String?,
  val hideAfter: Long,
  val onExit: (() -> Unit)?,
  private val onClick: (() -> Unit)?,
  private val continuation: CancellableContinuation<SnackbarResult>
) {

  fun performAction() {
    if (continuation.isActive) continuation.resume(SnackbarResult.ActionPerformed, onCancellation = null)
    onClick?.invoke()
  }

  fun dismiss() {
    if (continuation.isActive) continuation.resume(SnackbarResult.Dismissed, onCancellation = null)
    onExit?.invoke()
  }

}
