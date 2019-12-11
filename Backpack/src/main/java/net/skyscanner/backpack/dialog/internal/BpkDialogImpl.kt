package net.skyscanner.backpack.dialog.internal

import android.view.View
import android.widget.ImageView
import net.skyscanner.backpack.dialog.BpkDialog

internal interface BpkDialogImpl {

  var title: CharSequence

  var description: CharSequence

  var image: ImageView?

  var icon: BpkDialog.Icon?

  fun addActionButton(view: View)

  val contentView: View
}
