package net.skyscanner.backpack.card

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Dimension
import androidx.cardview.widget.CardView
import net.skyscanner.backpack.R

open class BpkCardView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = R.style.Bpk_card
) : CardView(context, attrs, defStyleAttr) {

  init {
    initialize(context, attrs, defStyleAttr)
  }

  @Dimension
  private var paddingSize: Int = 0

  /**
   * @property padding
   * padding for card
   */
  var padded: Boolean = true
    set(value) {
      field = value
      val padding = if (padded) paddingSize else 0
      this.setContentPadding(padding, padding, padding, padding)
    }

  /**
   * @property padding
   * focus state for card
   */
  var focused: Boolean = false
    set(value) {
      field = value
      cardElevation = context.resources.getDimension(if (value) R.dimen.bpkElevationLg else R.dimen.bpkElevationSm)
    }

  private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
    paddingSize = context.resources.getDimension(R.dimen.bpkSpacingBase).toInt()

    val a = context.obtainStyledAttributes(attrs, R.styleable.BpkCardView, defStyleAttr, 0)
    padded = a.getBoolean(R.styleable.BpkCardView_padded, true)
    focused = a.getBoolean(R.styleable.BpkCardView_focused, false)
    a.recycle()

    maxCardElevation = context.resources.getDimension(R.dimen.bpkElevationLg)
    radius = context.resources.getDimension(R.dimen.bpkBorderRadiusSm)
  }
}
