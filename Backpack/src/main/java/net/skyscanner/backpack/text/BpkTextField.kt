package net.skyscanner.backpack.text

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

open class BpkTextField @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatEditText(
  createContextThemeWrapper(
    createContextThemeWrapper(context, attrs, androidx.appcompat.R.attr.editTextStyle),
    attrs, R.attr.bpkTextFieldStyle
  ),
  attrs,
  defStyleAttr) {

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

    var textColor = ContextCompat.getColor(context, R.color.bpkGray700)
    var hintNormalColor = ContextCompat.getColor(context, R.color.bpkGray300)
    var hintFocusedColor = ContextCompat.getColor(context, R.color.bpkGray500)
    var iconColor = ContextCompat.getColor(context, R.color.bpkGray700)

    var background: Drawable = ColorDrawable(ContextCompat.getColor(context, R.color.bpkWhite))

    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkTextField,
      defStyleAttr,
      0
    ).use {
      textColor = it.getColor(R.styleable.BpkTextField_textFieldColor, textColor)
      hintNormalColor = it.getColor(R.styleable.BpkTextField_textFieldColorHintNormal, hintNormalColor)
      hintFocusedColor = it.getColor(R.styleable.BpkTextField_textFieldColorHintFocused, hintFocusedColor)
      iconColor = it.getColor(R.styleable.BpkTextField_textFieldColorIcon, iconColor)
      iconStart = it.getDrawable(R.styleable.BpkTextField_textFieldIconStart)
      iconEnd = it.getDrawable(R.styleable.BpkTextField_textFieldIconEnd)
      background = it.getDrawable(R.styleable.BpkTextField_textFieldBackground) ?: background
    }

    this.iconTintColor = iconColor
    setTextColor(textColor)
    setHintTextColor(ColorStateList(
      arrayOf(intArrayOf(android.R.attr.state_focused), intArrayOf()),
      intArrayOf(hintFocusedColor, hintNormalColor)
    ))

    val padding = resources.getDimensionPixelSize(R.dimen.bpkSpacingMd) +
      resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)
    setPaddingRelative(padding, padding, padding, padding)
    compoundDrawablePadding = padding

    gravity = Gravity.START or Gravity.CENTER_VERTICAL
    this.background = background
  }

  override fun onRtlPropertiesChanged(layoutDirection: Int) {
    super.onRtlPropertiesChanged(layoutDirection)
    textDirection = if (layoutDirection == View.LAYOUT_DIRECTION_RTL) View.TEXT_DIRECTION_RTL else View.TEXT_DIRECTION_LTR
  }
}
