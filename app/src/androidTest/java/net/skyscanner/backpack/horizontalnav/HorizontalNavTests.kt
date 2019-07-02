package net.skyscanner.backpack.horizontalnav

import android.graphics.Color
import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.tabs.TabLayout
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import net.skyscanner.backpack.horisontalnav.BpkHorizontalNav
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HorizontalNavTests : BpkSnapshotTest() {

  private val horizontalNav = BpkHorizontalNav(testContext).init()

  @Before
  fun setup() {
    setDimensions(56, 300)
  }

  @Test
  fun screenshotHorizontalNav_Default() {
    snap(horizontalNav)
  }

  @Test
  fun screenshotHorizontalNav_scrollable() {
    horizontalNav.tabMode = TabLayout.MODE_SCROLLABLE
    snap(horizontalNav)
  }

  @Test
  fun screenshotHorizontalNav_fixed() {
    horizontalNav.tabMode = TabLayout.MODE_FIXED
    snap(horizontalNav)
  }

  @Test
  fun screenshotHorizontalNav_scrollableRtl() {
    horizontalNav.layoutDirection = View.LAYOUT_DIRECTION_RTL
    horizontalNav.tabMode = TabLayout.MODE_SCROLLABLE
    snap(horizontalNav)
  }

  @Test
  fun screenshotHorizontalNav_fixedRtl() {
    horizontalNav.layoutDirection = View.LAYOUT_DIRECTION_RTL
    horizontalNav.tabMode = TabLayout.MODE_FIXED
    snap(horizontalNav)
  }

  @Test
  fun screenshotHorizontalNav_withTheme() {
    val horizontalNav = BpkHorizontalNav(createThemedContext(testContext)).init()
    snap(horizontalNav)
  }

  @Test
  fun screenshotHorizontalNav_alternate() {
    val horizontalNav = BpkHorizontalNav(testContext).init()
    horizontalNav.appearance = BpkHorizontalNav.Appearance.Alternate
    snap(horizontalNav)
  }

  private fun BpkHorizontalNav.init() = apply {
    setBackgroundColor(Color.WHITE)
    addTab(newTab().setText("1"))
    addTab(newTab().setText("2"))
    addTab(newTab().setText("3"))
  }
}
