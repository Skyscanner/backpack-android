/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.dialog.internal

import android.app.Dialog
import android.view.*
import android.view.ViewGroup.*
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.dialog.BpkDialog

internal interface BpkDialogImpl {

  var title: String

  var description: String

  val image: ImageView?

  var icon: BpkDialog.Icon?

  var isCanceledOnTouchOutside: Boolean

  fun addActionButton(view: View)

  abstract class Base(
    @LayoutRes layout: Int,
    dialog: Dialog
  ) : BpkDialogImpl {

    protected val root: View = LayoutInflater.from(dialog.context).inflate(layout, null)

    private val titleView = root.findViewById<TextView?>(R.id.dialog_title)

    private val descriptionView = root.findViewById<TextView?>(R.id.dialog_description)

    private val iconView = root.findViewById<BpkDialogIcon?>(R.id.dialog_icon)

    private val buttonsRoot = root.findViewById<ViewGroup?>(R.id.dialog_buttons_root)

    override val image: ImageView? = root.findViewById(R.id.dialog_image)

    init {
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      dialog.setContentView(root)
    }

    override var title: String = ""
      set(value) {
        field = value
        titleView?.text = value
      }

    override var description: String = ""
      set(value) {
        field = value
        descriptionView?.text = value
      }

    override var icon: BpkDialog.Icon? = null
      set(value) {
        field = value
        iconView?.icon = icon
      }

    override var isCanceledOnTouchOutside: Boolean = true

    override fun addActionButton(view: View) {
      buttonsRoot?.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }
  }
}
