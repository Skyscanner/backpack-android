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

package net.skyscanner.backpack.chip

import android.content.Context
import android.util.AttributeSet
import net.skyscanner.backpack.R
import net.skyscanner.backpack.chip.internal.BpkChipStyle
import net.skyscanner.backpack.chip.internal.BpkChipStyles
import net.skyscanner.backpack.util.createContextThemeWrapper

class BpkOutlineChip @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : BpkChip(createContextThemeWrapper(context, attrs, R.attr.bpkOutlineChipStyle), attrs, defStyleAttr) {

  override fun provideStyle(context: Context, attrs: AttributeSet?, defStyleAttr: Int): BpkChipStyle =
    BpkChipStyles.Stroke(context, attrs, defStyleAttr)
}
