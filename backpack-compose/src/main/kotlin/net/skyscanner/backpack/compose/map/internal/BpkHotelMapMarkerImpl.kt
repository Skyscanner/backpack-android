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

package net.skyscanner.backpack.compose.map.internal

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
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.map.BpkHotelMarkerStatus
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.internal.BpkMapMarkerColors
import net.skyscanner.backpack.compose.utils.rememberCapturedComposeBitmapDescriptor

@Composable
internal fun BpkHotelMapMarkerImpl(
    contentDescription: String,
    icon: BpkIcon,
    status: BpkHotelMarkerStatus,
    state: MarkerState,
    tag: Any?,
    visible: Boolean,
    zIndex: Float?,
    onClick: (Marker) -> Boolean,
) {
    val iconBitmap = rememberCapturedComposeBitmapDescriptor(icon, status) {
        HotelMarkerLayoutImpl(status = status, icon = icon)
    }

    iconBitmap?.let {
        MarkerInfoWindow(
            state = state,
            tag = tag,
            title = contentDescription,
            visible = visible,
            zIndex = if (status == BpkHotelMarkerStatus.Selected && zIndex == null) 1.0f else zIndex ?: 0.0f,
            icon = iconBitmap,
            onClick = onClick,
        ) {}
    }
}

@Composable
internal fun HotelMarkerLayoutImpl(status: BpkHotelMarkerStatus, icon: BpkIcon, modifier: Modifier = Modifier) {
    val iconColor = when (status) {
        BpkHotelMarkerStatus.Unselected -> BpkTheme.colors.corePrimary
        BpkHotelMarkerStatus.Selected -> BpkTheme.colors.textOnDark
    }

    val backgroundColor = when (status) {
        BpkHotelMarkerStatus.Unselected -> BpkMapMarkerColors.mapPreviousSelection
        BpkHotelMarkerStatus.Selected -> BpkTheme.colors.corePrimary
    }

    val markerSize = when (status) {
        BpkHotelMarkerStatus.Selected -> DpSize(SelectedHotelMarkerWidth, SelectedHotelMarkerHeight)
        else -> DpSize(DefaultHotelMarkerWidth, DefaultHotelMarkerHeight)
    }

    val iconOffset = when (status) {
        BpkHotelMarkerStatus.Selected -> BpkSpacing.Sm
        else -> DefaultHotelIconOffset
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

private val DefaultHotelIconOffset = 2.dp

private val SelectedHotelMarkerWidth = 24.dp
private val SelectedHotelMarkerHeight = 29.dp

private val DefaultHotelMarkerWidth = 20.dp
private val DefaultHotelMarkerHeight = 24.dp
