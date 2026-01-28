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
import com.android.tools.lint.detector.api.Location
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.PsiElement
import net.skyscanner.backpack.lint.util.UastTreeUtils
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.UQualifiedReferenceExpression
import org.jetbrains.uast.UReferenceExpression

/**
 * Detects hardcoded .dp values used in size/width/height methods and suggests extracting to named constants.
 */
@Suppress("UnstableApiUsage")
class HardcodedSizeDetector : Detector(), SourceCodeScanner {

    companion object {
        private const val EXPLANATION =
            "Avoid hardcoded .dp values for dimensions. Extract to a named constant for better maintainability.\n\n" +
                "Example:\n" +
                "private val ImageHeight = 200.dp\n\n" +
                "// Then use:\n" +
                "Modifier.height(ImageHeight)"

        val ISSUE = Issue.create(
            id = "HardcodedSize",
            briefDescription = "Hardcoded size (.dp) detected",
            explanation = EXPLANATION,
            category = Category.CORRECTNESS,
            severity = Severity.WARNING,
            implementation = Implementation(
                HardcodedSizeDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        private val SIZE_METHODS = setOf(
            "size",
            "width",
            "height",
            "offset",
            "absoluteOffset",
            "requiredSize",
            "requiredWidth",
            "requiredHeight",
            "defaultMinSize",
            "sizeIn",
            "widthIn",
            "heightIn",
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
            if (intValue != null) {
                val methodName = UastTreeUtils.findContainingMethodName(parent, SIZE_METHODS)
                if (methodName != null) {
                    val constantName = getConstantName(methodName)
                    val message = getSuggestion(intValue, methodName, constantName)
                    val fix = buildExtractConstantFix(context, parent, intValue, constantName)
                    context.report(ISSUE, context.getLocation(parent), message, fix)
                }
            }
        }
    }

    private fun getConstantName(methodName: String): String {
        return when (methodName) {
            "width", "requiredWidth", "widthIn" -> "ItemWidth"
            "height", "requiredHeight", "heightIn" -> "ItemHeight"
            else -> "ItemSize"
        }
    }

    private fun getSuggestion(value: Int, methodName: String, constantName: String): String {
        return "Extract hardcoded size to a named constant.\n\n" +
            "Example:\n" +
            "private val $constantName = $value.dp\n\n" +
            "// Then use:\n" +
            "Modifier.$methodName($constantName)"
    }

    private fun buildExtractConstantFix(
        context: JavaContext,
        dpExpression: UQualifiedReferenceExpression,
        value: Int,
        constantName: String,
    ): LintFix {
        val insertionPoint = findInsertionPoint(context, dpExpression)

        val replaceFix = LintFix.create()
            .replace()
            .text("$value.dp")
            .with(constantName)
            .build()

        val insertFix = LintFix.create()
            .replace()
            .range(insertionPoint)
            .beginning()
            .with("private val $constantName = $value.dp\n")
            .build()

        return LintFix.create()
            .name("Extract to constant '$constantName'")
            .composite(insertFix, replaceFix)
    }

    private fun findInsertionPoint(context: JavaContext, element: UElement): Location {
        var current: UElement? = element
        while (current != null) {
            when (current) {
                is UMethod -> {
                    current.sourcePsi?.let { psi ->
                        return context.getLocation(psi)
                    }
                }
                is UClass -> {
                    current.sourcePsi?.let { psi ->
                        return context.getLocation(psi)
                    }
                }
            }
            current = current.uastParent
        }
        return context.getLocation(element)
    }
}
