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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.clickable

@Composable
fun BpkFloatingNotification(
  hostState: BpkFloatingNotificationState,
  text: String,
  modifier: Modifier = Modifier,
  icon: BpkIcon? = null,
  cta: Cta? = null,
  animation: Animation = Animation(),
) {
  val transitionDuration = 400
  val fadeAnimationSpec: FiniteAnimationSpec<Float> = tween(durationMillis = transitionDuration)
  val slideAnimationSpec: FiniteAnimationSpec<IntOffset> = tween(durationMillis = transitionDuration)
  val requiredSize = if (LocalConfiguration.current.screenWidthDp < 360) {
    RequiredSize(width = 288.dp, height = 52.dp)
  } else if (LocalConfiguration.current.screenWidthDp in 360..512) {
    RequiredSize(width = 312.dp, height = 52.dp)
  } else {
    RequiredSize(width = 400.dp, height = 72.dp)
  }

  AnimatedVisibility(
    modifier = modifier,
    visible = hostState.visible,
    enter = if (animation.animateOnEnter) {
      slideInVertically(slideAnimationSpec, initialOffsetY = { it / 2 }) + fadeIn(animationSpec = fadeAnimationSpec)
    } else {
      EnterTransition.None
    },
    exit = if (animation.animateOnExit) {
      slideOutVertically(slideAnimationSpec, targetOffsetY = { it / 2 }) + fadeOut(animationSpec = fadeAnimationSpec)
    } else {
      ExitTransition.None
    }
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(start = BpkSpacing.Base, end = BpkSpacing.Base, bottom = 30.dp),
      contentAlignment = Alignment.BottomCenter
    ) {
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(BpkBorderRadius.Md))
          .background(color = BpkTheme.colors.corePrimary)
          .requiredSize(height = requiredSize.height, width = requiredSize.width)
          .padding(start = BpkSpacing.Base, end = BpkSpacing.Base),
        contentAlignment = Alignment.CenterStart
      ) {
        Row(
          horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
          verticalAlignment = Alignment.CenterVertically
        ) {
          icon?.let { icon ->
            BpkIcon(
              icon = icon,
              contentDescription = null,
              size = BpkIconSize.Small,
              tint = BpkTheme.colors.textOnDark
            )
          }
          BpkText(
            modifier = Modifier.weight(0.8f),
            text = text,
            maxLines = 2,
            color = BpkTheme.colors.textOnDark,
            style = BpkTheme.typography.footnote
          )
          cta?.let { cta ->
            Box(
              modifier = Modifier
                .weight(0.2f)
                .sizeIn(minHeight = BpkSpacing.Xxl, minWidth = BpkSpacing.Xxl)
                .clickable { cta.onClick.invoke() },
              contentAlignment = Alignment.Center
            ) {
              BpkText(
                text = cta.text,
                textAlign = TextAlign.Center,
                color = BpkTheme.colors.textOnDark,
                style = BpkTheme.typography.label2
              )
            }
          }
        }
      }
    }
  }
}

data class Cta(
  val text: String,
  val onClick: () -> Unit,
)

data class Animation(
  val animateOnEnter: Boolean = true,
  val animateOnExit: Boolean = true,
)

@Stable
class BpkFloatingNotificationState(
  initiallyVisible: Boolean,
  private val coroutineScope: CoroutineScope,
) {

  private val animationDuration = 1000L
  var visible by mutableStateOf(initiallyVisible)
    private set

  fun show() {
    visible = true
    coroutineScope.launch {
      delay(animationDuration)
      visible = false
    }
  }
}

@Composable
fun rememberBpkFloatingNotificationState(
  initiallyVisible: Boolean = false
): BpkFloatingNotificationState {
  val scope = rememberCoroutineScope()
  return rememberSaveable(
    scope,
    saver = Saver(
      save = { it.visible },
      restore = {
        BpkFloatingNotificationState(initiallyVisible = it, scope).apply {
          if (it) {
            show()
          }
        }
      }
    )
  ) {
    BpkFloatingNotificationState(initiallyVisible = initiallyVisible, coroutineScope = scope)
  }
}

private data class RequiredSize(val width: Dp, val height: Dp)

