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
import androidx.appcompat.content.res.AppCompatResources
import io.github.inflationx.viewpump.InflateResult
import io.github.inflationx.viewpump.Interceptor
import net.skyscanner.backpack.R

class ElevationInterceptor : Interceptor {

  @RequiresApi(Build.VERSION_CODES.M)
  override fun intercept(chain: Interceptor.Chain): InflateResult {
    val result = chain.proceed(chain.request())
    val view = result.view()
    if (view != null) {
      val validSpaces = listOf(0.0f,
        view.context.resources.getDimension(R.dimen.bpkElevationBase),
        view.context.resources.getDimension(R.dimen.bpkElevationLg),
        view.context.resources.getDimension(R.dimen.bpkElevationSm),
        view.context.resources.getDimension(R.dimen.bpkElevationXl),
        view.context.resources.getDimension(R.dimen.bpkElevationXs),
        view.context.resources.getDimension(R.dimen.bpkElevationXxl))

      val elevation = ResourcesUtil.pxToDp(view.elevation, view.context)
      if (elevation !in validSpaces) {
        if (view.background == null) {
          view.background = AppCompatResources.getDrawable(view.context, R.drawable.bpk_internal_elevation_highlight)
        } else {
          view.foreground = AppCompatResources.getDrawable(view.context, R.drawable.bpk_internal_elevation_highlight)
        }
      }
    }
    return result
  }
}
