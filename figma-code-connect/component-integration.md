# Component Integration Guide

## Overview

This guide shows you how to add Figma Code Connect to Backpack Android components. We'll cover the integration patterns, best practices, and step-by-step implementation.

## 🎯 Integration Patterns

### Pattern 1: Simple Component (Single Variant)
For components with minimal configuration options.

**Example: BpkCard**
```kotlin
@FigmaConnect(url = "https://www.figma.com/design/...")
class BpkCardCodeConnect {

    @FigmaProperty(FigmaType.Instance, "Content")
    val content: @Composable () -> Unit = { Text("Card content") }

    @Composable
    fun CardExample() {
        BpkCard(
            onClick = { },
            content = content,
        )
    }
}
```

### Pattern 2: Multiple Variants (Separate Classes)
For components with multiple distinct variants.

**Example: BpkButton with Icon Variants**
```kotlin
@FigmaConnect(url = "https://www.figma.com/design/...")
@FigmaVariant(key = "Icon", value = "None")
class BpkButtonCodeConnect {
    // Properties and composable function
}

@FigmaConnect(url = "https://www.figma.com/design/...")
@FigmaVariant(key = "Icon", value = "Left")
class BpkButtonIconLeftCodeConnect {
    // Properties and composable function
}
```

### Pattern 3: Typography Variants
For text components with multiple style options.

**Example: BpkText Typography Styles**
```kotlin
@FigmaConnect(url = "https://www.figma.com/design/...")
@FigmaVariant(key = "Style", value = "Hero 1")
class BpkTextHero1CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.hero1,
        )
    }
}
```

## 🛠️ Step-by-Step Integration

### Step 1: Analyze the Figma Component

1. **Open Figma Dev Mode** for your component
2. **Identify component properties**:
   - Which properties are configurable?
   - What are the possible values?
   - Are there variants or different states?

**Example properties you might find:**
- `Style`: Primary, Secondary, Featured
- `State`: Normal, Disabled, Loading
- `Size`: Small, Medium, Large
- `Icon`: None, Left, Right

### Step 2: Get the Figma URL

1. In Figma, select your component
2. Copy the URL from browser address bar
3. The URL should look like:
   ```
   https://www.figma.com/design/irZ3YBx8vOm16ICkAr7mB3/Backpack-Components?node-id=2965-0
   ```

### Step 3: Create the Code Connect File

Create `Bpk[Component]CodeConnect.kt` alongside your main component file:

```kotlin
/*
 * Backpack for Android - Skyscanner's Design System
 * ... (standard header)
 */

package net.skyscanner.backpack.compose.[component]

import androidx.compose.runtime.Composable
import com.figma.code.connect.Figma
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import com.figma.code.connect.FigmaVariant

@FigmaConnect(url = "YOUR_FIGMA_URL_HERE")
@FigmaVariant(key = "PropertyName", value = "PropertyValue") // If needed
class BpkComponentCodeConnect {

    @FigmaProperty(FigmaType.Enum, "Style")
    val style: ComponentStyle = Figma.mapping(
        "Primary" to ComponentStyle.Primary,
        "Secondary" to ComponentStyle.Secondary,
    )

    @Composable
    fun ComponentExample() {
        BpkComponent(
            style = style,
            // ... other properties
        )
    }
}
```

### Step 4: Define Properties

Map Figma properties to Kotlin properties using appropriate `FigmaType`:

#### Text Properties
```kotlin
@FigmaProperty(FigmaType.Text, "Label")
val text: String = "Button text"
```

#### Enum Properties (Dropdowns)
```kotlin
@FigmaProperty(FigmaType.Enum, "Style")
val type: BpkButtonType = Figma.mapping(
    "Primary" to BpkButtonType.Primary,
    "Secondary" to BpkButtonType.Secondary,
    "Featured" to BpkButtonType.Featured,
)
```

#### Boolean Properties (Toggles)
```kotlin
@FigmaProperty(FigmaType.Boolean, "Enabled")
val enabled: Boolean = true
```

#### Instance Properties (Nested Components)
```kotlin
@FigmaProperty(FigmaType.Instance, "Icon")
val icon: (@Composable () -> Unit)? = null
```

### Step 5: Handle Multiple Variants

If your component has multiple distinct variants, create separate classes:

```kotlin
// Base variant
@FigmaConnect(url = "...")
@FigmaVariant(key = "Icon", value = "None")
class BpkButtonCodeConnect { /* ... */ }

// Icon Left variant
@FigmaConnect(url = "...")
@FigmaVariant(key = "Icon", value = "Left")
class BpkButtonIconLeftCodeConnect { /* ... */ }

// Icon Right variant
@FigmaConnect(url = "...")
@FigmaVariant(key = "Icon", value = "Right")
class BpkButtonIconRightCodeConnect { /* ... */ }
```

