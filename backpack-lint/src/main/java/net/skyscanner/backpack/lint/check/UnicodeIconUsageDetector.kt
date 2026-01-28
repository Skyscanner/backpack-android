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
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.ULiteralExpression
import org.jetbrains.uast.UPolyadicExpression

@Suppress("UnstableApiUsage")
class UnicodeIconUsageDetector : Detector(), SourceCodeScanner {

    companion object {
        private const val EXPLANATION =
            "Unicode symbols used as icons are not accessible. Use BpkIcon composables with contentDescription instead.\n\n" +
                "Need support? Share your message in #backpack Slack channel: https://skyscanner.slack.com/archives/C0JHPDSSU"

        val ISSUE = Issue.create(
            id = "UnicodeIconUsage",
            briefDescription = "Unicode symbol used as icon instead of BpkIcon",
            explanation = EXPLANATION,
            category = Category.A11Y,
            severity = Severity.ERROR,
            implementation = Implementation(
                UnicodeIconUsageDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        private val TEXT_PACKAGES = setOf(
            "androidx.compose.material",
            "androidx.compose.material3",
            "net.skyscanner.backpack.compose.text",
        )
    }

    override fun getApplicableMethodNames(): List<String> = listOf("Text", "BpkText")

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val methodPackage = context.evaluator.getPackage(method)?.qualifiedName ?: return
        if (methodPackage !in TEXT_PACKAGES) return

        val textArg = node.valueArguments.firstOrNull() ?: return
        val textValue = extractStringValue(textArg) ?: return

        val suggestions = findIconSuggestions(textValue)
        if (suggestions.isNotEmpty()) {
            val message = buildMessage(suggestions)
            context.report(
                ISSUE,
                context.getLocation(node),
                message,
            )
        }
    }

    private fun extractStringValue(element: org.jetbrains.uast.UElement): String? {
        return when (element) {
            is ULiteralExpression -> element.value as? String
            is UPolyadicExpression -> {
                element.operands
                    .mapNotNull { (it as? ULiteralExpression)?.value as? String }
                    .joinToString("")
                    .ifEmpty { null }
            }
            else -> null
        }
    }

    private fun findIconSuggestions(text: String): Map<String, String> {
        val suggestions = mutableMapOf<String, String>()

        for (char in text) {
            val bpk = UnicodeIconTokenMap.UNICODE_TO_BPK[char]
            if (bpk != null) {
                suggestions[char.toString()] = bpk
            }
        }

        for ((emoji, bpk) in UnicodeIconTokenMap.EMOJI_TO_BPK) {
            if (text.contains(emoji)) {
                suggestions[emoji] = bpk
            }
        }

        return suggestions
    }

    private fun buildMessage(suggestions: Map<String, String>): String {
        val unique = suggestions.entries
            .groupBy({ it.value }, { it.key })
            .map { (bpk, chars) ->
                val charList = chars.joinToString(", ") { "\"$it\"" }
                "$charList → Use $bpk with contentDescription"
            }

        return if (unique.size == 1) {
            "Unicode symbol used as icon. ${unique[0]}."
        } else {
            "Unicode symbols used as icons:\n" +
                unique.joinToString("\n") { "• $it" }
        }
    }
}
