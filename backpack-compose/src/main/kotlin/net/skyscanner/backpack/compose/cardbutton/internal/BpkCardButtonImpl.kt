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

package net.skyscanner.backpack.compose.cardbutton.internal

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import kotlinx.coroutines.delay
import net.skyscanner.backpack.compose.cardbutton.BpkCardButtonSize
import net.skyscanner.backpack.compose.cardbutton.BpkCardButtonStyle
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Heart
import net.skyscanner.backpack.compose.tokens.HeartOutline
import net.skyscanner.backpack.compose.tokens.ShareAndroid
import net.skyscanner.backpack.compose.tokens.internal.BpkCardButtonColors
import net.skyscanner.backpack.compose.utils.clickableWithRipple

private enum class BpkCardButtonState {
    Rest,
    Transition,
}

@Composable
internal fun BpkSaveCardButtonImpl(
    checked: Boolean,
    contentDescription: String,
    onCheckedChange: (Boolean) -> Unit,
    size: BpkCardButtonSize,
    style: BpkCardButtonStyle,
    modifier: Modifier = Modifier,
) {
    var state by remember { mutableStateOf(BpkCardButtonState.Rest) }
    val scaleAnimation = remember { Animatable(1f) }
    if (state == BpkCardButtonState.Transition) {
        LaunchedEffect(key1 = Unit) {
            scaleAnimation.animateTo(
                targetValue = 30f / 24f,
                animationSpec = tween(
                    durationMillis = 400,
                    easing = { OvershootInterpolator().getInterpolation(it) },
                ),
            )
            delay(500)
            scaleAnimation.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = { OvershootInterpolator().getInterpolation(it) },
                ),
            )
            state = BpkCardButtonState.Rest
        }
    }
    val colorAnimation by animateColorAsState(
        targetValue = when (style) {
            BpkCardButtonStyle.OnDark -> BpkTheme.colors.textOnDark
            else -> if (checked) BpkTheme.colors.coreAccent else BpkTheme.colors.textPrimary
        },
        animationSpec = tween(300),
    )
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .size(if (size == BpkCardButtonSize.Default) BpkSpacing.Xxl else BpkSpacing.Xl)
            .background(
                color = when (style) {
                    BpkCardButtonStyle.Contained -> BpkCardButtonColors.containedFill
                    else -> Color.Transparent
                },
            )
            .semantics { this.contentDescription = contentDescription }
            .then(
                if (state == BpkCardButtonState.Rest) {
                    Modifier.toggleable(
                        value = checked,
                        role = Role.Switch,
                        onValueChange = { checked ->
                            if (checked) state = BpkCardButtonState.Transition
                            onCheckedChange(checked)
                        },
                    )
                } else Modifier,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(modifier = Modifier.scale(scaleAnimation.value)) {
            BpkIcon(
                icon = if (checked) BpkIcon.Heart else BpkIcon.HeartOutline,
                contentDescription = null,
                size = if (size == BpkCardButtonSize.Default) BpkIconSize.Large else BpkIconSize.Small,
                tint = colorAnimation,
            )
        }
    }
}

@Composable
internal fun BpkShareCardButtonImpl(
    contentDescription: String,
    onClick: () -> Unit,
    size: BpkCardButtonSize,
    style: BpkCardButtonStyle,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .size(if (size == BpkCardButtonSize.Default) BpkSpacing.Xxl else BpkSpacing.Xl)
            .background(
                color = when (style) {
                    BpkCardButtonStyle.Contained -> BpkCardButtonColors.containedFill
                    else -> Color.Transparent
                },
            )
            .clickableWithRipple(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        BpkIcon(
            modifier = Modifier,
            icon = BpkIcon.ShareAndroid,
            contentDescription = contentDescription,
            size = if (size == BpkCardButtonSize.Default) BpkIconSize.Large else BpkIconSize.Small,
            tint = when (style) {
                BpkCardButtonStyle.OnDark -> BpkTheme.colors.textOnDark
                else -> BpkTheme.colors.textPrimary
            },
        )
    }
}
