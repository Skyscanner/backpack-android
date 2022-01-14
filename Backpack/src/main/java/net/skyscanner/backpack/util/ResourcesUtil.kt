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

package net.skyscanner.backpack.util

import android.content.Context
import android.content.res.TypedArray
import android.util.DisplayMetrics
import androidx.annotation.Dimension
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.math.roundToInt

internal object ResourcesUtil {

  @Dimension
  fun dpToPx(@Dimension dp: Int, context: Context): Int {
    return dpToPx(dp.toFloat(), context).roundToInt()
  }

  @Dimension
  fun dpToPx(@Dimension dp: Float, context: Context): Float {
    return dp * context.resources.displayMetrics.density
  }

  @Dimension
  fun pxToDp(px: Float, context: Context): Float {
    return (px / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT))
  }
}

@OptIn(ExperimentalContracts::class)
internal inline fun <R> TypedArray.use(block: (TypedArray) -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  try {
    return this.let(block)
  } finally {
    this.recycle()
  }
}
