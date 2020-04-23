package net.skyscanner.backpack.flare

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.text.BpkText
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkFlareTest : BpkSnapshotTest() {

  private val subject = BpkFlare(testContext).apply {
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
  fun screenshotTestFlareDefault() {
    snap(subject.apply {
      addView(imageView)
    })
  }

  @Test
  fun screenshotTestFlareRounded() {
    snap(subject.apply {
      addView(imageView)
      round = true
    })
  }

  @Test
  fun screenshotTestFlarePointerPositionStart() {
    snap(subject.apply {
      addView(imageView)
      pointerPosition = BpkFlare.PointerPosition.START
    })
  }

  @Test
  fun screenshotTestFlarePointerPositionEnd() {
    snap(subject.apply {
      addView(imageView)
      pointerPosition = BpkFlare.PointerPosition.END
    })
  }

  @Test
  fun screenshotTestFlarePointerPositionStart_RTL() {
    snap(subject.apply {
      pointerPosition = BpkFlare.PointerPosition.START
      layoutDirection = View.LAYOUT_DIRECTION_RTL
      addView(imageView)
    })
  }

  @Test
  fun screenshotTestFlarePointerPositionEnd_RTL() {
    snap(subject.apply {
      pointerPosition = BpkFlare.PointerPosition.END
      layoutDirection = View.LAYOUT_DIRECTION_RTL
      addView(imageView)
    })
  }

  @Test
  fun screenshotTestFlareInsetPaddingMode() {
    snap(
      setupViewForInsetPaddingTest(testContext) {
        subject.insetPaddingMode = BpkFlare.InsetPaddingMode.BOTTOM
      }
    )
  }

  @Test
  fun screenshotTestFlareInsetPaddingMode_withRelativePaddingRTL() {
    val wrapContent = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    snap(FrameLayout(testContext).apply {
      layoutDirection = View.LAYOUT_DIRECTION_RTL

      addView(subject.apply {
        layoutParams = wrapContent
        insetPaddingMode = BpkFlare.InsetPaddingMode.BOTTOM

        addView(LinearLayoutCompat(context).apply {
          layoutParams = wrapContent
          background = ColorDrawable(Color.LTGRAY)
          setPaddingRelative(0, 10, 40, 0)

          addView(BpkText(testContext).apply {
            layoutParams = wrapContent
            text = testContext.resources.getString(R.string.stub_sm)
          })
        })
      })
    })
  }

  @Test
  fun screenshotTestFlare_withPointerDirectionUP() {
    snap(subject.apply {
      addView(imageView)
      pointerDirection = BpkFlare.PointerDirection.UP
    })
  }

  @Test
  fun screenshotTestFlare_withPaddingModeTop_andPointerDirectionUP() {
    snap(
      setupViewForInsetPaddingTest(testContext) {
        subject.pointerDirection = BpkFlare.PointerDirection.UP
        subject.insetPaddingMode = BpkFlare.InsetPaddingMode.TOP
      }
    )
  }

  private fun setupViewForInsetPaddingTest(context: Context, configSubject: () -> Unit): View {
    val wrapContent = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT)

    return FrameLayout(testContext).apply {
      addView(subject.apply {
        layoutParams = wrapContent
        configSubject()

        addView(LinearLayoutCompat(context).apply {
          layoutParams = wrapContent
          background = ColorDrawable(Color.LTGRAY)

          addView(BpkText(testContext).apply {
            layoutParams = wrapContent
            text = testContext.resources.getString(R.string.stub_sm)
          })
        })
      })
    }
  }
}
