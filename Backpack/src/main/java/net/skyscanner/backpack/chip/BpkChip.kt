package net.skyscanner.backpack.chip

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.wrapContextWithDefaults

open class BpkChip @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BpkText(wrapContextWithDefaults(context), attrs, defStyleAttr) {

  var disabled: Boolean = false
    set(value) {
      field = value
      this.isEnabled = !disabled
    }

  var chipBackgroundColor: Int = ContextCompat.getColor(context, R.color.bpkBackgroundSecondary)
    set(value) {
      field = value
      updateBackground()
    }

  var selectedBackgroundColor: Int = ContextCompat.getColor(context, R.color.bpkPrimary)
    set(value) {
      field = value
      updateBackground()
    }

  init {
    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    val t = TypedValue()
    var wrapped: Context = context

    if (context.theme.resolveAttribute(R.attr.bpkChipStyle, t, true)) {
      // If we have global styles (theming) for the chip we wrap again so this overrides the default
      wrapped = ContextThemeWrapper(context, t.resourceId)
    }

    val attr = wrapped.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0)
    background = ContextCompat.getDrawable(wrapped, R.drawable.chip_background)
    disabled = attr.getBoolean(R.styleable.BpkChip_disabled, false)
    isSelected = attr.getBoolean(R.styleable.BpkChip_selected, false)
    chipBackgroundColor = attr.getColor(R.styleable.BpkChip_chipBackgroundColor, chipBackgroundColor)
    selectedBackgroundColor = attr.getColor(R.styleable.BpkChip_chipSelectedBackgroundColor, selectedBackgroundColor)
    attr.recycle()
    setup()
  }

  internal open fun setup() {
    textStyle = SM
    weight = Weight.EMPHASIZED

    val textColor = ColorStateList(
      arrayOf(
        intArrayOf(android.R.attr.state_enabled, android.R.attr.state_selected),
        intArrayOf(android.R.attr.state_enabled),
        intArrayOf(-android.R.attr.state_enabled)
      ),
      intArrayOf(
        ContextCompat.getColor(context, R.color.bpkWhite),
        ContextCompat.getColor(context, R.color.bpkTextPrimary),
        ContextCompat.getColor(context, R.color.bpkSkyGrayTint04)
      )
    )

    this.setTextColor(textColor)
    this.setSingleLine(true)

    // Background
    updateBackground()
  }

  fun toggle() {
    if (!disabled) {
      isSelected = !isSelected
    }
  }

  private fun updateBackground() {
    val backgroundTintList = ColorStateList(
      arrayOf(
        intArrayOf(android.R.attr.state_enabled, android.R.attr.state_selected),
        intArrayOf(android.R.attr.state_enabled),
        intArrayOf(-android.R.attr.state_enabled)
      ),
      intArrayOf(
        selectedBackgroundColor,
        chipBackgroundColor,
        chipBackgroundColor
      )
    )
    ViewCompat.setBackgroundTintList(this, backgroundTintList)
  }
}
