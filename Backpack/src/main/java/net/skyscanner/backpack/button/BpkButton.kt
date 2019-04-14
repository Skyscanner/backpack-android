package net.skyscanner.backpack.button

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.annotation.DrawableRes
import androidx.annotation.ColorRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.TextViewCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.createContextThemeOverlayWrapper
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

private const val INVALID_RESOURCE = -1

private class Tokens(val context: Context) {
  val bpkSpacingBase = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)
  val bpkSpacingLg = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingLg)
  val bpkSpacingMd = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingMd)
  val bpkSpacingSm = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)
  val bpkBorderSizeLg = context.resources.getDimensionPixelSize(R.dimen.bpkBorderSizeLg)
}

open class BpkButton : AppCompatButton {
  constructor(context: Context) : this(context, null)
  constructor(context: Context, type: Type) : this(context, null, getStyle(type), type)
  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, getStyle(context, attrs))
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, Type.Primary)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, type: Type)
    : super(createContextThemeOverlayWrapper(context, attrs), attrs, defStyleAttr) {
    this.initialType = type
    initialize(attrs, defStyleAttr)
  }

  companion object {
    const val START = 0
    const val END = 1
    const val ICON_ONLY = 2
  }

  private val tokens = Tokens(context)

  private var isInitialized = false
  private var initialType: Type

  private var wrappedIcon: Drawable? = null

  @ColorInt
  private var buttonBackgroundColor: Int = ContextCompat.getColor(context, R.color.bpkGreen500)
  @ColorInt
  private var buttonTextColor: Int = ContextCompat.getColor(context, R.color.bpkWhite)
  @ColorInt
  private var buttonStrokeColor: Int = ContextCompat.getColor(context, android.R.color.transparent)

  private lateinit var bpkFont: BpkText.FontDefinition
  private lateinit var textMeasurement: TextMeasurement

  private val strokeWidth = tokens.bpkBorderSizeLg
  private val paddingHorizontal = tokens.bpkSpacingBase - tokens.bpkSpacingSm

  private val paddingVertical = tokens.bpkSpacingMd + (strokeWidth / 2)

  private var originalStartPadding: Int = 0
  private var originalEndPadding: Int = 0

  private val roundedButtonCorner = context.resources.getDimension(R.dimen.bpkSpacingLg)

  @VisibleForTesting
  internal val disabledBackground =
    getSelectorDrawable(
      normalColor = ContextCompat.getColor(context, R.color.bpkGray100),
      pressedColor = darken(ContextCompat.getColor(context, R.color.bpkGray100)),
      disabledColor = ContextCompat.getColor(context, R.color.bpkGray100),
      cornerRadius = roundedButtonCorner,
      strokeWidth = null,
      strokeColor = null
    )

  val type: Type
    get() {
      return initialType
    }

  @IntDef(START, END, ICON_ONLY)
  annotation class IconPosition

  var icon: Drawable? = null
    set(value) {
      if (value != field) {
        field = value

        value?.let {
          wrappedIcon = convertToBitmap(it)?.let { bitmap ->
            BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, tokens.bpkSpacingBase, tokens.bpkSpacingBase, true))
          }
        }

        setUpIfInitialized()
      }
    }

  @BpkButton.IconPosition
  var iconPosition: Int = BpkButton.END
    set(value) {
      if (value != field) {
        field = value
        setUpIfInitialized()
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

  override fun setEnabled(enabled: Boolean) {
    // Even though we have a StateListDrawable, it is not enough just to rely on the state of
    // the background for enabled/disabled change as we have text color and other properties
    // changing incl the stroke of the background which means we need to change that as well.
    super.setEnabled(enabled)
    setUpIfInitialized()
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButton, defStyleAttr, 0)
    try {
      if (attr.hasValue(R.styleable.BpkButton_buttonType)) {
        initialType = Type.fromId(attr.getInt(R.styleable.BpkButton_buttonType, 0))
      }

      iconPosition = attr.getInt(R.styleable.BpkButton_buttonIconPosition, END)

      buttonBackgroundColor = attr.getColor(R.styleable.BpkButton_buttonBackgroundColor, ContextCompat.getColor(context, type.bgColor))
      buttonTextColor = attr.getColor(R.styleable.BpkButton_buttonTextColor, ContextCompat.getColor(context, type.textColor))
      buttonStrokeColor = attr.getResourceId(R.styleable.BpkButton_buttonStrokeColor, ContextCompat.getColor(context, type.strokeColor))

      attr.getResourceId(R.styleable.BpkButton_buttonIcon, INVALID_RESOURCE).let {
        if (it != INVALID_RESOURCE) {
          icon = AppCompatResources.getDrawable(context, it)
        }
      }
    } finally {
      attr.recycle()
    }

    bpkFont = BpkText.getFont(context, BpkText.XS, BpkText.Weight.EMPHASIZED)
    textMeasurement = TextMeasurement(paint)
    isInitialized = true
    setup()
  }

  private fun setUpIfInitialized() {
    if (isInitialized) {
      setup()
    }
  }

  private fun setup() {
    isClickable = isEnabled
    if (iconPosition == ICON_ONLY) {
      text = ""
    }

    TextViewCompat.setTextAppearance(this, R.style.bpkButtonBase)
    gravity = Gravity.CENTER

    // If a custom font is set we update the typeface to reflect it.
    // We do not support custom letter spacing for custom fonts at the moment
    if (bpkFont.isCustomFont) {
      typeface = bpkFont.typeface
      setTextSize(TypedValue.COMPLEX_UNIT_PX, bpkFont.fontSize.toFloat())
    }

    var paddingHorizontal = paddingHorizontal
    var paddingVertical = paddingVertical

    if (iconPosition == ICON_ONLY) {
      paddingHorizontal = tokens.bpkSpacingMd + strokeWidth
    }

    setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)

    if (!text.isNullOrEmpty()) {
      compoundDrawablePadding = tokens.bpkSpacingSm
    }

    background = getButtonBackground()

    if (this.isEnabled) {
      this.setTextColor(getColorSelector(
        buttonTextColor,
        darken(buttonTextColor, .1f),
        ContextCompat.getColor(context, R.color.bpkGray300)))
    } else {
      this.setTextColor(ContextCompat.getColor(context, R.color.bpkGray300))
    }

    wrappedIcon?.let {
      DrawableCompat.setTintList(
        it,
        getColorSelector(
          buttonTextColor,
          darken(buttonTextColor, .1f),
          ContextCompat.getColor(context, R.color.bpkGray300)
        )
      )

      this.setCompoundDrawablesRelativeWithIntrinsicBounds(
        if (iconPosition == START || iconPosition == ICON_ONLY) it else null,
        null,
        if (iconPosition == END) it else null,
        null
      )
    }
  }

  private fun getButtonBackground(): Drawable? {
    return if (this.isEnabled) {
      val pressedColor = if (buttonBackgroundColor == android.R.color.transparent) {
        ContextCompat.getColor(context, R.color.bpkGray300)
      } else {
        darken(buttonBackgroundColor)
      }
      getSelectorDrawable(
        normalColor = buttonBackgroundColor,
        pressedColor = pressedColor,
        disabledColor = ContextCompat.getColor(context, R.color.bpkGray100),
        cornerRadius = roundedButtonCorner,
        strokeWidth = if (type !== Type.Secondary && type !== Type.Destructive) null else strokeWidth,
        strokeColor = buttonStrokeColor
      )
    } else disabledBackground
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

    val textWidth = textMeasurement.getTextWidth(text.toString())
    val iconPadding = Math.max(compoundDrawablePadding, 1)

    val paddingSize = if (leftDrawable != null && rightDrawable != null) {
      (width - leftDrawable.intrinsicWidth - rightDrawable.intrinsicWidth - textWidth - iconPadding * 4) / 2
    } else if (leftDrawable != null) {
      (width - leftDrawable.intrinsicWidth - iconPadding * 2 - textWidth) / 2
    } else {
      (width - rightDrawable.intrinsicWidth - iconPadding * 2 - textWidth) / 2
    }

    if (relative) {
      super.setPaddingRelative(
        Math.max(originalStartPadding, paddingSize),
        paddingTop,
        Math.max(originalEndPadding, paddingSize),
        paddingBottom
      )
    } else {
      super.setPadding(
        Math.max(originalStartPadding, paddingSize),
        paddingTop,
        Math.max(originalEndPadding, paddingSize),
        paddingBottom
      )
    }
  }

  enum class Type(internal val id: Int, @ColorRes internal val bgColor: Int, @ColorRes internal val textColor: Int, @ColorRes internal val strokeColor: Int) {
    Primary(0, R.color.bpkGreen500, R.color.bpkWhite, android.R.color.transparent),
    Secondary(1, R.color.bpkWhite, R.color.bpkBlue600, R.color.bpkGray100),
    Featured(2, R.color.bpkPink500, R.color.bpkWhite, android.R.color.transparent),
    Destructive(3, R.color.bpkWhite, R.color.bpkRed500, R.color.bpkGray100),
    Outline(4, android.R.color.transparent, R.color.bpkWhite, R.color.bpkWhite);

    internal companion object {
      internal fun fromId(id: Int): Type {
        for (f in values()) {
          if (f.id == id) return f
        }
        throw IllegalArgumentException()
      }
    }
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
) = RippleDrawable(
  getColorSelector(normalColor, pressedColor, disabledColor),
  corneredDrawable(normalColor, cornerRadius, strokeColor, strokeWidth),
  corneredDrawable(Color.BLACK, cornerRadius, strokeColor, strokeWidth)
)

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
  } else {
    // This is required otherwise the ripple effect leaks outside the button
    gd.setStroke(0, -1)
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
) = ColorStateList(
  arrayOf(
    intArrayOf(-android.R.attr.state_enabled),
    intArrayOf(android.R.attr.state_pressed),
    intArrayOf(android.R.attr.state_focused),
    intArrayOf(android.R.attr.state_activated),
    intArrayOf()
  ),
  intArrayOf(disabledColor, pressedColor, pressedColor, pressedColor, normalColor)
)

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

private fun convertToBitmap(drawable: Drawable): Bitmap? {
  if (drawable is BitmapDrawable) {
    return drawable.bitmap
  } else if (drawable is VectorDrawable || drawable is VectorDrawableCompat) {
    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
  }

  Log.w("BpkButton", "Icon drawable not supported, make sure the size is set to 16dp")
  return null
}

private fun getStyle(context: Context, attrs: AttributeSet?): Int {
  val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButton, 0, 0)
  val style = BpkButton.Type.fromId(attr.getInt(R.styleable.BpkButton_buttonType, 0))
  return getStyle(style)
}

private fun getStyle(type: BpkButton.Type): Int {
  return when (type) {
    BpkButton.Type.Primary -> R.attr.bpkButtonPrimaryStyle
    BpkButton.Type.Secondary -> R.attr.bpkButtonSecondaryStyle
    BpkButton.Type.Outline -> R.attr.bpkButtonOutlineStyle
    BpkButton.Type.Featured -> R.attr.bpkButtonFeaturedStyle
    BpkButton.Type.Destructive -> R.attr.bpkButtonDestructiveStyle
  }
}
