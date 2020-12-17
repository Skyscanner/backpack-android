package net.skyscanner.backpack.util

import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextDirectionHeuristic
import android.text.TextDirectionHeuristics
import android.text.TextPaint
import android.text.TextUtils
import android.util.Log
import java.lang.reflect.Constructor
import net.skyscanner.backpack.BuildConfig

internal object TextLayoutCompat {

  private const val TAG = "TextLayoutCompat"

  private val StaticLayoutConstructor: Constructor<StaticLayout>? = try {
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

  fun StaticLayout(
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
  ): StaticLayout =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      StaticLayout.Builder.obtain(source, bufStart, bufEnd, paint, outerWidth)
        .setAlignment(align)
        .setTextDirection(textDir)
        .setLineSpacing(spacingAdd, spacingMult)
        .setIncludePad(includePad)
        .setEllipsize(ellipsize)
        .setEllipsizedWidth(ellipsizedWidth)
        .setMaxLines(maxLines)
        .build()
    } else try {
      StaticLayoutConstructor?.newInstance(
        source,
        bufStart,
        bufEnd,
        paint,
        outerWidth,
        align,
        textDir,
        spacingMult,
        spacingAdd,
        includePad,
        ellipsize,
        ellipsizedWidth,
        maxLines,
      ) ?: throw NullPointerException("StaticLayoutConstructor is null!")
    } catch (t: Throwable) {
      if (BuildConfig.DEBUG) {
        Log.e(TAG, "Failed to create StaticLayout instance using reflection.", t)
      }
      @Suppress("DEPRECATION")
      StaticLayout(
        source,
        bufStart,
        bufEnd,
        paint,
        outerWidth,
        align,
        spacingMult,
        spacingAdd,
        includePad,
        ellipsize,
        ellipsizedWidth,
      )
    }
}
