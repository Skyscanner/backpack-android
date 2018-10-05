package net.skyscanner.backpack.card

import android.content.Context
import android.support.annotation.Dimension
import android.support.v7.widget.CardView
import android.util.AttributeSet
import net.skyscanner.backpack.R


open class BpkCard @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = R.style.Bpk_card
) : CardView(context, attrs, defStyleAttr) {

  init {
    initialize(context, attrs, defStyleAttr)
  }

  /**
   * @property padding
   * padding for card
   */
  var padded: Boolean = false
    set(value) {
      field = value
      @Dimension
      val padding = if (padded) context.resources.getDimension(R.dimen.bpkSpacingBase).toInt() else 0
      this.setContentPadding(padding, padding, padding, padding)
    }

  /**
   * @property padding
   * focus state for card
   */
  var focused: Boolean = false
    set(value) {
      field = value
      cardElevation = context.resources.getDimension(if (value) R.dimen.bpkElevationLg else R.dimen.bpkElevationXs)
    }

  private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
    val a = context.obtainStyledAttributes(attrs, R.styleable.BpkCard, defStyleAttr, 0)
    padded = a.getBoolean(R.styleable.BpkCard_padded, true)
    focused = a.getBoolean(R.styleable.BpkCard_focused, false)
    a.recycle()

    maxCardElevation = context.resources.getDimension(R.dimen.bpkElevationLg)
    radius = context.resources.getDimension(R.dimen.bpkBorderRadiusSm)
  }
}
