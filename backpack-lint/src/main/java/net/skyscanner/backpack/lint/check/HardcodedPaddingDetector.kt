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
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.PsiElement
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.ULiteralExpression
import org.jetbrains.uast.UQualifiedReferenceExpression
import org.jetbrains.uast.UReferenceExpression

/**
 * Detects hardcoded .dp values used in padding methods and suggests BpkSpacing tokens.
 */
@Suppress("UnstableApiUsage")
class HardcodedPaddingDetector : Detector(), SourceCodeScanner {

    companion object {
        private const val EXPLANATION =
            "Use BpkSpacing.* tokens instead of hardcoded .dp values for padding. " +
                "Hardcoding spacing bypasses the design system and creates inconsistent layouts.\n\n" +
                "Need support? Share your message in #backpack Slack channel: https://skyscanner.slack.com/archives/C0JHPDSSU"

        val ISSUE = Issue.create(
            id = "HardcodedPadding",
            briefDescription = "Hardcoded padding (.dp) detected",
            explanation = EXPLANATION,
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                HardcodedPaddingDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        private val PADDING_METHODS = setOf(
            "padding",
            "paddingStart",
            "paddingEnd",
            "paddingTop",
            "paddingBottom",
            "paddingHorizontal",
            "paddingVertical",
            "absolutePadding",
            "windowInsetsPadding",
            "safeContentPadding",
            "systemBarsPadding",
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
                if (value is Number && isInsidePaddingMethod(parent)) {
                    val intValue = value.toInt()
                    val message = getSuggestion(intValue)
                    val token = GeneratedSpacingTokenMap.SPACING_TOKEN_MAP[intValue]
                    val fix = if (token != null) {
                        LintFix.create()
                            .replace()
                            .text("$intValue.dp")
                            .with(token)
                            .autoFix()
                            .build()
                    } else {
                        null
                    }
                    context.report(ISSUE, context.getLocation(parent), message, fix)
                }
            }
        }
    }

    private fun isInsidePaddingMethod(dpExpression: UQualifiedReferenceExpression): Boolean {
        var current = dpExpression.uastParent
        var depth = 0
        val maxDepth = 10

        while (current != null && depth < maxDepth) {
            if (current is UCallExpression) {
                val methodName = current.methodName
                if (methodName in PADDING_METHODS) {
                    if (current.valueArguments.any { arg -> containsExpression(arg, dpExpression) }) {
                        return true
                    }
                }
            }
            current = current.uastParent
            depth++
        }
        return false
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

    private fun getSuggestion(value: Int): String {
        val token = GeneratedSpacingTokenMap.SPACING_TOKEN_MAP[value]
        return if (token != null) {
            "Use $token instead of $value.dp"
        } else {
            val available = GeneratedSpacingTokenMap.SPACING_TOKEN_MAP.entries
                .sortedBy { it.key }
                .joinToString("\n") { "• ${it.key}.dp = ${it.value}" }
            "Use BpkSpacing.* tokens instead of $value.dp. Available tokens:\n$available"
        }
    }
}
