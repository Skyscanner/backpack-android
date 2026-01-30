# LintFix Patterns

Guide to creating quickfixes for Android Lint rules.

## Basic Quickfix

Replace text with a suggested fix:

```kotlin
val fix = LintFix.create()
    .replace()
    .text("16.dp")           // Text to find
    .with("BpkSpacing.Md")   // Replacement
    .build()

context.report(ISSUE, location, message, fix)
```

## Auto-Fix

Mark a fix as safe to apply automatically:

```kotlin
val fix = LintFix.create()
    .replace()
    .text("Color.Red")
    .with("BpkTheme.colors.coreAccent")
    .autoFix()  // Safe to auto-apply
    .build()
```

## Multiple Alternatives

Let user choose from multiple fixes:

```kotlin
val fixes = listOf("ItemHeight", "ComponentHeight", "ContentHeight").map { name ->
    LintFix.create()
        .replace()
        .text("56.dp")
        .with(name)
        .build()
}

val fix = LintFix.create()
    .alternatives(*fixes.toTypedArray())

context.report(ISSUE, location, message, fix)
```

## Composite Fixes

Apply multiple fixes together (e.g., insert declaration + replace usage):

```kotlin
// Fix 1: Insert constant declaration
val insertFix = LintFix.create()
    .replace()
    .range(insertionPoint)  // Location to insert at
    .beginning()            // Insert at start of range
    .with("private val ItemHeight = 56.dp\n")
    .build()

// Fix 2: Replace hardcoded value with constant name
val replaceFix = LintFix.create()
    .replace()
    .text("56.dp")
    .with("ItemHeight")
    .build()

// Combine both
val fix = LintFix.create()
    .name("Extract to constant 'ItemHeight'")
    .composite(insertFix, replaceFix)
```

## Select Text After Fix

Auto-select placeholder text so user can immediately type:

```kotlin
val fix = LintFix.create()
    .replace()
    .text("56.dp")
    .with("TODO")
    .select("(TODO)")  // Regex to select - user can type to replace
    .build()
```

## Finding Insertion Points

Insert constants before the containing method or class:

```kotlin
private fun findInsertionPoint(context: JavaContext, element: UElement): Location {
    var current: UElement? = element
    while (current != null) {
        when (current) {
            is UMethod -> {
                current.sourcePsi?.let { return context.getLocation(it) }
            }
            is UClass -> {
                current.sourcePsi?.let { return context.getLocation(it) }
            }
        }
        current = current.uastParent
    }
    return context.getLocation(element)
}
```

## Named Fixes

Give fixes descriptive names for the IDE menu:

```kotlin
LintFix.create()
    .name("Use existing constant 'CardHeight'")  // Shows in IDE
    .replace()
    .text("120.dp")
    .with("CardHeight")
    .build()
```

## Conditional Fixes

Only provide fix when a token mapping exists:

```kotlin
val token = TOKEN_MAP[hexValue]
val fix = if (token != null) {
    LintFix.create()
        .replace()
        .text("Color($hexValue)")
        .with(token)
        .build()
} else {
    null  // No quickfix available
}

context.report(ISSUE, location, message, fix)
```

## Key Points

1. Use `autoFix()` only for safe, unambiguous fixes
2. Use `alternatives()` when multiple valid options exist
3. Use `composite()` for multi-step fixes (insert + replace)
4. Use `name()` for clear menu descriptions
5. Use `select()` when user needs to customize the fix
6. Always test fixes with `expectFixDiffs()` or `expectContains()`
