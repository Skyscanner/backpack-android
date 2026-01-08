/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

package net.skyscanner.backpack.configuration

import android.view.View
import android.widget.FrameLayout
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.util.runIfCurrentlyVisible
import net.skyscanner.backpack.util.runOnFirstVisible
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class ViewUtilsTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(EmptyActivity::class.java)

    @Test
    fun testInvisibleViewNeverHitsCallback() {
        var callbackExecuted = false
        var result = false

        activityRule.scenario.onActivity { activity ->
            val testView = FrameLayout(activity).apply {
                visibility = View.INVISIBLE
            }
            activity.root.addView(testView)

            result = testView.runIfCurrentlyVisible {
                callbackExecuted = true
            }
        }

        assertFalse("Callback should not execute for invisible view", result)
        assertFalse("Callback should not be executed", callbackExecuted)
    }

    @Test
    fun testVisibleViewImmediatelyHitsCallback() {
        var callbackExecuted = false
        var result = false

        activityRule.scenario.onActivity { activity ->
            val testView = FrameLayout(activity).apply {
                visibility = View.VISIBLE
                layoutParams = FrameLayout.LayoutParams(100, 100)
            }
            activity.root.addView(testView)

            // Force layout to ensure view is properly measured and positioned
            activity.root.requestLayout()
            activity.root.post {
                result = testView.runIfCurrentlyVisible {
                    callbackExecuted = true
                }
            }
        }

        // Wait a bit to ensure the post operation completes
        Thread.sleep(200)

        assertTrue("Callback should execute for visible view", result)
        assertTrue("Callback should be executed", callbackExecuted)
    }

    @Test
    fun testGoneThenVisibleViewHitsCallbackOnceVisible() {
        val latch = CountDownLatch(1)
        var callbackExecuted = false

        activityRule.scenario.onActivity { activity ->
            val testView = FrameLayout(activity).apply {
                visibility = View.GONE
                layoutParams = FrameLayout.LayoutParams(100, 100)
            }
            activity.root.addView(testView)

            // Set up the callback to execute when view becomes visible
            testView.runOnFirstVisible {
                callbackExecuted = true
                latch.countDown()
            }

            // Initially, callback should not have executed
            assertFalse("Callback should not execute initially for gone view", callbackExecuted)

            // Make the view visible
            activity.runOnUiThread {
                testView.visibility = View.VISIBLE
            }
        }

        // Wait for the callback to execute
        val callbackTriggered = latch.await(2, TimeUnit.SECONDS)
        assertTrue("Callback should be triggered within timeout", callbackTriggered)
        assertTrue("Callback should be executed after view becomes visible", callbackExecuted)
    }
}
