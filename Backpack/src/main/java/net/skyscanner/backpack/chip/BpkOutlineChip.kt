package net.skyscanner.backpack.chip

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.createContextThemeWrapper

class BpkOutlineChip @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BpkText(createContextThemeWrapper(context, attrs, R.attr.bpkOutlineChipStyle), attrs, defStyleAttr) {

  private val verticalPadding = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingSm)
  private val horizontalPadding = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingBase)

  init {
    gravity = Gravity.CENTER_VERTICAL
    textStyle = SM
    this.setTextColor(ContextCompat.getColor(context, R.color.bpkWhite))
    this.isSingleLine = true
    setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    context.theme.obtainStyledAttributes(attrs, R.styleable.BpkChip, defStyleAttr, 0)
    background = corneredDrawable(
      resources.getDimension(R.dimen.bpkBorderRadiusPill),
      ContextCompat.getColor(context, R.color.bpkWhite),
      context.resources.getDimensionPixelSize(R.dimen.bpkBorderSizeSm)
    )
  }

  private fun corneredDrawable(
    @Dimension cornerRadius: Float? = null,
    @ColorInt strokeColor: Int? = null,
    strokeWidth: Int? = null
  ): Drawable {
    val gd = GradientDrawable()
    cornerRadius?.let { gd.cornerRadius = it }
    if (strokeWidth != null && strokeColor != null) {
      gd.setStroke(strokeWidth, strokeColor)
    } else {
      // This is required otherwise the ripple effect leaks outside the button
      gd.setStroke(0, -1)
    }
    return gd
  }
}
