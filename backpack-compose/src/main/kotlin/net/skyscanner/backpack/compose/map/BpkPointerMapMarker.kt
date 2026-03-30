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
import net.skyscanner.backpack.compose.map.internal.BpkPointerMapMarkerImpl
import net.skyscanner.backpack.compose.map.internal.PointerMarkerLayoutImpl

@Deprecated("Use BpkLocationMapMarker instead.", ReplaceWith("BpkLocationMapMarker"), DeprecationLevel.WARNING)
@Composable
fun BpkPointerMapMarker(
    title: String,
    state: MarkerState = rememberUpdatedMarkerState(),
    tag: Any? = null,
    visible: Boolean = true,
    zIndex: Float = 0.0f,
    onClick: (Marker) -> Boolean = { false },
    onInfoWindowClick: (Marker) -> Unit = {},
) {
    BpkPointerMapMarkerImpl(
        title = title,
        state = state,
        tag = tag,
        visible = visible,
        zIndex = zIndex,
        onClick = onClick,
        onInfoWindowClick = onInfoWindowClick,
    )
}

@Composable
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
fun PointerMarkerLayout(modifier: Modifier = Modifier) {
    PointerMarkerLayoutImpl(modifier = modifier)
}
