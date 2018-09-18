package net.skyscanner.backpack.button

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatButton
import android.text.Layout
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import net.skyscanner.backpack.R

open class BpkButton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

  private val margin by lazy(LazyThreadSafetyMode.NONE) { dpToPx(4, context) }
  private val roundedButtonCorner by lazy(LazyThreadSafetyMode.NONE) { dpToPx(16, context).toFloat() }
  private val strokeWidth by lazy(LazyThreadSafetyMode.NONE) { 2 }

  var type: Type = Type.Primary
    set(value) {
      field = value
      setup()
    }

  init {
    val a: TypedArray = context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkButton,
      defStyleAttr, 0
    )

    type = Type.fromId(a.getInt(R.styleable.BpkButton_buttonType, 0))

    a.recycle()

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
    this.setBackgroundColor(ContextCompat.getColor(context, type.bgColor))
    this.setTextColor(ContextCompat.getColor(context, type.textColor))
    this.background = getSelectorDrawable(
      normalColor = ContextCompat.getColor(context, type.bgColor),
      cornerRadius = roundedButtonCorner,
      strokeWidth = strokeWidth,
      strokeColor = type.strokeColor
    )
    this.textAlignment = View.TEXT_ALIGNMENT_CENTER
  }
}

fun dpToPx(dp: Int, ctx: Context): Int {
  val density = ctx.resources.displayMetrics.density
  return Math.round(dp.toFloat() * density)
}

@SuppressLint("ObsoleteSdkInt")
fun getSelectorDrawable(
  @ColorInt normalColor: Int,
  cornerRadius: Float? = null,
  strokeColor: Int? = null,
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
      getPressedColorSelector(normalColor, pressedColor, greyOut(normalColor)),
      corneredDrawable(normalColor, cornerRadius, strokeColor, strokeWidth),
      corneredDrawable(Color.WHITE, cornerRadius)
    )
  }
}

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

private fun getPressedColorSelector(
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

private fun darken(@ColorInt normalColor: Int, factor: Float = .2f): Int {
  val hsv = FloatArray(3)
  Color.colorToHSV(normalColor, hsv)
  hsv[2] *= 1f - factor // value component
  return Color.HSVToColor(hsv)
}

private fun greyOut(normalColor: Int): Int {
  return Color.argb(
    Color.alpha(normalColor),
    Color.red(normalColor) / 2,
    Color.green(normalColor) / 2,
    Color.blue(normalColor) / 2
  )
}
