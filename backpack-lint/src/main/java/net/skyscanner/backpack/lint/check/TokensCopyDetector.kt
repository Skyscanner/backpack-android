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
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UQualifiedReferenceExpression

@Suppress("UnstableApiUsage")
class TokensCopyDetector : Detector(), SourceCodeScanner {

    companion object {
        private val EXPLANATION =
            "Do not use .copy() to modify design tokens. Request a new semantic token from the design team instead. Using .copy() bypasses the design system and breaks theming support.\n\n${LintConstants.SUPPORT_MESSAGE}"

        val ISSUE = Issue.create(
            id = "TokensCopy",
            briefDescription = "Design token modified with .copy()",
            explanation = EXPLANATION,
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                TokensCopyDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        // Regex patterns to detect design token access (both fully qualified and imported)
        private val TOKEN_PATTERNS = listOf(
            // BpkTheme.colors.xxx, BpkTheme.typography.xxx, etc.
            Regex("""BpkTheme\.(colors|typography|getColors|getTypography)\."""),
            // Standalone tokens: BpkSpacing.xxx, BpkBorderRadius.xxx, etc.
            Regex("""(BpkSpacing|BpkBorderRadius|BpkElevation|BpkDimension)\."""),
            // Imported references: colors.xxx, typography.xxx (when BpkTheme.colors is imported)
            // Removed ^ anchor to allow matching anywhere in the string
            Regex("""(^|[^.a-zA-Z])(colors|typography)\."""),
        )
    }

    override fun getApplicableMethodNames(): List<String> = listOf("copy")

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        // Check if .copy() is being called on any design token
        val receiver = node.receiver
        if (receiver is UQualifiedReferenceExpression) {
            val receiverText = receiver.asSourceString()

            // Check all possible patterns:
            // 1. BpkTheme.colors.xxx, BpkTheme.typography.xxx
            if (receiverText.contains("BpkTheme.colors.") ||
                receiverText.contains("BpkTheme.typography.") ||
                receiverText.contains("BpkTheme.getColors()") ||
                receiverText.contains("BpkTheme.getTypography()")) {
                context.report(ISSUE, context.getLocation(node), EXPLANATION)
                return
            }

            // 2. Standalone tokens: BpkSpacing.xxx, BpkBorderRadius.xxx, etc.
            if (receiverText.contains("BpkSpacing.") ||
                receiverText.contains("BpkBorderRadius.") ||
                receiverText.contains("BpkElevation.") ||
                receiverText.contains("BpkDimension.")) {
                context.report(ISSUE, context.getLocation(node), EXPLANATION)
                return
            }

            // 3. Imported references: colors.xxx, typography.xxx
            // Use word boundary check to avoid matching "mycolors.xxx"
            if (receiverText.matches(Regex(""".*\bcolors\.[a-zA-Z].*""")) ||
                receiverText.matches(Regex(""".*\btypography\.[a-zA-Z].*"""))) {
                context.report(ISSUE, context.getLocation(node), EXPLANATION)
                return
            }
        }
    }
}
