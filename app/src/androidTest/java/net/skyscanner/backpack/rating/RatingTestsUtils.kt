package net.skyscanner.backpack.rating

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import net.skyscanner.backpack.demo.R

internal fun createTestRating(
  context: Context,
  orientation: BpkRating.Orientation = BpkRating.Orientation.Horizontal,
  size: BpkRating.Size = BpkRating.Size.Base,
  value: Float = 7f,
  rtl: Boolean = false
) =
  BpkRating(context, orientation, size).apply {
    icon = {
      when (it) {
        BpkRating.Score.Low -> context.getDrawable(R.drawable.bpk_star_outline)
        BpkRating.Score.Medium -> context.getDrawable(R.drawable.bpk_star_half)
        BpkRating.Score.High -> context.getDrawable(R.drawable.bpk_star)
      }
    }
    title = {
      when (it) {
        BpkRating.Score.Low -> "Low"
        BpkRating.Score.Medium -> "Medium"
        BpkRating.Score.High -> "High"
      }
    }
    subtitle = {
      when (it) {
        BpkRating.Score.Low -> "Sub Low"
        BpkRating.Score.Medium -> "Sub Medium"
        BpkRating.Score.High -> "Sub High"
      }
    }
    this.value = value
    layoutDirection = if (rtl) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
    background = ColorDrawable(Color.WHITE)
  }
