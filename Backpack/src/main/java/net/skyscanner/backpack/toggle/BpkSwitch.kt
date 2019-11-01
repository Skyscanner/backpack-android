package net.skyscanner.backpack.toggle

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.getColor
import net.skyscanner.backpack.util.use

private fun wrapContext(context: Context, attrs: AttributeSet?): Context {
  val withBaseStyle = createContextThemeWrapper(context, attrs, androidx.appcompat.R.attr.switchStyle)
  return createContextThemeWrapper(withBaseStyle, attrs, R.attr.bpkSwitchStyle)
}

// taken from https://github.com/material-components/material-components-android/blob/master/lib/java/com/google/android/material/color/MaterialColors.java#L42
private const val CHECKED_TRACK_COLOR_ALPHA = (0.38f * 255f).toInt()

/**
 * BpkSwitch allow users to toggle between two states, on or off.
 *
 * This class extends [SwitchCompat] directly and thus follows the same interface and design,
 * with the exception of [SwitchCompat.getTrackTintList] and [SwitchCompat.getThumbTintList] that are set
 * according to Backpack's design.
 *
 * @see SwitchCompat
 */
open class BpkSwitch @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : SwitchCompat(wrapContext(context, attrs), attrs, defStyleAttr) {

  init {
    initialize(attrs, defStyleAttr)
  }

  fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    context.theme.obtainStyledAttributes(attrs, R.styleable.BpkSwitch, defStyleAttr, 0).use {
      val checkedColor = it.getColor(R.styleable.BpkSwitch_switchPrimaryColor, getColor(R.color.bpkSkyBlue))
      val trackCheckedColor = ColorUtils.setAlphaComponent(checkedColor, CHECKED_TRACK_COLOR_ALPHA)

      trackTintList = getColorStateList(trackCheckedColor, ContextCompat.getColor(context, R.color.bpkSkyGrayTint06))
      thumbTintList = getColorStateList(checkedColor, ContextCompat.getColor(context, R.color.bpkBackgroundSecondary))
    }
  }

  private fun getColorStateList(checkedColor: Int, uncheckedColor: Int) =
    ColorStateList(
      arrayOf(
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked)
      ),
      intArrayOf(
        checkedColor,
        uncheckedColor
      )
    )
}
