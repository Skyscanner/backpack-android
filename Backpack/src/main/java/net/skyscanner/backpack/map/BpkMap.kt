/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

package net.skyscanner.backpack.map

import android.content.Context
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import net.skyscanner.backpack.map.view.createBpkMarkerView

/**
 * Receives the map in async mode and adds some Backpack customisations to it.
 */
fun MapView.getBpkMapAsync(onReady: (GoogleMap) -> Unit) {
  getMapAsync {
    it.setInfoWindowAdapter(BpkWindowInfoAdapter(context))
    onReady(it)
  }
}

/**
 * Receives the map in async mode and adds some Backpack customisations to it.
 */
fun SupportMapFragment.getBpkMapAsync(onReady: (GoogleMap) -> Unit) {
  getMapAsync {
    it.setInfoWindowAdapter(BpkWindowInfoAdapter(requireContext()))
    onReady(it)
  }
}

private class BpkWindowInfoAdapter(
  private val context: Context,
) : GoogleMap.InfoWindowAdapter {

  override fun getInfoWindow(marker: Marker): View =
    createBpkMarkerView(context, marker.title, marker.icon, showPointer = !marker.pointerOnly)
      .apply { isSelected = !marker.pointerOnly }

  override fun getInfoContents(p0: Marker): View? =
    null
}
