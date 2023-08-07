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

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

@Suppress("UnstableApiUsage")
class BpkComposeComponentUsageDetector : Detector(), SourceCodeScanner {

    companion object {
        val ISSUE = Issue.create(
            id = "BpkComposeComponentUsage",
            briefDescription = "A Backpack version of this component is available",
            explanation = "Use Backpack components where available to improve consistency, accessibility and dark mode support.",
            category = Category.CORRECTNESS,
            severity = Severity.WARNING,
            implementation = Implementation(
                BpkComposeComponentUsageDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        private val APPLICABLE_TYPES =
            Component.entries.flatMap { it.componentsToReplace.map { component -> component.substringAfterLast('.') } }
    }

    override fun getApplicableMethodNames(): List<String> {
        return APPLICABLE_TYPES
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val methodPackage = context.evaluator.getPackage(method)!!.qualifiedName
        val qualifiedName = "$methodPackage.${node.methodName}"
        val component = Component.find(qualifiedName)
        if (component != null) {
            context.report(
                ISSUE,
                context.getLocation(node),
                "Backpack component available for $qualifiedName. Use ${component.fullName} instead. More info at ${component.url}",
            )
        }
    }

    private enum class Component(
        val fullName: String,
        val webName: String,
        val componentsToReplace: Set<String>,
    ) {
        DIALOG(
            fullName = "net.skyscanner.backpack.compose.dialog.BpkDialog",
            webName = "dialog",
            componentsToReplace = setOf("androidx.compose.ui.window.Dialog"),
        ),
        TEXT_FIELD(
            fullName = "net.skyscanner.backpack.compose.textfield.BpkTextField",
            webName = "text-input",
            componentsToReplace = setOf(
                "androidx.compose.foundation.text.BasicTextField",
            ),
        ),
        ;

        val url: String = "https://skyscanner.design/latest/components/$webName/compose.html"

        private fun replacesComponent(componentToReplace: String) = componentsToReplace.contains(componentToReplace)

        companion object {
            internal fun find(componentToReplace: String) =
                values().firstOrNull { it.replacesComponent(componentToReplace) }
        }
    }
}
