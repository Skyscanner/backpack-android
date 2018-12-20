package net.skyscanner.backpack.card

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DimenRes
import androidx.annotation.Dimension
import androidx.cardview.widget.CardView
import net.skyscanner.backpack.R

/**
 * Cards are used to group related items.
 * They allow complex datasets to be broken down into individual, distinct areas for easy consumption.
 *
 * @see [CardView]
 */
open class BpkCardView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = R.style.Bpk_card
) : CardView(context, attrs, defStyleAttr) {

  /**
   * List of possible border radius for the [BpkCardView].
   * Those map directly to the following tokens:
   *
   * [CornerStyle.SMALL] = [R.dimen.bpkBorderRadiusSm]
   * [CornerStyle.LARGE] = [R.dimen.bpkBorderRadiusLg]
   */
  enum class CornerStyle(@DimenRes val tokenRes: Int) {
    SMALL(R.dimen.bpkBorderRadiusSm),
    LARGE(R.dimen.bpkBorderRadiusLg)
  }

  init {
    initialize(context, attrs, defStyleAttr)
  }

  @Dimension
  private var paddingSize: Int = 0

  /**
   * Sets the card to padded or not
   * @property padded
   */
  var padded: Boolean = true
    set(value) {
      field = value
      val padding = if (padded) paddingSize else 0
      this.setContentPadding(padding, padding, padding, padding)
    }

  /**
   * Sets the card to focused or not
   * @property focused
   */
  var focused: Boolean = false
    set(value) {
      field = value
      cardElevation = context.resources.getDimension(if (value) R.dimen.bpkElevationLg else R.dimen.bpkElevationSm)
    }

  /**
   * Sets the border radius of the card.
   *
   * @see [BpkCardView.CornerStyle]
   * @property cornerStyle
   */
  var cornerStyle: CornerStyle = CornerStyle.SMALL
    set(value) {
      field = value
      radius = context.resources.getDimension(value.tokenRes)
    }

  private fun initialize(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
    paddingSize = context.resources.getDimension(R.dimen.bpkSpacingBase).toInt()

    val a = context.obtainStyledAttributes(attrs, R.styleable.BpkCardView, defStyleAttr, 0)
    padded = a.getBoolean(R.styleable.BpkCardView_padded, true)
    focused = a.getBoolean(R.styleable.BpkCardView_focused, false)
    cornerStyle = CornerStyle.values()[a.getInt(R.styleable.BpkCardView_cornerStyle, 0)]
    a.recycle()

    maxCardElevation = context.resources.getDimension(R.dimen.bpkElevationLg)
  }
}
