package net.skyscanner.backpack.button.internal

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.*
import kotlin.math.max

internal const val ICON_POSITION_START = 0
internal const val ICON_POSITION_END = 1
internal const val ICON_POSITION_ICON_ONLY = 2

internal const val INVALID_RES = -1

abstract class BpkButtonBase internal constructor(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int
) : AppCompatButton(wrapContextWithDefaults(context), attrs, defStyleAttr) {

  private var isInitialized = false
  internal val tokens = ButtonTokens(this.context)

  private val font = BpkText.getFont(this.context, BpkText.SM, BpkText.Weight.EMPHASIZED)

  protected var defaultTextColor: Int = ContextCompat.getColor(this.context, R.color.bpkWhite)
    set(value) {
      field = value
      updateSelf()
    }

  private var _buttonTextColor: Int? = null
  private var buttonTextColor: Int
    get() = _buttonTextColor ?: defaultTextColor
    set(value) {
      _buttonTextColor = value
      updateSelf()
    }

  @Dimension
  private var originalStartPadding: Int = 0

  @Dimension
  private var originalEndPadding: Int = 0

  private var _iconPosition: Int = ICON_POSITION_END
  open var iconPosition: Int
    get() = _iconPosition
    set(value) {
      _iconPosition = value
      updateSelf()
      update()
    }

  private var _icon: Drawable? = null
  var icon: Drawable? = null
    get() = _icon
    set(value) {
      if (value != field) {
        field = value

        value?.let {
          _icon = adjustDrawableSize(it)
        }

        updateSelf()
        update()
      }
    }

  init {
    maxLines = 1
    isAllCaps = true
    gravity = Gravity.CENTER
    isClickable = isEnabled

    this.context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButton, defStyleAttr, 0)
      ?.use {
        // TODO: Try to refactor this so using getColorStateList is not necessary.
        // We are using getColorStateList because we can't set consistent default because of how
        // BpkButton changes the default colour based on its type.
        // Furthermore using getColor is problematic because we need to provide a default and if we
        // make INVALID_RES (-1) the default, it is not possible to se this property to white, because
        // white is also represented as -1.
        _buttonTextColor = it.getColorStateList(R.styleable.BpkButton_buttonTextColor)?.defaultColor

        _iconPosition = it.getInt(R.styleable.BpkButton_buttonIconPosition, ICON_POSITION_END)
        it.getResourceId(R.styleable.BpkButton_buttonIcon, INVALID_RES).let { res ->
          if (res != INVALID_RES) {
            _icon = AppCompatResources.getDrawable(context, res)?.let(::adjustDrawableSize)
          }
        }
      }

    updateSelf()

    isInitialized = true
  }

  private fun updateSelf() {
    font.applyTo(this)

    if (this.isEnabled) {
      this.setTextColor(
        getColorSelector(
          buttonTextColor,
          darken(buttonTextColor, .1f),
          tokens.gray300
        )
      )
    } else {
      this.setTextColor(tokens.gray300)
    }

    _icon?.let {
      DrawableCompat.setTintList(
        it,
        getColorSelector(
          buttonTextColor,
          darken(buttonTextColor, .1f),
          tokens.gray300
        )
      )

      this.setCompoundDrawables(
        it.takeIf { iconPosition == ICON_POSITION_START || iconPosition == ICON_POSITION_ICON_ONLY },
        null,
        it.takeIf { iconPosition == ICON_POSITION_END },
        null
      )
    }
  }

  protected abstract fun update()

  override fun setEnabled(enabled: Boolean) {
    super.setEnabled(enabled)

    isClickable = isEnabled

    if (isInitialized) {
      updateSelf()
      update()
    }
  }

  override fun setCompoundDrawablesWithIntrinsicBounds(@DrawableRes left: Int, @DrawableRes top: Int, @DrawableRes right: Int, @DrawableRes bottom: Int) {
    super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    updatePadding()
  }

  override fun setCompoundDrawablesWithIntrinsicBounds(left: Drawable?, top: Drawable?, right: Drawable?, bottom: Drawable?) {
    super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    updatePadding()
  }

  override fun setText(text: CharSequence, type: TextView.BufferType) {
    super.setText(text, type)
    updatePadding()
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    updatePadding(w)
  }

  override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
    super.setPadding(left, top, right, bottom)
    originalStartPadding = left
    originalEndPadding = right
    updatePadding(false)
  }

  override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
    super.setPaddingRelative(start, top, end, bottom)
    originalStartPadding = start
    originalEndPadding = end
    updatePadding()
  }

  private fun updatePadding(relative: Boolean = true) {
    updatePadding(measuredWidth, relative)
  }

  private fun updatePadding(width: Int, relative: Boolean = true) {
    if (width == 0) return

    val compoundDrawables = compoundDrawables
    if (compoundDrawables.isEmpty() || compoundDrawables.size != 4) return

    val leftDrawable = compoundDrawables[0]
    val rightDrawable = compoundDrawables[2]
    if (leftDrawable == null && rightDrawable == null) return
    val layout = layout ?: return
    if (layout.lineCount == 0) return

    val textWidth = layout.getLineWidth(0).toInt()
    val iconPadding = max(compoundDrawablePadding, 1)

    val paddingSize = if (leftDrawable != null && rightDrawable != null) {
      (width - leftDrawable.intrinsicWidth - rightDrawable.intrinsicWidth - textWidth - iconPadding * 4) / 2
    } else if (leftDrawable != null) {
      (width - leftDrawable.intrinsicWidth - iconPadding * 2 - textWidth) / 2
    } else {
      (width - rightDrawable.intrinsicWidth - iconPadding * 2 - textWidth) / 2
    }

    if (relative) {
      super.setPaddingRelative(
        max(originalStartPadding, paddingSize),
        paddingTop,
        max(originalEndPadding, paddingSize),
        paddingBottom
      )
    } else {
      super.setPadding(
        max(originalStartPadding, paddingSize),
        paddingTop,
        max(originalEndPadding, paddingSize),
        paddingBottom
      )
    }
  }

  private fun adjustDrawableSize(drawable: Drawable): Drawable? {
    drawable.setBounds(0, 0, tokens.bpkSpacingBase, tokens.bpkSpacingBase)
    return drawable
  }
}
