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

package net.skyscanner.backpack.starrating

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction
import android.view.accessibility.AccessibilityNodeInfo.RangeInfo
import android.widget.RatingBar
import android.widget.SeekBar
import kotlin.math.max
import kotlin.math.roundToInt
import net.skyscanner.backpack.R
import net.skyscanner.backpack.starrating.internal.BpkStarRatingBase
import net.skyscanner.backpack.util.createContextThemeWrapper

open class BpkInteractiveStarRating @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : BpkStarRatingBase(
  context = createContextThemeWrapper(context, attrs, R.attr.bpkInteractiveStarRatingStyle),
  attrs = attrs,
  defStyleAttr = defStyleAttr,
  empty = R.drawable.bpk_star_outline,
  half = R.drawable.bpk_star,
  full = R.drawable.bpk_star,
  starSize = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingXxl)
) {

  init {
    if (importantForAccessibility == IMPORTANT_FOR_ACCESSIBILITY_AUTO) {
      importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
    }
  }

  var onRatingChangedListener: ((Float, Float) -> Unit)? = null

  final override var rating: Float
    get() = super.rating
    set(value) {
      val newValue = value.roundToInt().toFloat()
      if (newValue != super.rating) {
        super.rating = newValue
        onRatingChangedListener?.invoke(newValue, maxRating.toFloat())
      }
    }

  private var lastRating: Float = rating

  override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    if (ev.action == MotionEvent.ACTION_DOWN) {
      lastRating = rating
    }

    val x = if (layoutDirection == View.LAYOUT_DIRECTION_RTL) width - ev.x else ev.x
    val itemWidth = width / maxRating
    val selectedItems = x / itemWidth
    super.rating = max(1f, selectedItems + 0.5f).roundToInt().toFloat()

    if (ev.action == MotionEvent.ACTION_UP) {
      onRatingChangedListener?.invoke(rating, maxRating.toFloat())
    }
    if (ev.action == MotionEvent.ACTION_CANCEL) {
      super.rating = lastRating
    }

    return true
  }

  override fun performAccessibilityAction(action: Int, arguments: Bundle?): Boolean {
    if (super.performAccessibilityAction(action, arguments)) {
      return true
    }

    when (action) {
      AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD -> {
        rating -= 1f
        updateStateDescription()
        return true
      }
      AccessibilityNodeInfo.ACTION_SCROLL_FORWARD -> {
        rating += 1f
        updateStateDescription()
        return true
      }
    }

    return false
  }

  override fun onInitializeAccessibilityEvent(event: AccessibilityEvent) {
    super.onInitializeAccessibilityEvent(event)
    event.itemCount = maxRating
    event.currentItemIndex = rating.toInt()
    if (event.eventType == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
      updateStateDescription()
    }
  }

  override fun getAccessibilityClassName(): CharSequence {
    return RatingBar::class.java.name
  }

  override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
    super.onInitializeAccessibilityNodeInfo(info)
    val rangeInfo = RangeInfo.obtain(
      RangeInfo.RANGE_TYPE_INT, 0f, maxRating.toFloat(), rating
    )
    info.rangeInfo = rangeInfo
    if (rating > 0) {
      info.addAction(AccessibilityAction.ACTION_SCROLL_BACKWARD)
    }
    if (rating < maxRating) {
      info.addAction(AccessibilityAction.ACTION_SCROLL_FORWARD)
    }
    info.className = SeekBar::class.java.name
    info.addAction(AccessibilityAction.ACTION_SET_PROGRESS)
  }

  private fun updateStateDescription() {
    val text = getAccessibilityText()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      stateDescription = text
    } else if (isAccessibilityFocused) {
      announceForAccessibility(text)
    }
  }

  interface OnRatingChangedListener : (Float, Float) -> Unit {
    override fun invoke(current: Float, max: Float)
  }
}
