package net.skyscanner.backpack.dialog.internal

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.util.AttributeSet
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

  init {
    val size = resources.getDimensionPixelSize(R.dimen.bpk_dialog_icon_size)
    minimumWidth = size
    minimumHeight = size
    maxWidth = size
    maxHeight = size
    scaleType = ScaleType.CENTER
    imageTintList = ColorStateList.valueOf(getColor(R.color.bpkWhite))
    background = ContextCompat.getDrawable(this.context, R.drawable.bpk_dialog_bg_icon)
  }

  var icon: BpkDialog.Icon? = null
    set(value) {
      field = value
      if (value != null) {
        setImageDrawable(ContextCompat.getDrawable(context, value.drawableRes))
        background.setColorFilter(
          ContextCompat.getColor(context, value.colorRes),
          PorterDuff.Mode.ADD)
      }
    }
}
