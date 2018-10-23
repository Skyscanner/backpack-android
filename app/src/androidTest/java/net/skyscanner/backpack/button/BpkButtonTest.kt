package net.skyscanner.backpack.button

import androidx.core.content.ContextCompat
import androidx.test.runner.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

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
  fun screenshotTestButtonBasicPrimaryWithIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Primary
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(button)
  }

  @Test
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
  fun screenshotTestButtonBasicSecondaryWithIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Secondary
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.END
    button.text = "Message"
    snap(button)
  }

  @Test
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
  fun screenshotTestButtonBasicFeaturedWithIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Featured
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(button)
  }

  @Test
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
  fun screenshotTestButtonBasicDestructiveWithIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Destructive
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.START
    button.text = "Message"
    snap(button)
  }

  @Test
  fun screenshotTestButtonBasicDestructiveOnlyIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Destructive
    button.icon = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.iconPosition = BpkButton.ICON_ONLY
    snap(button)
  }
}
