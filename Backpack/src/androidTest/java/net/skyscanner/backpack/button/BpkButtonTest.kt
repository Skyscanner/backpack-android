/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

package net.skyscanner.backpack.button

import android.app.Activity
import android.os.Looper
import androidx.appcompat.content.res.AppCompatResources
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import junit.framework.Assert.fail
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.TestActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkButtonTest {

  private lateinit var activity: Activity

  @get:Rule
  internal var activityRule: ActivityTestRule<TestActivity> =
    ActivityTestRule(TestActivity::class.java)

  @Before
  fun setUp() {
    // Required otherwise some tests fail with
    // android.util.AndroidRuntimeException: Animators may only be run on Looper threads
    if (Looper.myLooper() == null) {
      Looper.prepare()
    }

    activity = activityRule.activity
  }

  @Test
  fun test_message() {
    val button = BpkButton(activity).apply {
      text = "Message"
    }
    Assert.assertEquals("Message", button.text.toString())
    fail()
  }

  // The drawables are set as start,top,end,bottom and are accessible in the compoundDrawables array
  @Test
  fun test_icon_end() {
    val trainIcon = AppCompatResources.getDrawable(activity, R.drawable.bpk_train)
    val button = BpkButton(activity).apply {
      icon = trainIcon
      iconPosition = BpkButton.END
    }

    Assert.assertNotNull(button.compoundDrawablesRelative[2])
  }

  @Test
  fun test_icon_start() {
    val trainIcon = AppCompatResources.getDrawable(activity, R.drawable.bpk_train)
    val button = BpkButton(activity).apply {
      icon = trainIcon
      iconPosition = BpkButton.START
    }
    Assert.assertNotNull(button.compoundDrawablesRelative[0])
  }
}
