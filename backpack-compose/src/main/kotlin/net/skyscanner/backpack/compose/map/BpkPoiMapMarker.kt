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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberMarkerState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.map.internal.PoiMarkerShape
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.rememberCapturedComposeBitmapDescriptor
import net.skyscanner.backpack.compose.tokens.internal.BpkMapMarkerColors

enum class BpkPoiMarkerStatus {
    Unselected,
    Selected,
}

/**
 * @param onClick Callback invoked when the marker is clicked. Return true to consume the event, for example to disable the default centre map on marker behaviour.
 */
@Composable
fun BpkPoiMapMarker(
    contentDescription: String,
    icon: BpkIcon,
    status: BpkPoiMarkerStatus = BpkPoiMarkerStatus.Unselected,
    state: MarkerState = rememberMarkerState(),
    tag: Any? = null,
    visible: Boolean = true,
    zIndex: Float? = null,
    onClick: (Marker) -> Boolean = { false },
) {
    val iconBitmap = rememberCapturedComposeBitmapDescriptor(icon, status) {
        PoiMarkerLayout(status = status, icon = icon)
    }

    MarkerInfoWindow(
        state = state,
        tag = tag,
        title = contentDescription,
        visible = visible,
        zIndex = if (status == BpkPoiMarkerStatus.Selected && zIndex == null) 1.0f else zIndex ?: 0.0f,
        icon = iconBitmap,
        onClick = onClick,
    ) {}
}

@Composable
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun PoiMarkerLayout(status: BpkPoiMarkerStatus, icon: BpkIcon, modifier: Modifier = Modifier) {
    val iconColor = BpkTheme.colors.textOnDark

    val backgroundColor = when (status) {
        BpkPoiMarkerStatus.Unselected -> BpkMapMarkerColors.mapPoiPin
        BpkPoiMarkerStatus.Selected -> BpkTheme.colors.corePrimary
    }

    val markerSize = when (status) {
        BpkPoiMarkerStatus.Selected -> DpSize(SelectedMarkerWidth, SelectedMarkerHeight)
        else -> DpSize(DefaultMarkerWidth, DefaultMarkerHeight)
    }

    val iconOffset = when (status) {
        BpkPoiMarkerStatus.Selected -> BpkSpacing.Sm
        else -> DefaultIconOffset
    }

    val shape = PoiMarkerShape()

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .background(color = backgroundColor, shape = shape)
            .size(markerSize),
    ) {
        BpkIcon(
            icon = icon,
            contentDescription = null,
            size = BpkIconSize.Small,
            tint = iconColor,
            modifier = Modifier
                .padding(top = iconOffset)
                .scale(0.75f),
        )
    }
}
private val DefaultIconOffset = 2.dp

private val SelectedMarkerWidth = 24.dp
private val SelectedMarkerHeight = 29.dp

private val DefaultMarkerWidth = 20.dp
private val DefaultMarkerHeight = 24.dp
