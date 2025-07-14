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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.map.BpkLocationMapMarker
import net.skyscanner.backpack.compose.map.BpkPoiMapMarker
import net.skyscanner.backpack.compose.map.BpkPoiMarkerStatus
import net.skyscanner.backpack.compose.map.BpkPriceMapMarkerV2
import net.skyscanner.backpack.compose.map.BpkPriceMarkerV2Status
import net.skyscanner.backpack.compose.tokens.Airports
import net.skyscanner.backpack.compose.tokens.Heart
import net.skyscanner.backpack.compose.tokens.Landmark
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.MapMarkersComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.meta.StoryKind

@Composable
@MapMarkersComponent
@ComposeStory(kind = StoryKind.DemoOnly, name = "Price")
fun PriceMapMarkerV2Story(modifier: Modifier = Modifier) {
    val markerStatuses = remember {
        mutableStateListOf(
            BpkPriceMarkerV2Status.Selected,
            BpkPriceMarkerV2Status.PreviousSelected,
            BpkPriceMarkerV2Status.Unselected,
            BpkPriceMarkerV2Status.Selected,
            BpkPriceMarkerV2Status.PreviousSelected,
            BpkPriceMarkerV2Status.Unselected,
            BpkPriceMarkerV2Status.Selected,
            BpkPriceMarkerV2Status.PreviousSelected,
            BpkPriceMarkerV2Status.Unselected,
        )
    }

    val markerPositions = listOf(
        // No icons
        LatLng(51.588308, -0.391776),
        LatLng(51.585308, -0.391776),
        LatLng(51.582308, -0.391776),
        // Heart icons
        LatLng(51.578308, -0.391776),
        LatLng(51.575308, -0.391776),
        LatLng(51.572308, -0.391776),
        // Other icons
        LatLng(51.568308, -0.391776),
        LatLng(51.565308, -0.391776),
        LatLng(51.562308, -0.391776),
    )

    val markerIcons = listOf(
        null,
        BpkIcon.Heart,
        BpkIcon.Airports,
    )

    val cameraPositionState = rememberCameraPositionState().apply {
        position = CameraPosition.fromLatLngZoom(
            LatLng(51.575308, -0.391776),
            14f,
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        mapColorScheme = ComposeMapColorScheme.FOLLOW_SYSTEM,
    ) {
        markerPositions.forEachIndexed { index, latLng ->
            BpkPriceMapMarkerV2(
                title = stringArrayResource(R.array.map_marker_prices)[index % 3],
                status = markerStatuses[index],
                state = rememberMarkerState(position = latLng),
                onClick = {
                    markerStatuses.forEachIndexed { i, status ->
                        if (i / 3 == index / 3 && status == BpkPriceMarkerV2Status.Selected) {
                            markerStatuses[i] = BpkPriceMarkerV2Status.PreviousSelected
                        }
                    }
                    markerStatuses[index] = BpkPriceMarkerV2Status.Selected
                    false
                },
                prefixIcon = markerIcons[index / 3],
            )
        }
    }
}

@Composable
@MapMarkersComponent
@ComposeStory(kind = StoryKind.DemoOnly, name = "Location")
fun LocationMapMarkerStory(modifier: Modifier = Modifier) {
    GoogleMap(
        modifier = modifier,
        cameraPositionState = rememberCameraPositionState { MapPosition },
        mapColorScheme = ComposeMapColorScheme.FOLLOW_SYSTEM,
    ) {
        MarkerPositions.forEachIndexed { index, latLng ->
            BpkLocationMapMarker(
                title = stringArrayResource(R.array.map_marker_prices)[index],
                state = rememberMarkerState(position = latLng),
            )
        }
    }
}

@Composable
@MapMarkersComponent
@ComposeStory(kind = StoryKind.DemoOnly, name = "Poi")
fun PoiMapMarkerStory(modifier: Modifier = Modifier) {
    var focusedMarker by remember { mutableIntStateOf(0) }

    fun markerStatus(index: Int): BpkPoiMarkerStatus = when (index) {
        focusedMarker -> BpkPoiMarkerStatus.Selected
        else -> BpkPoiMarkerStatus.Unselected
    }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = rememberCameraPositionState { MapPosition },
        mapColorScheme = ComposeMapColorScheme.FOLLOW_SYSTEM,
    ) {
        MarkerPositions.forEachIndexed { index, latLng ->
            BpkPoiMapMarker(
                contentDescription = stringResource(R.string.map_marker_icon_landmark),
                status = markerStatus(index),
                icon = BpkIcon.Landmark,
                state = rememberMarkerState(position = latLng),
                onClick = { focusedMarker = index; false },
            )
        }
    }
}

private val MapPosition = CameraPosition.fromLatLngZoom(LatLng(51.528308, -0.381776), 14f)
private val MarkerPositions = listOf(
    LatLng(51.528308, -0.381776),
    LatLng(51.531626, -0.376539),
    LatLng(51.524563, -0.379421),
    LatLng(51.532181, -0.389347),
)
