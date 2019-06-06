package net.skyscanner.backpack.fab

import android.animation.AnimatorInflater
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.BpkTheme
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.darken

internal open class BpkFab @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatButton(createContextThemeWrapper(context, attrs, R.attr.bpkFabStyle), attrs, defStyleAttr) {

  @ColorInt
  private var iconColor: Int = 0

  var icon: Drawable? = null
    set(value) {
      field?.callback = null
      unscheduleDrawable(field)
      field = value
      if (value != null) {
        value.callback = this
        value.setBounds(0, 0, value.intrinsicWidth, value.intrinsicHeight)
        DrawableCompat.setTint(value, iconColor)
        if (value.isStateful) {
          icon?.state = drawableState
        }
      }
      invalidate()
    }

  init {
    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    val ta = context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkFab,
      defStyleAttr,
      0
    )

    var backgroundColour = BpkTheme.getColor(context, R.color.bpkGreen500)
    var iconColour = BpkTheme.getColor(context, R.color.bpkWhite)
    var icon: Drawable? = null

    if (ta != null) {
      backgroundColour = ta.getInt(R.styleable.BpkFab_fabBackgroundColor, backgroundColour)
      iconColour = ta.getInt(R.styleable.BpkFab_fabIconColor, iconColour)
      val iconRes = ta.getResourceId(R.styleable.BpkFab_fabIcon, 0)
      if (iconRes != 0) {
        icon = ContextCompat.getDrawable(context, iconRes)
      }
      ta.recycle()
    }

    this.iconColor = iconColour
    this.icon = icon
    this.isClickable = isEnabled
    this.stateListAnimator = AnimatorInflater.loadStateListAnimator(context, R.drawable.bpk_button_state_animator)
    this.elevation = resources.getDimensionPixelSize(R.dimen.bpkElevationBase).toFloat()
    this.background = getSelectorDrawable(
      backgroundColour,
      pressedColor = darken(backgroundColour),
      disabledColor = BpkTheme.getColor(context, R.color.bpkGray100)
    )
  }

  override fun verifyDrawable(who: Drawable) =
    super.verifyDrawable(who) || icon == who

  override fun jumpDrawablesToCurrentState() {
    super.jumpDrawablesToCurrentState()
    icon?.jumpToCurrentState()
  }

  override fun drawableStateChanged() {
    super.drawableStateChanged()
    icon?.let {
      if (it.isStateful && it.setState(drawableState)) {
        invalidateDrawable(it)
      }
    }
  }

  override fun drawableHotspotChanged(x: Float, y: Float) {
    super.drawableHotspotChanged(x, y)
    icon?.setHotspot(x, y)
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    icon?.let {
      val bounds = it.bounds

      val halfWidth = bounds.width() / 2f
      val halfHeight = bounds.height() / 2f

      val cX = width / 2f
      val cY = height / 2f

      canvas.translate(cX - halfWidth, cY - halfHeight)
      it.draw(canvas)
    }
  }

  private fun getSelectorDrawable(
    @ColorInt normalColor: Int,
    @ColorInt pressedColor: Int,
    @ColorInt disabledColor: Int
  ) = RippleDrawable(
    getColorSelector(normalColor, pressedColor, disabledColor),
    circleDrawable(normalColor),
    circleDrawable(Color.BLACK)
  )

  private fun circleDrawable(@ColorInt color: Int) =
    ShapeDrawable(OvalShape()).apply {
      paint.color = color
      paint.style = Paint.Style.FILL
    }

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
}
