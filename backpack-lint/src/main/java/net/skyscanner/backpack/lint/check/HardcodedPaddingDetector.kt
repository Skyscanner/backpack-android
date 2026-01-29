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
import net.skyscanner.backpack.lint.util.LintConstants
import net.skyscanner.backpack.lint.util.TokenSuggestionBuilder
import net.skyscanner.backpack.lint.util.UastTreeUtils
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
                LintConstants.SUPPORT_MESSAGE

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

        private val suggestionBuilder = TokenSuggestionBuilder(
            tokenMap = GeneratedSpacingTokenMap.SPACING_TOKEN_MAP,
            tokenTypePrefix = "BpkSpacing",
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
            val intValue = UastTreeUtils.extractNumericDpValue(parent)
            if (intValue != null && UastTreeUtils.isInsideMethodCall(parent, PADDING_METHODS)) {
                val message = suggestionBuilder.buildMessage(intValue)
                val fix = suggestionBuilder.buildLintFix(intValue)
                context.report(ISSUE, context.getLocation(parent), message, fix)
            }
        }
    }
}
