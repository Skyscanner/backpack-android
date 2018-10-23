package net.skyscanner.backpack.badge

import android.graphics.PixelFormat
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkBadgeTest {

  @Test
  fun test_message() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val badge = BpkBadge(context)
    badge.message = "error"
    badge.type = BpkBadge.Type.Destructive
    Assert.assertEquals("error", badge.text.toString())
  }

  @Test
  fun test_alpha_default() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val badge = BpkBadge(context)
    badge.type = BpkBadge.Type.Success
    Assert.assertEquals(PixelFormat.OPAQUE, badge.background.opacity)
  }

  @Test
  fun test_alpha_outline() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val badge = BpkBadge(context)
    badge.type = BpkBadge.Type.Outline
    Assert.assertEquals(PixelFormat.TRANSLUCENT, badge.background.opacity)
  }
}
