package net.skyscanner.backpack.chip

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.TextViewCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.Tooling
import net.skyscanner.backpack.util.BpkView

open class BpkChip @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = R.style.Bpk_chip
) : BpkText(context, attrs, defStyleAttr), Tooling by BpkView() {

  init {
    initialize(context, attrs, defStyleAttr)
  }

  var disabled: Boolean = false
    set(value) {
      field = value
      this.isEnabled = !disabled
    }

  private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
    val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0)
    disabled = attr.getBoolean(R.styleable.BpkChip_disabled, false)
    isSelected = attr.getBoolean(R.styleable.BpkChip_selected, false)
    attr.recycle()
    setup()
    highlight(this)
  }

  open fun setup() {
    // Elevation
    ViewCompat.setElevation(this, resources.getDimension(R.dimen.bpkElevationSm))

    // Text
    TextViewCompat.setTextAppearance(this, R.style.bpkTextSm)
    this.setTextColor(ResourcesCompat.getColorStateList(resources, R.color.bpk_chip_text_color, context.theme))
    this.setSingleLine(true)

    // Background
    val drawable = ResourcesCompat.getDrawable(resources, R.drawable.chip_background, context.theme)
    ViewCompat.setBackground(this, drawable)

    // State change
    setOnClickListener {
      if (!disabled) {
        isSelected = !isSelected
      }
    }
  }
}
