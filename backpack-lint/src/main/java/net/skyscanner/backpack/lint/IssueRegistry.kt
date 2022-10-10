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

package net.skyscanner.backpack.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import net.skyscanner.backpack.lint.check.BpkComponentUsageDetector
import net.skyscanner.backpack.lint.check.BpkComposeComponentUsageDetector
import net.skyscanner.backpack.lint.check.BpkDeprecatedColorUsageDetector
import net.skyscanner.backpack.lint.check.HardcodedColorResourceDetector
import net.skyscanner.backpack.lint.check.HardcodedColorUsageDetector

@Suppress("unused", "UnstableApiUsage")
class IssueRegistry : IssueRegistry() {

  override val api: Int = CURRENT_API

  override val issues: List<Issue> = listOf(
    BpkComposeComponentUsageDetector.ISSUE,
    BpkComponentUsageDetector.ISSUE,
    HardcodedColorUsageDetector.ISSUE,
    HardcodedColorResourceDetector.ISSUE,
    BpkDeprecatedColorUsageDetector.ISSUE,
  )

  override val vendor: Vendor = Vendor(vendorName = "Skyscanner", identifier = "backpack-android")
}
