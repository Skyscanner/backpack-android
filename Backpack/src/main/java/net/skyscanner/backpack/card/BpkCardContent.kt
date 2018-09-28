package net.skyscanner.backpack.card

import android.content.Context
import android.graphics.Paint
import android.support.annotation.Nullable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import net.skyscanner.backpack.R

open class BpkCardContent(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int) : LinearLayoutCompat(context, attrs, defStyleAttr) {

  constructor(context: Context) : this(context, null)
  constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)

  init {
    initialize(context)
  }

  private fun initialize(context: Context) {
    weightSum = 1f
    isClickable = true

    val typedArray = context.obtainStyledAttributes(intArrayOf(R.attr.selectableItemBackground))
    val backgroundResource = typedArray.getResourceId(0, 0)
    setBackgroundResource(backgroundResource)
    typedArray.recycle()
  }

  override fun setOrientation(orientation: Int) {
    super.setOrientation(orientation)
    getChildAt(1)?.let {
      if (it is Divider) {
        removeView(it)
        addView(Divider(context, orientation), 1)
      }
    }
  }

  override fun onFinishInflate() {
    if (childCount == 2) {
      addView(Divider(context, orientation), 1)
    }
    super.onFinishInflate()
  }

  class Divider(context: Context, val orientation: Int) : ImageView(context) {
    init {
      val margin = context.resources.getDimension(R.dimen.bpkSpacingMd).toInt()
      val vertical = orientation == LinearLayout.VERTICAL
      val params = if (vertical) {
        LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5).apply {
          setMargins(margin, 0, margin, 0)
        }
      } else {
        LinearLayoutCompat.LayoutParams(5, LinearLayout.LayoutParams.MATCH_PARENT).apply {
          setMargins(0, margin, 0, margin)
        }
      }

      setLayerType(View.LAYER_TYPE_SOFTWARE, Paint())
      background = ContextCompat.getDrawable(context, if (vertical) R.drawable.dashed_line else R.drawable.vertical_dashed_line)
      layoutParams = params
    }
  }
}
