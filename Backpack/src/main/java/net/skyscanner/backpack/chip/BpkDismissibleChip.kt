package net.skyscanner.backpack.chip

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.chip.Chip
import net.skyscanner.backpack.R


open class BpkDismissibleChip @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int = R.style.Bpk_chip) : Chip(context, attrs, defStyleAttr) {

  init {
    context.setTheme(com.google.android.material.R.style.Widget_MaterialComponents_Chip_Entry)
    initialize(context, attrs, R.style.Bpk_chip)
  }

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
    this.isCloseIconVisible = true
    this.setTextColor(ResourcesCompat.getColorStateList(resources, R.color.bpk_chip_text_color, context.theme))
    closeIcon = ResourcesCompat.getDrawable(resources, R.drawable.bpk_close, context.theme)
  }
}
