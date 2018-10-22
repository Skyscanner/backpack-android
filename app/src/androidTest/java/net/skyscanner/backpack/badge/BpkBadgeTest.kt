package net.skyscanner.backpack.badge

import androidx.test.runner.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkBadgeTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(32, 96)
  }

  @Test
  fun screenshotTestBadgeDefault() {
    val badge = BpkBadge(testContext)
    badge.text = "Message"
    snap(badge)
  }

  @Test
  fun screenshotTestBadgeSuccess() {
    val badge = BpkBadge(testContext)
    badge.type = BpkBadge.Type.Success
    badge.text = "Message"
    snap(badge)
  }

  @Test
  fun screenshotTestBadgeDestructive() {
    val badge = BpkBadge(testContext)
    badge.type = BpkBadge.Type.Destructive
    badge.text = "Message"
    snap(badge)
  }

  @Test
  fun screenshotTestBadgeInverse() {
    val badge = BpkBadge(testContext)
    badge.type = BpkBadge.Type.Inverse
    badge.text = "Message"
    snap(badge)
  }
  @Test
  fun screenshotTestBadgeLight() {
    val badge = BpkBadge(testContext)
    badge.type = BpkBadge.Type.Light
    badge.text = "Message"
    snap(badge)
  }
  @Test
  fun screenshotTestBadgeWarning() {
    val badge = BpkBadge(testContext)
    badge.type = BpkBadge.Type.Warning
    badge.text = "Message"
    snap(badge)
  }
  @Test
  fun screenshotTestBadgeOutline() {
    val badge = BpkBadge(testContext)
    badge.type = BpkBadge.Type.Outline
    badge.text = "Message"
    snap(badge)
  }
}
