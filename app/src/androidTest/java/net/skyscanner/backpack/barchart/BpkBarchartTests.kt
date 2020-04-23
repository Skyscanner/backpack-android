package net.skyscanner.backpack.barchart

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkBarchartTests : BpkSnapshotTest() {

  private lateinit var activity: AppCompatActivity

  @get:Rule
  var activityRule: ActivityTestRule<AppCompatActivity> =
    ActivityTestRule(AppCompatActivity::class.java)

  @Before
  fun setup() {
    activity = activityRule.activity
    setDimensions(400, 400)
  }

  @Test
  fun screenshotTestBarChart_Empty() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0, value = 0.0f)
        )
      )
    }
    capture()
  }

  @Test
  fun screenshotTestBarChart_Half() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0, value = 0.5f)
        )
      )
    }
    capture()
  }

  @Test
  fun screenshotTestBarChart_Full() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0, value = 1.0f)
        )
      )
    }
    capture()
  }

  @Test
  fun screenshotTestBarChart_Overfilled() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0, value = 1.1f)
        )
      )
    }
    capture()
  }

  @Test
  fun screenshotTestBarChart_Inactive() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0, inactive = true)
        )
      )
    }
    capture()
  }

  @Test
  fun screenshotTestBarChart_WithLegend() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0)
        ),
        legend = BpkBarChart.Legend(
          activeTitle = "Enabled",
          inactiveTitle = "Disabled"
        )
      )
    }
    capture()
  }

  @Test
  fun screenshotTestBarChart_GroupTitleChange() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0),
          createMonth(1)
        )
      )
    }
    // scrolling list with few swipes
    capture {
      perform(ViewActions.swipeLeft())
      perform(ViewActions.swipeLeft())
      perform(ViewActions.swipeLeft())
    }
  }

  @Test
  fun screenshotTestBarChart_WithBadge() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0)
        )
      )
    }
    capture {
      perform(ViewActions.click())
    }
  }

  @Test
  fun screenshotTestBarChart_WithoutBadge() {
    init {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0)
        )
      )
    }
    capture {
      perform(ViewActions.click())
      perform(ViewActions.click())
    }
  }

  @Test
  fun screenshotTestBarChart_Themed() {
    init(R.style.LondonTheme) {
      model = BpkBarChart.Model(
        groups = listOf(
          createMonth(0)
        ),
        legend = BpkBarChart.Legend(
          activeTitle = "Enabled",
          inactiveTitle = "Disabled"
        )
      )
    }
    capture()
  }

  private inline fun init(@StyleRes theme: Int = 0, crossinline block: BpkBarChart.() -> Unit) {
    activity.runOnUiThread {
      if (theme != 0) {
        activity.setTheme(theme)
      }
      activity.setContentView(R.layout.fragment_bar_chart)
      val barChart = activity.findViewById<BpkBarChart>(R.id.bar_chart)
      barChart.background = ColorDrawable(Color.WHITE)
      block(barChart)
    }
  }

  private inline fun capture(crossinline block: ViewInteraction.() -> ViewInteraction = { this }) {
    val asyncSnapshot = prepareForAsyncTest()
    onView(ViewMatchers.withId(R.id.bar_chart))
      .block()
      .check { v, _ ->
        asyncSnapshot.record(v)
      }
  }

  private fun createMonth(
    month: Int,
    badge: Int = 100,
    value: Float = 0.5f,
    inactive: Boolean = false
  ) = BpkBarChart.Group(
    title = arrayOf("January", "February", "March", "April", "May", "June", "July")[month % 6],
    items = ArrayList<BpkBarChart.Column>(10).apply {
      for (dayOfTheMonth in 0 until 30) {
        add(createBar(month * 30 + dayOfTheMonth,
          badge, value, inactive
        ))
      }
    }
  )

  private fun createBar(
    dayOfTheYear: Int,
    badge: Int = 100,
    value: Float = 0.5f,
    inactive: Boolean = false
  ) = BpkBarChart.Column(
    title = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")[dayOfTheYear % 7],
    subtitle = (dayOfTheYear % 30 + 1).toString(),
    badge = "£$badge",
    value = value,
    inactive = inactive
  )
}
