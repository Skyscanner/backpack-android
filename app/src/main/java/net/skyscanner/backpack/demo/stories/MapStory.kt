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
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.BpkBaseActivity
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.MapMarkersComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.map.addBpkMarker
import net.skyscanner.backpack.map.getBpkMapAsync
import net.skyscanner.backpack.util.unsafeLazy

@Composable
@MapMarkersComponent
@ViewStory
fun MapStory(modifier: Modifier = Modifier) {
  val context = LocalContext.current
  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base, Alignment.CenterVertically),
  ) {
    BpkButton(stringResource(R.string.maps_markers_pointers_only)) {
      context.openMapStory(MapFragment.Type.PointersOnly)
    }
    BpkButton(stringResource(R.string.maps_markers_badges)) {
      context.openMapStory(MapFragment.Type.Badges)
    }
    BpkButton(stringResource(R.string.maps_markers_badges_with_icons)) {
      context.openMapStory(MapFragment.Type.Badges)
    }
  }
}

private fun Context.openMapStory(type: MapFragment.Type) =
  startActivity(
    Intent(this, MapActivity::class.java)
      .putExtra(MapActivity.EXTRA_TYPE, type),
  )

class MapActivity : BpkBaseActivity() {

  companion object {
    const val EXTRA_TYPE = "type"
  }

  private val detailToolbar by unsafeLazy { findViewById<Toolbar>(R.id.detail_toolbar) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_component_detail)
    setSupportActionBar(detailToolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    setTitle(R.string.map_markers)

    if (savedInstanceState == null) {
      val type = intent.getSerializableExtra(EXTRA_TYPE) as MapFragment.Type
      supportFragmentManager.beginTransaction()
        .add(R.id.component_detail_container, MapFragment.of(type))
        .commit()
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> this.onBackPressed()
    }
    return super.onOptionsItemSelected(item)
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
      it.addBpkMarker(
        context = context,
        position = LatLng(45.0, 0.0),
        title = "Badge 1",
        icon = if (type == Type.BadgesWithIcons) R.drawable.bpk_city else 0,
        pointerOnly = type == Type.PointersOnly,
      )
      it.addBpkMarker(
        context = context,
        position = LatLng(0.0, 0.0),
        title = "Badge 2",
        icon = if (type == Type.BadgesWithIcons) R.drawable.bpk_city else 0,
        pointerOnly = type == Type.PointersOnly,
      )
      it.addBpkMarker(
        context = context,
        position = LatLng(-45.0, 0.0),
        title = "Badge 3",
        icon = if (type == Type.BadgesWithIcons) R.drawable.bpk_city else 0,
        pointerOnly = type == Type.PointersOnly,
      )
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
