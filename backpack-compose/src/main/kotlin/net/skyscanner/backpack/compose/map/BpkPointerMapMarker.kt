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

import androidx.annotation.RestrictTo
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderSize
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.rememberCapturedComposeBitmapDescriptor

@Deprecated("Use BpkLocationMapMarker instead.", ReplaceWith("BpkLocationMapMarker"), DeprecationLevel.WARNING)
@Composable
fun BpkPointerMapMarker(
    title: String,
    state: MarkerState = MarkerState(),
    tag: Any? = null,
    visible: Boolean = true,
    zIndex: Float = 0.0f,
    onClick: (Marker) -> Boolean = { false },
    onInfoWindowClick: (Marker) -> Unit = {},
) {
    val iconBitmap = rememberCapturedComposeBitmapDescriptor {
        PointerMarkerLayout()
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

@Composable
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun PointerMarkerLayout(modifier: Modifier = Modifier) {
    Box(
        modifier
            .size(BpkSpacing.Base)
            .border(
                border = BorderStroke(BpkBorderSize.Lg, BpkTheme.colors.surfaceDefault),
                shape = CircleShape,
            )
            .padding(BpkBorderSize.Lg)
            .background(color = BpkTheme.colors.coreAccent, shape = CircleShape),
    )
}
