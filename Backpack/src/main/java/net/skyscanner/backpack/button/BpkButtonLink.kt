package net.skyscanner.backpack.button

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.button.internal.BpkButtonBase
import net.skyscanner.backpack.button.internal.ICON_POSITION_END
import net.skyscanner.backpack.button.internal.ICON_POSITION_START
import net.skyscanner.backpack.button.internal.getColorSelector
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.darken
import net.skyscanner.backpack.util.resolveThemeDrawable
import net.skyscanner.backpack.util.use

open class BpkButtonLink @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BpkButtonBase(
  createContextThemeWrapper(context, attrs, R.attr.bpkButtonLinkStyle),
  attrs,
  defStyleAttr
) {

  companion object {
    const val START = ICON_POSITION_START
    const val END = ICON_POSITION_END
  }

  @IntDef(START, END)
  annotation class IconPosition

  @BpkButtonLink.IconPosition
  override var iconPosition
    get() = super.iconPosition
    set(value) {
      super.iconPosition = value
    }

  private var _uppercase = true
  var uppercase: Boolean
    get() = _uppercase
    set(value) {
      _uppercase = value
      update()
    }

  final override var icon: Drawable?
    get() = super.icon
    set(value) {
      super.icon = value
    }

  init {
    var textColor = ContextCompat.getColor(context, R.color.bpkPrimary)

    compoundDrawablePadding = tokens.bpkSpacingSm

    this.context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButtonLink, defStyleAttr, 0)
      .use {
        _uppercase = it.getBoolean(R.styleable.BpkButtonLink_uppercase, true)
        textColor = it.getColor(R.styleable.BpkButton_buttonTextColor, textColor)
      }

    background = resolveThemeDrawable(context, android.R.attr.selectableItemBackground)
    setTextColor(getColorSelector(
      textColor,
      darken(textColor, .1f),
      ContextCompat.getColor(context, R.color.__buttonDisabledText)
    ))
    update()
  }

  override fun update() {
    val paddingVertical = tokens.bpkSpacingMd + (tokens.bpkBorderSizeLg / 2)
    setPadding(0, paddingVertical, 0, paddingVertical)
    isAllCaps = _uppercase
  }
}
