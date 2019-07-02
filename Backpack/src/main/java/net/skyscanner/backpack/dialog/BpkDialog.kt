package net.skyscanner.backpack.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import net.skyscanner.backpack.R
import net.skyscanner.backpack.text.BpkText
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat

open class BpkDialog(
  context: Context,
  val style: Style = Style.ALERT
) : Dialog(context, 0) {

  data class Icon(@DrawableRes val drawableRes: Int, @ColorRes val colorRes: Int)

  enum class Style {
    ALERT, BOTTOM_SHEET
  }

  private var viewHolder: ViewHolder? = null
  private val variables = DialogConstants(context)

  init {
    setupDialog()
  }

  var title = ""
    set(value) {
      field = value
      viewHolder?.title?.text = value
    }

  var description = ""
    set(value) {
      field = value
      viewHolder?.description?.text = value
    }

  var icon: Icon? = null
    set(value) {
      field = value
      viewHolder?.iconView?.icon = icon
    }

  fun addActionButton(button: View) {
    button.layoutParams = LinearLayoutCompat.LayoutParams(
      LinearLayoutCompat.LayoutParams.MATCH_PARENT,
      LinearLayoutCompat.LayoutParams.WRAP_CONTENT
    ).apply {
      setMargins(0, 0, 0, variables.buttonStackItemSpace)
    }
    viewHolder?.contentLayout?.addView(button)
  }

  private fun setupDialog() {
    if (style == Style.BOTTOM_SHEET) {
      window?.setGravity(Gravity.BOTTOM)
      window?.setWindowAnimations(R.style.Bpk_dialog_animation)
    }

    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(setUpContentView())
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    val metrics = DisplayMetrics()
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(metrics)
  }

  private fun setUpContentView(): View {
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val view = inflater.inflate(R.layout.bpk_dialog, null)

    viewHolder = ViewHolder(view).apply {
      val params = contentLayout.layoutParams as RelativeLayout.LayoutParams
      params.topMargin = variables.contentContainerMarginTop
      contentLayout.background = getContentBackground()
      contentLayout.setPadding(
        variables.contentContainerPaddingHorizontal,
        variables.contentContainerPaddingTop,
        variables.contentContainerPaddingHorizontal,
        variables.contentContainerPaddingBottom
      )
    }

    return view
  }

  private fun getContentBackground(): GradientDrawable {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.RECTANGLE
    val radii = variables.contentRadius
    shape.cornerRadii = floatArrayOf(radii, radii, radii, radii, radii, radii, radii, radii)
    shape.setColor(ResourcesCompat.getColor(context.resources, R.color.bpkWhite, context.theme))
    return shape
  }

  private class ViewHolder(container: View) {
    val title: BpkText = container.findViewById(R.id.dialog_title)
    val description: BpkText = container.findViewById(R.id.dialog_description)
    val iconView: BpkDialogIcon = container.findViewById(R.id.dialog_icon)
    val contentLayout: ViewGroup = container.findViewById(R.id.dialog_content_layout)
  }
}
