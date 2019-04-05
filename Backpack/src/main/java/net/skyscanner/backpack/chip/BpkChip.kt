package net.skyscanner.backpack.chip

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ContextThemeWrapper
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.createContextThemeOverlayWrapper

open class BpkChip @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BpkText(createContextThemeOverlayWrapper(context, attrs), attrs, defStyleAttr) {

  init {
    initialize(attrs, defStyleAttr)
  }

  var disabled: Boolean = false
    set(value) {
      field = value
      this.isEnabled = !disabled
    }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0)
    disabled = attr.getBoolean(R.styleable.BpkChip_disabled, false)
    isSelected = attr.getBoolean(R.styleable.BpkChip_selected, false)
    attr.recycle()
    setup()
  }

  internal open fun setup() {
    // Elevation
    ViewCompat.setElevation(this, resources.getDimension(R.dimen.bpkElevationSm))

    textStyle = BpkText.SM
    this.setTextColor(ResourcesCompat.getColorStateList(resources, R.color.bpk_chip_text_color, context.theme))
    this.setSingleLine(true)

    // Wrap with the default style so we always have a valid attribute in the xml
    var context = ContextThemeWrapper(this.context, R.style.Bpk_chip)

    val t = TypedValue()
    if (context.theme.resolveAttribute(R.attr.bpkChipStyle, t, true)) {
      // If we have global styles (theming) for the chip we wrap again so this overrides the default
      context = ContextThemeWrapper(context, t.resourceId)
    }

    // Background
    val drawable = ResourcesCompat.getDrawable(resources, R.drawable.chip_background, context.theme)
    ViewCompat.setBackground(this, drawable)
  }

  fun toggle() {
    if (!disabled) {
      isSelected = !isSelected
    }
  }
}
