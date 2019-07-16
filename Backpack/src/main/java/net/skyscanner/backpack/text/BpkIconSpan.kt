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
    this.drawable.setBounds(0, 0, this.drawable.intrinsicWidth, this.drawable.intrinsicHeight)
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

  var state: IntArray
    get() = drawable.state
    set(value) {
      drawable.state = value
    }

  override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, out: Paint.FontMetricsInt?): Int {
    if (out != null) {
      val fontMetrics = paint.fontMetricsInt
      val height = fontMetrics.fontHeight
      val width = (height * drawable.intrinsicWidth) / drawable.intrinsicHeight
      drawable.setBounds(0, 0, width, height)

      out.ascent = fontMetrics.ascent
      out.top = fontMetrics.top
      out.descent = fontMetrics.descent
      out.bottom = fontMetrics.bottom
    }
    return drawable.bounds.width()
  }

  override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
    val count = canvas.save()
    canvas.translate(x, (y + paint.fontMetricsInt.ascent).toFloat())
    drawable.draw(canvas)
    canvas.restoreToCount(count)
  }
}

private val Paint.FontMetricsInt.fontHeight
  get() = descent - ascent

internal infix fun CharSequence.withIcon(icon: BpkIconSpan): CharSequence {
  return SpannableStringBuilder(this).append(' ').append(icon.asSpan())
}

internal infix fun BpkIconSpan.withText(text: CharSequence): CharSequence {
  return SpannableStringBuilder().append(this.asSpan()).append(' ').append(text)
}

internal fun BpkIconSpan.asSpan(): CharSequence {
  return SpannableStringBuilder().append(" ", this, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
}
