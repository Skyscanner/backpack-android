# Detection Patterns

Advanced patterns for smart lint detection.

## Finding Existing Constants

Scan file for existing constants to suggest reuse or generate unique names.

### Data Class for Constants

```kotlin
private data class DpConstant(val name: String, val value: Int)

private val DP_CONSTANT_PATTERN = Regex("""\bval\s+(\w+)\s*=\s*(\d+)\.dp""")
```

### Find All Constants in File

```kotlin
private fun findAllDpConstants(element: UElement): List<DpConstant> {
    val file = element.getContainingUFile() ?: return emptyList()
    val constants = mutableListOf<DpConstant>()

    file.accept(object : AbstractUastVisitor() {
        override fun visitFile(node: UFile): Boolean {
            val source = node.sourcePsi?.text ?: return false
            DP_CONSTANT_PATTERN.findAll(source).forEach { match ->
                val name = match.groupValues[1]
                val value = match.groupValues[2].toIntOrNull()
                if (value != null) {
                    constants.add(DpConstant(name, value))
                }
            }
            return true
        }
    })

    return constants.distinctBy { it.name }
}
```

### Separate Same Value vs All Names

```kotlin
val allConstants = findAllDpConstants(element)
val sameValueConstants = allConstants.filter { it.value == targetValue }.map { it.name }
val allConstantNames = allConstants.map { it.name }.toSet()
```

## Generating Unique Names

Avoid suggesting names that already exist.

```kotlin
private fun generateUniqueName(baseName: String, existingNames: Set<String>): String {
    if (baseName !in existingNames) {
        return baseName
    }
    var counter = 2
    while ("$baseName$counter" in existingNames) {
        counter++
    }
    return "$baseName$counter"
}
```

### Usage

```kotlin
val baseName = "ItemHeight"
val uniqueName = generateUniqueName(baseName, allConstantNames)
// Returns "ItemHeight" if not exists, or "ItemHeight2", "ItemHeight3", etc.
```

## Detecting Hardcoded vs Dynamic Values

Allow dynamic values from variables while blocking hardcoded literals.

### Extract Hex Value Only From Literals

```kotlin
private fun extractHexValue(argument: UElement?): String? {
    if (argument is ULiteralExpression) {
        val value = argument.value
        if (value is Long || value is Int) {
            val hex = value.toString().toLongOrNull()?.toString(16)?.uppercase()
            return hex?.let { "0x${it.padStart(8, '0')}" }
        }
    }
    return null  // Not a literal - allow dynamic values
}
```

### Usage in Detection

```kotlin
override fun visitConstructor(context: JavaContext, node: UCallExpression, constructor: PsiMethod) {
    val arguments = node.valueArguments
    if (arguments.isNotEmpty()) {
        val hexValue = extractHexValue(arguments.firstOrNull())
        if (hexValue != null) {
            // Hardcoded literal - report error
            context.report(ISSUE, context.getLocation(node), message)
        }
        // If hexValue is null, it's a variable/expression - allow it
    }
}
```

### What Gets Detected vs Allowed

```kotlin
// DETECTED (hardcoded literals):
Color(0xFFFF0000)
Color.Red

// ALLOWED (dynamic values):
Color(packageUiModel.brandColor.toColorInt())
Color(getColorFromBackend())
Color(colorValue)  // variable
```

## Finding Parent Method Context

Check if expression is inside specific method calls. Use `UastTreeUtils` for this.

```kotlin
// Use the utility function - traverses until root
val methodName = UastTreeUtils.findContainingMethodName(dpExpression, SIZE_METHODS)
if (methodName != null) {
    // Expression is inside a size method - report
}
```

### Available in UastTreeUtils

```kotlin
// Check if inside method call (returns Boolean)
UastTreeUtils.isInsideMethodCall(element, methodNames)

// Get containing method name (returns String?)
UastTreeUtils.findContainingMethodName(element, methodNames)

// Extract numeric value from 16.dp (returns Int?)
UastTreeUtils.extractNumericDpValue(expression)

// Find insertion point for adding code (returns Location)
UastTreeUtils.findInsertionPoint(context, element)
```

## Extracting Numeric Values

Get integer value from `.dp` expressions.

```kotlin
fun extractNumericDpValue(expression: UQualifiedReferenceExpression): Int? {
    val receiver = expression.receiver
    if (receiver is ULiteralExpression) {
        val value = receiver.value
        if (value is Number) {
            return value.toInt()
        }
    }
    return null
}
```

## Token Mapping Pattern

Map values to design tokens.

```kotlin
private val SPACING_TOKEN_MAP = mapOf(
    4 to "BpkSpacing.Sm",
    8 to "BpkSpacing.Md",
    16 to "BpkSpacing.Base",
    24 to "BpkSpacing.Lg",
)

private fun getSuggestion(value: Int): String {
    val token = SPACING_TOKEN_MAP[value]
    return if (token != null) {
        "Use $token instead of $value.dp"
    } else {
        val available = SPACING_TOKEN_MAP.entries.joinToString { "${it.key}.dp → ${it.value}" }
        "Use BpkSpacing.* instead. Available: $available"
    }
}
```

## Avoiding Duplicate Reports

Track reported locations to prevent duplicate errors.

```kotlin
class MyDetector : Detector(), SourceCodeScanner {
    private val reportedLocations = mutableSetOf<String>()

    override fun beforeCheckFile(context: Context) {
        reportedLocations.clear()
    }

    private fun reportOnce(context: JavaContext, element: UElement, message: String) {
        val key = "${context.file.path}:${element.sourcePsi?.textRange?.startOffset}"
        if (key in reportedLocations) return
        reportedLocations.add(key)
        context.report(ISSUE, context.getLocation(element), message)
    }
}
```

## Regex Pattern Tips

### Match val Declarations Only

```kotlin
// BAD: Matches parameter assignments like "width = 1.dp"
Regex("""(\w+)\s*=\s*(\d+)\.dp""")

// GOOD: Only matches val declarations (any visibility)
Regex("""\bval\s+(\w+)\s*=\s*(\d+)\.dp""")
```

### Extract Multiple Values

```kotlin
val TYPOGRAPHY_PATTERN = Regex("""TextStyle\(.*fontSize\s*=\s*(\d+)\.sp.*fontWeight\s*=\s*FontWeight\.(\w+)""")

val match = TYPOGRAPHY_PATTERN.find(callText)
val fontSize = match?.groupValues?.get(1)?.toIntOrNull()
val fontWeight = match?.groupValues?.get(2)
```

## Key Points

1. Use `ULiteralExpression` check to distinguish hardcoded vs dynamic values
2. Scan entire file for existing constants before suggesting new names
3. Generate unique names with number suffix to avoid conflicts
4. Use regex with `\bval\s+` to match only val declarations (any visibility)
5. Track reported locations to prevent duplicates
6. Use `UastTreeUtils` for common tree traversal operations
