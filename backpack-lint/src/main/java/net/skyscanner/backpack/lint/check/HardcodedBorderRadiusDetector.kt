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
 * Detects hardcoded .dp values used in border radius/corner methods and suggests BpkBorderRadius tokens.
 */
@Suppress("UnstableApiUsage")
class HardcodedBorderRadiusDetector : Detector(), SourceCodeScanner {

    companion object {
        private val EXPLANATION =
            "Use BpkBorderRadius.* tokens instead of hardcoded .dp values. " +
                "Hardcoding border radius bypasses the design system and creates inconsistent shapes.\n\n" +
                LintConstants.SUPPORT_MESSAGE

        val ISSUE = Issue.create(
            id = "HardcodedBorderRadius",
            briefDescription = "Hardcoded border radius (.dp) detected",
            explanation = EXPLANATION,
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                HardcodedBorderRadiusDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
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
            "RoundedCornerShape",
        )

        private val suggestionBuilder = TokenSuggestionBuilder(
            tokenMap = GeneratedBorderRadiusTokenMap.BORDER_RADIUS_TOKEN_MAP,
            tokenTypePrefix = "BpkBorderRadius",
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
            if (intValue != null && isInsideBorderRadiusMethod(parent)) {
                val message = suggestionBuilder.buildMessage(intValue)
                val fix = suggestionBuilder.buildLintFix(intValue)
                context.report(ISSUE, context.getLocation(parent), message, fix)
            }
        }
    }

    private fun isInsideBorderRadiusMethod(dpExpression: UQualifiedReferenceExpression): Boolean {
        return UastTreeUtils.isInsideMethodCall(
            element = dpExpression,
            methodNames = BORDER_RADIUS_METHODS,
            additionalMatcher = { it.contains("corner", ignoreCase = true) },
        )
    }
}
