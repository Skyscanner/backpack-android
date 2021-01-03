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

package net.skyscanner.backpack.text

import android.content.Context
import android.text.TextPaint
import android.text.style.CharacterStyle

class BpkFontSpan(private val font: BpkText.FontDefinition) : CharacterStyle() {

  constructor(
    context: Context,
    textStyle: Int = BpkText.BASE,
    weight: BpkText.Weight = BpkText.Weight.NORMAL
  ) :
    this(BpkText.getFont(context, textStyle, weight))

  override fun updateDrawState(tp: TextPaint) =
    font.applyTo(tp)
}
