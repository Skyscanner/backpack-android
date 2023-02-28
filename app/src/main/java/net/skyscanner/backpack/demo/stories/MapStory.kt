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

package net.skyscanner.backpack.demo.stories

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.MapMarkersComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.map.addBpkMarker
import net.skyscanner.backpack.map.getBpkMapAsync
import net.skyscanner.backpack.map.setupBpkInfoWindowAdapter
import net.skyscanner.backpack.util.InternalBackpackApi

@Composable
@MapMarkersComponent
@ViewStory("Pointers")
fun MapMarkerPointersStory(modifier: Modifier = Modifier) =
  MapMarkerDemo(MapFragment.Type.PointersOnly, modifier)

@Composable
@MapMarkersComponent
@ViewStory("Badges")
fun MapMarkerBadgesStory(modifier: Modifier = Modifier) =
  MapMarkerDemo(MapFragment.Type.Badges, modifier)

@Composable
@MapMarkersComponent
@ViewStory("With icons")
fun MapMarkerWithIconsStory(modifier: Modifier = Modifier) =
  MapMarkerDemo(MapFragment.Type.BadgesWithIcons, modifier)

@OptIn(InternalBackpackApi::class)
@Composable
fun MapMarkerDemo(
  type: MapFragment.Type,
  modifier: Modifier = Modifier,
) {
  val context = LocalContext.current
  GoogleMap(modifier) {
    MapEffect(context, type) {
      it.setupBpkInfoWindowAdapter(context)
      setupMapMarkers(context, it, type)
    }
  }
}

class MapFragment : Story() {

  enum class Type {
    PointersOnly,
    Badges,
    BadgesWithIcons,
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
    val context = requireContext()
    val type = arguments?.getSerializable(TYPE) as Type
    mapFragment.getBpkMapAsync {
      setupMapMarkers(context, it, type)
    }
  }

  companion object {
    private const val TYPE = "type"

    infix fun of(type: Type) = MapFragment().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, R.layout.fragment_map)
      arguments?.putBoolean(SCROLLABLE, false)
      arguments?.putSerializable(TYPE, type)
    }
  }
}

private fun setupMapMarkers(context: Context, map: GoogleMap, type: MapFragment.Type) {
  map.addBpkMarker(
    context = context,
    position = LatLng(45.0, 0.0),
    title = "Badge 1",
    icon = if (type == MapFragment.Type.BadgesWithIcons) R.drawable.bpk_city else 0,
    pointerOnly = type == MapFragment.Type.PointersOnly,
  )
  map.addBpkMarker(
    context = context,
    position = LatLng(0.0, 0.0),
    title = "Badge 2",
    icon = if (type == MapFragment.Type.BadgesWithIcons) R.drawable.bpk_city else 0,
    pointerOnly = type == MapFragment.Type.PointersOnly,
  )
  map.addBpkMarker(
    context = context,
    position = LatLng(-45.0, 0.0),
    title = "Badge 3",
    icon = if (type == MapFragment.Type.BadgesWithIcons) R.drawable.bpk_city else 0,
    pointerOnly = type == MapFragment.Type.PointersOnly,
  )
}
