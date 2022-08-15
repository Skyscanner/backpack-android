package net.skyscanner.backpack.skeleton

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.annotation.AttrRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.overlay.internal.CornerRadiusViewOutlineProvider
import net.skyscanner.backpack.overlay.internal.EmptyViewOutlineProvider
import net.skyscanner.backpack.util.use

class BpkImageSkeleton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  @AttrRes defStyleAttr: Int = 0,
) : BpkSkeletonBase(context, attrs, defStyleAttr) {

  enum class CornerType(
    internal val id: Int,
  ) {
    Square(0),
    Rounded(1),
  }

  var cornerType: CornerType = CornerType.Square
    set(value) {
      field = value
      if (value === CornerType.Rounded) {
        outlineProvider = CornerRadiusViewOutlineProvider(R.dimen.bpkBorderRadiusSm)
        clipToOutline = true
      } else {
        outlineProvider = EmptyViewOutlineProvider
        clipToOutline = false
      }
    }

  init {
    inflate(this.context, R.layout.view_bpk_skeleton_image, this)

    setShimmerBackgroundColor(this)
    context.obtainStyledAttributes(attrs, R.styleable.BpkImageSkeleton, defStyleAttr, 0).use {
      cornerType = parseCornerAttribute(it, cornerType)
    }
    startShimmer(findViewById(R.id.bpk_skeleton_shimmer))
  }

  private companion object {
    private fun parseCornerAttribute(it: TypedArray, fallback: CornerType) =
      it.getInt(R.styleable.BpkImageSkeleton_cornerType, fallback.id).let { id ->
        CornerType.values().find { it.id == id } ?: fallback
      }
  }
}
