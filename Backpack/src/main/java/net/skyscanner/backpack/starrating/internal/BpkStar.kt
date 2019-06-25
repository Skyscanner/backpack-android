package net.skyscanner.backpack.starrating.internal

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable

internal class BpkStar(
  private val empty: Drawable,
  private val half: Drawable,
  private val full: Drawable
) : LayerDrawable(arrayOf(
  empty,
  half,
  full
)) {

  enum class Value {
    Empty,
    Half,
    Full
  }

  internal var value = Value.Empty
    set(value) {
      if (field != value) {
        field = value
        invalidateSelf()
      }
    }

  override fun draw(canvas: Canvas) {
    when (value) {
      Value.Empty -> empty.draw(canvas)
      Value.Half -> half.draw(canvas)
      Value.Full -> full.draw(canvas)
    }
  }
}
