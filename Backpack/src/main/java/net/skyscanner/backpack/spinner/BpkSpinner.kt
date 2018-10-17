package net.skyscanner.backpack.spinner

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.ColorInt
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import net.skyscanner.backpack.R
import kotlin.jvm.JvmOverloads

/**
 * BpkSpinner is designed to indicate that a part of the product is loading or performing a task
 * when the amount of time needed is unknown.
 *
 * To further customize the visual of the progress `progressBarStyle` and
 * `progressBarStyleSmall` should be used:
 *
 * ```xml
 * <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
 *   <item name="android:progressBarStyle">@style/CustomProgress</item>
 * </style>
 *
 * <style name="CustomProgress" parent="@style/Widget.AppCompat.ProgressBar">
 *   <item name="android:minWidth">200dp</item>
 * </style>
 * ```
 *
 * @see [BpkSpinner.Type]
 */
open class BpkSpinner @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  private val colors = arrayOf(
    R.color.bpkBlue500,
    R.color.bpkWhite,
    R.color.bpkGray700
  )

  private var mProgressBar: ProgressBar? = null

  /**
   * Updates the Spinner's type.
   * @see [BpkSpinner.Type]
   */
  var type = Type.PRIMARY
    set(value) {
      field = value
      updateColor()
    }

  /**
   * Toggles the small version of the Spinner
   */
  var small = false
    set(value) {
      field = value
      updateSize()
    }

  init {
    initialize(context, attrs, defStyleAttr)
  }

  @ColorInt
  fun getColor(): Int =
    ResourcesCompat.getColor(resources, colors[type.ordinal], context.theme)

  private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
    val a = context.obtainStyledAttributes(attrs, R.styleable.BpkSpinner, defStyleAttr, 0)
    small = a.getBoolean(R.styleable.BpkSpinner_small, false)
    type = Type.values()[a.getInt(R.styleable.BpkSpinner_type, 0)]
    a.recycle()
  }

  private fun updateColor() {
    mProgressBar?.indeterminateDrawable?.mutate()?.setColorFilter(getColor(), PorterDuff.Mode.SRC_IN)
  }

  private fun updateSize() {
    val style = if (small) android.R.attr.progressBarStyleSmall else android.R.attr.progressBarStyle
    mProgressBar = ProgressBar(context, null, style)

    removeAllViews()
    addView(
      mProgressBar,
      ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT))

    updateColor()
  }

  enum class Type {
    PRIMARY, LIGHT, DARK
  }
}
