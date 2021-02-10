package net.skyscanner.backpack.map

import android.content.Context
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import net.skyscanner.backpack.map.view.createMarkerView

fun MapView.getBpkMapAsync(onReady: (GoogleMap) -> Unit) {
  getMapAsync {
    it.setInfoWindowAdapter(BpkWindowInfoAdapter(context))
    onReady(it)
  }
}

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
    createMarkerView(context, marker.title, marker.icon, true)

  override fun getInfoContents(p0: Marker): View? =
    null
}
