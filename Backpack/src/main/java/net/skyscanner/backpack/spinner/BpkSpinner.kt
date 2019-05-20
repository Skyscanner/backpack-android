package net.skyscanner.backpack.spinner

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.BpkTheme

private const val INVALID_RES = -1

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

  private val colors by lazy {
    arrayOf(
      R.color.bpkBlue500,
      R.color.bpkWhite,
      R.color.bpkGray700
    )
  }

  private var mProgressBar: ProgressBar? = null
  @ColorInt private var themePrimaryColor: Int = INVALID_RES

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
    initialize(attrs, defStyleAttr)
  }

  @ColorInt
  fun getColor(): Int {
    if (type === Type.PRIMARY && themePrimaryColor != INVALID_RES) {
      return themePrimaryColor
    }
    return BpkTheme.getColor(context, colors[type.ordinal])
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    val a = context.obtainStyledAttributes(attrs, R.styleable.BpkSpinner, defStyleAttr, 0)
    val small = a.getBoolean(R.styleable.BpkSpinner_small, false)
    val type = Type.values()[a.getInt(R.styleable.BpkSpinner_type, 0)]

    val t = TypedValue()
    if (context.theme.resolveAttribute(R.attr.bpkSpinnerPrimaryStyle, t, true)) {
      val withPrimaryStyle = ContextThemeWrapper(context, t.resourceId)
      val spinnerPrimaryStyle = withPrimaryStyle.obtainStyledAttributes(attrs, R.styleable.BpkSpinner)
      themePrimaryColor = spinnerPrimaryStyle.getColor(R.styleable.BpkSpinner_spinnerColor, INVALID_RES)
      spinnerPrimaryStyle.recycle()
    }

    a.recycle()

    this.small = small
    this.type = type
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
