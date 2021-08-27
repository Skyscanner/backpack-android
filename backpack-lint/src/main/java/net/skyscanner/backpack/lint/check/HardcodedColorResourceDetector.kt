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
import com.android.utils.text
import org.jetbrains.kotlin.util.capitalizeDecapitalize.toLowerCaseAsciiOnly
import org.w3c.dom.Element

@Suppress("UnstableApiUsage")
class HardcodedColorResourceDetector : ResourceXmlDetector() {

  companion object {

    private const val EXPLANATION = "Use Backpack colors to improve consistency and dark mode support."

    val ISSUE = Issue.create(
      id = "HardcodedColorResourceDetector",
      briefDescription = "Hardcoded color resource definition found",
      explanation = "Use Backpack colors to improve consistency and dark mode support.",
      category = Category.CORRECTNESS,
      severity = Severity.WARNING,
      implementation = Implementation(
        HardcodedColorResourceDetector::class.java,
        Scope.RESOURCE_FILE_SCOPE
      )
    )
  }

  override fun appliesTo(folderType: ResourceFolderType): Boolean {
    return folderType == ResourceFolderType.VALUES
  }

  override fun getApplicableElements(): Collection<String> {
    return listOf("color")
  }

  override fun visitElement(context: XmlContext, element: Element) {
    val value = element.text()
    if (value.startsWith("#") && !value.endsWith("000000") && !value.toLowerCaseAsciiOnly().endsWith("ffffff")) {
      /** check for colour resources specified directly - these will mostly be non-backpack colours.
       Some colours for black/white may exist for alpha
       **/
      context.report(
        ISSUE,
        context.getElementLocation(element),
        EXPLANATION
      )
    }
  }
}
