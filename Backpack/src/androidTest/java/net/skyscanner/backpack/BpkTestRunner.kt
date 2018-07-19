package net.skyscanner.backpack

import android.os.Bundle
import android.support.test.runner.AndroidJUnitRunner
import com.facebook.testing.screenshot.ScreenshotRunner

class BpkTestRunner : AndroidJUnitRunner() {

  override fun onCreate(args: Bundle?) {
    ScreenshotRunner.onCreate(this, args)
    super.onCreate(args)
  }

  override fun finish(resultCode: Int, results: Bundle) {
    ScreenshotRunner.onDestroy()
    super.finish(resultCode, results)
  }
}
