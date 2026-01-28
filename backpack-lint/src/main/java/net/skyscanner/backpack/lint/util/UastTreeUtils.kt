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

package net.skyscanner.backpack.lint.util

import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.ULiteralExpression
import org.jetbrains.uast.UQualifiedReferenceExpression

/**
 * Utility functions for traversing and analyzing UAST (Unified Abstract Syntax Tree) elements.
 */
internal object UastTreeUtils {

    private const val DEFAULT_MAX_DEPTH = 10

    /**
     * Finds a parent method call with a matching method name.
     *
     * @param element The starting element to search from
     * @param methodNames Set of method names to match
     * @param maxDepth Maximum depth to traverse up the tree (default: 10)
     * @param additionalMatcher Optional additional matcher for method names (e.g., contains check)
     * @return The matching [UCallExpression] if found, null otherwise
     */
    fun findParentMethodCall(
        element: UElement,
        methodNames: Set<String>,
        maxDepth: Int = DEFAULT_MAX_DEPTH,
        additionalMatcher: ((String) -> Boolean)? = null,
    ): UCallExpression? {
        var current = element.uastParent
        var depth = 0

        while (current != null && depth < maxDepth) {
            if (current is UCallExpression) {
                val methodName = current.methodName
                if (methodName in methodNames || (additionalMatcher != null && methodName?.let(additionalMatcher) == true)) {
                    return current
                }
            }
            current = current.uastParent
            depth++
        }
        return null
    }

    /**
     * Checks if the element is inside a method call with one of the specified method names,
     * and the element is an argument of that method call.
     *
     * @param element The element to check (typically a .dp expression)
     * @param methodNames Set of method names to match
     * @param maxDepth Maximum depth to traverse up the tree (default: 10)
     * @param additionalMatcher Optional additional matcher for method names
     * @return True if the element is inside a matching method call, false otherwise
     */
    fun isInsideMethodCall(
        element: UQualifiedReferenceExpression,
        methodNames: Set<String>,
        maxDepth: Int = DEFAULT_MAX_DEPTH,
        additionalMatcher: ((String) -> Boolean)? = null,
    ): Boolean {
        var current = element.uastParent
        var depth = 0

        while (current != null && depth < maxDepth) {
            if (current is UCallExpression) {
                val methodName = current.methodName
                if (methodName in methodNames || (additionalMatcher != null && methodName?.let(additionalMatcher) == true)) {
                    if (current.valueArguments.any { arg -> containsExpression(arg, element) }) {
                        return true
                    }
                }
            }
            current = current.uastParent
            depth++
        }
        return false
    }

    /**
     * Finds a parent method call and returns its name if the element is an argument of that method.
     *
     * @param element The element to check
     * @param methodNames Set of method names to match
     * @param maxDepth Maximum depth to traverse up the tree (default: 10)
     * @return The matching method name if found, null otherwise
     */
    fun findContainingMethodName(
        element: UQualifiedReferenceExpression,
        methodNames: Set<String>,
        maxDepth: Int = DEFAULT_MAX_DEPTH,
    ): String? {
        var current = element.uastParent
        var depth = 0

        while (current != null && depth < maxDepth) {
            if (current is UCallExpression) {
                val methodName = current.methodName
                if (methodName in methodNames) {
                    if (current.valueArguments.any { arg -> containsExpression(arg, element) }) {
                        return methodName
                    }
                }
            }
            current = current.uastParent
            depth++
        }
        return null
    }

    /**
     * Checks if the target element is contained within the parent element's subtree.
     * This traverses up from the target to see if it reaches the parent.
     *
     * @param parent The potential parent element
     * @param target The element to check
     * @return True if target is contained within parent, false otherwise
     */
    fun containsExpression(parent: UElement, target: UElement): Boolean {
        if (parent == target) return true
        var current: UElement? = target
        while (current != null) {
            if (current == parent) return true
            current = current.uastParent
            if (current is UCallExpression && current != parent) break
        }
        return false
    }

    /**
     * Extracts a numeric literal value from a .dp qualified reference expression.
     *
     * @param parent The qualified reference expression (e.g., "16.dp")
     * @return The numeric value as Int if the receiver is a numeric literal, null otherwise
     */
    fun extractNumericDpValue(parent: UQualifiedReferenceExpression): Int? {
        val receiver = parent.receiver
        if (receiver is ULiteralExpression) {
            val value = receiver.value
            if (value is Number) {
                return value.toInt()
            }
        }
        return null
    }
}
