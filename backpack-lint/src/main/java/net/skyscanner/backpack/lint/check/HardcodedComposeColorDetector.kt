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
import net.skyscanner.backpack.lint.util.LintConstants
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiField
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.ULiteralExpression
import org.jetbrains.uast.UQualifiedReferenceExpression
import org.jetbrains.uast.UReferenceExpression

@Suppress("UnstableApiUsage")
class HardcodedComposeColorDetector : Detector(), SourceCodeScanner {

    companion object {
        private val EXPLANATION =
            "This color doesn't exist in Backpack. Please check BpkTheme.colors for available colors.\n\n${LintConstants.SUPPORT_MESSAGE}"

        private fun getColorSuggestion(hex: String): String {
            val tokens = GeneratedColorTokenMap.COLOR_TOKEN_MAP[hex]
            return if (tokens != null) {
                if (tokens.size == 1) {
                    "Use ${tokens[0]} instead of Color($hex)"
                } else {
                    val tokenList = tokens.joinToString("\n- ")
                    "Use one of these tokens instead of Color($hex). All these tokens have the same color value:\n- $tokenList"
                }
            } else {
                EXPLANATION
            }
        }

        val ISSUE = Issue.create(
            id = "HardcodedComposeColor",
            briefDescription = "Hardcoded Compose Color detected",
            explanation = EXPLANATION,
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                HardcodedComposeColorDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        // Common hardcoded color names to detect
        private val HARDCODED_COLOR_NAMES = setOf(
            "Red", "Blue", "Green", "Yellow", "Cyan", "Magenta", "White", "Black",
            "Gray", "LightGray", "DarkGray",
        )
    }

    override fun getApplicableConstructorTypes(): List<String> = listOf(
        "androidx.compose.ui.graphics.Color",
    )

    override fun getApplicableMethodNames(): List<String> = listOf("Color") + HARDCODED_COLOR_NAMES.toList()

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val evaluator = context.evaluator
        val methodName = node.methodName ?: return

        // Check if this is Color() constructor-like call via companion object
        if (methodName == "Color" || methodName == "constructor-impl") {
            val containingClass = method.containingClass
            if (containingClass != null) {
                val fqName = evaluator.getQualifiedName(containingClass)
                if (fqName == "androidx.compose.ui.graphics.Color" ||
                    fqName == "androidx.compose.ui.graphics.ColorKt") {
                    val arguments = node.valueArguments
                    if (arguments.isNotEmpty()) {
                        // Try to extract hex value for specific suggestion
                        val hexValue = extractHexValue(arguments.firstOrNull())
                        val message = if (hexValue != null) {
                            getColorSuggestion(hexValue)
                        } else {
                            EXPLANATION
                        }

                        val token = hexValue?.let { GeneratedColorTokenMap.COLOR_TOKEN_MAP[it] }
                        val fix = if (token != null && hexValue != null) {
                            if (token.size == 1) {
                                // Single option: provide direct quick fix
                                LintFix.create()
                                    .replace()
                                    .text("Color($hexValue)")
                                    .with(token[0])
                                    .autoFix()
                                    .build()
                            } else {
                                // Multiple options: provide alternatives for user to choose
                                val alternatives = token.map { tokenName ->
                                    LintFix.create()
                                        .replace()
                                        .text("Color($hexValue)")
                                        .with(tokenName)
                                        .build()
                                }
                                LintFix.create()
                                    .alternatives(*alternatives.toTypedArray())
                            }
                        } else {
                            null
                        }

                        context.report(
                            ISSUE,
                            context.getLocation(node),
                            message,
                            fix,
                        )
                    }
                    return
                }
            }
        }
    }

    private fun extractHexValue(argument: org.jetbrains.uast.UElement?): String? {
        if (argument is ULiteralExpression) {
            val value = argument.value
            if (value is Long || value is Int) {
                val hex = value.toString().toLongOrNull()?.toString(16)?.uppercase()
                return hex?.let { "0x${it.padStart(8, '0')}" }
            }
        }
        return null
    }

    override fun visitConstructor(
        context: JavaContext,
        node: UCallExpression,
        constructor: PsiMethod,
    ) {
        // Verify this is actually the Compose Color constructor
        val evaluator = context.evaluator
        val containingClass = constructor.containingClass ?: return
        val fqName = evaluator.getQualifiedName(containingClass)

        if (fqName != "androidx.compose.ui.graphics.Color") {
            return
        }

        // Report ALL Color constructor calls with arguments
        // (Color.Unspecified and Color.Transparent have no arguments, so they're allowed)
        val arguments = node.valueArguments
        if (arguments.isNotEmpty()) {
            // Try to extract hex value for specific suggestion
            val hexValue = extractHexValue(arguments.firstOrNull())
            val message = if (hexValue != null) {
                getColorSuggestion(hexValue)
            } else {
                EXPLANATION
            }

            val token = hexValue?.let { GeneratedColorTokenMap.COLOR_TOKEN_MAP[it] }
            val fix = if (token != null && hexValue != null) {
                if (token.size == 1) {
                    // Single option: provide direct quick fix
                    LintFix.create()
                        .replace()
                        .text("Color($hexValue)")
                        .with(token[0])
                        .autoFix()
                        .build()
                } else {
                    // Multiple options: provide alternatives for user to choose
                    val alternatives = token.map { tokenName ->
                        LintFix.create()
                            .replace()
                            .text("Color($hexValue)")
                            .with(tokenName)
                            .build()
                    }
                    LintFix.create()
                        .alternatives(*alternatives.toTypedArray())
                }
            } else {
                null
            }

            context.report(
                ISSUE,
                context.getLocation(node),
                message,
                fix,
            )
        }
    }

    override fun getApplicableReferenceNames(): List<String> = HARDCODED_COLOR_NAMES.toList()

    override fun visitReference(
        context: JavaContext,
        reference: UReferenceExpression,
        referenced: PsiElement,
    ) {
        // Check if this is a Color.Red, Color.Blue, etc. reference
        if (referenced !is PsiField) return

        val parent = reference.uastParent
        if (parent is UQualifiedReferenceExpression) {
            val containingClass = referenced.containingClass ?: return
            val evaluator = context.evaluator
            val fqName = evaluator.getQualifiedName(containingClass)

            // Verify it's the Compose Color class
            if (fqName == "androidx.compose.ui.graphics.Color") {
                // Skip Color.Unspecified and Color.Transparent
                val fieldName = referenced.name
                if (fieldName !in setOf("Unspecified", "Transparent")) {
                    context.report(
                        ISSUE,
                        context.getLocation(parent),
                        EXPLANATION,
                    )
                }
            }
        }
    }
}
