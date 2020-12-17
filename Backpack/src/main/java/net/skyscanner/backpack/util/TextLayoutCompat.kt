package net.skyscanner.backpack.util

import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextDirectionHeuristic
import android.text.TextDirectionHeuristics
import android.text.TextPaint
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import java.lang.reflect.Constructor
import net.skyscanner.backpack.BuildConfig

internal object TextLayoutCompat {

  private const val TAG = "TextLayoutCompat"

  private val creator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    StaticLayoutCreatorApi23
  } else {
    StaticLayoutCreatorApi21
  }

  fun staticLayout(
    source: CharSequence,
    bufStart: Int = 0,
    bufEnd: Int = source.length,
    paint: TextPaint,
    outerWidth: Int,
    align: Layout.Alignment,
    textDir: TextDirectionHeuristic = TextDirectionHeuristics.FIRSTSTRONG_LTR,
    spacingMult: Float = 1f,
    spacingAdd: Float = 0f,
    includePad: Boolean,
    ellipsize: TextUtils.TruncateAt? = null,
    ellipsizedWidth: Int = outerWidth,
    maxLines: Int = Int.MAX_VALUE,
  ): StaticLayout = creator.create(
    source = source,
    bufStart = bufStart,
    bufEnd = bufEnd,
    paint = paint,
    outerWidth = outerWidth,
    align = align,
    textDir = textDir,
    spacingMult = spacingMult,
    spacingAdd = spacingAdd,
    includePad = includePad,
    ellipsize = ellipsize,
    ellipsizedWidth = ellipsizedWidth,
    maxLines = maxLines,
  )

  interface StaticLayoutCreator {

    fun create(
      source: CharSequence,
      bufStart: Int,
      bufEnd: Int,
      paint: TextPaint,
      outerWidth: Int,
      align: Layout.Alignment,
      textDir: TextDirectionHeuristic,
      spacingMult: Float,
      spacingAdd: Float,
      includePad: Boolean,
      ellipsize: TextUtils.TruncateAt?,
      ellipsizedWidth: Int,
      maxLines: Int
    ): StaticLayout
  }

  object StaticLayoutCreatorApi21 : StaticLayoutCreator {

    private val ConstructorReference: Constructor<StaticLayout>? = try {
      StaticLayout::class.java.getDeclaredConstructor(
        CharSequence::class.java,
        Int::class.javaPrimitiveType,
        Int::class.javaPrimitiveType,
        TextPaint::class.java,
        Int::class.javaPrimitiveType,
        Layout.Alignment::class.java,
        TextDirectionHeuristic::class.java,
        Float::class.javaPrimitiveType,
        Float::class.javaPrimitiveType,
        Boolean::class.javaPrimitiveType,
        TextUtils.TruncateAt::class.java,
        Int::class.javaPrimitiveType,
        Int::class.javaPrimitiveType,
      ).apply {
        isAccessible = true
      }
    } catch (t: Throwable) {

      if (BuildConfig.DEBUG) {
        Log.e(TAG, "Failed to find multi-line StaticLayout constructor using reflection", t)
      }

      null
    }

    @Suppress("DEPRECATION")
    override fun create(
      source: CharSequence,
      bufStart: Int,
      bufEnd: Int,
      paint: TextPaint,
      outerWidth: Int,
      align: Layout.Alignment,
      textDir: TextDirectionHeuristic,
      spacingMult: Float,
      spacingAdd: Float,
      includePad: Boolean,
      ellipsize: TextUtils.TruncateAt?,
      ellipsizedWidth: Int,
      maxLines: Int,
    ): StaticLayout = try {

      ConstructorReference?.newInstance(
        source, bufStart, bufEnd, paint, outerWidth, align, textDir, spacingMult,
        spacingAdd, includePad, ellipsize, ellipsizedWidth, maxLines,
      ) ?: throw NullPointerException("StaticLayoutConstructor is null!")
    } catch (t: Throwable) {

      if (BuildConfig.DEBUG) {
        Log.e(TAG, "Failed to create StaticLayout instance using reflection.", t)
      }

      StaticLayout(
        source, bufStart, bufEnd, paint, outerWidth, align, spacingMult,
        spacingAdd, includePad, ellipsize, ellipsizedWidth,
      )
    }
  }

  @RequiresApi(Build.VERSION_CODES.M)
  object StaticLayoutCreatorApi23 : StaticLayoutCreator {

    override fun create(
      source: CharSequence,
      bufStart: Int,
      bufEnd: Int,
      paint: TextPaint,
      outerWidth: Int,
      align: Layout.Alignment,
      textDir: TextDirectionHeuristic,
      spacingMult: Float,
      spacingAdd: Float,
      includePad: Boolean,
      ellipsize: TextUtils.TruncateAt?,
      ellipsizedWidth: Int,
      maxLines: Int,
    ): StaticLayout = StaticLayout.Builder.obtain(source, bufStart, bufEnd, paint, outerWidth)
      .setAlignment(align)
      .setTextDirection(textDir)
      .setLineSpacing(spacingAdd, spacingMult)
      .setIncludePad(includePad)
      .setEllipsize(ellipsize)
      .setEllipsizedWidth(ellipsizedWidth)
      .setMaxLines(maxLines)
      .build()
  }
}
