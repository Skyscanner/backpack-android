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

package net.skyscanner.backpack.compose.map

import androidx.annotation.RestrictTo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.map.internal.BpkIconMapMarkerImpl
import net.skyscanner.backpack.compose.map.internal.IconMarkerLayoutImpl

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
    state: MarkerState = rememberUpdatedMarkerState(),
    tag: Any? = null,
    visible: Boolean = true,
    zIndex: Float? = null,
    onClick: (Marker) -> Boolean = { false },
) {
    BpkIconMapMarkerImpl(
        contentDescription = contentDescription,
        icon = icon,
        status = status,
        state = state,
        tag = tag,
        visible = visible,
        zIndex = zIndex,
        onClick = onClick,
    )
}

@Composable
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun IconMarkerLayout(status: BpkIconMarkerStatus, icon: BpkIcon, modifier: Modifier = Modifier) {
    IconMarkerLayoutImpl(status = status, icon = icon, modifier = modifier)
}
