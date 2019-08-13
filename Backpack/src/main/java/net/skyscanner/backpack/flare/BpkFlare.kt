package net.skyscanner.backpack.flare

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.use

/**
 * [BpkFlare] is designed to render a single item inside a "bubble".
 * Think of message apps and how they show content inside text bubbles.
 *
 * No background or padding should be set directly to this view, set it directly its
 * child.
 */
open class BpkFlare @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  enum class PointerPosition(internal val id: Int, internal val offset: Float) {
    START(0, 0.25f),
    MIDDLE(1, 0.5f),
    END(2, 0.75f)
  }

  private val pointerDrawable: Drawable = ContextCompat.getDrawable(context, R.drawable.flare_default_pointer)!!.apply {
    setBounds(0, 0, intrinsicWidth, intrinsicHeight)
  }
  private val cornerRadiusDrawable: Drawable = ContextCompat.getDrawable(context, R.drawable.flare_default_radius)!!.apply {
    setBounds(0, 0, intrinsicWidth, intrinsicHeight)
  }

  private val mask = Bitmap.createBitmap(pointerDrawable.intrinsicWidth, pointerDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888).apply {
    pointerDrawable.draw(Canvas(this))
  }
  private val radiusMask = Bitmap.createBitmap(cornerRadiusDrawable.intrinsicWidth, cornerRadiusDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888).apply {
    cornerRadiusDrawable.draw(Canvas(this))
  }

  private val clipRect = RectF()
  private val porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
  private val paint = Paint().apply {
    xfermode = porterDuffXfermode
    isAntiAlias = true
  }

  /**
   * When fitContent is true the view will ensure extra space is reserved for the bubble pointer to
   * ensure all content is visible.
   */
  var fitContent = false
  set(value) {
    field = value
    requestLayout()
  }

  /**
   * Specify if corner radius should be added or not.
   */
  var round = false
  set(value) {
    field = value
    requestLayout()
  }

  /**
   * Specify where the pointer should be rendered.
   *
   * @see [PointerPosition]
   */
  var pointerPosition = PointerPosition.MIDDLE
  set(value) {
    field = value
    requestLayout()
  }

  init {
    this.initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    context.theme.obtainStyledAttributes(attrs, R.styleable.BpkFlare, defStyleAttr, 0)
      ?.use {
        fitContent = it.getBoolean(R.styleable.BpkFlare_flareFitContent, fitContent)
        round = it.getBoolean(R.styleable.BpkFlare_flareRound, round)

        pointerPosition = it.getInt(R.styleable.BpkFlare_flarePointerPosition, pointerPosition.id)
          .let(::mapXmlToPointerPosition) ?: pointerPosition
      }

    background = null
    setWillNotDraw(false)
    setPadding(0, 0, 0, 0)
    // PorterDuffXfermode doesn't work in some devices when hardware acceleration is on
    setLayerType(LAYER_TYPE_SOFTWARE, null)
  }

  override fun draw(canvas: Canvas) {
    val count = canvas.saveCount

    val width = width.toFloat()
    val height = height.toFloat()

    val pointerHalfWidth = mask.width / 2
    val pointerOffset = if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
      1 - pointerPosition.offset
    } else {
      pointerPosition.offset
    }

    val yStart = height - mask.height
    val pointerXStart = width * pointerOffset - pointerHalfWidth
    val pointerXEnd = width * pointerOffset + pointerHalfWidth

    clipRect.set(0f, yStart, pointerXStart, height)
    canvas.clipOutRectCompat(clipRect)

    clipRect.set(width, yStart, pointerXEnd, height)
    canvas.clipOutRectCompat(clipRect)

    super.draw(canvas)
    canvas.restoreToCount(count)

    canvas.drawBitmap(mask, pointerXStart, yStart, paint)

    if (round) {
      drawRadiusMask(canvas)
    }
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val firstChild = getChildAt(0)
    if (firstChild == null || !fitContent || firstChild.layoutParams.height >= 0) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    } else {
      getChildAt(0)?.let {
        // When fit content is true we add the size of the pointer to the view's height, in order
        // for the children to take up that size we need to change `WRAP_CONTENT` to `MATCH_PARENT`
        if (it.layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
          it.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        it.measure(widthMeasureSpec, heightMeasureSpec)
      }

      super.onMeasure(
        widthMeasureSpec,
        MeasureSpec.makeMeasureSpec(
          getChildAt(0).measuredHeight + pointerDrawable.intrinsicHeight,
          MeasureSpec.EXACTLY
        ))
    }
  }

  private fun Canvas.clipOutRectCompat(rect: RectF) {
    if (Build.VERSION.SDK_INT >= 26) {
      this.clipOutRect(rect)
    } else {
      @Suppress("DEPRECATION")
      this.clipRect(rect, Region.Op.DIFFERENCE)
    }
  }

  private fun drawRadiusMask(canvas: Canvas) {
    val width = width.toFloat()
    val height = height.toFloat()
    val radiusHeight = radiusMask.height
    val radiusWidth = radiusMask.width
    val radiusHalfHeight = (radiusHeight / 2).toFloat()
    val radiusHalfWidth = (radiusHeight / 2).toFloat()
    val pointerHeight = mask.height

    val count = canvas.saveCount

    // bottom right corner
    canvas.drawBitmap(radiusMask, 0f, height - pointerHeight - radiusHeight, paint)

    // top right corner
    canvas.rotate(180f, radiusHalfWidth, radiusHalfHeight)
    canvas.drawBitmap(radiusMask, -(width - radiusWidth), 0f, paint)

    // top left corner
    canvas.rotate(-90f, radiusHalfWidth, radiusHalfHeight)
    canvas.drawBitmap(radiusMask, 0f, 0f, paint)

    // bottom right corner
    canvas.rotate(-180f, radiusHalfWidth, radiusHalfHeight)
    canvas.drawBitmap(radiusMask, -(height - pointerHeight - radiusHeight), width - radiusWidth, paint)

    canvas.restoreToCount(count)
  }

  private fun mapXmlToPointerPosition(id: Int) =
    PointerPosition.values().find { it.id == id }
}
