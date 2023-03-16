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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
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
        modifier = modifier,
        shape = RoundedCornerShape(BpkBorderRadius.Md),
        backgroundColor = BpkTheme.colors.corePrimary,
        contentColor = BpkTheme.colors.textOnDark,
    ) {
        CompositionLocalProvider(LocalContentAlpha provides 1f) {
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
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun floatingNotificationTransforms(slideDistancePx: Int): AnimatedContentScope<BpkFloatingNotificationData?>.() -> ContentTransform =
    {
        ContentTransform(
            targetContentEnter = fadeIn(tween(TRANSITION_DURATION)) + slideInVertically(tween(TRANSITION_DURATION)) { slideDistancePx },
            initialContentExit = fadeOut(tween(TRANSITION_DURATION)) + slideOutVertically(tween(TRANSITION_DURATION)) { slideDistancePx },
        )
    }

private const val TRANSITION_DURATION = 500
