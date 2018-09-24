package net.skyscanner.backpack.button

import android.support.test.runner.AndroidJUnit4
import android.support.v4.content.ContextCompat
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkButtonTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(40,120)
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
    button.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.text = "Message"
    snap(button)
  }

  @Test
  fun screenshotTestButtonBasicPrimaryOnlyIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Primary
    button.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
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
    button.iconEnd = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.text = "Message"
    snap(button)
  }

  @Test
  fun screenshotTestButtonBasicSecondaryOnlyIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Secondary
    button.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
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
    button.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.text = "Message"
    snap(button)
  }

  @Test
  fun screenshotTestButtonBasicFeaturedOnlyIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Featured
    button.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
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
    button.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.text = "Message"
    snap(button)
  }

  @Test
  fun screenshotTestButtonBasicDestructiveOnlyIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Destructive
    button.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    snap(button)
  }

  @Test
  fun screenshotTestButtonLargePrimary() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Primary
    button.text = "Message"
    snap(button)
  }

  @Test
  fun screenshotTestButtonLargePrimaryWithIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Primary
    button.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.text = "Message"
    snap(button)
  }

  @Test
  fun screenshotTestButtonLargePrimaryOnlyIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Primary
    button.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    snap(button)
  }

  @Test
  fun screenshotTestButtonLargeSecondary() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Secondary
    button.text = "Message"
    snap(button)
  }

  @Test
  fun screenshotTestButtonLargeSecondaryWithIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Secondary
    button.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.text = "Message"
    snap(button)
  }

  @Test
  fun screenshotTestButtonLargeSecondaryOnlyIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Secondary
    button.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    snap(button)
  }

  @Test
  fun screenshotTestButtonLargeFeatured() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Featured
    button.text = "Message"
    snap(button)
  }


  @Test
  fun screenshotTestButtonLargeFeaturedWithIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Featured
    button.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.text = "Message"
    snap(button)
  }

  @Test
  fun screenshotTestButtonLargeFeaturedOnlyIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Featured
    button.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    snap(button)
  }

  @Test
  fun screenshotTestButtonLargeDestructive() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Destructive
    button.text = "Message"
    snap(button)
  }


  @Test
  fun screenshotTestButtonLargeDestructiveWithIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Destructive
    button.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    button.text = "Message"
    snap(button)
  }

  @Test
  fun screenshotTestButtonLargeDestructiveOnlyIcon() {
    val button = BpkButton(testContext)
    button.type = BpkButton.Type.Destructive
    button.iconStart = ContextCompat.getDrawable(testContext, R.drawable.bpk_tick)
    snap(button)
  }
}
