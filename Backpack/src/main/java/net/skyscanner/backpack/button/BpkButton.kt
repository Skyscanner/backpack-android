package net.skyscanner.backpack.button

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.IntDef
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.TextViewCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.enableDebugHighlight

open class BpkButton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

  @IntDef(START, END, ICON_ONLY)
  annotation class IconPosition

  companion object {
    const val START = 0
    const val END = 1
    const val ICON_ONLY = 2
  }

  @BpkButton.IconPosition
  var iconPosition: Int = BpkButton.END
    set(value) {
      field = value
      this.setup()
    }

  private val iconSize = context.resources.getDimension(R.dimen.bpk_icon_size_small).toInt()
  private val defaultPadding = context.resources.getDimension(R.dimen.bpkSpacingLg).toInt() / 2
  // Text is 12dp and icon is 16dp. if icon is present,
  // padding needs to be reduced by 2 dp on both sides
  private val paddingWithIcon = (context.resources.getDimension(R.dimen.bpkSpacingLg).toInt() / 2) - 2

  private val roundedButtonCorner = context.resources.getDimension(R.dimen.bpkSpacingLg)
  private val strokeWidth = context.resources.getDimension(R.dimen.bpkBorderSizeLg).toInt()

  private val INVALID_RESOURCE = -1

  var type: Type = Type.Primary
    set(value) {
      field = value
      setup()
    }

  var icon: Drawable? = null
    set(value) {
      if (value != null) {
        field = value
        setup()
      }
    }

  internal val disabledBackground =
    getSelectorDrawable(
      normalColor = ContextCompat.getColor(context, R.color.bpkGray100),
      pressedColor = darken(ContextCompat.getColor(context, R.color.bpkGray100)),
      disabledColor = ContextCompat.getColor(context, R.color.bpkGray100),
      cornerRadius = roundedButtonCorner,
      strokeWidth = 0,
      strokeColor = ContextCompat.getColor(context, android.R.color.transparent)
    )

  init {
    val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButton, defStyleAttr, 0)
    try {
      type = Type.fromId(attr.getInt(R.styleable.BpkButton_buttonType, 0))
      iconPosition = attr.getInt(R.styleable.BpkButton_buttonIconPosition, END)

      attr.getResourceId(R.styleable.BpkButton_buttonIcon, INVALID_RESOURCE).let {
        if (it != INVALID_RESOURCE) {
          icon = AppCompatResources.getDrawable(context, it)
        }
      }
    } finally {
      attr.recycle()
    }
    setup()
  }

  /**
   * Even though we have a StateListDrawable, it is not enough just to rely on the state of
   * the background for enabled/disabled change as we have text color and other properties
   * changing incl the stroke of the background which means we need to change that as well.
   */
  override fun setEnabled(enabled: Boolean) {
    super.setEnabled(enabled)
    setup()
  }

  enum class Type(internal val id: Int, @ColorRes internal val bgColor: Int, @ColorRes internal val textColor: Int, @ColorRes internal val strokeColor: Int) {
    Primary(0, R.color.bpkGreen500, R.color.bpkWhite, android.R.color.transparent),
    Secondary(1, R.color.bpkWhite, R.color.bpkBlue600, R.color.bpkGray100),
    Featured(2, R.color.bpkPink500, R.color.bpkWhite, android.R.color.transparent),
    Destructive(3, R.color.bpkWhite, R.color.bpkRed500, R.color.bpkGray100);

    internal companion object {
      internal fun fromId(id: Int): Type {
        for (f in values()) {
          if (f.id == id) return f
        }
        throw IllegalArgumentException()
      }
    }
  }

  private fun setup() {
    this.isClickable = isEnabled
    // enforce null text for icon only
    if (iconPosition == ICON_ONLY) {
      text = null
    }

    this.setPadding(
      if (iconPosition == ICON_ONLY) paddingWithIcon else defaultPadding,
      if (this.icon != null) paddingWithIcon else defaultPadding,
      if (iconPosition == ICON_ONLY) paddingWithIcon else defaultPadding,
      if (this.icon != null) paddingWithIcon else defaultPadding
    )

    if (!text.isNullOrEmpty()) {
      compoundDrawablePadding = paddingWithIcon / 2
    }

    this.background = if (this.isEnabled) {
      getSelectorDrawable(
        normalColor = ContextCompat.getColor(context, type.bgColor),
        pressedColor = darken(ContextCompat.getColor(context, type.bgColor)),
        disabledColor = ContextCompat.getColor(context, R.color.bpkGray100),
        cornerRadius = roundedButtonCorner,
        strokeWidth = strokeWidth,
        strokeColor = ContextCompat.getColor(context, type.strokeColor)
      )
    } else disabledBackground

    this.setTextColor(ContextCompat.getColor(context, if (this.isEnabled) type.textColor else R.color.bpkGray300))
    TextViewCompat.setTextAppearance(this, R.style.bpkButtonBase)
    this.gravity = Gravity.CENTER

    this.icon?.let {
      DrawableCompat.setTintList(
        it,
        getColorSelector(
          ContextCompat.getColor(context, type.textColor),
          ContextCompat.getColor(context, type.textColor),
          ContextCompat.getColor(context, type.textColor)
        )
      )
    }

    icon?.setBounds(0, 0, iconSize, iconSize)
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(
      if (iconPosition == START || iconPosition == ICON_ONLY) icon else null,
      null,
      if (iconPosition == END) icon else null,
      null
    )
    enableDebugHighlight()
  }
}

