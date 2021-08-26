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

package net.skyscanner.backpack.lint.check

import com.android.tools.lint.checks.infrastructure.TestFiles.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

@Suppress("UnstableApiUsage")
class HardcodedColorUsageDetectorTest {

  @Test
  fun `clean when layout is using color attribute`() {
    val layout = xml(
      "layout/color_layout.xml",
      """<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
  android:background="?colorPrimary"
  android:layout_width="match_parent"
  android:layout_height="match_parent" />"""
    )
      .indented()
      .within("res")
    lint().files(layout)
      .allowMissingSdk()
      .issues(HardcodedColorUsageDetector.ISSUE)
      .run()
      .expectClean()
  }

  @Test
  fun `clean when layout is using color resource`() {
    val layout = xml(
      "layout/color_layout.xml",
      """<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
  android:background="@color/bpk_black"
  android:layout_width="match_parent"
  android:layout_height="match_parent" />"""
    )
      .indented()
      .within("res")
    lint().files(layout)
      .allowMissingSdk()
      .issues(HardcodedColorUsageDetector.ISSUE)
      .run()
      .expectClean()
  }

  @Test
  fun `warning when layout has a hardcoded color`() {
    val expected =
      """res/layout/color_layout.xml:3: Warning: Avoid using hardcoded colors. This may cause issues with theming and dark mode. [HardcodedColorUsage]
  android:background="#fff"
                      ~~~~
0 errors, 1 warnings"""

    val layout = xml(
      "layout/color_layout.xml",
      """<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
  android:background="#fff"
  android:layout_width="match_parent"
  android:layout_height="match_parent" />"""
    )
      .indented()
      .within("res")

    lint().files(layout)
      .allowMissingSdk()
      .issues(HardcodedColorUsageDetector.ISSUE)
      .run()
      .expectWarningCount(1)
      .expect(expected)
  }
}
