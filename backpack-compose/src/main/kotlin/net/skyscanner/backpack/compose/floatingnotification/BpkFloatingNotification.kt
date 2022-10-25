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
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Heart
import net.skyscanner.backpack.compose.utils.clickable

@Composable
fun BpkFloatingNotification(
  modifier: Modifier = Modifier,
  text: String,
  show: Boolean,
  icon: BpkIcon? = null,
  animation: Animation = Animation(),
  cta: Cta? = null,
  onExit: (() -> Unit)? = null,
  ) {
  val transitionDuration = 400
  var internalShow by remember { mutableStateOf(true) }
  if (show) {
    LaunchedEffect(key1 = Unit) {
      internalShow = true
      delay(animation.hideAfter.toLong())
      internalShow = false
      onExit?.invoke()
    }
  }
  val fadeAnimationSpec: FiniteAnimationSpec<Float> = tween(durationMillis = transitionDuration, easing = LinearEasing)
  val slideAnimationSpec: FiniteAnimationSpec<IntOffset> = tween(durationMillis = transitionDuration, easing = LinearEasing)
  AnimatedVisibility(
    modifier = modifier,
    visible = internalShow,
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
        .padding(start = BpkSpacing.Base, end = BpkSpacing.Base),
      contentAlignment = Alignment.BottomCenter
    ) {
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(BpkBorderRadius.Md))
          .background(color = BpkTheme.colors.corePrimary)
          .sizeIn(
            minHeight = 52.dp,
            maxHeight = 72.dp,
            minWidth = if (LocalConfiguration.current.screenWidthDp < 360) 288.dp else 312.dp,
            maxWidth = 400.dp
          )
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
  val hideAfter: Int = 4000,
)


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun LightMode_TextOnly() {
  // run in Interactive Mode
  val (show, setShow) = remember { mutableStateOf(false) }
  BpkFloatingNotification(
    text = "Lorem",
    show = show
  )
  LaunchedEffect(key1 = Unit) {
    delay(1000)
    setShow(true)
  }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun LightMode_Icon() {
  val (show, setShow) = remember { mutableStateOf(false) }
  BpkFloatingNotification(
    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    icon = BpkIcon.Heart,
    show = show
  )
  LaunchedEffect(key1 = Unit) {
    delay(1000)
    setShow(true)
  }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun LightMode_CTA() {
  val (show, setShow) = remember { mutableStateOf(false) }
  BpkFloatingNotification(
    text = "Lorem ipsum dolor sit amet sdfs fsfsd fsdfsdf dsdfs fsdfs",
    icon = BpkIcon.Heart,
    cta = Cta("open whatever", onClick = {}),
    show = show
  )
  LaunchedEffect(key1 = Unit) {
    delay(1000)
    setShow(true)
  }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun LightMode_CTA_NoAnimation() {
  val (show, setShow) = remember { mutableStateOf(false) }
  BpkFloatingNotification(
    text = "Lorem ipsum dolor sit amet",
    icon = BpkIcon.Heart,
    cta = Cta("View", onClick = {}),
    animation = Animation(
      animateOnEnter = false,
      animateOnExit = false
    ),
    show = show
  )
  LaunchedEffect(key1 = Unit) {
    delay(1000)
    setShow(true)
  }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, backgroundColor = 0x000000)
@Composable
private fun NightMode_CTA() {
  val (show, setShow) = remember { mutableStateOf(false) }
  BpkFloatingNotification(
    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    icon = BpkIcon.Heart,
    cta = Cta("Open", onClick = {}),
    show = show
  )
  LaunchedEffect(key1 = Unit) {
    delay(1000)
    setShow(true)
  }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun LightMode_TextOnly_RTL() {
  CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
    // run in Interactive Mode
    val (show, setShow) = remember { mutableStateOf(false) }
    BpkFloatingNotification(
      text = "خسیر خسی س خسهد ی سخهسیخ عسی سخیه دسخهی د سسخی دس خحهسید سیمسی  ",
      icon = BpkIcon.Heart,
      cta = Cta("تماشا", onClick = {}),
      show = show
    )
    LaunchedEffect(key1 = Unit) {
      delay(1000)
      setShow(true)
    }
  }
}
