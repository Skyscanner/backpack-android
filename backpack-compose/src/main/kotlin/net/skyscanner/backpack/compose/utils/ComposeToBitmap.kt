/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.utils

import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalView
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.createBitmap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlinx.coroutines.delay

@Composable
internal fun rememberCapturedComposeBitmapDescriptor(
    vararg keys: Any?,
    content: @Composable () -> Unit,
): BitmapDescriptor? {
    val bitmap = rememberCapturedComposeBitmap(*keys, content = content)
    return remember(bitmap) { bitmap?.let { BitmapDescriptorFactory.fromBitmap(it) } }
}

@Composable
internal fun rememberCapturedComposeBitmap(
    vararg keys: Any?,
    content: @Composable () -> Unit,
): Bitmap? {
    val parent = LocalView.current as ViewGroup
    val currentContext = rememberCompositionContext()
    val currentContent by rememberUpdatedState(content)
    var cachedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(parent, currentContext, currentContent, *keys) {
        var retries = 0
        val maxRetries = 5

        while (retries < maxRetries) {
            val bitmap = renderComposeToBitmap(parent, currentContext, currentContent)
            if (bitmap != null) {
                cachedBitmap = bitmap
                return@LaunchedEffect
            }
            retries++
            if (retries < maxRetries) {
                delay(16) // Wait ~1 frame (~16ms at 60fps)
            }
        }
    }

    return cachedBitmap
}

private fun renderComposeToBitmap(
    parent: ViewGroup,
    compositionContext: CompositionContext,
    content: @Composable () -> Unit,
): Bitmap? {
    val composeView = ComposeView(parent.context)
    composeView.setParentCompositionContext(compositionContext)
    composeView.setContent(content)

    return try {
        parent.addView(composeView)

        composeView.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        )

        composeView.layout(0, 0, composeView.measuredWidth, composeView.measuredHeight)

        // If measurements are not ready yet (0x0), return null to retry on next frame
        if (composeView.measuredWidth <= 0 || composeView.measuredHeight <= 0) {
            return null
        }

        val bitmap = createBitmap(composeView.measuredWidth, composeView.measuredHeight)

        bitmap.applyCanvas {
            composeView.draw(this)
        }

        bitmap
    } finally {
        parent.removeView(composeView)
    }
}
