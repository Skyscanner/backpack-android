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

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Snackbar
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotificationSizes.MOBILE_MAX_WIDTH
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotificationSizes.MOBILE_REQUIRED_HEIGHT
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotificationSizes.MOBILE_REQUIRED_WIDTH
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotificationSizes.SMALL_MOBILE_MAX_WIDTH
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotificationSizes.SMALL_MOBILE_REQUIRED_HEIGHT
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotificationSizes.SMALL_MOBILE_REQUIRED_WIDTH
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotificationSizes.TABLET_REQUIRED_HEIGHT
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotificationSizes.TABLET_REQUIRED_WIDTH
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.clickable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BpkFloatingNotification(
  hostState: BpkFloatingNotificationState,
  modifier: Modifier = Modifier,
) {

  val currentData = hostState.currentData
  LaunchedEffect(currentData) {
    if (currentData != null) {
      val duration = 4000L
      delay(duration)
      currentData.dismiss()
    }
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .padding(start = BpkSpacing.Base, end = BpkSpacing.Base, bottom = 30.dp),
    contentAlignment = Alignment.BottomCenter,
  ) {

    val fadeAnimationSpec: FiniteAnimationSpec<Float> = tween(durationMillis = TRANSITION_DURATION)
    val slideAnimationSpec: FiniteAnimationSpec<IntOffset> = tween(durationMillis = TRANSITION_DURATION)


    var lastAvailableData by remember { mutableStateOf(currentData) }
    if (currentData != null) {
      lastAvailableData = currentData
    }

    AnimatedVisibility(
      modifier = modifier,
      visible = currentData != null,
      enter = slideInVertically(slideAnimationSpec, initialOffsetY = { it / 2 }) + fadeIn(animationSpec = fadeAnimationSpec),
      exit = slideOutVertically(slideAnimationSpec, targetOffsetY = { it / 2 }) + fadeOut(animationSpec = fadeAnimationSpec),
    ) {

      lastAvailableData?.let { lastData ->

        AnimatedContent(targetState = lastData) { it ->
          BpkFloatingNotificationImpl(
            data = it,
            modifier = Modifier.animateEnterExit(
              enter = EnterTransition.None,
              exit = ExitTransition.None,
//              enter = slideInVertically(
//                slideAnimationSpec,
//                initialOffsetY = { it }) + fadeIn(animationSpec = tween(durationMillis = TRANSITION_DURATION * 5)),
//              exit = slideOutVertically(
//                slideAnimationSpec,
//                targetOffsetY = { -it }) + fadeOut(animationSpec = tween(durationMillis = TRANSITION_DURATION * 5)),
            ),
          )
        }

      }


//      }

    }


  }


}


@Composable
private fun BpkFloatingNotificationImpl(
  data: BpkFloatingNotificationData,
  modifier: Modifier = Modifier,
) {
  val requiredSize = if (LocalConfiguration.current.screenWidthDp < SMALL_MOBILE_MAX_WIDTH) {
    RequiredSize(width = SMALL_MOBILE_REQUIRED_WIDTH, height = SMALL_MOBILE_REQUIRED_HEIGHT)
  } else if (LocalConfiguration.current.screenWidthDp in SMALL_MOBILE_MAX_WIDTH..MOBILE_MAX_WIDTH) {
    RequiredSize(width = MOBILE_REQUIRED_WIDTH, height = MOBILE_REQUIRED_HEIGHT)
  } else {
    RequiredSize(width = TABLET_REQUIRED_WIDTH, height = TABLET_REQUIRED_HEIGHT)
  }

  Snackbar(
    modifier = modifier
      .requiredSize(height = requiredSize.height, width = requiredSize.width)
      .padding(start = BpkSpacing.Base, end = BpkSpacing.Base),
    shape = RoundedCornerShape(BpkBorderRadius.Md),
    backgroundColor = BpkTheme.colors.corePrimary,
    contentColor = BpkTheme.colors.textOnDark,
  ) {

    Row(
      modifier = Modifier.fillMaxHeight(),
      horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      data.icon?.let { icon ->
        BpkIcon(
          icon = icon,
          contentDescription = null,
          size = BpkIconSize.Small,
        )
      }
      BpkText(
        modifier = Modifier.weight(0.8f),
        text = data.message,
        maxLines = 2,
        style = BpkTheme.typography.footnote
      )
      data.action?.let { action ->
        Box(
          modifier = Modifier
            .weight(0.2f)
            .sizeIn(minHeight = BpkSpacing.Xxl, minWidth = BpkSpacing.Xxl)
            .clickable { data.performAction() },
          contentAlignment = Alignment.Center
        ) {
          BpkText(
            text = action,
            textAlign = TextAlign.Center,
            style = BpkTheme.typography.label2
          )
        }
      }
    }
  }
}

data class Cta(
  val text: String,
  val onClick: () -> Unit,
)

@Stable
data class BpkFloatingNotificationData(
  val message: String,
  val action: String?,
  val onClick: (() -> Unit)?,
  val icon: BpkIcon?,
  private val continuation: CancellableContinuation<SnackbarResult>
) {

  fun performAction() {
    if (continuation.isActive) continuation.resume(SnackbarResult.ActionPerformed, onCancellation = null)
    onClick?.invoke()
  }

  fun dismiss() {
    if (continuation.isActive) continuation.resume(SnackbarResult.Dismissed, onCancellation = null)
  }

}

@Stable
class BpkFloatingNotificationState {

  private val mutex = Mutex()

  var currentData by mutableStateOf<BpkFloatingNotificationData?>(null)
    private set

  suspend fun show(
    message: String,
    action: String? = null,
    onClick: (() -> Unit)? = null,
    icon: BpkIcon? = null,
  ): SnackbarResult = mutex.withLock {
    try {
      return suspendCancellableCoroutine { continuation ->
        currentData = BpkFloatingNotificationData(message, action, onClick, icon, continuation)
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

internal object BpkFloatingNotificationSizes {
  const val SMALL_MOBILE_MAX_WIDTH = 360
  const val MOBILE_MAX_WIDTH = 512

  val SMALL_MOBILE_REQUIRED_WIDTH = 288.dp
  val SMALL_MOBILE_REQUIRED_HEIGHT = 52.dp
  val MOBILE_REQUIRED_WIDTH = 312.dp
  val MOBILE_REQUIRED_HEIGHT = 52.dp
  val TABLET_REQUIRED_WIDTH = 400.dp
  val TABLET_REQUIRED_HEIGHT = 72.dp
}

private const val TRANSITION_DURATION = 400

private data class RequiredSize(val width: Dp, val height: Dp)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun LightMode_TextOnly() {
  // run in Interactive Mode
  val state = rememberBpkFloatingNotificationState()
  BpkFloatingNotification(hostState = state)
  LaunchedEffect(key1 = Unit) {
    state.show(message = "Lorem ipsum dolor sit amet")
  }
}
