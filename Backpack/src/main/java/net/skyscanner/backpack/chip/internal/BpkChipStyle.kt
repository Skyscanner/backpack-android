/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

package net.skyscanner.backpack.chip.internal

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt

internal interface BpkChipStyle {

  val background: Drawable

  val text: ColorStateList

  @get:ColorInt
  @setparam:ColorInt
  var selectedBackgroundColor: Int

  @get:ColorInt
  @setparam:ColorInt
  var backgroundColor: Int

  @get:ColorInt
  @setparam:ColorInt
  var disabledBackgroundColor: Int

  @get:ColorInt
  @setparam:ColorInt
  var textColor: Int
}
