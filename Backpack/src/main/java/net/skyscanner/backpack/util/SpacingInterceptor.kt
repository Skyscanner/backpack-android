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

package net.skyscanner.backpack.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import io.github.inflationx.viewpump.InflateResult
import io.github.inflationx.viewpump.Interceptor
import net.skyscanner.backpack.R

class SpacingInterceptor : Interceptor {

  @RequiresApi(Build.VERSION_CODES.M)
  override fun intercept(chain: Interceptor.Chain): InflateResult {
    val result = chain.proceed(chain.request())
    val view = result.view()
    if (view != null) {
      val padding = listOf(view.paddingBottom, view.paddingLeft, view.paddingRight, view.paddingTop)
      val validSpaces = listOf(0,
        view.context.resources.getDimension(R.dimen.bpkSpacingBase).toInt(),
        view.context.resources.getDimension(R.dimen.bpkSpacingLg).toInt(),
        view.context.resources.getDimension(R.dimen.bpkSpacingMd).toInt(),
        view.context.resources.getDimension(R.dimen.bpkSpacingSm).toInt(),
        view.context.resources.getDimension(R.dimen.bpkSpacingXl).toInt(),
        view.context.resources.getDimension(R.dimen.bpkSpacingXxl).toInt())

      if (!validSpaces.containsAll(padding)) {
        if (view.background == null) {
          view.background = ResourcesCompat.getDrawable(view.resources, R.drawable.bpk_internal_elevation_highlight, view.context.theme)
        } else {
          view.foreground = ResourcesCompat.getDrawable(view.resources, R.drawable.bpk_internal_elevation_highlight, view.context.theme)
        }
      }
    }
    return result
  }
}
