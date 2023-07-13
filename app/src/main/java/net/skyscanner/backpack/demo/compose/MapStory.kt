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

package net.skyscanner.backpack.demo.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.map.BpkIconMapMarker
import net.skyscanner.backpack.compose.map.BpkIconMarkerStatus
import net.skyscanner.backpack.compose.map.BpkMap
import net.skyscanner.backpack.compose.map.BpkPointerMapMarker
import net.skyscanner.backpack.compose.map.BpkPriceMapMarker
import net.skyscanner.backpack.compose.map.BpkPriceMarkerStatus
import net.skyscanner.backpack.compose.tokens.Landmark
import net.skyscanner.backpack.demo.components.MapMarkersComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@MapMarkersComponent
@ComposeStory(name = "Price")
fun PriceMapMarkerStory(modifier: Modifier = Modifier) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(51.528308, -0.381776), 14f)
    }
    BpkMap(cameraPositionState = cameraPositionState) {
        BpkPriceMapMarker(
            title = "£295",
            status = BpkPriceMarkerStatus.Default,
            state = rememberMarkerState(position = LatLng(51.528308, -0.381776)),
        )

        BpkPriceMapMarker(
            title = "£192",
            status = BpkPriceMarkerStatus.Viewed,
            state = rememberMarkerState(position = LatLng(51.526508, -0.384476)),
        )

        BpkPriceMapMarker(
            title = "£192",
            status = BpkPriceMarkerStatus.Focused,
            state = rememberMarkerState(position = LatLng(51.524508, -0.379476)),
        )

        BpkPriceMapMarker(
            title = "£150",
            status = BpkPriceMarkerStatus.Disabled,
            state = rememberMarkerState(position = LatLng(51.532108, -0.389376)),
        )
    }
}

@Composable
@MapMarkersComponent
@ComposeStory(name = "Icon")
fun IconMapMarkerStory(modifier: Modifier = Modifier) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(51.528308, -0.381776), 14f)
    }
    BpkMap(cameraPositionState = cameraPositionState) {
        BpkIconMapMarker(
            contentDescription = "Landmark",
            icon = BpkIcon.Landmark,
            status = BpkIconMarkerStatus.Default,
            state = rememberMarkerState(position = LatLng(51.528308, -0.381776)),
        )

        BpkIconMapMarker(
            contentDescription = "Landmark",
            icon = BpkIcon.Landmark,
            status = BpkIconMarkerStatus.Focused,
            state = rememberMarkerState(position = LatLng(51.526508, -0.384476)),
        )

        BpkIconMapMarker(
            contentDescription = "Landmark",
            icon = BpkIcon.Landmark,
            status = BpkIconMarkerStatus.Disabled,
            state = rememberMarkerState(position = LatLng(51.532108, -0.389376)),
        )
    }
}

@Composable
@MapMarkersComponent
@ComposeStory(name = "Pointer")
fun PointerMapMarkerStory(modifier: Modifier = Modifier) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(51.528308, -0.381776), 14f)
    }
    BpkMap(cameraPositionState = cameraPositionState) {
        BpkPointerMapMarker(
            title = "£295",
            state = rememberMarkerState(position = LatLng(51.528308, -0.381776)),
        )

        BpkPointerMapMarker(
            title = "£195",
            state = rememberMarkerState(position = LatLng(51.526508, -0.384476)),
        )

        BpkPointerMapMarker(
            title = "£149",
            state = rememberMarkerState(position = LatLng(51.532108, -0.389376)),
        )
    }
}
