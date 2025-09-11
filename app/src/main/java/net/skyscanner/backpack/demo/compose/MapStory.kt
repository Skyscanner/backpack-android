/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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
import androidx.compose.runtime.mutableStateOf
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
import com.google.maps.android.compose.rememberUpdatedMarkerState
import net.skyscanner.backpack.icon.BpkIcon
import net.skyscanner.backpack.compose.map.BpkHotelMapMarker
import net.skyscanner.backpack.compose.map.BpkHotelMarkerStatus
import net.skyscanner.backpack.compose.map.BpkIconMapMarker
import net.skyscanner.backpack.compose.map.BpkIconMarkerStatus
import net.skyscanner.backpack.compose.map.BpkLocationMapMarker
import net.skyscanner.backpack.compose.map.BpkPoiMapMarker
import net.skyscanner.backpack.compose.map.BpkPoiMarkerStatus
import net.skyscanner.backpack.compose.map.BpkPointerMapMarker
import net.skyscanner.backpack.compose.map.BpkPriceMapMarker
import net.skyscanner.backpack.compose.map.BpkPriceMapMarkerV2
import net.skyscanner.backpack.compose.map.BpkPriceMarkerStatus
import net.skyscanner.backpack.compose.map.BpkPriceMarkerV2Status
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.MapMarkersComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.icon.tokens.Airports
import net.skyscanner.backpack.icon.tokens.Cafe
import net.skyscanner.backpack.icon.tokens.Heart
import net.skyscanner.backpack.icon.tokens.Hotels
import net.skyscanner.backpack.icon.tokens.Landmark
import net.skyscanner.backpack.meta.StoryKind

@Composable
@MapMarkersComponent
@ComposeStory(kind = StoryKind.DemoOnly, name = "Price")
fun PriceMapMarkerStory(modifier: Modifier = Modifier) {
    var focusedMarker by remember { mutableIntStateOf(0) }
    var viewedMarkers by remember { mutableStateOf(setOf(1)) }

    fun markerStatus(index: Int): BpkPriceMarkerStatus = when {
        index == 3 -> BpkPriceMarkerStatus.Disabled
        index == focusedMarker -> BpkPriceMarkerStatus.Focused
        viewedMarkers.contains(index) -> BpkPriceMarkerStatus.Viewed
        else -> BpkPriceMarkerStatus.Default
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = rememberCameraPositionState { MapPosition },
        mapColorScheme = ComposeMapColorScheme.FOLLOW_SYSTEM,
    ) {
        MarkerPositions.forEachIndexed { index, latLng ->
            BpkPriceMapMarker(
                title = stringArrayResource(R.array.map_marker_prices)[index],
                status = markerStatus(index),
                state = rememberUpdatedMarkerState(latLng),
                onClick = { focusedMarker = index; viewedMarkers += index; false },
            )
        }
    }
}

@Composable
@MapMarkersComponent
@ComposeStory(kind = StoryKind.DemoOnly, name = "PriceV2")
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
                state = rememberUpdatedMarkerState(latLng),
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
@ComposeStory(kind = StoryKind.DemoOnly, name = "Icon")
fun IconMapMarkerStory(modifier: Modifier = Modifier) {
    var focusedMarker by remember { mutableIntStateOf(0) }

    fun markerStatus(index: Int): BpkIconMarkerStatus = when (index) {
        3 -> BpkIconMarkerStatus.Disabled
        focusedMarker -> BpkIconMarkerStatus.Focused
        else -> BpkIconMarkerStatus.Default
    }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = rememberCameraPositionState { MapPosition },
        mapColorScheme = ComposeMapColorScheme.FOLLOW_SYSTEM,
    ) {
        MarkerPositions.forEachIndexed { index, latLng ->
            BpkIconMapMarker(
                contentDescription = stringResource(if (index == 2) R.string.map_marker_icon_cafe else R.string.map_marker_icon_landmark),
                status = markerStatus(index),
                icon = if (index == 2) BpkIcon.Cafe else BpkIcon.Landmark,
                state = rememberUpdatedMarkerState(latLng),
                onClick = { focusedMarker = index; false },
            )
        }
    }
}

@Composable
@MapMarkersComponent
@ComposeStory(kind = StoryKind.DemoOnly, name = "Pointer")
fun PointerMapMarkerStory(modifier: Modifier = Modifier) {
    GoogleMap(
        modifier = modifier,
        cameraPositionState = rememberCameraPositionState { MapPosition },
        mapColorScheme = ComposeMapColorScheme.FOLLOW_SYSTEM,
    ) {
        MarkerPositions.forEachIndexed { index, latLng ->
            BpkPointerMapMarker(
                title = stringArrayResource(R.array.map_marker_prices)[index],
                state = rememberUpdatedMarkerState(latLng),
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
                state = rememberUpdatedMarkerState(latLng),
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
                state = rememberUpdatedMarkerState(latLng),
                onClick = { focusedMarker = index; false },
            )
        }
    }
}

@Composable
@MapMarkersComponent
@ComposeStory(kind = StoryKind.DemoOnly, name = "Hotel")
fun HotelMapMarkerStory(modifier: Modifier = Modifier) {
    var focusedMarker by remember { mutableIntStateOf(0) }

    fun markerStatus(index: Int): BpkHotelMarkerStatus = when (index) {
        focusedMarker -> BpkHotelMarkerStatus.Selected
        else -> BpkHotelMarkerStatus.Unselected
    }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = rememberCameraPositionState { MapPosition },
        mapColorScheme = ComposeMapColorScheme.FOLLOW_SYSTEM,
    ) {
        MarkerPositions.forEachIndexed { index, latLng ->
            BpkHotelMapMarker(
                contentDescription = stringResource(R.string.navigation_tabs_hotels),
                status = markerStatus(index),
                icon = BpkIcon.Hotels,
                state = rememberUpdatedMarkerState(latLng),
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
