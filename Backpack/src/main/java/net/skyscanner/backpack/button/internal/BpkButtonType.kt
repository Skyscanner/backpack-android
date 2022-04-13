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

package net.skyscanner.backpack.button.internal

import android.content.Context
import android.util.AttributeSet
import net.skyscanner.backpack.R
import net.skyscanner.backpack.button.BpkButton

internal fun BpkButton.Type.Companion.fromAttrs(context: Context, attrs: AttributeSet?): BpkButton.Type {
  val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.BpkButton, 0, 0)
  return fromId(attr.getInt(R.styleable.BpkButton_buttonType, 0))
}

internal fun BpkButton.Type.Companion.fromId(id: Int): BpkButton.Type =
  when (id) {
    0 -> BpkButton.Type.Primary
    1 -> BpkButton.Type.Secondary
    2 -> BpkButton.Type.Featured
    3 -> BpkButton.Type.Destructive
    4 -> BpkButton.Type.PrimaryOnDark
    5 -> BpkButton.Type.PrimaryOnLight
    6 -> BpkButton.Type.SecondaryOnDark
    7 -> BpkButton.Type.Link
    8 -> BpkButton.Type.LinkOnDark
    else -> throw IllegalArgumentException()
  }

internal fun BpkButton.Type.createStyle(context: Context): ButtonStyle =
  when (this) {
    BpkButton.Type.Primary -> ButtonStyles.Primary(context)
    BpkButton.Type.Secondary -> ButtonStyles.Secondary(context)
    BpkButton.Type.Featured -> ButtonStyles.Featured(context)
    BpkButton.Type.Destructive -> ButtonStyles.Destructive(context)
    BpkButton.Type.PrimaryOnDark -> ButtonStyles.PrimaryOnDark(context)
    BpkButton.Type.PrimaryOnLight -> ButtonStyles.PrimaryOnLight(context)
    BpkButton.Type.SecondaryOnDark -> ButtonStyles.SecondaryOnDark(context)
    BpkButton.Type.Link -> ButtonStyles.Link(context)
    BpkButton.Type.LinkOnDark -> ButtonStyles.LinkOnDark(context)
  }
