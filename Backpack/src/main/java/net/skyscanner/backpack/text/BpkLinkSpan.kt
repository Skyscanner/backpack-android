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

package net.skyscanner.backpack.text

import android.content.Context
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import net.skyscanner.backpack.R

class BpkLinkSpan<T>(
  context: Context,
  private val link: T,
  private val linkHandler: (T) -> Unit
) : ClickableSpan() {

  private val color = context.getColor(R.color.bpkTextLink)

  override fun updateDrawState(tp: TextPaint) {
    tp.color = color
    tp.isUnderlineText = true
  }

  override fun onClick(widget: View) {
    linkHandler(link)
  }
}
