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
import net.skyscanner.backpack.button.internal.getRippleDrawable
import net.skyscanner.backpack.util.createContextThemeWrapper
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
    defaultTextColor = ContextCompat.getColor(this.context, R.color.bpkSkyBlue)

    compoundDrawablePadding = tokens.bpkSpacingSm

    this.context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButtonLink, defStyleAttr, 0)
      ?.use {
        _uppercase = it.getBoolean(R.styleable.BpkButtonLink_uppercase, true)
      }

    update()
  }

  override fun update() {
    val paddingVertical = tokens.bpkSpacingMd + (tokens.bpkBorderSizeLg / 2)
    setPadding(0, paddingVertical, 0, paddingVertical)

    if (isEnabled) {
      background = getRippleDrawable(
        normalColor = ContextCompat.getColor(context, android.R.color.transparent)
      )
    } else {
      background = null
    }

    isAllCaps = _uppercase
  }
}
