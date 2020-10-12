package net.skyscanner.backpack.overlay

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.overlay.internal.CornerRadiusViewOutlineProvider
import net.skyscanner.backpack.overlay.internal.EmptyViewOutlineProvider
import net.skyscanner.backpack.util.use
import kotlin.math.roundToInt

open class BpkOverlayView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  enum class CornerType(
    internal val id: Int,
    internal val clipToOutline: Boolean = false,
    internal val outlineProvider: ViewOutlineProvider = EmptyViewOutlineProvider
  ) {
    None(0),
    Rounded(
      id = 1,
      clipToOutline = true,
      outlineProvider = CornerRadiusViewOutlineProvider(R.dimen.bpkBorderRadiusSm)
    ),
  }

  enum class OverlayType(
    internal val id: Int,
    internal val colorRes: Int = android.R.color.transparent,
    internal val opacity: Float = 0.0f
  ) {
    None(0),
    Tint(
      id = 1,
      colorRes = R.color.bpkBlack,
      opacity = 0.56f
    )
  }

  var cornerType: CornerType = CornerType.None
    set(value) {
      field = value
      outlineProvider = value.outlineProvider
      clipToOutline = value.clipToOutline
    }

  private var overlayColor = Color.TRANSPARENT

  var overlayType: OverlayType = OverlayType.None
    set(value) {
      field = value

      val color = ContextCompat.getColor(context, value.colorRes)

      this.overlayColor = Color.argb(
        (field.opacity * 255).roundToInt(),
        Color.red(color),
        Color.green(color),
        Color.blue(color)
      )

      invalidate()
    }

  init {
    var cornerType = cornerType
    var overlayType = overlayType

    context.obtainStyledAttributes(attrs, R.styleable.BpkOverlayView, defStyleAttr, 0).use {
      cornerType = parseCornerAttribute(it, cornerType)
      overlayType = parseOverlayAttribute(it, overlayType)
    }
    this.cornerType = cornerType
    this.overlayType = overlayType
  }

  override fun drawChild(canvas: Canvas, child: View, drawingTime: Long): Boolean {
    val result = super.drawChild(canvas, child, drawingTime)
    if (child == getChildAt(0)) {
      canvas.drawColor(overlayColor)
    }
    return result
  }

  private companion object {

    private fun parseOverlayAttribute(it: TypedArray, fallback: OverlayType) =
      it.getInt(R.styleable.BpkOverlayView_overlayType, fallback.id).let { id ->
        OverlayType.values().find { it.id == id } ?: fallback
      }

    private fun parseCornerAttribute(it: TypedArray, fallback: CornerType) =
      it.getInt(R.styleable.BpkOverlayView_overlayCornerType, fallback.id).let { id ->
        CornerType.values().find { it.id == id } ?: fallback
      }
  }
}
