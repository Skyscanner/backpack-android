/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.bottomsheet

import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.karumi.shot.ActivityScenarioUtils.waitForActivity
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.SnapshotUtil.assumeVariant
import net.skyscanner.backpack.demo.R
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers.greaterThanOrEqualTo
import org.hamcrest.Matchers.lessThanOrEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkBottomSheetTest : BpkSnapshotTest() {

  @get:Rule
  val rule = activityScenarioRule<AppCompatActivity>()

  private lateinit var bottomSheetBehaviour: BpkBottomSheetBehaviour<BpkBottomSheet>

  @Before
  fun setup() {
    setDimensions(200, 200)
  }

  @Test
  fun default() {
    snap(setupBottomSheet())
  }

  @Test
  fun expanded() {
    assumeVariant(BpkTestVariant.Default)
    rule.scenario.waitForActivity().also { activity ->
      runOnUi {
        val root = setupBottomSheet()
        activity.setContentView(root)
        root.post {
          bottomSheetBehaviour.state = STATE_EXPANDED
        }
      }
    }
    val callback = Callback(bottomSheetBehaviour)
    IdlingRegistry.getInstance().register(callback)

    InstrumentationRegistry.getInstrumentation().waitForIdleSync()
    var bottomSheet: View? = null
    Espresso.onView(withId(TEST_ID)).check { view, _ ->
      bottomSheet = view
    }
    snap(bottomSheet!!)
    IdlingRegistry.getInstance().unregister(callback)
  }

  private fun setupBottomSheet(): View {
    val root = CoordinatorLayout(testContext).apply { id = TEST_ID }
    val frameLayout = FrameLayout(root.context)
    frameLayout.background = AppCompatResources.getDrawable(frameLayout.context, R.color.bpkCanvasContrast)
    frameLayout.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    root.addView(frameLayout)

    val bottomSheet = BpkBottomSheet(frameLayout.context)
    val bottomSheetParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    bottomSheetBehaviour = BpkBottomSheetBehaviour(root.context)
    bottomSheetBehaviour.peekHeight = bottomSheet.resources.getDimensionPixelSize(R.dimen.bpkSpacingXxl)
    bottomSheetParams.behavior = bottomSheetBehaviour
    bottomSheet.layoutParams = bottomSheetParams
    root.addView(bottomSheet)

    return root
  }

  class Callback(behavior: BottomSheetBehavior<*>) : BottomSheetCallback(), IdlingResource {
    private var isIdleNow: Boolean

    override fun isIdleNow(): Boolean {
      return isIdleNow
    }

    private var resourceCallback: ResourceCallback? = null

    init {
      behavior.addBottomSheetCallback(this)
      val state = behavior.state
      isIdleNow = isIdleState(state)
    }

    override fun onStateChanged(bottomSheet: View, @BottomSheetBehavior.State newState: Int) {
      val wasIdle = isIdleNow
      isIdleNow = isIdleState(newState)
      if (!wasIdle && isIdleNow && resourceCallback != null) {
        resourceCallback!!.onTransitionToIdle()
      }
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
      assertThat(slideOffset, `is`(greaterThanOrEqualTo(-1f)))
      assertThat(slideOffset, `is`(lessThanOrEqualTo(1f)))
    }

    override fun getName(): String {
      return Callback::class.java.simpleName
    }

    override fun registerIdleTransitionCallback(callback: ResourceCallback?) {
      resourceCallback = callback
    }

    private fun isIdleState(state: Int): Boolean {
      return (state != BottomSheetBehavior.STATE_DRAGGING && state != BottomSheetBehavior.STATE_SETTLING)
    }
  }
}

private val TEST_ID = 123
