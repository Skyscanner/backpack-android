package net.skyscanner.backpack.toggle

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.SwitchCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.ThemesUtil
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.getColor

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
  defStyleAttr: Int = R.attr.bpkSwitchStyle
) : SwitchCompat(createContextThemeWrapper(context, attrs, androidx.appcompat.R.attr.switchStyle), attrs, defStyleAttr) {

  init {
    initialize(attrs, defStyleAttr)
  }

  fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    val a = context.theme.obtainStyledAttributes(attrs, R.styleable.BpkSwitch, defStyleAttr, 0)
    val primaryColor = a.getColor(R.styleable.BpkSwitch_switchPrimaryColor, getColor(R.color.bpkBlue500))
    a.recycle()

    trackTintList = ColorStateList.valueOf(ThemesUtil.getGray100Color(context))
    thumbTintList = ColorStateList(
      arrayOf(
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked)
      ),
      intArrayOf(
        primaryColor,
        ThemesUtil.getGray50Color(context)
      )
    )
  }
}
