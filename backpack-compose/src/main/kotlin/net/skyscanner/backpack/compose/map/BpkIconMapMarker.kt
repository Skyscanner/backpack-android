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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.map.internal.IconMarkerShape
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderSize
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.rememberCapturedComposeBitmapDescriptor

enum class BpkIconMarkerStatus {
    Default,
    Focused,
    Disabled,
}

/**
 * @param onClick Callback invoked when the marker is clicked. Return true to consume the event, for example to disable the default centre map on marker behaviour.
 */
@Deprecated("Use BpkPoiMapMarker instead.", ReplaceWith("BpkPoiMapMarker"), DeprecationLevel.WARNING)
@Composable
fun BpkIconMapMarker(
    contentDescription: String,
    icon: BpkIcon,
    status: BpkIconMarkerStatus = BpkIconMarkerStatus.Default,
    state: MarkerState = MarkerState(),
    tag: Any? = null,
    visible: Boolean = true,
    zIndex: Float? = null,
    onClick: (Marker) -> Boolean = { false },
) {
    val iconBitmap = rememberCapturedComposeBitmapDescriptor(icon, status) {
        IconMarkerLayout(status = status, icon = icon)
    }

    MarkerInfoWindow(
        state = state,
        tag = tag,
        title = contentDescription,
        visible = visible,
        zIndex = if (status == BpkIconMarkerStatus.Focused && zIndex == null) 1.0f else zIndex ?: 0.0f,
        icon = iconBitmap,
        onClick = onClick,
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
        BpkIconMarkerStatus.Focused -> DpSize(FocusedMarkerWidth, FocusedMarkerHeight)
        else -> DpSize(DefaultMarkerWidth, DefaultMarkerHeight)
    }

    val iconOffset = when (status) {
        BpkIconMarkerStatus.Focused -> BpkSpacing.Md
        else -> DefaultIconOffset
    }

    val shape = IconMarkerShape()

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .background(color = backgroundColor, shape = shape)
            .border(
                width = BpkBorderSize.Sm,
                color = strokeColor,
                shape = shape,
            )
            .size(markerSize),
    ) {
        BpkIcon(
            icon = icon,
            contentDescription = null,
            size = BpkIconSize.Small,
            tint = iconColor,
            modifier = Modifier.padding(top = iconOffset),
        )
    }
}

private val DefaultIconOffset = 5.dp

private val FocusedMarkerWidth = 32.dp
private val FocusedMarkerHeight = 40.dp

private val DefaultMarkerWidth = 26.dp
private val DefaultMarkerHeight = 32.dp
