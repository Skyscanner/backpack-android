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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import net.skyscanner.backpack.demo.BpkBaseActivity
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.MapMarkersComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.ComponentItem
import net.skyscanner.backpack.map.addBpkMarker
import net.skyscanner.backpack.map.getBpkMapAsync

@Composable
@MapMarkersComponent
@ViewStory(screenshot = false)
fun MapStory(modifier: Modifier = Modifier) {
  val context = LocalContext.current
  val values = remember { MapActivity.Type.values().toList() }

  LazyColumn(modifier.fillMaxSize()) {
    items(values) {
      ComponentItem(
        title = when (it) {
          MapActivity.Type.PointersOnly -> stringResource(R.string.maps_markers_pointers_only)
          MapActivity.Type.Badges -> stringResource(R.string.maps_markers_badges)
          MapActivity.Type.BadgesWithIcons -> stringResource(R.string.maps_markers_badges_with_icons)
        },
        onClick = { context.startActivity(MapActivity.of(context, it)) },
      )
    }
  }
}

class MapActivity : BpkBaseActivity() {

  enum class Type {
    PointersOnly,
    Badges,
    BadgesWithIcons,
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_map)
    setSupportActionBar(findViewById(R.id.detail_toolbar))
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    val type = intent.getSerializableExtra(EXTRA_TYPE) as Type
    val context = this

    if (savedInstanceState == null) {
      supportFragmentManager
        .findFragmentById(R.id.map_fragment)
        .let { it as SupportMapFragment }
        .getBpkMapAsync {
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
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> this.onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  companion object {

    private const val EXTRA_TYPE = "type"

    fun of(context: Context, type: Type): Intent =
      Intent(context, MapActivity::class.java)
        .putExtra(EXTRA_TYPE, type)
  }
}
