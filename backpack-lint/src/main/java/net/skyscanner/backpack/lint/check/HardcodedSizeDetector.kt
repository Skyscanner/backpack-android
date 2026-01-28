/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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
import com.intellij.psi.PsiElement
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.ULiteralExpression
import org.jetbrains.uast.UQualifiedReferenceExpression
import org.jetbrains.uast.UReferenceExpression

/**
 * Detects hardcoded .dp values used in size/width/height methods and suggests extracting to named constants.
 */
@Suppress("UnstableApiUsage")
class HardcodedSizeDetector : Detector(), SourceCodeScanner {

    companion object {
        private const val EXPLANATION =
            "Avoid hardcoded .dp values for dimensions. Extract to a named constant for better maintainability.\n\n" +
                "Example:\n" +
                "private val ImageHeight = 200.dp\n\n" +
                "// Then use:\n" +
                "Modifier.height(ImageHeight)"

        val ISSUE = Issue.create(
            id = "HardcodedSize",
            briefDescription = "Hardcoded size (.dp) detected",
            explanation = EXPLANATION,
            category = Category.CORRECTNESS,
            severity = Severity.WARNING,
            implementation = Implementation(
                HardcodedSizeDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        private val SIZE_METHODS = setOf(
            "size",
            "width",
            "height",
            "offset",
            "absoluteOffset",
            "requiredSize",
            "requiredWidth",
            "requiredHeight",
            "defaultMinSize",
            "sizeIn",
            "widthIn",
            "heightIn",
        )
    }

    override fun getApplicableReferenceNames(): List<String> = listOf("dp")

    override fun visitReference(
        context: JavaContext,
        reference: UReferenceExpression,
        referenced: PsiElement,
    ) {
        val parent = reference.uastParent
        if (parent is UQualifiedReferenceExpression) {
            val receiver = parent.receiver
            if (receiver is ULiteralExpression) {
                val value = receiver.value
                if (value is Number) {
                    val methodName = findSizeMethodName(parent)
                    if (methodName != null) {
                        val intValue = value.toInt()
                        val message = getSuggestion(intValue, methodName)
                        context.report(ISSUE, context.getLocation(parent), message)
                    }
                }
            }
        }
    }

    private fun findSizeMethodName(dpExpression: UQualifiedReferenceExpression): String? {
        var current = dpExpression.uastParent
        var depth = 0
        val maxDepth = 10

        while (current != null && depth < maxDepth) {
            if (current is UCallExpression) {
                val methodName = current.methodName
                if (methodName in SIZE_METHODS) {
                    if (current.valueArguments.any { arg -> containsExpression(arg, dpExpression) }) {
                        return methodName
                    }
                }
            }
            current = current.uastParent
            depth++
        }
        return null
    }

    private fun containsExpression(
        parent: org.jetbrains.uast.UElement,
        target: org.jetbrains.uast.UElement,
    ): Boolean {
        if (parent == target) return true
        var current: org.jetbrains.uast.UElement? = target
        while (current != null) {
            if (current == parent) return true
            current = current.uastParent
            if (current is UCallExpression && current != parent) break
        }
        return false
    }

    private fun getSuggestion(value: Int, methodName: String): String {
        val constantName = when (methodName) {
            "width", "requiredWidth", "widthIn" -> "ItemWidth"
            "height", "requiredHeight", "heightIn" -> "ItemHeight"
            else -> "ItemSize"
        }
        return "Extract hardcoded size to a named constant.\n\n" +
            "Example:\n" +
            "private val $constantName = $value.dp\n\n" +
            "// Then use:\n" +
            "Modifier.$methodName($constantName)"
    }
}
