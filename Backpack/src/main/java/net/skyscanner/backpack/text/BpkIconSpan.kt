package net.skyscanner.backpack.text

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.*
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat

internal class BpkIconSpan(drawable: Drawable) : ImageSpan(DrawableCompat.wrap(drawable.mutate())) {

  init {
    this.drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
  }

  constructor(resources: Resources, @DrawableRes id: Int) :
    this(ResourcesCompat.getDrawable(resources, id, null)!!)

  constructor(context: Context, @DrawableRes id: Int) :
    this(ContextCompat.getDrawable(context, id)!!)

  var tint: ColorStateList? = null
    set(value) {
      field = value
      DrawableCompat.setTintList(drawable, value)
    }

  override fun getSize(
    paint: Paint,
    text: CharSequence,
    start: Int,
    end: Int,
    fontMetricsInt: Paint.FontMetricsInt?
  ): Int {
    val drawable = drawable
    val rect = drawable.bounds
    if (fontMetricsInt != null) {
      val fmPaint = paint.fontMetricsInt
      val fontHeight = fmPaint.descent - fmPaint.ascent
      val drHeight = rect.bottom - rect.top
      val centerY = fmPaint.ascent + fontHeight / 2

      fontMetricsInt.ascent = centerY - drHeight / 2
      fontMetricsInt.top = fontMetricsInt.ascent
      fontMetricsInt.bottom = centerY + drHeight / 2
      fontMetricsInt.descent = fontMetricsInt.bottom
    }
    return rect.right
  }

  override fun draw(
    canvas: Canvas,
    text: CharSequence,
    start: Int,
    end: Int,
    x: Float,
    top: Int,
    y: Int,
    bottom: Int,
    paint: Paint
  ) {
    val drawable = drawable
    canvas.save()
    val fmPaint = paint.fontMetricsInt
    val fontHeight = fmPaint.descent - fmPaint.ascent
    val centerY = y + fmPaint.descent - fontHeight / 2
    val transY = centerY - (drawable.bounds.bottom - drawable.bounds.top) / 2
    canvas.translate(x, transY.toFloat())
    drawable.draw(canvas)
    canvas.restore()
  }
}

internal infix fun CharSequence.withIcon(icon: BpkIconSpan): CharSequence {
  return SpannableStringBuilder(this).append(" ").append(" ", icon, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
}

internal infix fun BpkIconSpan.withText(text: CharSequence): CharSequence {
  return SpannableStringBuilder().append(" ", this, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE).append(" ").append(text)
}
