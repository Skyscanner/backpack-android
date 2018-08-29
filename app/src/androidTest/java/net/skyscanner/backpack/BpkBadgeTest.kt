package net.skyscanner.backpack

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.facebook.testing.screenshot.Screenshot
import com.facebook.testing.screenshot.ViewHelpers
import net.skyscanner.backpack.badge.BpkBadge
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkBadgeTest {
  private lateinit var testContext: Context

  @Before
  fun setup() {
    testContext = InstrumentationRegistry.getTargetContext()
  }

  @Test
  fun screenshotTestBadgeDefault() {
    val badge = BpkBadge(testContext)
    badge.text = "Message"
    ViewHelpers.setupView(badge)
      .layout()
    Screenshot.snap(badge)
      .record()
  }

  @Test
  fun screenshotTestBadgeSuccess() {
    val badge = BpkBadge(testContext)
    badge.type = BpkBadge.Type.Success
    badge.text = "Message"
    ViewHelpers.setupView(badge)
      .layout()
    Screenshot.snap(badge)
      .record()
  }

  @Test
  fun screenshotTestBadgeDestructive() {
    val badge = BpkBadge(testContext)
    badge.type = BpkBadge.Type.Destructive
    badge.text = "Message"
    ViewHelpers.setupView(badge)
      .layout()
    Screenshot.snap(badge)
      .record()
  }

  @Test
  fun screenshotTestBadgeInverse() {
    val badge = BpkBadge(testContext)
    badge.type = BpkBadge.Type.Inverse
    badge.text = "Message"
    ViewHelpers.setupView(badge)
      .layout()
    Screenshot.snap(badge)
      .record()
  }
  @Test
  fun screenshotTestBadgeLight() {
    val badge = BpkBadge(testContext)
    badge.type = BpkBadge.Type.Light
    badge.text = "Message"
    ViewHelpers.setupView(badge)
      .layout()
    Screenshot.snap(badge)
      .record()
  }
  @Test
  fun screenshotTestBadgeWarning() {
    val badge = BpkBadge(testContext)
    badge.type = BpkBadge.Type.Warning
    badge.text = "Message"
    ViewHelpers.setupView(badge)
      .layout()
    Screenshot.snap(badge)
      .record()
  }
  @Test
  fun screenshotTestBadgeOutline() {
    val badge = BpkBadge(testContext)
    badge.type = BpkBadge.Type.Outline
    badge.text = "Message"
    ViewHelpers.setupView(badge)
      .layout()
    Screenshot.snap(badge)
      .record()
  }
}
