package net.skyscanner.backpack.util

import android.content.Context
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResourcesUtilTest {

  private lateinit var context: Context

  @Before
  fun setUp() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
  }

  @Test
  fun test_getColor() {
    val expected = ResourcesCompat.getColor(context.resources, R.color.bpkSkyGrayTint07, context.theme)
    Assert.assertEquals(expected, ResourcesUtil.getColor(TextView(context), R.color.bpkSkyGrayTint07))
    Assert.assertEquals(expected, ResourcesUtil.getColor(context.resources, R.color.bpkSkyGrayTint07))
  }

  @Test
  fun test_getColor_extension() {
    val expected = ResourcesCompat.getColor(context.resources, R.color.bpkSkyGrayTint07, context.theme)
    Assert.assertEquals(expected, TextView(context).getColor(R.color.bpkSkyGrayTint07))
  }
}
