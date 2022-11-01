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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
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

@Composable
fun BpkFloatingNotification(
  hostState: BpkFloatingNotificationState,
  modifier: Modifier = Modifier,
) {
  val data = hostState.data ?: return
  val fadeAnimationSpec: FiniteAnimationSpec<Float> = tween(durationMillis = TRANSITION_DURATION)
  val slideAnimationSpec: FiniteAnimationSpec<IntOffset> = tween(durationMillis = TRANSITION_DURATION)
  val requiredSize = if (LocalConfiguration.current.screenWidthDp < SMALL_MOBILE_MAX_WIDTH) {
    RequiredSize(width = SMALL_MOBILE_REQUIRED_WIDTH, height = SMALL_MOBILE_REQUIRED_HEIGHT)
  } else if (LocalConfiguration.current.screenWidthDp in SMALL_MOBILE_MAX_WIDTH..MOBILE_MAX_WIDTH) {
    RequiredSize(width = MOBILE_REQUIRED_WIDTH, height = MOBILE_REQUIRED_HEIGHT)
  } else {
    RequiredSize(width = TABLET_REQUIRED_WIDTH, height = TABLET_REQUIRED_HEIGHT)
  }

  AnimatedVisibility(
    modifier = modifier,
    visible = hostState.visible,
    enter = slideInVertically(slideAnimationSpec, initialOffsetY = { it / 2 }) + fadeIn(animationSpec = fadeAnimationSpec),
    exit = slideOutVertically(slideAnimationSpec, targetOffsetY = { it / 2 }) + fadeOut(animationSpec = fadeAnimationSpec)
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
          data.icon?.let { icon ->
            BpkIcon(
              icon = icon,
              contentDescription = null,
              size = BpkIconSize.Small,
              tint = BpkTheme.colors.textOnDark
            )
          }
          BpkText(
            modifier = Modifier.weight(0.8f),
            text = data.text,
            maxLines = 2,
            color = BpkTheme.colors.textOnDark,
            style = BpkTheme.typography.footnote
          )
          data.cta?.let { cta ->
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

internal data class BpkFloatingNotificationData(
  val text: String,
  val icon: BpkIcon? = null,
  val cta: Cta? = null,
)

@Stable
class BpkFloatingNotificationState(initiallyVisible: Boolean) {

  internal var data by mutableStateOf<BpkFloatingNotificationData?>(null)
    private set
  private val animationDuration = 4000L
  var visible by mutableStateOf(initiallyVisible)
    private set
  suspend fun show(
    text: String,
    icon: BpkIcon? = null,
    cta: Cta? = null,
  ) {
    data = BpkFloatingNotificationData(text, icon, cta)
    delay(TRANSITION_DURATION / 2L)
    visible = true
    delay(animationDuration)
    visible = false
    delay(TRANSITION_DURATION / 2L)
    data = null
  }
}

@Composable
fun rememberBpkFloatingNotificationState(
  initiallyVisible: Boolean = false
): BpkFloatingNotificationState {
    return remember {
      BpkFloatingNotificationState(initiallyVisible = initiallyVisible)
    }
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
