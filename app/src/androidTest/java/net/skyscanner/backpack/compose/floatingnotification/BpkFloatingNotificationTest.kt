package net.skyscanner.backpack.compose.floatingnotification

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Heart
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkFloatingNotificationTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(height = 52, width = 288)
  }

  @Test
  fun textOnly() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkFloatingNotification(
        text = "Lorem ipsum dolor sit amet",
        show = true
      )
    }
  }

  @Test
  fun withIcon() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkFloatingNotification(
        text = "Lorem ipsum dolor sit amet",
        icon = BpkIcon.Heart,
        show = true
      )
    }
  }

  @Test
  fun withCTA() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkFloatingNotification(
        text = "Lorem ipsum dolor sit amet",
        cta = CTA("Open", onClick = {}),
        show = true
      )
    }
  }

  @Test
  fun withIconAndCTA() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkFloatingNotification(
        text = "Lorem ipsum dolor sit amet",
        cta = CTA("Open", onClick = {}),
        icon = BpkIcon.Heart,
        show = true
      )
    }
  }
}
