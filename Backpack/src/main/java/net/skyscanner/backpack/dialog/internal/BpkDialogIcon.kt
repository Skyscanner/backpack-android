package net.skyscanner.backpack.dialog.internal

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.dialog.BpkDialog
import net.skyscanner.backpack.util.getColor

internal class BpkDialogIcon @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

  private val size = resources.getDimensionPixelSize(R.dimen.bpk_dialog_icon_size)

  init {
    minimumWidth = size
    minimumHeight = size
    maxWidth = size
    maxHeight = size
    scaleType = ScaleType.CENTER
    imageTintList = ColorStateList.valueOf(getColor(R.color.bpkWhite))
  }

  var icon: BpkDialog.Icon? = null
    set(value) {
      field = value
      if (value != null) {
        setImageDrawable(ContextCompat.getDrawable(context, value.drawableRes))
        background = createBackground(value.colorRes)
      }
    }

  private fun createBackground(@ColorRes backgroundId: Int) = GradientDrawable().apply {
    cornerRadius = size / 2f
    color = ContextCompat.getColorStateList(context, backgroundId)
    setStroke(
      resources.getDimensionPixelSize(R.dimen.bpk_dialog_icon_stroke),
      ContextCompat.getColorStateList(context, R.color.__dialogBackground)
    )
  }
}
