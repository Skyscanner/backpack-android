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
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.ULiteralExpression
import org.jetbrains.uast.UQualifiedReferenceExpression
import org.jetbrains.uast.UReferenceExpression

@Suppress("UnstableApiUsage")
class HardcodedTypographyDetector : Detector(), SourceCodeScanner {

    companion object {
        private const val EXPLANATION =
            """Use BpkTypography.* instead of hardcoded font sizes or TextStyle creation. Hardcoding typography bypasses the design system and creates inconsistent text styling.

Available BpkTypography styles:
    • BpkTypography.hero1 - Hero 1 (120sp/Bold)
    • BpkTypography.hero2 - Hero 2 (96sp/Bold)
    • BpkTypography.hero3 - Hero 3 (76sp/Bold)
    • BpkTypography.hero4 - Hero 4 (64sp/Bold)
    • BpkTypography.hero5 - Hero 5 (52sp/Bold)
    • BpkTypography.heading1 - Heading 1 (40sp/Bold)
    • BpkTypography.heading2 - Heading 2 (32sp/Bold)
    • BpkTypography.heading3 - Heading 3 (24sp/Bold)
    • BpkTypography.heading4 - Heading 4 (20sp/Bold)
    • BpkTypography.heading5 - Heading 5 (16sp/Bold)
    • BpkTypography.subheading - Subheading (20sp/Normal)
    • BpkTypography.bodyLongform - Body Longform (20sp/Normal)
    • BpkTypography.bodyDefault - Body Default (16sp/Normal)
    • BpkTypography.footnote - Footnote (14sp/Normal)
    • BpkTypography.caption - Caption (12sp/Normal)
    • BpkTypography.label1 - Label 1 (16sp/Bold)
    • BpkTypography.label2 - Label 2 (14sp/Bold)
    • BpkTypography.label3 - Label 3 (12sp/Bold)
    • BpkTypography.baseLarken - Base Larken (16sp/Bold/Larken)
    • BpkTypography.smLarken - Small Larken (14sp/Bold/Larken)
    • BpkTypography.xsLarken - XSmall Larken (12sp/Bold/Larken)

If you're unsure which typography style to use, consult with design.

Need support? Share your message in #backpack Slack channel: https://skyscanner.slack.com/archives/C0JHPDSSU"""

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
    }

    override fun getApplicableReferenceNames(): List<String> = listOf("sp")

    override fun visitReference(
        context: JavaContext,
        reference: UReferenceExpression,
        referenced: PsiElement,
    ) {
        // Check if this is a .sp property access (e.g., 16.sp)
        val parent = reference.uastParent
        if (parent is UQualifiedReferenceExpression) {
            // Check if the receiver is a LITERAL number (not a variable)
            val receiver = parent.receiver
            if (receiver is ULiteralExpression) {
                // This is a hardcoded number like 16.sp
                val value = receiver.value
                if (value is Number) {
                    context.report(
                        ISSUE,
                        context.getLocation(parent),
                        EXPLANATION,
                    )
                }
            }
            // If receiver is NOT a literal (e.g., FONT_SIZE_CONSTANT.sp), we allow it
        }
    }

    override fun getApplicableConstructorTypes(): List<String> = listOf(
        "androidx.compose.ui.text.TextStyle",
    )

    override fun visitConstructor(
        context: JavaContext,
        node: UCallExpression,
        constructor: PsiMethod,
    ) {
        // Check if TextStyle constructor has fontSize parameter
        val evaluator = context.evaluator
        val containingClass = constructor.containingClass ?: return
        val fqName = evaluator.getQualifiedName(containingClass)

        if (fqName == "androidx.compose.ui.text.TextStyle") {
            // Check if fontSize argument is present
            val hasFontSize = node.valueArguments.any { arg ->
                val argName = arg.getExpressionType()?.canonicalText
                argName?.contains("TextUnit") == true
            }

            if (hasFontSize) {
                context.report(
                    ISSUE,
                    context.getLocation(node),
                    EXPLANATION,
                )
            }
        }
    }

    override fun getApplicableMethodNames(): List<String> = listOf("TextStyle")

    override fun visitMethodCall(
        context: JavaContext,
        node: UCallExpression,
        method: PsiMethod,
    ) {
        // Check for TextStyle() factory function calls
        val evaluator = context.evaluator
        val methodName = node.methodName ?: return

        if (methodName == "TextStyle") {
            val containingClass = method.containingClass
            if (containingClass != null) {
                val fqName = evaluator.getQualifiedName(containingClass)
                if (fqName == "androidx.compose.ui.text.TextStyle" ||
                    fqName == "androidx.compose.ui.text.TextStyleKt") {
                    val arguments = node.valueArguments
                    if (arguments.isNotEmpty()) {
                        context.report(
                            ISSUE,
                            context.getLocation(node),
                            EXPLANATION,
                        )
                    }
                }
            }
        }
    }
}
