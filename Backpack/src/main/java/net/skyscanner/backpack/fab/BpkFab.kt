package net.skyscanner.backpack.fab

import android.animation.AnimatorInflater
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.createContextThemeWrapper
import net.skyscanner.backpack.util.use

open class BpkFab @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FloatingActionButton(
  createContextThemeWrapper(
    createContextThemeWrapper(context, attrs, com.google.android.material.R.attr.floatingActionButtonStyle),
    attrs, R.attr.bpkFabStyle
  ),
  attrs, defStyleAttr) {

  init {
    initialize(attrs, defStyleAttr)
  }

  private fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {
    var backgroundColour = ContextCompat.getColor(context, R.color.bpkMonteverde)
    var iconColour = ContextCompat.getColor(context, R.color.bpkWhite)

    context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.BpkFab,
      defStyleAttr,
      0
    ).use {
      backgroundColour = it.getColor(R.styleable.BpkFab_fabBackgroundColor, backgroundColour)
      iconColour = it.getColor(R.styleable.BpkFab_fabIconColor, iconColour)
    }

    this.imageTintList = ColorStateList.valueOf(iconColour)
    this.isClickable = isEnabled
    this.stateListAnimator = AnimatorInflater.loadStateListAnimator(context, R.drawable.bpk_button_state_animator)
    this.elevation = resources.getDimensionPixelSize(R.dimen.bpkElevationBase).toFloat()
    this.backgroundTintList = getColorSelector(backgroundColour, backgroundColour, ContextCompat.getColor(context, R.color.bpkSkyGrayTint06))
  }

  override fun setEnabled(enabled: Boolean) {
    super.setEnabled(enabled)
    isClickable = enabled
  }

  private fun getColorSelector(
    @ColorInt normalColor: Int,
    @ColorInt pressedColor: Int,
    @ColorInt disabledColor: Int
  ) = ColorStateList(
    arrayOf(
      intArrayOf(-android.R.attr.state_enabled),
      intArrayOf(android.R.attr.state_pressed),
      intArrayOf(android.R.attr.state_focused),
      intArrayOf(android.R.attr.state_activated),
      intArrayOf()
    ),
    intArrayOf(disabledColor, pressedColor, pressedColor, pressedColor, normalColor)
  )
}
