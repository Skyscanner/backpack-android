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


import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.ResourceXmlDetector
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import com.android.tools.lint.detector.api.XmlScannerConstants
import org.w3c.dom.Attr

@Suppress("UnstableApiUsage")
class HardcodedColorUsageDetector : ResourceXmlDetector() {

  companion object {
    const val EXPLANATION = "Avoid using hardcoded colors. This may cause issues with theming and dark mode."

    val ISSUE = Issue.create(
      id = "HardcodedColorUsage",
      briefDescription = "Hardcoded color used",
      explanation = EXPLANATION,
      category = Category.CORRECTNESS,
      severity = Severity.WARNING,
      implementation = Implementation(
        HardcodedColorUsageDetector::class.java,
        Scope.RESOURCE_FILE_SCOPE
      )
    )
  }

  override fun appliesTo(folderType: ResourceFolderType): Boolean {
    return folderType == ResourceFolderType.LAYOUT
  }

  override fun getApplicableAttributes(): Collection<String> {
    return XmlScannerConstants.ALL
  }

  override fun visitAttribute(context: XmlContext, attribute: Attr) {
    if (attribute.value.startsWith("#")) {
      context.report(
        ISSUE,
        context.getValueLocation(attribute),
        EXPLANATION
      )
    }
  }
}
