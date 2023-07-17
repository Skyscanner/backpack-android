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
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    key: Any,
    content: @Composable () -> Unit,
): BitmapDescriptor {
    val bitmap = rememberCapturedComposeBitmap(key, content)
    return remember(bitmap) { BitmapDescriptorFactory.fromBitmap(bitmap) }
}

@Composable
internal fun rememberCapturedComposeBitmap(
    key: Any,
    content: @Composable () -> Unit,
): Bitmap {
    val androidContext = LocalContext.current
    val helperView = remember(content) { ComposeView(androidContext) }
    val parentView = LocalView.current as ViewGroup

    val content by rememberUpdatedState(content)
    val compositionContext by rememberUpdatedState(currentCompositionLocalContext)
    var cachedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val bitmap = remember(parentView, helperView, compositionContext, content, key) {
        renderComposeToBitmap(parentView, helperView, compositionContext, cachedBitmap, content)
    }
    cachedBitmap = bitmap
    return bitmap
}

private fun renderComposeToBitmap(
    parent: ViewGroup,
    view: ComposeView,
    compositionContext: CompositionLocalContext,
    cachedBitmap: Bitmap?,
    content: @Composable () -> Unit,
): Bitmap {

    view.setContent {
        CompositionLocalProvider(compositionContext) {
            content()
        }
    }

    parent.addView(view)

    view.measure(
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
    )

    view.layout(0, 0, view.measuredWidth, view.measuredHeight)

    val bitmap = when {
        cachedBitmap == null ||
            cachedBitmap.width != view.measuredWidth ||
            cachedBitmap.height != view.measuredHeight ->
            Bitmap.createBitmap(
                view.measuredWidth,
                view.measuredHeight,
                Bitmap.Config.ARGB_8888,
            )
        else -> cachedBitmap
    }

    bitmap.applyCanvas {
        view.draw(this)
    }

    parent.removeView(view)

    return bitmap
}
