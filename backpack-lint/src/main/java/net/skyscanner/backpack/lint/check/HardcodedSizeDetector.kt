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
import org.jetbrains.uast.UFile
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.UQualifiedReferenceExpression
import org.jetbrains.uast.UReferenceExpression
import org.jetbrains.uast.getContainingUFile
import org.jetbrains.uast.visitor.AbstractUastVisitor

/**
 * Detects hardcoded .dp values used in size/width/height methods and suggests extracting to named constants.
 * If an existing constant with the same value exists in the file, it will suggest reusing that constant.
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

        private val DP_CONSTANT_PATTERN = Regex("""(\w+)\s*=\s*(\d+)\.dp""")
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
                    val existingConstants = findExistingDpConstants(parent, intValue)
                    val fix = buildFix(context, parent, intValue, methodName, existingConstants)
                    val message = buildMessage(intValue, methodName, existingConstants)
                    context.report(ISSUE, context.getLocation(parent), message, fix)
                }
            }
        }
    }

    private fun findExistingDpConstants(element: UElement, targetValue: Int): List<String> {
        val file = element.getContainingUFile() ?: return emptyList()
        val constants = mutableListOf<String>()

        file.accept(object : AbstractUastVisitor() {
            override fun visitFile(node: UFile): Boolean {
                val source = node.sourcePsi?.text ?: return false
                DP_CONSTANT_PATTERN.findAll(source).forEach { match ->
                    val name = match.groupValues[1]
                    val value = match.groupValues[2].toIntOrNull()
                    if (value == targetValue) {
                        constants.add(name)
                    }
                }
                return true
            }
        })

        return constants.distinct()
    }

    private fun buildMessage(value: Int, methodName: String, existingConstants: List<String>): String {
        return if (existingConstants.isNotEmpty()) {
            val constantList = existingConstants.joinToString(", ")
            "Hardcoded size detected. Existing constant(s) with same value: $constantList\n\n" +
                "Use an existing constant or extract to a new one."
        } else {
            val suggestedName = getConstantName(methodName)
            "Extract hardcoded size to a named constant.\n\n" +
                "Example:\n" +
                "private val $suggestedName = $value.dp\n\n" +
                "// Then use:\n" +
                "Modifier.$methodName($suggestedName)"
        }
    }

    private fun buildFix(
        context: JavaContext,
        dpExpression: UQualifiedReferenceExpression,
        value: Int,
        methodName: String,
        existingConstants: List<String>,
    ): LintFix {
        val fixes = mutableListOf<LintFix>()

        // Add fixes for existing constants (just replace, no insert needed)
        existingConstants.forEach { constantName ->
            val reuseFix = LintFix.create()
                .name("Use existing constant '$constantName'")
                .replace()
                .text("$value.dp")
                .with(constantName)
                .autoFix()
                .build()
            fixes.add(reuseFix)
        }

        // Add fixes for creating new constants with different naming options
        val namingOptions = getConstantNameOptions(methodName)
        val insertionPoint = findInsertionPoint(context, dpExpression)

        namingOptions.forEach { constantName ->
            // Skip if this name already exists
            if (constantName !in existingConstants) {
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

                val compositeFix = LintFix.create()
                    .name("Extract to new constant '$constantName'")
                    .composite(insertFix, replaceFix)

                fixes.add(compositeFix)
            }
        }

        return if (fixes.size == 1) {
            fixes.first()
        } else {
            LintFix.create()
                .alternatives(*fixes.toTypedArray())
        }
    }

    private fun getConstantName(methodName: String): String {
        return when (methodName) {
            "width", "requiredWidth", "widthIn" -> "ItemWidth"
            "height", "requiredHeight", "heightIn" -> "ItemHeight"
            else -> "ItemSize"
        }
    }

    private fun getConstantNameOptions(methodName: String): List<String> {
        return when (methodName) {
            "width", "requiredWidth", "widthIn" -> listOf("ItemWidth", "ComponentWidth", "ContentWidth")
            "height", "requiredHeight", "heightIn" -> listOf("ItemHeight", "ComponentHeight", "ContentHeight")
            else -> listOf("ItemSize", "ComponentSize", "ContentSize")
        }
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
