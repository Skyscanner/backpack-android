# Testing Lint Rules

Guide to writing effective tests for Android Lint detectors.

## Basic Test Structure

```kotlin
@Suppress("UnstableApiUsage")
class MyDetectorTest {

    @Test
    fun `detects hardcoded value`() {
        val code = kotlin("""
            package test
            import androidx.compose.ui.unit.dp

            fun test() {
                val size = 16.dp
            }
        """).indented()

        lint().files(code, dpStub())
            .allowMissingSdk()
            .issues(MyDetector.ISSUE)
            .run()
            .expectErrorCount(1)
            .expectContains("Use BpkSpacing")
    }
}
```

## Creating Stubs

Stubs provide fake implementations so lint can resolve types.

### Simple Stub

```kotlin
private fun dpStub() = kotlin("""
    package androidx.compose.ui.unit

    class Dp
    val Int.dp: Dp get() = Dp()
""").indented()
```

### Stub with Multiple Types

```kotlin
private fun colorStub() = kotlin("""
    package androidx.compose.ui.graphics

    class Color {
        constructor(value: Long)

        companion object {
            @JvmField val Unspecified: Color = Color(0)
            @JvmField val Transparent: Color = Color(0)
            @JvmField val Red: Color = Color(0)
        }
    }
""").indented()
```

### Stub for Extension Functions

```kotlin
private fun modifierStub() = kotlin("""
    package androidx.compose.ui
    import androidx.compose.ui.unit.Dp

    class Modifier {
        fun size(size: Dp): Modifier = this
        fun width(width: Dp): Modifier = this
        fun height(height: Dp): Modifier = this
        fun padding(all: Dp): Modifier = this

        companion object : Modifier()
    }
""").indented()
```

## Assertion Methods

### Check Error Count

```kotlin
.run()
.expectErrorCount(1)
.expectWarningCount(2)
```

### Check Message Content

```kotlin
.run()
.expectContains("Use BpkSpacing.Md")
.expectContains("instead of 16.dp")
```

### Check Clean Code

```kotlin
.run()
.expectClean()
```

### Check Exact Output

```kotlin
.run()
.expect("""
    src/test/Test.kt:5: Error: Use BpkTheme.colors [HardcodedColor]
        val color = Color.Red
                    ~~~~~~~~~
    1 errors, 0 warnings
""".trimIndent())
```

## Testing Quickfixes

### Verify Fix Exists

```kotlin
.run()
.expectContains("Use BpkSpacing.Md")  // Fix name in output
```

### Verify Fix Diff (when order is guaranteed)

```kotlin
.run()
.expectFixDiffs("""
    Fix for src/test/Test.kt line 5: Replace with BpkSpacing.Md:
    @@ -5 +5
    -     val size = 16.dp
    +     val size = BpkSpacing.Md
""".trimIndent())
```

### Multiple Alternatives

When fix order isn't guaranteed, check content instead:

```kotlin
.run()
.expectContains("ItemHeight")
.expectContains("ComponentHeight")
.expectContains("ContentHeight")
```

## Testing Patterns

### Test Detection

```kotlin
@Test
fun `detects hardcoded dp in size method`() {
    val code = kotlin("""
        package test
        import androidx.compose.ui.Modifier
        import androidx.compose.ui.unit.dp

        fun test() = Modifier.size(100.dp)
    """).indented()

    lint().files(code, modifierStub(), dpStub())
        .allowMissingSdk()
        .issues(HardcodedSizeDetector.ISSUE)
        .run()
        .expectWarningCount(1)
}
```

### Test Clean Code (No False Positives)

```kotlin
@Test
fun `allows named constant`() {
    val code = kotlin("""
        package test
        import androidx.compose.ui.Modifier
        import androidx.compose.ui.unit.dp

        private val ItemSize = 100.dp

        fun test() = Modifier.size(ItemSize)
    """).indented()

    lint().files(code, modifierStub(), dpStub())
        .allowMissingSdk()
        .issues(HardcodedSizeDetector.ISSUE)
        .run()
        .expectClean()
}
```

### Test Dynamic Values Allowed

```kotlin
@Test
fun `allows dynamic color from variable`() {
    val code = kotlin("""
        package test
        import androidx.compose.ui.graphics.Color

        fun test(colorValue: Long) = Color(colorValue)
    """).indented()

    lint().files(code, colorStub())
        .allowMissingSdk()
        .issues(HardcodedComposeColorDetector.ISSUE)
        .run()
        .expectClean()
}
```

### Test Existing Constant Suggestion

```kotlin
@Test
fun `suggests existing constant with same value`() {
    val code = kotlin("""
        package test
        import androidx.compose.ui.Modifier
        import androidx.compose.ui.unit.dp

        private val CardHeight = 120.dp

        fun test() = Modifier.height(120.dp)
    """).indented()

    lint().files(code, modifierStub(), dpStub())
        .allowMissingSdk()
        .issues(HardcodedSizeDetector.ISSUE)
        .run()
        .expectContains("Existing constant(s) with same value: CardHeight")
}
```

## Common Issues

### Test Passes But Real Code Fails

**Cause**: Type resolution works in tests (stubs) but fails in real codebase.

**Fix**: Use Kotlin PSI or reference detection instead of constructor/method detection.

### Stub Not Resolving

**Check**:
- Package matches real package exactly
- Class/function signatures match
- All required types are stubbed

### Fix Order Not Guaranteed

**Issue**: `expectFixDiffs()` fails because alternatives appear in different order.

**Fix**: Use `expectContains()` to check fix content without order dependency.

## Key Points

1. Always use `.allowMissingSdk()` in tests
2. Create minimal stubs that match real signatures
3. Test both detection AND clean code cases
4. Test quickfix suggestions exist
5. Use `expectContains()` when order varies
6. Stub all dependent types (Color needs Long, Modifier needs Dp, etc.)
