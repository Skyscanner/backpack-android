package net.skyscanner.backpack.compose

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import net.skyscanner.backpack.BpkComposeTest
import net.skyscanner.backpack.demo.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkTextTest : BpkComposeTest() {

  private lateinit var activity: AppCompatActivity

  @get:Rule
  var activityRule: ActivityTestRule<MainActivity> =
    ActivityTestRule(MainActivity::class.java)

  @Test
  fun default() = composable(activity) {
    Text(text = "Sample")
  }
}
