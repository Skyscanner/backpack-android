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

open class BpkDialog private constructor(
  context: Context,
  val style: Style,
  val type: Type?,
) : Dialog(context, 0) {

  @Deprecated("Use BpkDialog(Context, Type) instead")
  constructor(
    context: Context,
    style: Style = Style.ALERT,
  ) : this(
    context = context,
    style = style,
    type = if (style == Style.FLARE) Type.Flare else null,
  )

  constructor(
    context: Context,
    type: Type,
  ) : this(
    context = context,
    type = type,
    style = when (type) {
      Type.Flare -> Style.FLARE
      else -> Style.ALERT
    }
  )

  data class Button(internal val text: String, internal val onClick: () -> Unit)

  data class Icon
  @Deprecated("Custom icon background are not supported now and will be removed from public API soon")
  constructor(
    @DrawableRes val drawableRes: Int,
    @Deprecated("Icon background is semantic now. This field will not contain any useful data if Type is used")
    @ColorInt val color: Int,
  ) {

    @Suppress("DEPRECATION")
    constructor(@DrawableRes drawableRes: Int) : this(drawableRes, Color.TRANSPARENT)
  }

  enum class Type {
    NoIcon,
    Success,
    Warning,
    Danger,
    Flare,
  }

  enum class Style {
    ALERT, FLARE,
  }

  private val impl = when (type) {
    Type.Flare -> FlareDialogImpl(this)
    else -> AlertDialogImpl(this, type)
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
