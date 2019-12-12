package net.skyscanner.backpack.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import net.skyscanner.backpack.dialog.internal.AlertDialogImpl
import net.skyscanner.backpack.dialog.internal.FlareDialogImpl

open class BpkDialog(
  context: Context,
  val style: Style = Style.ALERT
) : Dialog(context, 0) {

  data class Icon(@DrawableRes val drawableRes: Int, @ColorRes val colorRes: Int)

  enum class Style {
    ALERT, BOTTOM_SHEET, FLARE
  }

  private val impl = when (style) {
    Style.ALERT -> AlertDialogImpl(this, false)
    Style.BOTTOM_SHEET -> AlertDialogImpl(this, true)
    Style.FLARE -> FlareDialogImpl(this)
  }

  var title: String
    get() = impl.title
    set(value) {
      impl.title = value
    }

  var description: String
    get() = impl.description
    set(value) {
      impl.description = value
    }

  val image: ImageView?
    get() = impl.image

  var icon: Icon?
    get() = impl.icon
    set(value) {
      impl.icon = value
    }

  fun addActionButton(view: View) {
    impl.addActionButton(view)
  }
}
