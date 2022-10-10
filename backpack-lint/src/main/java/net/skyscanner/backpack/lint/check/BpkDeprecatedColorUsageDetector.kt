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

import com.android.resources.ResourceFolderType
import com.android.resources.ResourceType
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.android.tools.lint.detector.api.XmlContext
import com.android.tools.lint.detector.api.XmlScanner
import com.android.tools.lint.detector.api.XmlScannerConstants
import org.jetbrains.uast.UElement
import org.w3c.dom.Attr
import org.w3c.dom.Element

@Suppress("UnstableApiUsage")
class BpkDeprecatedColorUsageDetector : Detector(), SourceCodeScanner, XmlScanner {

  companion object {
    private const val EXPLANATION =
      "This colour is now deprecated. Please switch to the new semantic colours - see internal New Colours documentation."

    val ISSUE = Issue.create(
      id = "BpkDeprecatedColorUsage",
      briefDescription = "Deprecated color used",
      explanation = EXPLANATION,
      category = Category.CORRECTNESS,
      severity = Severity.WARNING,
      implementation = Implementation(
        BpkDeprecatedColorUsageDetector::class.java,
        Scope.JAVA_AND_RESOURCE_FILES
      )
    )

    private const val COLOR_RES_PREFIX = "@color/"
  }

  override fun appliesTo(folderType: ResourceFolderType): Boolean {
    return folderType == ResourceFolderType.LAYOUT ||
      folderType == ResourceFolderType.COLOR ||
      folderType == ResourceFolderType.VALUES
  }

  override fun getApplicableAttributes(): Collection<String> = XmlScannerConstants.ALL

  override fun visitAttribute(context: XmlContext, attribute: Attr) {
    if (attribute.value.isColor() && BpkDeprecatedTokens.deprecatedColors.contains(attribute.value.trimPrefix())) {
      context.report(
        ISSUE,
        context.getValueLocation(attribute),
        EXPLANATION
      )
    }
  }

  override fun getApplicableElements(): Collection<String> = listOf("color")

  override fun visitElement(context: XmlContext, element: Element) {
    if (element.textContent.isColor() && BpkDeprecatedTokens.deprecatedColors.contains(element.textContent.trimPrefix())) {
      context.report(
        ISSUE,
        context.getElementLocation(element),
        EXPLANATION
      )
    }
  }

  override fun appliesToResourceRefs(): Boolean = true

  override fun visitResourceReference(
    context: JavaContext,
    node: UElement,
    type: ResourceType,
    name: String,
    isFramework: Boolean
  ) {
    if (BpkDeprecatedTokens.deprecatedColors.contains(name)) {
      context.report(
        ISSUE,
        context.getLocation(node),
        EXPLANATION
      )
    }
  }

  private fun String.isColor() = startsWith(COLOR_RES_PREFIX)
  private fun String.trimPrefix() = removePrefix(COLOR_RES_PREFIX)
}
