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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
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
    val widthDp = with(LocalDensity.current) {
        LocalWindowInfo.current.containerSize.width.toDp()
    }
    val componentHeight =
        if (widthDp >= TABLET_MIN_WIDTH.dp) DefaultTabletSize.height else DefaultPhoneSize.height
    val slideDistancePx = with(LocalDensity.current) { (BpkSpacing.Lg + componentHeight).toPx().toInt() }

    AnimatedContent(
        targetState = currentData,
        modifier = modifier,
        transitionSpec = floatingNotificationTransforms(slideDistancePx),
        label = "Floating Notification",
    ) { data ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = BpkSpacing.Base, end = BpkSpacing.Base, bottom = BpkSpacing.Lg)
                .navigationBarsPadding(),
            contentAlignment = Alignment.BottomCenter,
        ) {

            if (data != null) {
                BpkFloatingNotificationImpl(
                    data = data,
                    modifier = Modifier
                        .requiredHeight(componentHeight)
                        .widthIn(max = DefaultTabletSize.width),
                )
            }
        }
    }
}

@Stable
class BpkFloatingNotificationState {

    internal var currentData by mutableStateOf<BpkFloatingNotificationData?>(null)
        private set

    suspend fun show(
        text: String,
        cta: String? = null,
        onClick: (() -> Unit)? = null,
        icon: BpkIcon? = null,
        hideAfter: Long = DEFAULT_DELAY,
        onExit: (() -> Unit)? = null,
    ): Unit =
        try {
            suspendCancellableCoroutine { continuation ->
                currentData = BpkFloatingNotificationData(text, icon, cta, hideAfter, onExit, onClick, continuation)
            }
        } finally {
            currentData = null
        }
}

@Composable
fun rememberBpkFloatingNotificationState(): BpkFloatingNotificationState =
    remember {
        BpkFloatingNotificationState()
    }

private const val DEFAULT_DELAY = 4000L
private const val TABLET_MIN_WIDTH = 769
private val DefaultPhoneSize = DpSize(Dp.Unspecified, 52.dp)
private val DefaultTabletSize = DpSize(400.dp, 72.dp)
