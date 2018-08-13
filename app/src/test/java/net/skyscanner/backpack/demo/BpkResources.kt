package net.skyscanner.backpack.demo

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class Bp {

  @Test
  fun bpkColours() {
    val appContext = RuntimeEnvironment.application.applicationContext
    assertEquals("#00b2d6", appContext.getString(R.color.bpkBlue500))
    assertEquals("#00d775", appContext.getString(R.color.bpkGreen500))
    assertEquals("#ff5452", appContext.getString(R.color.bpkRed500))
    assertEquals("#ffbb00", appContext.getString(R.color.bpkYellow500))
    assertEquals("#fa488a", appContext.getString(R.color.bpkPink500))
    assertEquals("#817b8f", appContext.getString(R.color.bpkGray500))
    assertEquals("#ffffff", appContext.getString(R.color.bpkWhite))
  }

  @Test
  fun bpkSpacing() {
    val appContext = RuntimeEnvironment.application.applicationContext
    assertEquals("4dp", appContext.getString(R.dimen.bpkSpacingSm));
    assertEquals("8dp", appContext.getString(R.dimen.bpkSpacingMd));
    assertEquals("16dp", appContext.getString(R.dimen.bpkSpacingBase));
    assertEquals("24dp", appContext.getString(R.dimen.bpkSpacingLg));
    assertEquals("32dp", appContext.getString(R.dimen.bpkSpacingXl));
    assertEquals("40dp", appContext.getString(R.dimen.bpkSpacingXxl));
  }

  @Test
  fun bpkRadii() {
    val appContext = RuntimeEnvironment.application.applicationContext
    assertEquals("2dp", appContext.getString(R.dimen.bpkBorderRadiusSm));
    assertEquals("40dp", appContext.getString(R.dimen.bpkBorderRadiusPill));
  }

  @Test
  fun bpkElevation() {
    val appContext = RuntimeEnvironment.application.applicationContext
    assertEquals("2dp", appContext.getString(R.dimen.bpkElevationXs));
    assertEquals("4dp", appContext.getString(R.dimen.bpkElevationSm));
    assertEquals("6dp", appContext.getString(R.dimen.bpkElevationBase));
    assertEquals("16dp", appContext.getString(R.dimen.bpkElevationLg));
    assertEquals("24dp", appContext.getString(R.dimen.bpkElevationXl));
  }

  @Test
  fun bpkTextStyles() {
    val appContext = RuntimeEnvironment.application.getApplicationContext()
    val toTest = arrayOf(R.style.bpkTextSm, R.style.bpkTextSmEmphasized)

    for (style in toTest) {
      val result = appContext.obtainStyledAttributes(R.style.bpkTextLg, R.styleable.BpkTextStyle)

      assertNotNull(result.getString(R.styleable.BpkTextStyle_android_fontFamily))
      assertNotNull(result.getString(R.styleable.BpkTextStyle_android_textSize))

      result.recycle()
    }
  }
}
