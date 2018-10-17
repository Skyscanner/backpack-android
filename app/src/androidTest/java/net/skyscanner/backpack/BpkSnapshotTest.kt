package net.skyscanner.backpack

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.view.ContextThemeWrapper
import android.view.View
import com.facebook.testing.screenshot.Screenshot
import com.facebook.testing.screenshot.ViewHelpers
import net.skyscanner.backpack.demo.R

open class BpkSnapshotTest {

  private var height = 100
  private var width = 100
  var testContext: Context = ContextThemeWrapper(
    InstrumentationRegistry.getTargetContext(),
    R.style.AppTheme)

  internal fun snap(view: View) {
    ViewHelpers.setupView(view)
      .setExactHeightDp(height)
      .setExactWidthDp(width)
      .layout()
    Screenshot.snap(view)
      .record()
  }

  internal fun setDimensions(height: Int, width: Int) {
    this.height = height
    this.width = width
  }
}
