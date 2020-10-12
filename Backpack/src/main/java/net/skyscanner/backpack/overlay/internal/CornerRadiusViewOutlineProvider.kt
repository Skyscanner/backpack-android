package net.skyscanner.backpack.overlay.internal

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.DimenRes

internal class CornerRadiusViewOutlineProvider(
  @DimenRes private val radiusRes: Int
) : ViewOutlineProvider() {

  override fun getOutline(view: View, outline: Outline) {
    val radius = view.resources.getDimension(radiusRes)
    outline.setRoundRect(0, 0, view.width, view.height, radius)
  }
}
