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
        private const val EXPLANATION =
            "This color doesn't exist in Backpack. Please check BpkTheme.colors for available colors.\n\n${LintConstants.SUPPORT_MESSAGE}"

        private const val COMPOSE_COLOR_CLASS = "androidx.compose.ui.graphics.Color"
        private const val COMPOSE_COLOR_KT_CLASS = "androidx.compose.ui.graphics.ColorKt"

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

        private val HARDCODED_COLOR_NAMES = setOf(
            "Red", "Blue", "Green", "Yellow", "Cyan", "Magenta", "White", "Black",
            "Gray", "LightGray", "DarkGray",
        )
    }

    override fun getApplicableConstructorTypes(): List<String> = listOf(COMPOSE_COLOR_CLASS)

    override fun getApplicableMethodNames(): List<String> = listOf("Color") + HARDCODED_COLOR_NAMES.toList()

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val methodName = node.methodName ?: return
        if (methodName != "Color" && methodName != "constructor-impl") return

        val containingClass = method.containingClass ?: return
        val fqName = context.evaluator.getQualifiedName(containingClass)
        if (!isComposeColorClass(fqName)) return

        reportHardcodedColorIfLiteral(context, node)
    }

    override fun visitConstructor(
        context: JavaContext,
        node: UCallExpression,
        constructor: PsiMethod,
    ) {
        val containingClass = constructor.containingClass ?: return
        val fqName = context.evaluator.getQualifiedName(containingClass)
        if (fqName != COMPOSE_COLOR_CLASS) return

        reportHardcodedColorIfLiteral(context, node)
    }

    private fun isComposeColorClass(fqName: String?): Boolean {
        return fqName == COMPOSE_COLOR_CLASS || fqName == COMPOSE_COLOR_KT_CLASS
    }

    private fun reportHardcodedColorIfLiteral(context: JavaContext, node: UCallExpression) {
        val arguments = node.valueArguments
        if (arguments.isEmpty()) return

        val hexValue = extractHexValue(arguments.firstOrNull()) ?: return
        val message = getColorSuggestion(hexValue)
        val tokens = GeneratedColorTokenMap.COLOR_TOKEN_MAP[hexValue]
        val fix = buildColorLintFix(hexValue, tokens)

        context.report(ISSUE, context.getLocation(node), message, fix)
    }

    private fun buildColorLintFix(hexValue: String, tokens: List<String>?): LintFix? {
        tokens ?: return null
        return if (tokens.size == 1) {
            LintFix.create()
                .replace()
                .text("Color($hexValue)")
                .with(tokens[0])
                .autoFix()
                .build()
        } else {
            val alternatives = tokens.map { tokenName ->
                LintFix.create()
                    .replace()
                    .text("Color($hexValue)")
                    .with(tokenName)
                    .build()
            }
            LintFix.create().alternatives(*alternatives.toTypedArray())
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
            val qualifiedReference = evaluator.getQualifiedName(containingClass)

            if (qualifiedReference == COMPOSE_COLOR_CLASS) {
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
