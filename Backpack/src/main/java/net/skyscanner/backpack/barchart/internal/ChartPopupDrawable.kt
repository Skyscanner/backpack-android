package net.skyscanner.backpack.barchart.internal

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import net.skyscanner.backpack.R
import net.skyscanner.backpack.barchart.BpkBarChart
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.util.getColorForState
import net.skyscanner.backpack.util.withSave
import kotlin.math.roundToInt

internal class ChartPopupDrawable(
  context: Context,
  private val colors: BpkBarChart.Colors
) : Drawable() {

  private val resources = context.resources

  private val triangleWidth = resources.getDimension(R.dimen.bpk_barchart_popup_triangle_width)
  private val triangleHeight = resources.getDimension(R.dimen.bpk_barchart_popup_triangle_height)
  private val triangle = Path().apply {
    moveTo(0f, -1f)
    lineTo(0f, 0f)
    lineTo(triangleWidth / 2, triangleHeight)
    lineTo(triangleWidth, 0f)
    lineTo(triangleWidth, -10f)
    lineTo(0f, -1f)
  }

  private val borderRadius = resources.getDimension(R.dimen.bpkBorderRadiusSm)
  private val blockHeight = resources.getDimension(R.dimen.bpkSpacingXl)

  private val textSpacing = resources.getDimension(R.dimen.bpkSpacingMd)
  private val textBounds = Rect()

  private val backgroundPaint = Paint().apply {
    isAntiAlias = true
    isDither = true
    style = Paint.Style.FILL_AND_STROKE
  }

  private val textPaint = TextPaint().apply {
    isAntiAlias = true
    isDither = true
    style = Paint.Style.FILL_AND_STROKE
    BpkText.getFont(context, BpkText.SM, BpkText.Weight.EMPHASIZED).applyTo(this)
  }

  var text: String = ""
    set(value) {
      if (field != value) {
        field = value
        textPaint.getTextBounds(value, 0, value.length, textBounds)
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        invalidateSelf()
      }
    }

  override fun getIntrinsicWidth(): Int =
    (textBounds.width() + textSpacing + textSpacing).roundToInt()

  override fun getIntrinsicHeight(): Int =
    (blockHeight + triangleHeight).roundToInt()

  override fun getMinimumWidth(): Int =
    intrinsicWidth

  override fun getMinimumHeight(): Int =
    intrinsicHeight

  override fun getAlpha(): Int =
    backgroundPaint.alpha

  override fun setAlpha(alpha: Int) {
    backgroundPaint.alpha = alpha
  }

  override fun getOpacity(): Int =
    PixelFormat.TRANSPARENT

  override fun setColorFilter(colorFilter: ColorFilter?) {
    backgroundPaint.colorFilter = colorFilter
    textPaint.colorFilter = colorFilter
  }

  override fun getColorFilter(): ColorFilter? =
    backgroundPaint.colorFilter

  override fun isStateful(): Boolean =
    colors.popupBackground.isStateful || colors.popupText.isStateful

  override fun onStateChange(state: IntArray?): Boolean =
    isStateful

  override fun draw(canvas: Canvas) {
    backgroundPaint.color = colors.popupBackground.getColorForState(state)

    canvas.drawRoundRect(0f, 0f, bounds.width().toFloat(), blockHeight,
      borderRadius, borderRadius, backgroundPaint)

    canvas.withSave {
      val dx = (bounds.width() - triangleWidth) / 2f
      val dy = blockHeight

      translate(dx, dy)
      drawPath(triangle, backgroundPaint)
    }

    textPaint.color = colors.popupText.getColorForState(state)
    val textX = (bounds.width() - textBounds.width()) / 2f
    val textY = (blockHeight + textBounds.height()) / 2f
    canvas.drawText(text, textX, textY, textPaint)
  }
}
