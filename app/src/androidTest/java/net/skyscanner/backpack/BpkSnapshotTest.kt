package net.skyscanner.backpack

import android.content.Context
import android.os.Looper
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.test.InstrumentationRegistry
import com.facebook.testing.screenshot.Screenshot
import com.facebook.testing.screenshot.ViewHelpers
import com.facebook.testing.screenshot.internal.TestNameDetector
import net.skyscanner.backpack.demo.R

open class BpkSnapshotTest {

  private var height = 100
  private var width = 100
  var testContext: Context = ContextThemeWrapper(
    InstrumentationRegistry.getTargetContext(),
    R.style.AppTheme)

  protected fun setupView(view: View) {
    ViewHelpers.setupView(view)
      .setExactHeightDp(height)
      .setExactWidthDp(width)
      .layout()
  }

  protected fun snap(view: View) {
    val wrapper = LinearLayout(testContext)
    val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
      LinearLayout.LayoutParams.WRAP_CONTENT)
    wrapper.gravity = Gravity.CENTER
    params.setMargins(10, 10, 10, 10)
    wrapper.setBackgroundColor(R.color.bpkGray300)
    wrapper.layoutParams = params
    wrapper.addView(view)

    setupView(wrapper)
    Screenshot.snap(wrapper).record()
  }

  protected fun setDimensions(height: Int, width: Int) {
    this.height = height
    this.width = width
  }

  protected fun prepareForAsyncTest(): AsyncSnapshot {
    if (Looper.myLooper() == null) {
      Looper.prepare()
    }

    val testClass = TestNameDetector.getTestClass()
    val testName = TestNameDetector.getTestName()
    return AsyncSnapshot(testClass, testName)
  }

  inner class AsyncSnapshot(private val tesClass: String, private val testName: String) {
    fun record(view: View) {
      Screenshot.snap(view)
        .setName("${tesClass}_$testName")
        .record()
    }
  }
}
