package net.skyscanner.backpack.dialog

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.ThemesUtil
import net.skyscanner.backpack.util.getColor

internal class BpkDialogIcon @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

  private val variables = DialogConstants(context)
  private val imageView = AppCompatImageView(context)

  init {
    initialize()
  }

  var icon: BpkDialog.Icon? = null
    set(value) {
      field = value
      value?.let(::updateIcon)
    }

  private fun initialize() {
    val imageSize = resources.getDimension(R.dimen.bpk_icon_size_normal).toInt()
    imageView.layoutParams = RelativeLayout.LayoutParams(imageSize, imageSize).apply {
      addRule(CENTER_IN_PARENT, TRUE)
    }

    ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(getColor(R.color.bpkWhite)))
    background = getLayoutBackground()
    clipChildren = false

    addView(imageView)
  }

  override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
    params?.apply {
      width = variables.iconContainerSize
      height = variables.iconContainerSize
    }
    super.setLayoutParams(params)
  }

  private fun getLayoutBackground(): GradientDrawable {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.RECTANGLE
    val radii = variables.iconContainerRadius
    shape.cornerRadii = floatArrayOf(radii, radii, radii, radii, radii, radii, radii, radii)

    shape.setColor(ThemesUtil.getGray300Color(context))
    shape.setStroke(
      variables.iconContainerBorderSize.toInt(),
      ResourcesCompat.getColor(resources, R.color.bpkWhite, context.theme)
    )
    return shape
  }

  private fun updateIcon(icon: BpkDialog.Icon) {
    val drawable = ResourcesCompat.getDrawable(context.resources, icon.drawableRes, context.theme)
    val color = ResourcesCompat.getColor(resources, icon.colorRes, context.theme)
    background?.let {
      it as GradientDrawable
      it.setColor(color)
    }
    imageView.setImageDrawable(drawable)
  }
}
