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

package net.skyscanner.backpack.compose.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberMarkerState
import net.skyscanner.backpack.compose.theme.BpkTheme

@Composable
fun BpkPointerMapMarker(
    title: String,
    state: MarkerState = rememberMarkerState(),
    tag: Any? = null,
    visible: Boolean = true,
    zIndex: Float = 0.0f,
    onClick: (Marker) -> Boolean = { false },
    onInfoWindowClick: (Marker) -> Unit = {},
) {
    val size = with(LocalDensity.current) { 16.dp.toPx() }
    val strokeWidth = with(LocalDensity.current) { 2.dp.toPx() }
    val fillColor = BpkTheme.colors.coreAccent
    val strokeColor = BpkTheme.colors.surfaceDefault
    val iconBitmap = remember {
        createPointer(size, strokeWidth, fillColor, strokeColor)
    }

    MarkerInfoWindow(
        state = state,
        tag = tag,
        title = title,
        anchor = Offset(0.5f, 0.5f),
        visible = visible,
        zIndex = zIndex,
        icon = iconBitmap,
        onClick = onClick,
        onInfoWindowClick = onInfoWindowClick,
    ) {
        PriceMarkerLayout(title = title, status = BpkPriceMarkerStatus.Focused)
    }
}

private fun createPointer(size: Float, strokeWidth: Float, fillColor: Color, strokeColor: Color): BitmapDescriptor {
    val radius = size / 2
    val strokePath = Path().apply {
        addCircle(radius, radius, radius, Path.Direction.CW)
    }

    val fillPath = Path().apply {
        addCircle(radius, radius, radius - strokeWidth, Path.Direction.CW)
    }

    val bm = Bitmap.createBitmap(size.toInt(), size.toInt(), Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bm)

    val fillPaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
        color = fillColor.toArgb()
    }

    val strokePaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
        color = strokeColor.toArgb()
    }
    canvas.drawPath(strokePath, strokePaint)
    canvas.drawPath(fillPath, fillPaint)
    return BitmapDescriptorFactory.fromBitmap(bm)
}
