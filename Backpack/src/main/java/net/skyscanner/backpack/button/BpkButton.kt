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
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.IntDef
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.widget.TextViewCompat
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import android.view.Gravity
import net.skyscanner.backpack.R

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

  val roundedButtonCorner = context.resources.getDimension(R.dimen.bpkSpacingLg)
  private val strokeWidth = context.resources.getDimension(R.dimen.bpkBorderRadiusSm).toInt()

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

  enum class Type(internal var id: Int, @ColorRes internal var bgColor: Int, @ColorRes internal var textColor: Int, @ColorRes internal var strokeColor: Int) {
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
    //enforce null text for icon only
    if (iconPosition == ICON_ONLY) {
      text = null
    }

    this.setPadding(
      if (iconPosition == ICON_ONLY) paddingWithIcon else defaultPadding,
      if (this.icon != null)  paddingWithIcon else defaultPadding,
      if (iconPosition == ICON_ONLY) paddingWithIcon else defaultPadding,
      if (this.icon != null) paddingWithIcon else defaultPadding)

    if (!text.isNullOrEmpty()) {
      compoundDrawablePadding = paddingWithIcon / 2
    }

    this.background = getSelectorDrawable(
      normalColor = ContextCompat.getColor(context, if (this.isEnabled) type.bgColor else R.color.bpkGray100),
      cornerRadius = roundedButtonCorner,
      strokeWidth = if (this.isEnabled) strokeWidth else 0,
      strokeColor = if (this.isEnabled) ContextCompat.getColor(context,type.strokeColor) else ContextCompat.getColor(context,android.R.color.transparent)
    )
    this.setTextColor(ContextCompat.getColor(context, if (this.isEnabled) type.textColor else R.color.bpkGray300))
    TextViewCompat.setTextAppearance(this,  R.style.bpkButtonBase)
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
    this.setCompoundDrawablesWithIntrinsicBounds(
      if (iconPosition == START || iconPosition == ICON_ONLY) icon else null,
      null,
      if (iconPosition == END) icon else null,
      null)
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
  @ColorInt pressedColor: Int = darken(normalColor)
): Drawable {
  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
    val states = StateListDrawable()
    states.addState(
      intArrayOf(-android.R.attr.state_enabled),
      corneredDrawable(greyOut(normalColor), cornerRadius, strokeColor, strokeWidth)
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
      getColorSelector(normalColor, pressedColor, greyOut(normalColor)),
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

/**
 * Utility function for darkening out a given color
 *
 * @param normalColor required, representing the color we will darken, given as a color resource int
 *
 * @return Int
 */
private fun greyOut(@ColorInt normalColor: Int): Int {
  return Color.argb(
    Color.alpha(normalColor),
    Color.red(normalColor) / 2,
    Color.green(normalColor) / 2,
    Color.blue(normalColor) / 2
  )
}
