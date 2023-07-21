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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberMarkerState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.map.internal.IconMarkerShape
import net.skyscanner.backpack.compose.utils.rememberCapturedComposeBitmapDescriptor
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkElevation

enum class BpkIconMarkerStatus {
    Default,
    Focused,
    Disabled,
}

@Composable
fun BpkIconMapMarker(
    contentDescription: String,
    icon: BpkIcon,
    status: BpkIconMarkerStatus = BpkIconMarkerStatus.Default,
    state: MarkerState = rememberMarkerState(),
    tag: Any? = null,
    visible: Boolean = true,
    zIndex: Float = 0.0f,
    onClick: (Marker) -> Unit = {},
) {
    val iconBitmap = rememberCapturedComposeBitmapDescriptor(icon, status) {
        IconMarkerLayout(status = status, icon = icon)
    }

    MarkerInfoWindow(
        state = state,
        tag = tag,
        title = contentDescription,
        visible = visible,
        zIndex = zIndex,
        icon = iconBitmap,
        onClick = { onClick(it); false },
    ) {}
}

@Composable
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun IconMarkerLayout(status: BpkIconMarkerStatus, icon: BpkIcon, modifier: Modifier = Modifier) {
    val iconColor = when (status) {
        BpkIconMarkerStatus.Default -> BpkTheme.colors.surfaceDefault
        BpkIconMarkerStatus.Focused -> BpkTheme.colors.statusSuccessSpot
        BpkIconMarkerStatus.Disabled -> BpkTheme.colors.coreEco
    }

    val backgroundColor = when (status) {
        BpkIconMarkerStatus.Default -> BpkTheme.colors.statusSuccessSpot
        BpkIconMarkerStatus.Focused -> BpkTheme.colors.surfaceDefault
        BpkIconMarkerStatus.Disabled -> BpkTheme.colors.statusSuccessFill
    }

    val strokeColor = when (status) {
        BpkIconMarkerStatus.Focused -> BpkTheme.colors.statusSuccessSpot
        else -> backgroundColor
    }

    val markerSize = when (status) {
        BpkIconMarkerStatus.Focused -> DpSize(32.dp, 40.dp)
        else -> DpSize(26.dp, 32.dp)
    }

    val iconOffset = when (status) {
        BpkIconMarkerStatus.Focused -> 8.dp
        else -> 5.dp
    }

    val shape = IconMarkerShape()

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .shadow(elevation = BpkElevation.Sm, shape = shape)
            .background(color = backgroundColor, shape = shape)
            .border(
                width = 1.dp,
                color = strokeColor,
                shape = shape,
            )
            .size(markerSize),
    ) {
        BpkIcon(
            icon = icon,
            contentDescription = "",
            size = BpkIconSize.Small,
            tint = iconColor,
            modifier = Modifier.padding(top = iconOffset),
        )
    }
}
