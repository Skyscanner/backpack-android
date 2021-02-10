package net.skyscanner.backpack.map

import android.content.Context
import android.view.View
import androidx.annotation.DrawableRes
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import net.skyscanner.backpack.R
import net.skyscanner.backpack.map.view.createMarkerView
import net.skyscanner.backpack.util.screenshotView

var Marker.icon: Int
  @DrawableRes get() = extra.icon
  set(@DrawableRes value) {
    extra = extra.copy(icon = value)
  }

var Marker.bpkTag: Any?
  get() = extra.bpkTag
  set(value) {
    extra = extra.copy(bpkTag = value)
  }

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
    view = createMarkerView(this, title, icon, false),
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
