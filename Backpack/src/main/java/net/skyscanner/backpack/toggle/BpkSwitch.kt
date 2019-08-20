package net.skyscanner.backpack.toggle

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.getColor

private fun wrapContext(context: Context, attrs: AttributeSet?): Context {
  val withBaseStyle = createContextThemeWrapper(context, attrs, androidx.appcompat.R.attr.switchStyle)
  return createContextThemeWrapper(withBaseStyle, attrs, R.attr.bpkSwitchStyle)
}

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
    val styledAttrs = context.theme.obtainStyledAttributes(attrs, R.styleable.BpkSwitch, defStyleAttr, 0)
    val primaryColor = styledAttrs.getColor(R.styleable.BpkSwitch_switchPrimaryColor, getColor(R.color.bpkBlue500))
    styledAttrs.recycle()

    trackTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.bpkGray100))
    thumbTintList = ColorStateList(
      arrayOf(
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked)
      ),
      intArrayOf(
        primaryColor,
        ContextCompat.getColor(context, R.color.bpkGray50)
      )
    )
  }
}
