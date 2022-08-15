package net.skyscanner.backpack.skeleton

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.AttrRes
import net.skyscanner.backpack.R

open class BpkSkeletonBase @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  @AttrRes defStyleAttr: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr) {

  enum class SkeletonSize(
    internal val id: Int,
  ) {
    Small(0),
    Medium(1),
    Large(2),
  }

  fun setShimmerBackgroundColor(backGroundView: View) {
    backGroundView.setBackgroundColor(context.getColor(R.color.__skeletonBackground))
  }
  fun startShimmer(shimmerView: View) {
    // Use ObjectAnimator to draw the animation for translationX, translate the position from left to right.
    ObjectAnimator.ofFloat(shimmerView, "translationX", -500f, 500f).apply {
      duration = 1000 // Per specification.
      repeatCount = ObjectAnimator.INFINITE
      startDelay = 200 // Per specification.
      start()
    }
  }
}
