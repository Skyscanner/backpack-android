package net.skyscanner.backpack.card

import android.content.Context
import android.support.annotation.Nullable
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.widget.LinearLayout
import net.skyscanner.backpack.R


open class BpkCard(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int) : CardView(context, attrs, defStyleAttr) {

  constructor(context: Context) : this(context, null)
  constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, R.style.Bpk_card)

  init {
    initialize(context, attrs, defStyleAttr)
  }

  private var inflated = false

  var padded: Boolean = false
    set(value) {
      field = value
      if (inflated) adjustPadding()
    }

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

  override fun onFinishInflate() {
    inflated = true
    if (padded) {
      val firstChild = getChildAt(0)

      // Card content should have only two children (we check 3 because of the divider)
      if (firstChild is BpkCardContent && firstChild.childCount <= 3) {
        adjustPadding()
      } else {
        throw IllegalStateException(if (firstChild !is BpkCardContent) {
          "BpkCard should have a BpkCardContent as its only children"
        } else {
          "BpkCardContent can't have more than two children"
        })
      }
    }

    super.onFinishInflate()
  }

  private fun adjustPadding() {
    val padding = if (padded) context.resources.getDimension(R.dimen.bpkSpacingBase).toInt() else 0
    val cardContent = getChildAt(0) as BpkCardContent

    (0..cardContent.childCount).forEach {
      cardContent.getChildAt(it)?.let {
        if (it !is BpkCardContent.Divider) {

          it.setPadding(padding, padding, padding, padding)
          it.layoutParams?.let {
            it as LinearLayoutCompat.LayoutParams
            if (it.weight == 0f) it.weight = 0.5f
          }
        }
      }
    }
  }
}
