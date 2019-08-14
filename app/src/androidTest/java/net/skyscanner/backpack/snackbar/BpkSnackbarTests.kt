package net.skyscanner.backpack.snackbar

import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.android.material.snackbar.Snackbar
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import net.skyscanner.backpack.demo.MainActivity
import net.skyscanner.backpack.util.unsafeLazy
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkSnackbarTests : BpkSnapshotTest() {

  private lateinit var activity: AppCompatActivity

  @get:Rule
  var activityRule: ActivityTestRule<MainActivity> =
    ActivityTestRule(MainActivity::class.java)

  @Before
  fun setUp() {
    setDimensions(400, 350)
    activity = activityRule.activity
  }

  private val root by unsafeLazy {
    FrameLayout(testContext).apply {
      activity.setContentView(this)
    }
  }

  private val themedRoot by unsafeLazy {
    FrameLayout(createThemedContext(testContext)).apply {
      activity.setContentView(this)
    }
  }

  @Test
  fun screenshotTestSnackbar_Default() {
    capture {
      BpkSnackbar.builder(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
        .build()
    }
  }

  @Test
  fun screenshotTestSnackbar_DefaultWithAction() {
    capture {
      BpkSnackbar.builder(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
        .setAction("Action") {}
        .build()
    }
  }

  @Test
  fun screenshotTestSnackbar_DefaultThemed() {
    capture {
      BpkSnackbar.builder(themedRoot, "Test", BpkSnackbar.LENGTH_INDEFINITE)
        .build()
    }
  }

  @Test
  fun screenshotTestSnackbar_DefaultWithActionThemed() {
    capture {
      BpkSnackbar.builder(themedRoot, "Test", BpkSnackbar.LENGTH_INDEFINITE)
        .setAction("Action") {}
        .build()
    }
  }

  private inline fun capture(crossinline what: AsyncSnapshot.() -> Snackbar) {
    val asyncScreenshot = prepareForAsyncTest()
    activity.runOnUiThread {
      what(asyncScreenshot)
        .addCallback(object : Snackbar.Callback() {
          override fun onShown(sb: Snackbar) {
            asyncScreenshot.record(sb.view)
          }
        })
        .show()
    }
  }
}