/**
 * Utility method to create a drawable with given selector states for normal, pressed and disabled.
 * Uses either a RippleDrawable if above API 21 or StateListDrawable
 *
 * @param normalColor required, used as main background color on the drawable
 * @param cornerRadius optional, used as the radius on the corners (use 100 for square)
 * @param strokeColor optional, color integer for the stroke
 * @param strokeWidth optional, width in px for the stroke
 * @param pressedColor required, color integer for the pressed state, defaults to a 20% darken factor
 *
 * @return Drawable
 */
@SuppressLint("ObsoleteSdkInt")
fun getSelectorDrawable(
  @ColorInt normalColor: Int,
  cornerRadius: Float? = null,
  @ColorInt strokeColor: Int? = null,
  strokeWidth: Int? = null,
  @ColorInt pressedColor: Int,
  @ColorInt disabledColor: Int
): Drawable {
  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
    val states = StateListDrawable()
    states.addState(
      intArrayOf(-android.R.attr.state_enabled),
      corneredDrawable(disabledColor, cornerRadius, strokeColor, strokeWidth)
    )
    states.addState(
      intArrayOf(android.R.attr.state_pressed),
      corneredDrawable(pressedColor, cornerRadius, strokeColor, strokeWidth)
    )
    states.addState(
      intArrayOf(android.R.attr.state_focused),
      corneredDrawable(pressedColor, cornerRadius, strokeColor, strokeWidth)
    )
    states.addState(intArrayOf(), corneredDrawable(normalColor, cornerRadius, strokeColor, strokeWidth))
    return states
  } else {
    return RippleDrawable(
      getColorSelector(normalColor, pressedColor, disabledColor),
      corneredDrawable(normalColor, cornerRadius, strokeColor, strokeWidth),
      corneredDrawable(Color.BLACK, cornerRadius, strokeColor, strokeWidth)
    )
  }
}

/**
 * Utility function to create a cornered (or circle) drawable given a corner radius.
 * Additional stroke width and color can be defined
 *
 * @param color required, used as the background for the drawable
 * @param cornerRadius optional, if set used as the radius on the drawable
 * @param strokeColor optional, color integer for the stroke
 * @param strokeWidth optional, width in px for the stroke
 *
 * @return Drawable
 */
private fun corneredDrawable(
  color: Int,
  cornerRadius: Float? = null,
  strokeColor: Int? = null,
  strokeWidth: Int? = null
): Drawable {
  val gd = GradientDrawable()
  gd.setColor(color)
  cornerRadius?.let { gd.cornerRadius = it }
  if (strokeWidth != null && strokeColor != null) {
    gd.setStroke(strokeWidth, strokeColor)
  }
  return gd
}

/**
 * Utility function to state list for a drawable
 *
 * @param normalColor required, used as the color for any non specified special state
 * @param pressedColor required, used as the color for the pressed, focused and activated state
 * @param disabledColor required, used as the color for the disabled state
 *
 * @return ColorStateList
 */
private fun getColorSelector(
  @ColorInt normalColor: Int,
  @ColorInt pressedColor: Int,
  @ColorInt disabledColor: Int
): ColorStateList {
  return ColorStateList(
    arrayOf(
      intArrayOf(-android.R.attr.state_enabled),
      intArrayOf(android.R.attr.state_pressed),
      intArrayOf(android.R.attr.state_focused),
      intArrayOf(android.R.attr.state_activated),
      intArrayOf()
    ),
    intArrayOf(disabledColor, pressedColor, pressedColor, pressedColor, normalColor)
  )
}

/**
 * Utility function for darkening a given color
 *
 * @param normalColor required, representing the color we will darken, given as a color resource int
 * @param factor required, used for the darkening factor, default to 20%
 *
 * @return Int
 */
private fun darken(@ColorInt normalColor: Int, factor: Float = .2f): Int {
  val hsv = FloatArray(3)
  Color.colorToHSV(normalColor, hsv)
  hsv[2] *= 1f - factor // value component
  return Color.HSVToColor(hsv)
}
