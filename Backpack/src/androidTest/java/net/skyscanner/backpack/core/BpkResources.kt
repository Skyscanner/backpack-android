package net.skyscanner.backpack.core

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import net.skyscanner.backpack.R

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BpkResources {

  @Test
  fun bpkColours() {
    val appContext = InstrumentationRegistry.getTargetContext()
   assertEquals("#ff00b2d6", appContext.getString(R.color.bpkBlue500))
   assertEquals("#ff00d775", appContext.getString(R.color.bpkGreen500))
   assertEquals("#ffff5452", appContext.getString(R.color.bpkRed500))
   assertEquals("#ffffbb00", appContext.getString(R.color.bpkYellow500))
   assertEquals("#fffa488a", appContext.getString(R.color.bpkPink500))
   assertEquals("#ff817b8f", appContext.getString(R.color.bpkGray500))
   assertEquals("#ffffffff", appContext.getString(R.color.bpkWhite))
  }

  @Test
  fun bpkSpacing() {
    val appContext = InstrumentationRegistry.getTargetContext()
    assertEquals("4.0dip", appContext.getString(R.dimen.bpkSpacingSm))
    assertEquals("8.0dip", appContext.getString(R.dimen.bpkSpacingMd))
    assertEquals("16.0dip", appContext.getString(R.dimen.bpkSpacingBase))
    assertEquals("24.0dip", appContext.getString(R.dimen.bpkSpacingLg))
    assertEquals("32.0dip", appContext.getString(R.dimen.bpkSpacingXl))
    assertEquals("40.0dip", appContext.getString(R.dimen.bpkSpacingXxl))
  }
  @Test
  fun bpkRadii() {
    val appContext = InstrumentationRegistry.getTargetContext()
    assertEquals("2.0dip", appContext.getString(R.dimen.bpkBorderRadiusSm))
    assertEquals("40.0dip", appContext.getString(R.dimen.bpkBorderRadiusPill))
  }

  @Test
  fun bpkElevation() {
    val appContext = InstrumentationRegistry.getTargetContext()
    assertEquals("2.0dip", appContext.getString(R.dimen.bpkElevationXs))
    assertEquals("4.0dip", appContext.getString(R.dimen.bpkElevationSm))
    assertEquals("6.0dip", appContext.getString(R.dimen.bpkElevationBase))
    assertEquals("16.0dip", appContext.getString(R.dimen.bpkElevationLg))
    assertEquals("24.0dip", appContext.getString(R.dimen.bpkElevationXl))
  }

  @Test
  fun bpkTextStyles() {
    val appContext = InstrumentationRegistry.getTargetContext()
    val toTest = arrayOf(R.style.bpkTextSm, R.style.bpkTextSmEmphasized)

    for (style in toTest) {
      val result = appContext.obtainStyledAttributes(R.style.bpkTextLg, R.styleable.BpkTextStyle)

      assertNotNull(result.getString(R.styleable.BpkTextStyle_android_fontFamily))
      assertNotNull(result.getString(R.styleable.BpkTextStyle_android_textSize))

      result.recycle()
    }
  }
}
