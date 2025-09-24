# Figma Code Connect Annotations Guide

## Overview

This guide provides a complete reference for all Figma Code Connect annotations, when to use them, and how to check Figma Dev Mode for the correct property mappings.

## 🔍 Always Check Figma Dev Mode First

Before writing any annotations, **always open your component in Figma Dev Mode** to see:

1. **Available properties** and their exact names
2. **Property types** (text, boolean, enum, etc.)
3. **Possible values** for enum properties
4. **Component variants** and their configurations

### How to Access Dev Mode
1. Open your Figma file
2. Select the component you want to integrate
3. Toggle **Dev Mode** (top right corner)
4. Inspect the component properties panel

## 📝 Core Annotations

### @FigmaConnect

**Purpose**: Links your Kotlin class to a specific Figma component

**Usage**:
```kotlin
@FigmaConnect(url = "https://www.figma.com/design/fileId/fileName?node-id=nodeId")
class BpkComponentCodeConnect {
    // Component implementation
}
```

**How to get the URL**:
1. Select your component in Figma
2. Copy the browser URL
3. Use the complete URL including node-id

**Example**:
```kotlin
@FigmaConnect(url = "https://www.figma.com/design/irZ3YBx8vOm16ICkAr7mB3/Backpack-Components?node-id=2965-0")
class BpkButtonCodeConnect {
    // Button implementation
}
```

### @FigmaVariant

**Purpose**: Specifies which variant of a component this class represents

**When to use**:
- Component has multiple distinct configurations
- Different variants need different property mappings
- You want to keep variant logic separate and clean

**Usage**:
```kotlin
@FigmaConnect(url = "...")
@FigmaVariant(key = "PropertyName", value = "PropertyValue")
class BpkComponentVariantCodeConnect {
    // Variant-specific implementation
}
```

**Real Examples**:

```kotlin
// Button with no icon
@FigmaVariant(key = "Icon", value = "None")
class BpkButtonCodeConnect { /* ... */ }

// Button with left icon
@FigmaVariant(key = "Icon", value = "Left")
class BpkButtonIconLeftCodeConnect { /* ... */ }

// Text with Hero 1 style
@FigmaVariant(key = "Style", value = "Hero 1")
class BpkTextHero1CodeConnect { /* ... */ }

// Text with Heading 1 style
@FigmaVariant(key = "Style", value = "Heading 1")
class BpkTextHeading1CodeConnect { /* ... */ }
```

**Best Practice**: Create one class per major variant to keep code clean and parser-friendly.

### @FigmaProperty

**Purpose**: Maps Figma component properties to Kotlin class properties

**Syntax**:
```kotlin
@FigmaProperty(type, "PropertyName")
val propertyName: KotlinType = defaultValue
```

## 🎛️ Property Types Reference

### FigmaType.Text

**When to use**: For any text input in Figma (labels, descriptions, content)

**Kotlin mapping**: `String`

**Examples**:
```kotlin
@FigmaProperty(FigmaType.Text, "Label")
val text: String = "Button text"

@FigmaProperty(FigmaType.Text, "Text Prop")
val content: String = "Sample content"

@FigmaProperty(FigmaType.Text, "Placeholder")
val placeholder: String = "Enter text..."
```

**In Figma Dev Mode**: Look for text input fields

### FigmaType.Boolean

**When to use**: For toggle switches, checkboxes, or true/false states

**Kotlin mapping**: `Boolean`

**Examples**:
```kotlin
@FigmaProperty(FigmaType.Boolean, "Enabled")
val enabled: Boolean = true

@FigmaProperty(FigmaType.Boolean, "Show Icon")
val showIcon: Boolean = false

@FigmaProperty(FigmaType.Boolean, "Full Width")
val fullWidth: Boolean = false
```

**In Figma Dev Mode**: Look for toggle switches or boolean properties

### FigmaType.Enum

**When to use**: For dropdown selections, multiple choice options

**Kotlin mapping**: Any enum class, sealed class, or mapped boolean

**Basic Enum Example**:
```kotlin
@FigmaProperty(FigmaType.Enum, "Style")
val type: BpkButtonType = Figma.mapping(
    "Primary" to BpkButtonType.Primary,
    "Secondary" to BpkButtonType.Secondary,
    "Featured" to BpkButtonType.Featured,
    "Destructive" to BpkButtonType.Destructive,
)
```

**Boolean from Enum Example**:
```kotlin
@FigmaProperty(FigmaType.Enum, "State")
val enabled: Boolean = Figma.mapping(
    "Normal" to true,
    "Disabled" to false,
    "Loading" to true,
)
```

**Size Enum Example**:
```kotlin
@FigmaProperty(FigmaType.Enum, "Size")
val size: BpkButtonSize = Figma.mapping(
    "Small" to BpkButtonSize.Small,
    "Medium" to BpkButtonSize.Medium,
    "Large" to BpkButtonSize.Large,
)
```

**In Figma Dev Mode**: Look for dropdown menus with multiple options

⚠️ **Important**: Property values must match **exactly** (case-sensitive) with Figma Dev Mode

### FigmaType.Instance

**When to use**: For nested components, icons, or complex content

**Kotlin mapping**: `@Composable () -> Unit` or nullable version

**Examples**:
```kotlin
@FigmaProperty(FigmaType.Instance, "Icon")
val icon: (@Composable () -> Unit)? = null

@FigmaProperty(FigmaType.Instance, "Leading Icon")
val leadingIcon: (@Composable () -> Unit)? = null

@FigmaProperty(FigmaType.Instance, "Content")
val content: @Composable () -> Unit = { Text("Default content") }
```

