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

import android.location.Location
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.DefaultIndoorStateChangeListener
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.IndoorStateChangeListener
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun BpkMap(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    contentDescription: String? = null,
    googleMapOptionsFactory: () -> GoogleMapOptions = { GoogleMapOptions() },
    properties: MapProperties = MapProperties(),
    locationSource: LocationSource? = null,
    uiSettings: MapUiSettings = MapUiSettings(),
    indoorStateChangeListener: IndoorStateChangeListener = DefaultIndoorStateChangeListener,
    onMapClick: (LatLng) -> Unit = {},
    onMapLongClick: (LatLng) -> Unit = {},
    onMapLoaded: () -> Unit = {},
    onMyLocationButtonClick: () -> Boolean = { false },
    onMyLocationClick: (Location) -> Unit = {},
    onPOIClick: (PointOfInterest) -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(),
    content: (@Composable @GoogleMapComposable () -> Unit)? = null,
) {
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        contentDescription = contentDescription,
        googleMapOptionsFactory = googleMapOptionsFactory,
        properties = properties,
        locationSource = locationSource,
        uiSettings = uiSettings,
        indoorStateChangeListener = indoorStateChangeListener,
        onMapClick = onMapClick,
        onMapLongClick = onMapLongClick,
        onMapLoaded = onMapLoaded,
        onMyLocationButtonClick = onMyLocationButtonClick,
        onMyLocationClick = onMyLocationClick,
        onPOIClick = onPOIClick,
        contentPadding = contentPadding,
        content = content,
    )
}
