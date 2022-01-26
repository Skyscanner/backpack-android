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

package net.skyscanner.backpack.gradient

import android.content.Context
import android.graphics.drawable.GradientDrawable
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.resolveThemeColor

/**
 * Creates a [GradientDrawable] with the primary Backpack gradient colours by default.
 *
 * For a themeable primary gradient see [BpkGradients.getPrimary]
 *
 * @param context the current ui context.
 * @param orientation the gradient orientation. Default value is [GradientDrawable.Orientation.TL_BR]
 * @param colors the gradient colors. Default is Backpack's primary gradient.
 */
open class BpkGradients @JvmOverloads constructor(
  context: Context,
  orientation: Orientation = Orientation.TL_BR,
  colors: IntArray = intArrayOf(
    context.getColor(R.color.bpkSkyBlue),
    context.getColor(R.color.bpkPrimaryGradientLight),
  )
) : GradientDrawable(orientation, colors) {

  companion object {
    /**
     * Creates a [GradientDrawable] with backpack primary colors.
     *
     * The primary gradient can be themed by setting the following properties to your theme.
     * - bpkPrimaryGradientColorStart
     * - bpkPrimaryGradientColorEnd
     *
     * If any of those properties are not set the default primary gradient will be returned.
     *
     * @param context The current ui context.
     * @param orientation The gradient orientation. Default value is [GradientDrawable.Orientation.TL_BR]
     *
     * @return [BpkGradients]
     */
    @JvmStatic
    fun getPrimary(context: Context, orientation: Orientation = Orientation.TL_BR): BpkGradients {
      val themedProps = resolveAllAttributes(
        context,
        intArrayOf(R.attr.bpkPrimaryGradientColorStart, R.attr.bpkPrimaryGradientColorEnd)
      )

      return themedProps?.let { BpkGradients(context, orientation, it) } ?: BpkGradients(context, orientation)
    }
  }
}

private fun resolveAllAttributes(context: Context, intArray: IntArray): IntArray? {
  val resolved = intArray.fold(mutableListOf<Int>()) { acc, id ->
    resolveThemeColor(context, id)?.let { acc.add(it) }
    acc
  }

  return if (resolved.size != intArray.size) null else resolved.toIntArray()
}
