package net.skyscanner.backpack.chip

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.TextViewCompat
import net.skyscanner.backpack.R

open class BpkDismissibleChip @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = R.style.Bpk_chip
) : BpkChip(context, attrs, defStyleAttr) {

  var onDismissListener: OnDismissListener? = null

  override fun setup() {
    super.setup()
    // close icon
    val closeIcon = ResourcesCompat.getDrawable(resources, R.drawable.bpk_close_small, context.theme)
    DrawableCompat.setTintList(closeIcon!!, ResourcesCompat.getColorStateList(resources, R.color.chip_close_icon_tint, context.theme))
    this.compoundDrawablePadding = resources.getDimension(R.dimen.bpkElevationSm).toInt()

    TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(
      this,
      null,
      null, closeIcon, null
    )
    setOnClickListener {
      onDismissListener?.onDismiss(this)
    }
  }

  interface OnDismissListener {
    fun onDismiss(v: BpkDismissibleChip)
  }
}
