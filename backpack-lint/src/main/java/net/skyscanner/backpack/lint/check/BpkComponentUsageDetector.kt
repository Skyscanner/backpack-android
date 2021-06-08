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
import com.android.tools.lint.detector.api.Context
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Location
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.android.tools.lint.detector.api.XmlContext
import com.android.tools.lint.detector.api.XmlScanner
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UClass
import org.w3c.dom.Element

@Suppress("UnstableApiUsage")
class BpkComponentUsageDetector : Detector(), SourceCodeScanner, XmlScanner {

  companion object {
    val ISSUE = Issue.create(
      id = "BpkComponentUsage",
      briefDescription = "A backpack version of this component is available",
      explanation = "Use backpack components where available to improve consistency, accessibility and dark mode support.",
      category = Category.CORRECTNESS,
      severity = Severity.WARNING,
      implementation = Implementation(
        BpkComponentUsageDetector::class.java,
        Scope.JAVA_AND_RESOURCE_FILES
      )
    )

    private const val BPK_BUTTON = "net.skyscanner.backpack.BpkButton"

    private val COMPONENTS = mapOf(
      // BpkButton
      "android.widget.Button" to BPK_BUTTON,
      "Button" to BPK_BUTTON,
      "androidx.appcompat.widget.AppCompatButton" to BPK_BUTTON,
    )

    private val APPLICABLE_TYPES = COMPONENTS.keys.toList()
  }

  private val classCache = mutableListOf<String>()

  override fun getApplicableConstructorTypes(): List<String> = APPLICABLE_TYPES

  override fun applicableSuperClasses(): List<String> = APPLICABLE_TYPES

  override fun getApplicableElements(): Collection<String> = APPLICABLE_TYPES

  override fun appliesTo(folderType: ResourceFolderType): Boolean = folderType == ResourceFolderType.LAYOUT

  override fun visitClass(context: JavaContext, declaration: UClass) {
    val superClass = declaration.javaPsi.superClass?.qualifiedName ?: return
    val component = COMPONENTS[superClass]
    if (component != null) {
      if (declaration.name == null || classCache.contains(declaration.name)) {
        // in some cases we might have two relevant super classes (e.g. when there's an AppCompat and default component) - only report issue once
        return
      }
      classCache.add(declaration.name!!)
      context.report(context.getNameLocation(declaration), superClass, component)
    }
  }

  override fun visitConstructor(context: JavaContext, node: UCallExpression, constructor: PsiMethod) {
    val className = node.returnType?.canonicalText ?: return
    val component = COMPONENTS[className]
    if (component != null) {
      context.report(context.getLocation(node), className, component)
    }
  }

  override fun visitElement(context: XmlContext, element: Element) {
    val component = COMPONENTS[element.tagName]
    if (component != null) {
      context.report(context.getLocation(element), element.tagName, component)
    }
  }

  private fun Context.report(location: Location, existingComponent: String, component: String) {
    report(ISSUE, location, "Backpack component available for $existingComponent. Use $component instead")
  }
}
