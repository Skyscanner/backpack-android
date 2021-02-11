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
import android.graphics.Bitmap
import android.view.View
import androidx.annotation.DrawableRes
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import net.skyscanner.backpack.R
import net.skyscanner.backpack.map.view.createBpkMarkerView
import net.skyscanner.backpack.util.screenshotView

/**
 * An icon which will be displayed on the badge.
 */
var Marker.icon: Int
  @DrawableRes get() = extra.icon
  set(@DrawableRes value) {
    extra = extra.copy(icon = value)
  }

/**
 * An alternative solution to associate some object with given [Marker].
 *
 * [Marker.getTag] or [Marker.setTag] should never be used – Backpack utilises it
 * to store its internal properties (e.g. [Marker.icon]).
 *
 * @see Marker.getTag
 * @see Marker.setTag
 */
var Marker.bpkTag: Any?
  get() = extra.bpkTag
  set(value) {
    extra = extra.copy(bpkTag = value)
  }

/**
 * Adds custom Backpack marker to the map.
 * Methods like [GoogleMap.addMarker] should not be used as it may lead to undefined behaviour.
 *
 * Please be aware that this method is heavy as it captures the [View] to [Bitmap] and stores it in the memory.
 * Showing too many map markers may lead to frequent GC invocations and memory overflow.
 *
 * @param context will be used to construct the badge view
 * @param title will be displayed on the badge
 * @param icon will be displayed on the badge. If set to 0 (by default), no icon will be renderer.
 * @param tag any custom object needs to be associated with the marker
 *
 * @return [Marker] added to the map.
 */
fun GoogleMap.addBpkMarker(
  context: Context,
  position: LatLng,
  title: String,
  @DrawableRes icon: Int = 0,
  tag: Any? = null,
): Marker =
  addMarker(
    MarkerOptions()
      .position(position)
      .title(title)
      .infoWindowAnchor(0.5f, 1f)
      .also {
        it.icon(
          context.generateLabelIcon(title, icon) { x, y ->
            it.anchor(x, y)
          }
        )
      }
  ).apply {
    this.icon = icon
    this.bpkTag = tag
  }

internal inline fun Context.generateLabelIcon(
  title: String,
  icon: Int = 0,
  onAnchorPositionCalculated: (Float, Float) -> Unit
): BitmapDescriptor {

  val bitmap = screenshotView(
    view = createBpkMarkerView(this, title, icon),
  ) {
    val circle = it.findViewById<View>(R.id.circle)
    val halfCircleYPosition = it.height - (circle.height / 2f)
    onAnchorPositionCalculated(0.5f, halfCircleYPosition / it.height,)
  }

  return BitmapDescriptorFactory.fromBitmap(bitmap)
}

private var Marker.extra: Extra
  get() {
    val bpkTag = tag
    if (bpkTag != null) {
      require(bpkTag is Extra) { "Use Marker.bpkTag instead of Marker.tag" }
      return bpkTag
    }
    return Extra().also { tag = it }
  }
  set(value) {
    tag = value
  }

private data class Extra(
  @DrawableRes val icon: Int = 0,
  val bpkTag: Any? = null,
)
