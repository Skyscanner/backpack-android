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

package net.skyscanner.backpack.compose.utils

import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.graphics.applyCanvas
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

@Composable
internal fun rememberCapturedComposeBitmapDescriptor(
    vararg keys: Any,
    content: @Composable () -> Unit,
): BitmapDescriptor {
    val bitmap = rememberCapturedComposeBitmap(*keys, content = content)
    return remember(bitmap) { BitmapDescriptorFactory.fromBitmap(bitmap) }
}

@Composable
internal fun rememberCapturedComposeBitmap(
    vararg keys: Any,
    content: @Composable () -> Unit,
): Bitmap {
    val composeView = rememberComposeView()
    val parent = LocalView.current as ViewGroup

    val currentContent by rememberUpdatedState(content)
    var cachedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val bitmap = remember(parent, composeView, currentContent, currentCompositionLocalContext, *keys) {
        renderComposeToBitmap(parent, composeView, cachedBitmap, currentContent)
    }
    cachedBitmap = bitmap
    return bitmap
}

private fun renderComposeToBitmap(
    parent: ViewGroup,
    composeView: ComposeView,
    cachedBitmap: Bitmap?,
    content: @Composable () -> Unit,
): Bitmap {

    composeView.setContent(content)

    parent.addView(composeView)

    composeView.measure(
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
    )

    composeView.layout(0, 0, composeView.measuredWidth, composeView.measuredHeight)

    val bitmap = when {
        cachedBitmap == null ||
            cachedBitmap.width != composeView.measuredWidth ||
            cachedBitmap.height != composeView.measuredHeight ->
            Bitmap.createBitmap(
                composeView.measuredWidth,
                composeView.measuredHeight,
                Bitmap.Config.ARGB_8888,
            )

        else -> cachedBitmap
    }

    bitmap.applyCanvas {
        composeView.draw(this)
    }

    parent.removeView(composeView)

    return bitmap
}

@Composable
private fun rememberComposeView(): ComposeView {
    val androidContext = LocalContext.current
    val compositionContext = rememberCompositionContext()
    return remember(androidContext, compositionContext) {
        ComposeView(androidContext).apply {
            setParentCompositionContext(compositionContext)
        }
    }
}
