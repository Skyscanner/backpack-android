package net.skyscanner.backpack.button

import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
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
    setDimensions(300, 300)
  }

  @Test
  fun screenshotTestButtonBasicPrimary() {
    val button = BpkButton(testContext, BpkButton.Type.Primary)
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicPrimaryWithIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Primary)
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicPrimaryOnlyIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Primary)
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.ICON_ONLY
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicSecondary() {
    val button = BpkButton(testContext, BpkButton.Type.Secondary)
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicSecondaryWithIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Secondary)
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.END
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicSecondaryOnlyIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Secondary)
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.ICON_ONLY
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicFeatured() {
    val button = BpkButton(testContext, BpkButton.Type.Featured)
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicFeaturedWithIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Featured)
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicFeaturedOnlyIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Featured)
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.ICON_ONLY
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicDestructive() {
    val button = BpkButton(testContext, BpkButton.Type.Destructive)
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicDestructiveWithIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Destructive)
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicDestructiveOnlyIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Destructive)
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.ICON_ONLY
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicOutline() {
    val button = BpkButton(testContext, BpkButton.Type.Outline)
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicOutlineWithIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Outline)
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  @Ignore
  fun screenshotTestButtonBasicOutlineOnlyIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Outline)
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.ICON_ONLY
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicPrimary_withTheme() {
    val button = BpkButton(createThemedContext(testContext), BpkButton.Type.Primary)
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicSecondary_withTheme() {
    val button = BpkButton(createThemedContext(testContext), BpkButton.Type.Secondary)
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicFeatured_withTheme() {
    val button = BpkButton(createThemedContext(testContext), BpkButton.Type.Featured)
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  fun screenshotTestButtonBasicDestructive_withTheme() {
    val button = BpkButton(createThemedContext(testContext), BpkButton.Type.Destructive)
    button.text = "Message"
    snap(wrap(button))
  }

  @Test
  @Ignore
  fun screenshotTestButtonLargeWithIcon() {
    val button = BpkButton(testContext, BpkButton.Type.Primary)
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(wrap(button, 500))
  }

  @Test
  @Ignore
  fun screenshotTestButtonLargeWithIconTrailing() {
    val button = BpkButton(testContext, BpkButton.Type.Primary)
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.END
    button.text = "Message"
    snap(wrap(button, 500))
  }

  private fun wrap(button: BpkButton, with: Int = FrameLayout.LayoutParams.WRAP_CONTENT): FrameLayout {
    return FrameLayout(testContext).apply {
      button.layoutParams = FrameLayout.LayoutParams(with, FrameLayout.LayoutParams.WRAP_CONTENT)
      addView(button)
    }
  }
}