**In Figma Dev Mode**: Look for component instances or slots for other components

## 🎯 Annotation Patterns by Use Case

### Single Component (No Variants)

**When**: Component has one main configuration with customizable properties

```kotlin
@FigmaConnect(url = "...")
class BpkComponentCodeConnect {

    @FigmaProperty(FigmaType.Text, "Label")
    val text: String = "Default text"

    @FigmaProperty(FigmaType.Enum, "Style")
    val style: ComponentStyle = Figma.mapping(
        "Primary" to ComponentStyle.Primary,
        "Secondary" to ComponentStyle.Secondary,
    )

    @Composable
    fun ComponentExample() {
        BpkComponent(text = text, style = style)
    }
}
```

### Multiple Variants (Separate Classes)

**When**: Component has distinct variants that require different implementations

```kotlin
// Variant 1
@FigmaConnect(url = "...")
@FigmaVariant(key = "Type", value = "Standard")
class BpkComponentStandardCodeConnect {
    @FigmaProperty(FigmaType.Text, "Label")
    val text: String = "Standard button"

    @Composable
    fun ComponentExample() {
        BpkComponent(text = text, type = ComponentType.Standard)
    }
}

// Variant 2
@FigmaConnect(url = "...")
@FigmaVariant(key = "Type", value = "Icon")
class BpkComponentIconCodeConnect {
    @FigmaProperty(FigmaType.Text, "Label")
    val text: String = "Icon button"

    @Composable
    fun ComponentExample() {
        BpkComponent(
            text = text,
            type = ComponentType.Icon,
            icon = { BpkIcon(BpkIcons.ArrowRight, contentDescription = null) }
        )
    }
}
```

### Typography/Style Variants

**When**: Component has many style variations (like text typography)

```kotlin
@FigmaConnect(url = "...")
@FigmaVariant(key = "Style", value = "Hero 1")
class BpkTextHero1CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Hero text"

    @Composable
    fun TextExample() {
        BpkText(text = text, style = BpkTheme.typography.hero1)
    }
}

@FigmaConnect(url = "...")
@FigmaVariant(key = "Style", value = "Body Default")
class BpkTextBodyDefaultCodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Body text"

    @Composable
    fun TextExample() {
        BpkText(text = text, style = BpkTheme.typography.bodyDefault)
    }
}
```

## 🔧 Advanced Property Mapping

### Complex Enum Mappings

```kotlin
@FigmaProperty(FigmaType.Enum, "Button Type")
val buttonConfig: ButtonConfig = Figma.mapping(
    "Primary" to ButtonConfig(
        type = BpkButtonType.Primary,
        size = BpkButtonSize.Medium
    ),
    "Secondary Large" to ButtonConfig(
        type = BpkButtonType.Secondary,
        size = BpkButtonSize.Large
    ),
)
```

### Conditional Properties

```kotlin
@FigmaProperty(FigmaType.Boolean, "Has Icon")
val hasIcon: Boolean = false

@Composable
fun ButtonExample() {
    BpkButton(
        text = text,
        leadingIcon = if (hasIcon) {
            { BpkIcon(BpkIcons.ArrowLeft, contentDescription = null) }
        } else null
    )
}
```

## 📋 Validation Checklist

Before testing your annotations:

### Figma Dev Mode Check
- [ ] Property names match exactly (case-sensitive)
- [ ] All enum values are correctly spelled
- [ ] Property types are appropriate (Text, Boolean, Enum, Instance)
- [ ] Component URL is correct and includes node-id

### Code Structure Check
- [ ] Class name follows `Bpk[Component][Variant]CodeConnect` pattern
- [ ] File name ends with `.figma.kt`
- [ ] All required imports are present
- [ ] Default values are meaningful

### Functional Check
- [ ] `@Composable` function uses all defined properties
- [ ] No hardcoded values in the composable
- [ ] Component renders correctly with default values

## 🚨 Common Pitfalls

### ❌ Wrong Property Names
```kotlin
// Figma Dev Mode shows: "Button Style"
@FigmaProperty(FigmaType.Enum, "Style")  // ❌ Wrong!
@FigmaProperty(FigmaType.Enum, "Button Style")  // ✅ Correct!
```

### ❌ Case Sensitivity
```kotlin
// Figma shows: "Primary"
Figma.mapping(
    "primary" to BpkButtonType.Primary,  // ❌ Wrong case!
    "Primary" to BpkButtonType.Primary,  // ✅ Correct!
)
```

### ❌ Complex Property Logic
```kotlin
// ❌ Parser can't handle this complexity
@FigmaProperty(FigmaType.Enum, "Style")
val style: TextStyle = when(figmaStyle) {
    "Hero 1" -> BpkTheme.typography.hero1
    "Hero 2" -> if (isDarkMode) darkHero2 else lightHero2
    else -> BpkTheme.typography.bodyDefault
}

// ✅ Keep it simple - use separate variant classes instead
```

## 🔍 Debugging Tips

### Check Parser Output
```bash
figma connect parse --verbose | grep -A 10 "YourComponent"
```

### Validate Property Mapping
```bash
figma connect parse backpack-compose/src/main/kotlin/path/to/Your.figma.kt --verbose
```

### Common Error Messages
- **"Property not found"**: Check Figma Dev Mode for exact property name
- **"Invalid enum value"**: Verify case-sensitive enum value matching
- **"Parse error"**: Simplify property mapping logic

---

**Next Step**: Learn how to test and publish your components in [Testing & Publishing](testing-publishing.md)