### Step 6: Implement the Composable Function

```kotlin
@Composable
fun ComponentExample() {
    BpkComponent(
        text = text,
        type = type,
        enabled = enabled,
        onClick = { /* onClick logic */ },
        leadingIcon = if (icon != null) {
            { BpkIcon(icon = BpkIcons.ArrowLeft, contentDescription = null) }
        } else null,
    )
}
```

## 📋 Best Practices

### ✅ Do's

1. **Use descriptive class names**
   ```kotlin
   // ✅ Good
   class BpkTextHero1CodeConnect
   class BpkButtonIconLeftCodeConnect

   // ❌ Bad
   class BpkTextCodeConnect1
   class BpkButtonCodeConnect2
   ```

2. **Keep properties simple**
   ```kotlin
   // ✅ Good - Direct enum mapping
   @FigmaProperty(FigmaType.Enum, "Style")
   val type: BpkButtonType = Figma.mapping(...)

   // ❌ Bad - Complex logic
   @FigmaProperty(FigmaType.Enum, "Style")
   val type: BpkButtonType = when(figmaStyle) { ... }
   ```

3. **Provide meaningful default values**
   ```kotlin
   @FigmaProperty(FigmaType.Text, "Label")
   val text: String = "Button text"  // Clear example
   ```

4. **Use individual classes for variants**
   ```kotlin
   // ✅ Good - Separate classes
   class BpkTextHero1CodeConnect { style = BpkTheme.typography.hero1 }
   class BpkTextHero2CodeConnect { style = BpkTheme.typography.hero2 }

   // ❌ Bad - Complex property mapping
   class BpkTextCodeConnect {
       val style = when(figmaStyle) { "Hero 1" -> BpkTheme.typography.hero1 ... }
   }
   ```

### ❌ Don'ts

1. **Don't use complex `@Composable` properties**
   - The parser can't handle lambda expressions well
   - Keep properties simple and direct

2. **Don't hardcode values**
   ```kotlin
   // ❌ Bad
   BpkButton(text = "Click me", type = BpkButtonType.Primary)

   // ✅ Good
   BpkButton(text = text, type = type)
   ```

3. **Don't forget to check Figma Dev Mode**
   - Always verify property names match exactly
   - Check available values for enum properties

## 🔍 Property Mapping Reference

### Common Figma Property Types

| Figma Type | Kotlin Type | Usage |
|------------|-------------|-------|
| `Text` | `String` | Labels, descriptions, content |
| `Boolean` | `Boolean` | Toggles, enabled/disabled states |
| `Enum` | Custom enum/sealed class | Dropdown selections |
| `Instance` | `@Composable () -> Unit` | Nested components, icons |

### Enum Mapping Examples

```kotlin
// Button types
@FigmaProperty(FigmaType.Enum, "Style")
val type: BpkButtonType = Figma.mapping(
    "Primary" to BpkButtonType.Primary,
    "Secondary" to BpkButtonType.Secondary,
    "Featured" to BpkButtonType.Featured,
    "Destructive" to BpkButtonType.Destructive,
)

// Sizes
@FigmaProperty(FigmaType.Enum, "Size")
val size: BpkButtonSize = Figma.mapping(
    "Small" to BpkButtonSize.Small,
    "Medium" to BpkButtonSize.Medium,
    "Large" to BpkButtonSize.Large,
)

// States
@FigmaProperty(FigmaType.Enum, "State")
val enabled: Boolean = Figma.mapping(
    "Normal" to true,
    "Disabled" to false,
)
```

## 🧪 Testing Your Integration

After creating your Code Connect file:

### 1. Verify Syntax
```bash
./gradlew :backpack-compose:compileDebugKotlin
```

### 2. Test Parsing
```bash
figma connect parse --verbose
```

Look for your component in the output:
```json
{
  "component": "BpkComponent",
  "template": "BpkComponent(text = \"${text}\", type = ...)",
  "templateData": { "text": { "type": "string" } }
}
```

### 3. Test Specific File
```bash
figma connect parse backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/component/BpkComponentCodeConnect.kt --verbose
```

## 🚨 Common Issues & Solutions

### Issue: "Property not found in Figma"
**Solution**: Check Figma Dev Mode for exact property names and values

### Issue: "Parser errors with complex properties"
**Solution**: Simplify property mappings, use individual variant classes

### Issue: "Component not detected"
**Solution**: Ensure file ends with `CodeConnect.kt` and has proper annotations

### Issue: "Enum mapping errors"
**Solution**: Verify Figma enum values match exactly (case-sensitive)

---

**Next Step**: Learn about all available annotations in [Annotations Guide](annotations-guide.md)
