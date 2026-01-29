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

import com.android.tools.lint.client.api.UElementHandler
import net.skyscanner.backpack.lint.util.LintConstants
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.openapi.util.TextRange
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtConstantExpression
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtTreeVisitorVoid
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UFile

@Suppress("UnstableApiUsage")
class HardcodedTypographyDetector : Detector(), SourceCodeScanner {

    companion object {
        private val EXPLANATION =
            "Use BpkTheme.typography.* instead of hardcoded font sizes or TextStyle creation. " +
                "Hardcoding typography bypasses the design system and creates inconsistent text styling.\n\n" +
                LintConstants.SUPPORT_MESSAGE

        val ISSUE = Issue.create(
            id = "HardcodedTypography",
            briefDescription = "Hardcoded typography (.sp or TextStyle) detected",
            explanation = EXPLANATION,
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                HardcodedTypographyDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        private val FONT_WEIGHT_NAMES = listOf(
            "Thin", "ExtraLight", "Light", "Normal", "Medium",
            "SemiBold", "Bold", "ExtraBold", "Black",
        )

        // Regex to find hardcoded .sp usage like "16.sp", "12.sp"
        private val HARDCODED_SP_REGEX = Regex("""(\d+)\.sp\b""")
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>> = listOf(UFile::class.java)

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitFile(node: UFile) {
            val ktFile = node.sourcePsi as? KtFile ?: return
            val reportedRanges = mutableSetOf<TextRange>()

            reportTextStyleCalls(context, ktFile, reportedRanges)
            reportStandaloneSpUsage(context, ktFile, reportedRanges)
        }
    }

    private fun reportTextStyleCalls(
        context: JavaContext,
        ktFile: KtFile,
        reportedRanges: MutableSet<TextRange>,
    ) {
        ktFile.accept(object : KtTreeVisitorVoid() {
            override fun visitCallExpression(expression: KtCallExpression) {
                val callee = expression.calleeExpression?.text
                if (callee == "TextStyle" || callee?.endsWith(".TextStyle") == true) {
                    val spValue = extractSpValue(expression)
                    if (spValue != null) {
                        val fontWeight = extractFontWeight(expression.text)
                        reportedRanges.add(expression.textRange)
                        context.report(
                            ISSUE,
                            context.getLocation(expression),
                            getTokenSuggestion(spValue, fontWeight, "TextStyle"),
                        )
                    }
                }
                super.visitCallExpression(expression)
            }
        })
    }

    private fun reportStandaloneSpUsage(
        context: JavaContext,
        ktFile: KtFile,
        reportedRanges: Set<TextRange>,
    ) {
        ktFile.accept(object : KtTreeVisitorVoid() {
            override fun visitDotQualifiedExpression(expression: KtDotQualifiedExpression) {
                val selector = expression.selectorExpression?.text
                val receiver = expression.receiverExpression

                if (selector == "sp" && receiver is KtConstantExpression) {
                    val isInsideTextStyle = reportedRanges.any { it.contains(expression.textRange) }
                    if (!isInsideTextStyle) {
                        val spValue = receiver.text.toIntOrNull()
                        if (spValue != null) {
                            context.report(
                                ISSUE,
                                context.getLocation(expression),
                                getTokenSuggestion(spValue, "Normal", "$spValue.sp"),
                            )
                        }
                    }
                }
                super.visitDotQualifiedExpression(expression)
            }
        })
    }

    private fun extractSpValue(textStyleCall: KtCallExpression): Int? {
        val callText = textStyleCall.text
        val match = HARDCODED_SP_REGEX.find(callText)
        return match?.groupValues?.get(1)?.toIntOrNull()
    }

    private fun extractFontWeight(source: String): String {
        return FONT_WEIGHT_NAMES.firstOrNull { source.contains("FontWeight.$it") } ?: "Normal"
    }

    private fun getTokenSuggestion(spValue: Int, fontWeight: String, replacementTarget: String): String {
        val tokens = GeneratedTypographyTokenMap.TYPOGRAPHY_TOKEN_MAP[spValue to fontWeight]
        return if (tokens != null) {
            if (tokens.size == 1) {
                "Use ${tokens[0]} instead of $replacementTarget"
            } else {
                val tokenList = tokens.joinToString("\n") { "• $it" }
                "Use one of these typography styles instead of $replacementTarget:\n$tokenList"
            }
        } else {
            "Use BpkTheme.typography.* instead of $replacementTarget. ${buildAvailableTokensMessage()}"
        }
    }

    private fun buildAvailableTokensMessage(): String {
        val available = GeneratedTypographyTokenMap.TYPOGRAPHY_TOKEN_MAP.entries
            .sortedBy { it.key.first }
            .joinToString("\n") { (key, styles) ->
                "• ${key.first}sp/${key.second} = ${styles.joinToString(", ")}"
            }
        return "Available styles:\n$available"
    }
}
