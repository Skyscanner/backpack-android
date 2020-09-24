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

package net.skyscanner.backpack.horisontalnav.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ImageSpan
import androidx.appcompat.content.res.AppCompatResources
import net.skyscanner.backpack.R

internal class NotificationDotSpan(context: Context) : ImageSpan(
  AppCompatResources.getDrawable(context, R.drawable.bpk_horizontal_nav_dot)!!.apply {
    setBounds(0, 0, intrinsicWidth, intrinsicHeight)
  },
  ALIGN_BOTTOM
) {

  override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
    super.getSize(paint, text, start, end, fm)
    return drawable.bounds.width() * 3
  }

  override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
    val count = canvas.save()
    canvas.translate(drawable.bounds.width().toFloat(), -y.toFloat())
    super.draw(canvas, text, start, end, x, top, y, bottom, paint)
    canvas.restoreToCount(count)
  }
}
