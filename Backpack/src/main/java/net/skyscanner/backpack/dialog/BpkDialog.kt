/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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

package net.skyscanner.backpack.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import net.skyscanner.backpack.dialog.internal.AlertDialogImpl
import net.skyscanner.backpack.dialog.internal.FlareDialogImpl

open class BpkDialog(
  context: Context,
  val style: Style = Style.ALERT
) : Dialog(context, 0) {

  data class Button(internal val text: String, internal val onClick: () -> Unit)

  data class Icon internal constructor(
    @DrawableRes val drawableRes: Int,
    @Deprecated(
      "Icon background is semantic now. This field will not contain any useful data if semantic constructor is used"
    )
    @ColorInt
    val color: Int,
    internal val background: IconBackground,
  ) {

    @Deprecated("Custom icon background are not supported now and will be removed from public API soon")
    constructor(drawableRes: Int, @ColorInt color: Int) :
      this(drawableRes, color, IconBackground.UseColor)

    @Suppress("FunctionName")
    companion object {

      fun Success(@DrawableRes drawableRes: Int): Icon =
        Icon(drawableRes = drawableRes, color = Color.TRANSPARENT, background = IconBackground.Success)

      fun Warning(@DrawableRes drawableRes: Int): Icon =
        Icon(drawableRes = drawableRes, color = Color.TRANSPARENT, background = IconBackground.Warning)

      fun Danger(@DrawableRes drawableRes: Int): Icon =
        Icon(drawableRes = drawableRes, color = Color.TRANSPARENT, background = IconBackground.Danger)
    }
  }

  internal enum class IconBackground {
    UseColor,
    Success,
    Warning,
    Danger,
  }

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

  @Deprecated("Use addActionButton(BpkDialog.Button) instead")
  fun addActionButton(view: View) {
    impl.addActionButton(view)
  }

  fun addActionButton(button: Button) {
    impl.addActionButton(button)
  }

  override fun setCanceledOnTouchOutside(cancel: Boolean) {
    super.setCanceledOnTouchOutside(cancel)
    impl.isCanceledOnTouchOutside = cancel
  }
}
