package net.skyscanner.backpack.bottomnav

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkBottomNavTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(56, 340)
  }

  @Test
  fun screenshotTestBottomNav_Default() {
    snap(BpkBottomNav(testContext).apply {
      addItem(1, R.string.bottom_nav_home, R.drawable.bpk_hotels)
      addItem(2, R.string.bottom_nav_explore, R.drawable.bpk_navigation)
      addItem(3, R.string.bottom_nav_trips, R.drawable.bpk_trips)
      addItem(4, R.string.bottom_nav_profile, R.drawable.bpk_account_circle)
    })
  }

  @Test
  fun screenshotTestBottomNav_Themed() {
    snap(BpkBottomNav(createThemedContext(testContext)).apply {
      addItem(1, R.string.bottom_nav_home, R.drawable.bpk_hotels)
      addItem(2, R.string.bottom_nav_explore, R.drawable.bpk_navigation)
      addItem(3, R.string.bottom_nav_trips, R.drawable.bpk_trips)
      addItem(4, R.string.bottom_nav_profile, R.drawable.bpk_account_circle)
    })
  }
}
