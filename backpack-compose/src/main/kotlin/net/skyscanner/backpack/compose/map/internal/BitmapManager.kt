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

package net.skyscanner.backpack.compose.map.internal

import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.graphics.applyCanvas
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class BitmapManager {

    private val cache = mutableMapOf<String, BitmapDescriptor>()
    @Composable
    fun getBitmapDescriptor(key: String, content: @Composable () -> Unit): BitmapDescriptor {
        return if (cache.containsKey(key)) {
            cache[key]!!
        } else {
            val bitmap = composableToBitmapDescriptor(content)
            cache[key] = bitmap
            return bitmap
        }
    }
}

@Composable
private fun composableToBitmapDescriptor(content: @Composable () -> Unit): BitmapDescriptor {
    val compositionLocalContext by rememberUpdatedState(currentCompositionLocalContext)
    val view = ComposeView(LocalContext.current).apply {
        setContent {
            CompositionLocalProvider(compositionLocalContext) {
                content()
            }
        }
    }
    val currentView = LocalView.current as ViewGroup
    currentView.addView(view)
    view.measure(
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
    )
    view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    val bitmap = Bitmap.createBitmap(
        view.measuredWidth,
        view.measuredHeight,
        Bitmap.Config.ARGB_8888,
    )
    bitmap.applyCanvas {
        view.draw(this)
    }

    currentView.removeView(view)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
