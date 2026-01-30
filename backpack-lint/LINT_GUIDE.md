# Android Lint Rules Guide

A practical guide to writing custom lint rules for Android.

## Quick Links

- [LintFix Patterns](docs/LINT_FIXES.md) - Quickfixes, alternatives, composite fixes
- [Testing Guide](docs/LINT_TESTING.md) - Test structure, stubs, assertions
- [Detection Patterns](docs/LINT_PATTERNS.md) - Smart detection, unique names, dynamic values

---

## The Basics

A lint detector needs:

```kotlin
@Suppress("UnstableApiUsage")
class MyDetector : Detector(), SourceCodeScanner {

    companion object {
        val ISSUE = Issue.create(
            id = "MyIssueId",
            briefDescription = "Short description",
            explanation = "What's wrong and how to fix it",
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                MyDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )
    }
}
```

---

## UAST vs Kotlin PSI

### The Golden Rule

> **If lint can't resolve the type, UAST won't create the node.**

| Use | When |
|-----|------|
| UAST | Detecting method calls, property access (`.dp`, `.sp`) |
| Kotlin PSI | Matching by name only, when types can't be resolved |

---

## Detection Approaches

### 1. Reference Detection (`.dp`, `.sp`, `.copy()`)

```kotlin
override fun getApplicableReferenceNames(): List<String> = listOf("dp")

override fun visitReference(
    context: JavaContext,
    reference: UReferenceExpression,
    referenced: PsiElement,
) {
    val parent = reference.uastParent as? UQualifiedReferenceExpression ?: return
    context.report(ISSUE, context.getLocation(parent), "Message")
}
```

### 2. Method Call Detection

```kotlin
override fun getApplicableMethodNames(): List<String> = listOf("copy")

override fun visitMethodCall(
    context: JavaContext,
    node: UCallExpression,
    method: PsiMethod,
) {
    context.report(ISSUE, context.getLocation(node), "Message")
}
```

### 3. Constructor Detection (requires type resolution)

```kotlin
override fun getApplicableConstructorTypes(): List<String> = listOf(
    "androidx.compose.ui.graphics.Color"
)

override fun visitConstructor(
    context: JavaContext,
    node: UCallExpression,
    constructor: PsiMethod,
) {
    // Won't be called if type can't be resolved
    context.report(ISSUE, context.getLocation(node), "Message")
}
```

### 4. PSI Tree Walking (no type resolution needed)

```kotlin
override fun getApplicableUastTypes(): List<Class<out UElement>> = listOf(UFile::class.java)

override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
    override fun visitFile(node: UFile) {
        val ktFile = node.sourcePsi as? KtFile ?: return
        ktFile.accept(object : KtTreeVisitorVoid() {
            override fun visitCallExpression(expression: KtCallExpression) {
                if (expression.calleeExpression?.text == "TextStyle") {
                    context.report(ISSUE, context.getLocation(expression), "Message")
                }
                super.visitCallExpression(expression)
            }
        })
    }
}
```

---

## Quickfixes

### Simple Replace

```kotlin
val fix = LintFix.create()
    .replace()
    .text("16.dp")
    .with("BpkSpacing.Base")
    .autoFix()
    .build()
```

### Multiple Alternatives

```kotlin
val fixes = listOf("ItemHeight", "ComponentHeight").map { name ->
    LintFix.create().replace().text("56.dp").with(name).build()
}
val fix = LintFix.create().alternatives(*fixes.toTypedArray())
```

See [LintFix Patterns](docs/LINT_FIXES.md) for composite fixes, select(), and more.

---

## Testing

```kotlin
@Test
fun `detects hardcoded dp`() {
    lint().files(
        kotlin("""
            package test
            fun test() = Modifier.size(100.dp)
        """).indented(),
        dpStub()
    )
    .allowMissingSdk()
    .issues(MyDetector.ISSUE)
    .run()
    .expectWarningCount(1)
}
```

See [Testing Guide](docs/LINT_TESTING.md) for stubs, assertions, and patterns.

---

## Key Takeaways

1. **UAST needs type resolution** - use PSI when types can't be resolved
2. **Tests ≠ Reality** - tests have stubs, real code might not resolve
3. **Check for existing constants** - suggest reuse before creating new ones
4. **Generate unique names** - append numbers if name already exists
5. **Allow dynamic values** - only flag `ULiteralExpression`, not variables

---

## Resources

- [Android Lint API Guide](https://googlesamples.github.io/android-custom-lint-rules/)
- [Kotlin PSI Documentation](https://plugins.jetbrains.com/docs/intellij/psi.html)
- [UAST Documentation](https://plugins.jetbrains.com/docs/intellij/uast.html)
