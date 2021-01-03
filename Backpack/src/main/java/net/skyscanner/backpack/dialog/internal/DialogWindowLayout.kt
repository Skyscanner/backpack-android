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

package net.skyscanner.backpack.dialog.internal

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.roundToInt
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.unsafeLazy

internal class DialogWindowLayout @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  enum class Gravity {
    Top,
    Center,
    Bottom
  }

  private val content by unsafeLazy { getChildAt(0) }

  private val contentParams: LayoutParams
    get() = content.layoutParams.let { it as LayoutParams }

  private val contentHitRect = Rect()

  private var fullscreen = false
    set(value) {
      field = value
      background = if (value) AppCompatResources.getDrawable(context, R.color.__dialogBackground) else modalBg
    }

  private val modalPadding = resources.getDimensionPixelSize(R.dimen.bpk_dialog_margin)

  var dismissListener: () -> Unit = {}

  var verticalGravity: Gravity = Gravity.Center
    set(value) {
      field = value

      when (value) {
        Gravity.Top -> {
          contentParams.topToTop = LayoutParams.PARENT_ID
          contentParams.bottomToBottom = LayoutParams.UNSET
          setPadding(modalPadding, 0, modalPadding, modalPadding)
        }
        Gravity.Center -> {
          contentParams.topToTop = LayoutParams.PARENT_ID
          contentParams.bottomToBottom = LayoutParams.PARENT_ID
          setPadding(modalPadding, 0, modalPadding, 0)
        }
        Gravity.Bottom -> {
          contentParams.topToTop = LayoutParams.UNSET
          contentParams.bottomToBottom = LayoutParams.PARENT_ID
          setPadding(modalPadding, modalPadding, modalPadding, 0)
        }
      }

      content.requestLayout()
    }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    fullscreen = content.measuredHeight >= measuredHeight * heightPercentageToShowFullScreen
  }

  override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
    super.onLayout(changed, left, top, right, bottom)
    content.getHitRect(contentHitRect)
  }

  override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    if (fullscreen || contentHitRect.contains(ev.x.roundToInt(), ev.y.roundToInt())) {
      return super.dispatchTouchEvent(ev)
    }
    dismissListener()
    return false
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(ev: MotionEvent): Boolean {
    if (fullscreen || contentHitRect.contains(ev.x.roundToInt(), ev.y.roundToInt())) {
      return super.onTouchEvent(ev)
    }
    dismissListener()
    return false
  }

  private companion object {
    val modalBg = ColorDrawable(Color.TRANSPARENT)
    const val heightPercentageToShowFullScreen = 0.75f
  }
}
