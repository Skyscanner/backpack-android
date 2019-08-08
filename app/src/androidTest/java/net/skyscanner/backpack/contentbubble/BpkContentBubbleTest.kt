package net.skyscanner.backpack.contentbubble

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.text.BpkText
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkContentBubbleTest : BpkSnapshotTest() {

  private class CustomContentBubble(context: Context) : BpkContentBubble(
    context,
    null,
    0,
    ContextCompat.getDrawable(context, R.drawable.bpk_arrow_down)!!,
    ContextCompat.getDrawable(context, R.drawable.bpk_add_circle)!!)

  private val subject = BpkContentBubble(testContext).apply {
    layoutParams = ViewGroup.LayoutParams(300, 100)
  }

  private val imageView = ImageView(testContext).apply {
    setImageDrawable(ContextCompat.getDrawable(context, R.drawable.canadian_rockies_canada))
    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    scaleType = ImageView.ScaleType.CENTER_CROP
  }

  @Before
  fun setup() {
    setDimensions(100, 300)
  }

  @Test
  fun screenshotTestContentBubbleDefault() {
    snap(subject.apply {
      addView(imageView)
    })
  }

  @Test
  fun screenshotTestContentBubbleRounded() {
    snap(subject.apply {
      addView(imageView)
      round = true
    })
  }

  @Test
  fun screenshotTestContentBubblePointerPositionStart() {
    snap(subject.apply {
      addView(imageView)
      pointerPosition = BpkContentBubble.PointerPosition.START
    })
  }

  @Test
  fun screenshotTestContentBubblePointerPositionEnd() {
    snap(subject.apply {
      addView(imageView)
      pointerPosition = BpkContentBubble.PointerPosition.END
    })
  }

  @Test
  fun screenshotTestContentBubblePointerPositionStart_RTL() {
    snap(subject.apply {
      pointerPosition = BpkContentBubble.PointerPosition.START
      layoutDirection = View.LAYOUT_DIRECTION_RTL
      addView(imageView)
    })
  }

  @Test
  fun screenshotTestContentBubblePointerPositionEnd_RTL() {
    snap(subject.apply {
      pointerPosition = BpkContentBubble.PointerPosition.END
      layoutDirection = View.LAYOUT_DIRECTION_RTL
      addView(imageView)
    })
  }

  @Test
  fun screenshotTestContentBubbleFitContent() {
    snap(FrameLayout(testContext).apply {
      addView(subject.apply {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        fitContent = true
        addView(BpkText(testContext).apply {
          text = testContext.resources.getString(R.string.stub_sm)
          background = ColorDrawable(Color.LTGRAY)
        })
      })
    })
  }

  @Test
  fun screenshotTestContentBubble_withCustomDrawables() {
    snap(CustomContentBubble(testContext).apply {
      layoutParams = ViewGroup.LayoutParams(300, 100)
      round = true
      addView(imageView)
    })
  }
}
