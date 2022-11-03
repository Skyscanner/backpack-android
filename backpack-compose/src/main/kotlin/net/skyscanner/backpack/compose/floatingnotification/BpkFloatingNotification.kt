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

package net.skyscanner.backpack.compose.floatingnotification

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.skyscanner.backpack.compose.floatingnotification.internal.BpkFloatingNotificationData
import net.skyscanner.backpack.compose.floatingnotification.internal.BpkFloatingNotificationImpl
import net.skyscanner.backpack.compose.floatingnotification.internal.floatingNotificationTransforms
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BpkFloatingNotification(
  state: BpkFloatingNotificationState,
  modifier: Modifier = Modifier,
) {

  val currentData = state.currentData
  LaunchedEffect(currentData) {
    if (currentData != null) {
      val duration = currentData.hideAfter
      delay(duration)
      currentData.dismiss()
    }
  }

  AnimatedContent(
    targetState = currentData,
    modifier = modifier,
    transitionSpec = floatingNotificationTransforms(),
  ) { data ->

    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(start = BpkSpacing.Base, end = BpkSpacing.Base, bottom = BpkSpacing.Lg),
      contentAlignment = Alignment.BottomCenter,
    ) {

      if (data != null) {
        BpkFloatingNotificationImpl(data)
      }
    }
  }
}


@Stable
class BpkFloatingNotificationState {

  private val mutex = Mutex()

  internal var currentData by mutableStateOf<BpkFloatingNotificationData?>(null)
    private set

  suspend fun show(
    text: String,
    cta: String? = null,
    onClick: (() -> Unit)? = null,
    icon: BpkIcon? = null,
    hideAfter: Long = 4000L,
    onExit: (() -> Unit)? = null,
  ): SnackbarResult =
    mutex.withLock {
      try {
        return suspendCancellableCoroutine { continuation ->
          currentData = BpkFloatingNotificationData(text, icon, cta, hideAfter, onExit, onClick, continuation)
        }
      } finally {
        currentData = null
      }
    }
}

@Composable
fun rememberBpkFloatingNotificationState(): BpkFloatingNotificationState =
  remember {
    BpkFloatingNotificationState()
  }
