package net.skyscanner.backpack.button

import androidx.core.content.ContextCompat
import androidx.test.runner.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

/**
 * FIXME
 * 31/10/2018
 * see https://github.com/Skyscanner/backpack-android/pull/103
 *
 * The test cases with icons have been ignored. The snapshot test framework is not rendering
 * the icons when using `setCompoundDrawablesRelativeWithIntrinsicBounds` instead of
 * `setCompoundDrawablesWithIntrinsicBounds` on API-21 which is required to support RTL. We can add
 * them when we update the emulators on the CI or move to a device farm for snapshot testing
 */

class BpkButtonTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(40, 120)
  }

  @Test
  fun screenshotTestButtonBasicPrimary() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Primary
    button.text = "Message"
    snap(button)
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicPrimaryWithIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Primary
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(button)
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicPrimaryOnlyIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Primary
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.ICON_ONLY
    snap(button)
  }

  @Test
  fun screenshotTestButtonBasicSecondary() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Secondary
    button.text = "Message"
    snap(button)
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicSecondaryWithIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Secondary
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.END
    button.text = "Message"
    snap(button)
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicSecondaryOnlyIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Secondary
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.ICON_ONLY
    snap(button)
  }

  @Test
  fun screenshotTestButtonBasicFeatured() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Featured
    button.text = "Message"
    snap(button)
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicFeaturedWithIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Featured
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(button)
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicFeaturedOnlyIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Featured
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.ICON_ONLY
    snap(button)
  }

  @Test
  fun screenshotTestButtonBasicDestructive() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Destructive
    button.text = "Message"
    snap(button)
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicDestructiveWithIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Destructive
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(button)
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicDestructiveOnlyIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Destructive
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.ICON_ONLY
    snap(button)
  }

  @Test
  fun screenshotTestButtonBasicOutline() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Outline
    button.text = "Message"
    snap(button)
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicOutlineWithIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Outline
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(button)
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicOutlineOnlyIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Outline
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.ICON_ONLY
    snap(button)
  }
}
