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

@Suppress("UnstableApiUsage")
class HardcodedSpacingDetector : Detector(), SourceCodeScanner {

    companion object {
        private const val SPACING_EXPLANATION =
            "Use BpkSpacing.* tokens instead of hardcoded .dp values. Hardcoding spacing bypasses the design system and creates inconsistent layouts.\n\nNeed support? Share your message in #backpack Slack channel: https://skyscanner.slack.com/archives/C0JHPDSSU"

        private const val BORDER_RADIUS_EXPLANATION =
            "Use BpkBorderRadius.* tokens instead of hardcoded .dp values. Hardcoding border radius bypasses the design system and creates inconsistent shapes.\n\nNeed support? Share your message in #backpack Slack channel: https://skyscanner.slack.com/archives/C0JHPDSSU"

        val ISSUE = Issue.create(
            id = "HardcodedSpacing",
            briefDescription = "Hardcoded dimension (.dp) detected",
            explanation = SPACING_EXPLANATION,
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                HardcodedSpacingDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        private val SPACING_METHODS = setOf(
            "padding",
            "paddingStart",
            "paddingEnd",
            "paddingTop",
            "paddingBottom",
            "paddingHorizontal",
            "paddingVertical",
            "size",
            "width",
            "height",
            "offset",
            "absoluteOffset",
            "requiredSize",
            "requiredWidth",
            "requiredHeight",
        )

        private val BORDER_RADIUS_METHODS = setOf(
            "corner",
            "cornerRadius",
            "clip",
            "shape",
            "topStart",
            "topEnd",
            "bottomStart",
            "bottomEnd",
        )

        private enum class DimensionContext {
            SPACING,
            BORDER_RADIUS,
        }

        private fun getSuggestion(value: Int, context: DimensionContext): String {
            val (tokenMap, namespace) = when (context) {
                DimensionContext.SPACING -> GeneratedSpacingTokenMap.SPACING_TOKEN_MAP to "BpkSpacing"
                DimensionContext.BORDER_RADIUS -> GeneratedBorderRadiusTokenMap.BORDER_RADIUS_TOKEN_MAP to "BpkBorderRadius"
            }
            val token = tokenMap[value]
            return if (token != null) {
                "Use $token instead of $value.dp"
            } else {
                val available = tokenMap.entries
                    .sortedBy { it.key }
                    .joinToString("\n") { "• ${it.key}.dp = ${it.value}" }
                "Use $namespace.* tokens instead of $value.dp. Available tokens:\n$available"
            }
        }

        private fun getExplanation(context: DimensionContext): String =
            when (context) {
                DimensionContext.SPACING -> SPACING_EXPLANATION
                DimensionContext.BORDER_RADIUS -> BORDER_RADIUS_EXPLANATION
            }
    }

    override fun getApplicableReferenceNames(): List<String> = listOf("dp")

    override fun visitReference(
        context: JavaContext,
        reference: UReferenceExpression,
        referenced: PsiElement,
    ) {
        // Check if this is a .dp property access (e.g., 16.dp)
        val parent = reference.uastParent
        if (parent is UQualifiedReferenceExpression) {
            // Check if the receiver is a LITERAL number (not a variable)
            val receiver = parent.receiver
            if (receiver is ULiteralExpression) {
                // This is a hardcoded number like 16.dp
                val value = receiver.value
                if (value is Number) {
                    // Determine the context (spacing or border radius)
                    val dimensionContext = getDimensionContext(parent)
                    if (dimensionContext != null) {
                        val intValue = value.toInt()
                        val message = getSuggestion(intValue, dimensionContext)

                        // Get the appropriate token map based on context
                        val token = when (dimensionContext) {
                            DimensionContext.SPACING -> GeneratedSpacingTokenMap.SPACING_TOKEN_MAP[intValue]
                            DimensionContext.BORDER_RADIUS -> GeneratedBorderRadiusTokenMap.BORDER_RADIUS_TOKEN_MAP[intValue]
                        }

                        // If we have an exact match, provide a quick fix
                        val fix = if (token != null) {
                            com.android.tools.lint.detector.api.LintFix.create()
                                .replace()
                                .text("$intValue.dp")
                                .with(token)
                                .autoFix()
                                .build()
                        } else {
                            null
                        }

                        context.report(
                            ISSUE,
                            context.getLocation(parent),
                            message,
                            fix,
                        )
                    }
                }
            }
            // If receiver is NOT a literal (e.g., IMAGE_HEIGHT.dp), we allow it
        }
    }

    private fun getDimensionContext(dpExpression: UQualifiedReferenceExpression): DimensionContext? {
        // Walk up the tree to find if we're inside a spacing or border radius method call
        var current = dpExpression.uastParent
        var depth = 0
        val maxDepth = 10 // Limit search depth

        while (current != null && depth < maxDepth) {
            if (current is UCallExpression) {
                val methodName = current.methodName

                // Check for border radius context first (more specific)
                if (methodName in BORDER_RADIUS_METHODS || methodName?.contains("corner", ignoreCase = true) == true) {
                    // Check if the .dp expression is an argument to this method
                    if (current.valueArguments.any { arg -> containsExpression(arg, dpExpression) }) {
                        return DimensionContext.BORDER_RADIUS
                    }
                }

                // Check for spacing context
                if (methodName in SPACING_METHODS) {
                    // Check if the .dp expression is an argument to this spacing method
                    if (current.valueArguments.any { arg -> containsExpression(arg, dpExpression) }) {
                        return DimensionContext.SPACING
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
            // Stop if we've gone too far up
            if (current is UCallExpression && current != parent) {
                break
            }
        }
        return false
    }
}
