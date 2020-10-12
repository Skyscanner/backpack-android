package net.skyscanner.backpack.overlay.internal

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

internal object EmptyViewOutlineProvider : ViewOutlineProvider() {

  override fun getOutline(view: View, outline: Outline) = Unit
}
