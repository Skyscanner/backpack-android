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

package net.skyscanner.backpack.lint.check

import com.android.tools.lint.checks.infrastructure.TestFile
import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintResult
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

@Suppress("UnstableApiUsage")
class BpkComposeComponentUsageDetectorTest {

  @Test
  fun `warning when using component to replace`() {
    lint()
      .files(
        kotlin(
          """import androidx.compose.material.Button

@Composable
fun CustomButton() { Button() }"""
        ),
        button(),
      )
      .runCheck()
      .expectWarningCount(1)
      .expect(
        """
src/test.kt:4: Warning: Backpack component available for androidx.compose.material.Button. Use net.skyscanner.backpack.compose.button.BpkButton instead. More info at https://backpack.github.io/components/button [BpkComposeComponentUsage]
fun CustomButton() { Button() }
                     ~~~~~~~~
0 errors, 1 warnings
      """
      )
  }

  @Test
  fun `clean when using backpack component`() {
    lint()
      .files(
        kotlin(
          """import net.skyscanner.backpack.compose.button.BpkButton

@Composable
fun CustomButton() { BpkButton() }"""
        ),
        bpkButton(),
      )
      .runCheck()
      .expectClean()
  }

  private fun bpkButton(): TestFile =
    kotlin(
      """package net.skyscanner.backpack.compose.button

@Composable
fun BpkButton() {}"""
    )

  private fun button(): TestFile =
    kotlin(
      """package androidx.compose.material

@Composable
fun Button() {}"""
    )

  private fun TestLintTask.runCheck(): TestLintResult =
    issues(BpkComposeComponentUsageDetector.ISSUE)
      .allowMissingSdk()
      .run()
}
