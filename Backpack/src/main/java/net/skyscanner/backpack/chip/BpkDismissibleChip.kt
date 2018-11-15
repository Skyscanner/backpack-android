package net.skyscanner.backpack.chip

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.TextViewCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText


open class BpkDismissibleChip @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = R.style.Bpk_chip) : BpkText(context, attrs, defStyleAttr) {

  init {
    initialize(context, attrs, R.style.Bpk_chip)
  }
  private val iconSize = context.resources.getDimension(R.dimen.bpk_icon_size_small).toInt()

  var disabled: Boolean = false
    set(value) {
      field = value
      this.isEnabled = !disabled
    }

  private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
    val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0)
    try {
      disabled = attr.getBoolean(R.styleable.BpkChip_disabled, false)
    } finally {
      attr.recycle()
    }

    //Elevation
    ViewCompat.setElevation(this, resources.getDimension(R.dimen.bpkElevationSm))

    //Text
    TextViewCompat.setTextAppearance(this, R.style.bpkTextSm)
    this.setTextColor(ResourcesCompat.getColorStateList(resources, R.color.bpk_chip_text_color, context.theme))

    val closeIcon = ResourcesCompat.getDrawable(resources, R.drawable.bpk_close, context.theme)


    closeIcon?.setBounds(0, 0, iconSize, iconSize)
    DrawableCompat.setTintList(closeIcon!!, ResourcesCompat.getColorStateList(resources,R.color.chip_close_icon_tint,context.theme))

    TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(
      this,
      null,
      null, closeIcon, null
    )

    val background = ResourcesCompat.getDrawable(resources, R.drawable.chip_backgroud, context.theme)
    ViewCompat.setBackground(this, background)

  }
}

