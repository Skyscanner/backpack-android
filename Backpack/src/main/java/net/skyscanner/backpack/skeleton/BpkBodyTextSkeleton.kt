package net.skyscanner.backpack.skeleton

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import net.skyscanner.backpack.R
import net.skyscanner.backpack.overlay.internal.CornerRadiusViewOutlineProvider
import net.skyscanner.backpack.util.use

class BpkBodyTextSkeleton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  @AttrRes defStyleAttr: Int = 0,
) : BpkSkeletonBase(context, attrs, defStyleAttr) {

  var size = SkeletonSize.Small
    set(value) {
      field = value
      val skeletonContainer = findViewById<ConstraintLayout>(R.id.bpk_skeleton_container)
      var constraintSet = ConstraintSet()
      constraintSet.clone(skeletonContainer)
      constraintSet.constrainPercentWidth(R.id.bpk_skeleton_view, getWidthPercentage(value))
      constraintSet.applyTo(skeletonContainer)
    }

  private fun getWidthPercentage(size: SkeletonSize): Float {
    return when (size) {
      SkeletonSize.Small -> 0.42F
      SkeletonSize.Medium -> 0.62F
      SkeletonSize.Large -> 0.73F
    }
  }

  init {
    inflate(this.context, R.layout.view_bpk_skeleton_body_text, this)

    setShimmerBackgroundColor(findViewById(R.id.bpk_skeleton_view))
    context.obtainStyledAttributes(attrs, R.styleable.BpkBodyTextSkeleton, defStyleAttr, 0).use {
      size = parseSizeAttribute(it, size)
    }

    outlineProvider = CornerRadiusViewOutlineProvider(R.dimen.bpk_skeleton_xxs_border_radius)
    clipToOutline = true

    startShimmer(findViewById(R.id.bpk_skeleton_shimmer))
  }

  private companion object {
    private fun parseSizeAttribute(it: TypedArray, fallback: SkeletonSize) =
      it.getInt(R.styleable.BpkBodyTextSkeleton_skeletonSize, fallback.id).let { id ->
        SkeletonSize.values().find { it.id == id } ?: fallback
      }
  }
}
