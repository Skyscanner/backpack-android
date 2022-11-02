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

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BpkFloatingNotificationImpl(
  data: BpkFloatingNotificationData,
  modifier: Modifier = Modifier,
) {

  Snackbar(
    modifier = modifier
      .floatingNotificationSize(LocalConfiguration.current)
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
        modifier = Modifier.weight(1f),
        text = data.text,
        maxLines = 2,
        style = BpkTheme.typography.footnote,
        overflow = TextOverflow.Ellipsis,
      )

      data.cta?.let { cta ->
        BpkButton(
          text = cta,
          type = BpkButtonType.LinkOnDark,
          onClick = { data.performAction() },
          size = BpkButtonSize.Default,
        )
      }
    }
  }
}

@OptIn(ExperimentalAnimationApi::class)
internal fun floatingNotificationTransforms(): AnimatedContentScope<BpkFloatingNotificationData?>.() -> ContentTransform =
  {
    ContentTransform(
      targetContentEnter = fadeIn(tween(TRANSITION_DURATION)) + slideInVertically(tween(TRANSITION_DURATION)) { it / 2 },
      initialContentExit = fadeOut(tween(TRANSITION_DURATION)) + slideOutVertically(tween(TRANSITION_DURATION)) { it / 2 },
    )
  }

private object BpkFloatingNotificationSizes {
  const val SMALL_MOBILE_MAX_WIDTH = 360
  const val MOBILE_MAX_WIDTH = 512

  val SmallMobile = DpSize(288.dp, 52.dp)
  val Mobile = DpSize(312.dp, 52.dp)
  val Tablet = DpSize(400.dp, 72.dp)
}

private fun Modifier.floatingNotificationSize(configuration: Configuration): Modifier =
  when {
    configuration.screenWidthDp < BpkFloatingNotificationSizes.SMALL_MOBILE_MAX_WIDTH ->
      requiredSize(BpkFloatingNotificationSizes.SmallMobile)
    configuration.screenWidthDp in BpkFloatingNotificationSizes.SMALL_MOBILE_MAX_WIDTH..BpkFloatingNotificationSizes.MOBILE_MAX_WIDTH ->
      requiredSize(BpkFloatingNotificationSizes.Mobile)
    else -> requiredSize(BpkFloatingNotificationSizes.Tablet)
  }

private const val TRANSITION_DURATION = 300
