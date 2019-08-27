package net.skyscanner.backpack.demo.components

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

class LockableNestedScrollView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

  var scrollingEnabled = true

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(ev: MotionEvent): Boolean {
    return scrollingEnabled && super.onTouchEvent(ev)
  }

  override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
    return scrollingEnabled && super.onInterceptTouchEvent(ev)
  }
}
