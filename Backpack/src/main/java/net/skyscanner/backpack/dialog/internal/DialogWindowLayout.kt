package net.skyscanner.backpack.dialog.internal

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.unsafeLazy
import kotlin.math.roundToInt

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
      background = if (value) fullscreenBg else modalBg
    }

  private val modalPadding = resources.getDimensionPixelSize(R.dimen.bpk_dialog_margin)

  var dismissListener: () -> Unit = {
  }

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
    fullscreen = content.measuredHeight >= measuredHeight * fullscreenDefinition
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
    val fullscreenBg = ColorDrawable(Color.WHITE)
    const val fullscreenDefinition = 0.75f
  }
}
