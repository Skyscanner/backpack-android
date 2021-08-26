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
import com.android.tools.lint.checks.infrastructure.TestLintTask
import org.junit.Test

@Suppress("UnstableApiUsage")
class HardcodedColorResourceDetectorTest {

  @Test
  fun `clean when color is using color resource`() {
    val layout = xml(
      "values/colors.xml",
      """<?xml version="1.0" encoding="utf-8"?>
<resources>
  <color name="background_color">@color/bpkBlack</color>
</resources>"""
    )
      .indented()
      .within("res")
    TestLintTask.lint().files(layout)
      .allowMissingSdk()
      .issues(HardcodedColorResourceDetector.ISSUE)
      .run()
      .expectClean()
  }

  @Test
  fun `clean when defining transparent black`() {
    val layout = xml(
      "values/colors.xml",
      """<?xml version="1.0" encoding="utf-8"?>
<resources>
  <color name="background_color">#18000000</color>
</resources>"""
    )
      .indented()
      .within("res")
    TestLintTask.lint().files(layout)
      .allowMissingSdk()
      .issues(HardcodedColorResourceDetector.ISSUE)
      .run()
      .expectClean()
  }

  @Test
  fun `clean when defining transparent white`() {
    val layout = xml(
      "values/colors.xml",
      """<?xml version="1.0" encoding="utf-8"?>
<resources>
  <color name="background_color">#18FFFFFF</color>
</resources>"""
    )
      .indented()
      .within("res")
    TestLintTask.lint().files(layout)
      .allowMissingSdk()
      .issues(HardcodedColorResourceDetector.ISSUE)
      .run()
      .expectClean()
  }

  @Test
  fun `warning when defining transparent color`() {
    val expected =
      """res/values/colors.xml:3: Warning: Use Backpack colors to improve consistency and dark mode support. [HardcodedColorResourceDetector]
  <color name="background_color">#18f1f2f8</color>
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
0 errors, 1 warnings"""

    val layout = xml(
      "values/colors.xml",
      """<?xml version="1.0" encoding="utf-8"?>
<resources>
  <color name="background_color">#18f1f2f8</color>
</resources>"""
    )
      .indented()
      .within("res")
    TestLintTask.lint().files(layout)
      .allowMissingSdk()
      .issues(HardcodedColorResourceDetector.ISSUE)
      .run()
      .expectWarningCount(1)
      .expect(expected)
  }

  @Test
  fun `warning when defining color`() {
    val expected =
      """res/values/colors.xml:3: Warning: Use Backpack colors to improve consistency and dark mode support. [HardcodedColorResourceDetector]
  <color name="background_color">#f1f2f8</color>
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
0 errors, 1 warnings"""

    val layout = xml(
      "values/colors.xml",
      """<?xml version="1.0" encoding="utf-8"?>
<resources>
  <color name="background_color">#f1f2f8</color>
</resources>"""
    )
      .indented()
      .within("res")
    TestLintTask.lint().files(layout)
      .allowMissingSdk()
      .issues(HardcodedColorResourceDetector.ISSUE)
      .run()
      .expectWarningCount(1)
      .expect(expected)
  }
}
