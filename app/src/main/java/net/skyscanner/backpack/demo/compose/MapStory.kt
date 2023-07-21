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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.map.BpkIconMapMarker
import net.skyscanner.backpack.compose.map.BpkIconMarkerStatus
import net.skyscanner.backpack.compose.map.BpkPointerMapMarker
import net.skyscanner.backpack.compose.map.BpkPriceMapMarker
import net.skyscanner.backpack.compose.map.BpkPriceMarkerStatus
import net.skyscanner.backpack.compose.tokens.Cafe
import net.skyscanner.backpack.compose.tokens.Landmark
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.MapMarkersComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.meta.StoryKind

@Composable
@MapMarkersComponent
@ComposeStory(kind = StoryKind.DemoOnly, name = "Price")
fun PriceMapMarkerStory(modifier: Modifier = Modifier) {
    val cameraPositionState = rememberCameraPositionState {
        position = MAP_POSITION
    }

    var focusedMarker by remember { mutableStateOf(0) }
    var viewedMarkers by remember { mutableStateOf(setOf(1)) }

    fun markerStatus(index: Int): BpkPriceMarkerStatus {
        return when {
            index == focusedMarker -> BpkPriceMarkerStatus.Focused
            viewedMarkers.contains(index) -> BpkPriceMarkerStatus.Viewed
            else -> BpkPriceMarkerStatus.Default
        }
    }

    GoogleMap(cameraPositionState = cameraPositionState) {
        BpkPriceMapMarker(
            title = stringResource(R.string.map_marker_price_1),
            status = markerStatus(0),
            state = rememberMarkerState(position = MARKER_POSITION_1),
            onClick = { focusedMarker = 0; viewedMarkers += 0 },
        )

        BpkPriceMapMarker(
            title = stringResource(R.string.map_marker_price_2),
            status = markerStatus(1),
            state = rememberMarkerState(position = MARKER_POSITION_2),
            onClick = { focusedMarker = 1; viewedMarkers += 1 },
        )

        BpkPriceMapMarker(
            title = stringResource(R.string.map_marker_price_3),
            status = markerStatus(2),
            state = rememberMarkerState(position = MARKER_POSITION_3),
            onClick = { focusedMarker = 2; viewedMarkers += 2 },
        )

        BpkPriceMapMarker(
            title = stringResource(R.string.map_marker_price_4),
            status = BpkPriceMarkerStatus.Disabled,
            state = rememberMarkerState(position = MARKER_POSITION_4),
        )
    }
}

@Composable
@MapMarkersComponent
@ComposeStory(kind = StoryKind.DemoOnly, name = "Icon")
fun IconMapMarkerStory(modifier: Modifier = Modifier) {
    val cameraPositionState = rememberCameraPositionState {
        position = MAP_POSITION
    }

    var focusedMarker by remember { mutableStateOf(0) }
    GoogleMap(cameraPositionState = cameraPositionState) {
        BpkIconMapMarker(
            contentDescription = stringResource(R.string.map_marker_icon_landmark),
            icon = BpkIcon.Landmark,
            status = if (focusedMarker == 0) BpkIconMarkerStatus.Focused else BpkIconMarkerStatus.Default,
            state = rememberMarkerState(position = MARKER_POSITION_1),
            onClick = { focusedMarker = 0 },
        )

        BpkIconMapMarker(
            contentDescription = stringResource(R.string.map_marker_icon_landmark),
            icon = BpkIcon.Landmark,
            status = if (focusedMarker == 1) BpkIconMarkerStatus.Focused else BpkIconMarkerStatus.Default,
            state = rememberMarkerState(position = MARKER_POSITION_2),
            onClick = { focusedMarker = 1 },
        )

        BpkIconMapMarker(
            contentDescription = stringResource(R.string.map_marker_icon_cafe),
            icon = BpkIcon.Cafe,
            status = if (focusedMarker == 2) BpkIconMarkerStatus.Focused else BpkIconMarkerStatus.Default,
            state = rememberMarkerState(position = MARKER_POSITION_3),
            onClick = { focusedMarker = 2 },
        )

        BpkIconMapMarker(
            contentDescription = stringResource(R.string.map_marker_icon_landmark),
            icon = BpkIcon.Landmark,
            status = BpkIconMarkerStatus.Disabled,
            state = rememberMarkerState(position = MARKER_POSITION_4),
        )
    }
}

@Composable
@MapMarkersComponent
@ComposeStory(kind = StoryKind.DemoOnly, name = "Pointer")
fun PointerMapMarkerStory(modifier: Modifier = Modifier) {
    val cameraPositionState = rememberCameraPositionState {
        position = MAP_POSITION
    }
    GoogleMap(cameraPositionState = cameraPositionState) {
        BpkPointerMapMarker(
            title = stringResource(R.string.map_marker_price_1),
            state = rememberMarkerState(position = MARKER_POSITION_1),
        )

        BpkPointerMapMarker(
            title = stringResource(R.string.map_marker_price_2),
            state = rememberMarkerState(position = MARKER_POSITION_2),
        )

        BpkPointerMapMarker(
            title = stringResource(R.string.map_marker_price_3),
            state = rememberMarkerState(position = MARKER_POSITION_3),
        )

        BpkPointerMapMarker(
            title = stringResource(R.string.map_marker_price_4),
            state = rememberMarkerState(position = MARKER_POSITION_4),
        )
    }
}

private val MAP_POSITION = CameraPosition.fromLatLngZoom(LatLng(51.528308, -0.381776), 14f)
private val MARKER_POSITION_1 = LatLng(51.528308, -0.381776)
private val MARKER_POSITION_2 = LatLng(51.531626, -0.376539)
private val MARKER_POSITION_3 = LatLng(51.524563, -0.379421)
private val MARKER_POSITION_4 = LatLng(51.532181, -0.389347)
