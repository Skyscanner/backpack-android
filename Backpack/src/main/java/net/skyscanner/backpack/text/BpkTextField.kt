package net.skyscanner.backpack.text

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.graphics.drawable.DrawableCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.BpkTheme
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

class BpkTextField @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatEditText(createContextThemeWrapper(context, attrs, R.attr.bpkTextFieldStyle), attrs, defStyleAttr) {

  private var iconTintColor: Int = 0
    set(value) {
      field = value
      iconStart?.let { DrawableCompat.setTint(it, value) }
      iconEnd?.let { DrawableCompat.setTint(it, value) }
    }

  var iconStart: Drawable? = null
    set(value) {
      unscheduleDrawable(field)
      field = value
        ?.mutate()
        ?.let { DrawableCompat.wrap(it) }
        ?.also { DrawableCompat.setTint(value, iconTintColor) }
      setCompoundDrawablesRelativeWithIntrinsicBounds(iconStart, null, iconEnd, null)
    }

  var iconEnd: Drawable? = null
    set(value) {
      unscheduleDrawable(field)
      field = value
        ?.mutate()
        ?.let { DrawableCompat.wrap(it) }
        ?.also { DrawableCompat.setTint(value, iconTintColor) }
      setCompoundDrawablesRelativeWithIntrinsicBounds(iconStart, null, iconEnd, null)
    }

  init {
    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    BpkText.getFont(context, BpkText.SM, BpkText.Weight.NORMAL).applyTo(paint)

    var textFieldColor = BpkTheme.getColor(context, R.color.bpkGray700)
    var textFieldColorHintNormal = BpkTheme.getColor(context, R.color.bpkGray300)
    var textFieldColorHintFocused = BpkTheme.getColor(context, R.color.bpkGray500)
    var textFieldColorIcon = BpkTheme.getColor(context, R.color.bpkGray700)

    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkTextField,
      defStyleAttr,
      0
    ).use {
      textFieldColor = it.getColor(R.styleable.BpkTextField_textFieldColor, textFieldColor)
      textFieldColorHintNormal = it.getColor(R.styleable.BpkTextField_textFieldColorHintNormal, textFieldColorHintNormal)
      textFieldColorHintFocused = it.getColor(R.styleable.BpkTextField_textFieldColorHintFocused, textFieldColorHintFocused)
      textFieldColorIcon = it.getColor(R.styleable.BpkTextField_textFieldColorHintFocused, textFieldColorIcon)
      iconStart = it.getDrawable(R.styleable.BpkTextField_textFieldIconStart)
      iconEnd = it.getDrawable(R.styleable.BpkTextField_textFieldIconEnd)
    }

    iconTintColor = textFieldColorIcon
    setTextColor(textFieldColor)
    setHintTextColor(ColorStateList(
      arrayOf(intArrayOf(android.R.attr.state_focused), intArrayOf()),
      intArrayOf(textFieldColorHintFocused, textFieldColorHintNormal)
    ))

    val padding = resources.getDimensionPixelSize(R.dimen.bpkSpacingMd)
    setPaddingRelative(padding, padding, padding, padding)
    compoundDrawablePadding = padding
  }
}
