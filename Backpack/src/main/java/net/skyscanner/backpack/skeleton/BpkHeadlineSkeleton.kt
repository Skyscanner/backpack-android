package net.skyscanner.backpack.skeleton

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.annotation.AttrRes
import androidx.annotation.DimenRes
import androidx.core.view.updateLayoutParams
import net.skyscanner.backpack.R
import net.skyscanner.backpack.overlay.internal.CornerRadiusViewOutlineProvider
import net.skyscanner.backpack.util.use

class BpkHeadlineSkeleton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  @AttrRes defStyleAttr: Int = 0,
) : BpkSkeletonBase(context, attrs, defStyleAttr) {
  var size = SkeletonSize.Small
    set(value) {
      field = value

      val skeletonView = findViewById<RelativeLayout>(R.id.bpk_skeleton_container)
      var heightSize = context.resources.getDimensionPixelSize(getHeightSize(value))

      skeletonView.updateLayoutParams {
        width = context.resources.getDimensionPixelSize(R.dimen.bpk_headline_skeleton_width)
        height = heightSize
      }
    }

  @DimenRes
  private fun getHeightSize(size: SkeletonSize): Int {
    return when (size) {
      SkeletonSize.Small -> R.dimen.bpk_headline_skeleton_sm_height
      SkeletonSize.Medium -> R.dimen.bpk_headline_skeleton_md_height
      SkeletonSize.Large -> R.dimen.bpk_headline_skeleton_lg_height
    }
  }

  init {
    inflate(this.context, R.layout.view_bpk_skeleton_headline, this)

    setShimmerBackgroundColor(this)
    context.obtainStyledAttributes(attrs, R.styleable.BpkHeadlineSkeleton, defStyleAttr, 0).use {
      size = parseSizeAttribute(it, size)
    }

    outlineProvider = CornerRadiusViewOutlineProvider(R.dimen.bpk_skeleton_xxs_border_radius)
    clipToOutline = true

    startShimmer(findViewById(R.id.bpk_skeleton_shimmer))
  }

  private companion object {
    private fun parseSizeAttribute(it: TypedArray, fallback: SkeletonSize) =
      it.getInt(R.styleable.BpkHeadlineSkeleton_skeletonSize, fallback.id).let { id ->
        SkeletonSize.values().find { it.id == id } ?: fallback
      }
  }
}
