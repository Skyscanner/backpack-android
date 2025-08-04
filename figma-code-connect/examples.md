# Real Implementation Examples

## Overview

This guide showcases real implementations from the Backpack Android codebase, demonstrating different patterns and best practices for Figma Code Connect integration.

## 🔘 Example 1: BpkButton - Multiple Icon Variants

### Figma Component Analysis
- **Component**: Button with icon variations
- **Variants**: Icon position (None, Left, Right, Icon only)
- **Properties**: Style, State, Size, Icon
- **URL**: `https://www.figma.com/design/irZ3YBx8vOm16ICkAr7mB3/Backpack-Components?node-id=2965-0`

### Implementation Pattern: Separate Variant Classes

#### Variant 1: No Icon
```kotlin
@FigmaConnect(url = "https://www.figma.com/design/irZ3YBx8vOm16ICkAr7mB3/Backpack-Components?node-id=2965-0")
@FigmaVariant(key = "Icon", value = "None")
class BpkButtonCodeConnect {

    @FigmaProperty(FigmaType.Enum, "Style")
    val type: BpkButtonType = Figma.mapping(
        "Primary" to BpkButtonType.Primary,
        "Secondary" to BpkButtonType.Secondary,
        "Featured" to BpkButtonType.Featured,
        "Destructive" to BpkButtonType.Destructive,
        "Primary on Dark" to BpkButtonType.PrimaryOnDark,
        "Primary on Light" to BpkButtonType.PrimaryOnLight,
        "Secondary on Dark" to BpkButtonType.SecondaryOnDark,
        "Link" to BpkButtonType.Link,
        "Link on Dark" to BpkButtonType.LinkOnDark,
    )

    @FigmaProperty(FigmaType.Enum, "State")
    val enabled: Boolean = Figma.mapping(
        "Normal" to true,
        "Disabled" to false,
        "Loading" to true,
    )

    @FigmaProperty(FigmaType.Enum, "Size")
    val size: BpkButtonSize = Figma.mapping(
        "Small" to BpkButtonSize.Small,
        "Medium" to BpkButtonSize.Medium,
        "Large" to BpkButtonSize.Large,
    )

    @FigmaProperty(FigmaType.Text, "Label")
    val text: String = "Button"

    @Composable
    fun ButtonExample() {
        BpkButton(
            text = text,
            type = type,
            size = size,
            enabled = enabled,
            onClick = { },
        )
    }
}
```

#### Variant 2: Left Icon
```kotlin
@FigmaConnect(url = "https://www.figma.com/design/irZ3YBx8vOm16ICkAr7mB3/Backpack-Components?node-id=2965-0")
@FigmaVariant(key = "Icon", value = "Left")
class BpkButtonIconLeftCodeConnect {

    @FigmaProperty(FigmaType.Enum, "Style")
    val type: BpkButtonType = Figma.mapping(
        "Primary" to BpkButtonType.Primary,
        "Secondary" to BpkButtonType.Secondary,
        "Featured" to BpkButtonType.Featured,
        "Destructive" to BpkButtonType.Destructive,
    )

    @FigmaProperty(FigmaType.Enum, "Size")
    val size: BpkButtonSize = Figma.mapping(
        "Small" to BpkButtonSize.Small,
        "Medium" to BpkButtonSize.Medium,
        "Large" to BpkButtonSize.Large,
    )

    @FigmaProperty(FigmaType.Text, "Label")
    val text: String = "Button"

    @Composable
    fun ButtonExample() {
        BpkButton(
            text = text,
            type = type,
            size = size,
            onClick = { },
            leadingIcon = { BpkIcon(icon = BpkIcons.ArrowLeft, contentDescription = null) },
        )
    }
}
```

### Key Learnings
- **✅ Separate classes** for each icon variant keeps logic clean
- **✅ Shared properties** (Style, Size, Text) across variants for consistency
- **✅ Hardcoded icon** in leadingIcon - simpler than complex property mapping
- **✅ Realistic defaults** ("Button" for text, Primary for style)

## 📝 Example 2: BpkText - Typography Variants

### Figma Component Analysis
- **Component**: Text with typography style variations
- **Variants**: Typography styles (Hero1-5, Heading1-5, Body, etc.)
- **Properties**: Style, Text content
- **URL**: `https://www.figma.com/design/irZ3YBx8vOm16ICkAr7mB3/Backpack-Components?node-id=22608-25390`

### Implementation Pattern: Individual Typography Classes

#### Hero Typography Examples
```kotlin
@FigmaConnect(url = "https://www.figma.com/design/irZ3YBx8vOm16ICkAr7mB3/Backpack-Components?node-id=22608-25390")
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

@FigmaConnect(url = "https://www.figma.com/design/irZ3YBx8vOm16ICkAr7mB3/Backpack-Components?node-id=22608-25390")
@FigmaVariant(key = "Style", value = "Hero 2")
class BpkTextHero2CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.hero2,
        )
    }
}
```

#### Body Typography Examples
```kotlin
@FigmaConnect(url = "https://www.figma.com/design/irZ3YBx8vOm16ICkAr7mB3/Backpack-Components?node-id=22608-25390")
@FigmaVariant(key = "Style", value = "Body Default")
class BpkTextBodyDefaultCodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.bodyDefault,
        )
    }
}

@FigmaConnect(url = "https://www.figma.com/design/irZ3YBx8vOm16ICkAr7mB3/Backpack-Components?node-id=22608-25390")
@FigmaVariant(key = "Style", value = "Body Longform")
class BpkTextBodyLongformCodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(
            text = text,
            style = BpkTheme.typography.bodyLongform,
        )
    }
}
```

### Key Learnings
- **✅ Individual classes** for each typography style - 18 total classes
- **✅ Simple property mapping** - just text content
- **✅ Direct style references** - `BpkTheme.typography.hero1` vs complex mapping
- **✅ Consistent naming** - `BpkText[StyleName]CodeConnect` pattern
- **✅ Parser-friendly** - no complex logic, just direct property assignment

## 🃏 Example 3: BpkCard - Simple Component

### Figma Component Analysis
- **Component**: Basic card container
- **Variants**: None (single configuration)
- **Properties**: Content (instance), onClick interaction
- **URL**: `https://www.figma.com/design/irZ3YBx8vOm16ICkAr7mB3/Backpack-Components?node-id=card-node-id`

### Implementation Pattern: Single Class

```kotlin
@FigmaConnect(url = "https://www.figma.com/design/irZ3YBx8vOm16ICkAr7mB3/Backpack-Components?node-id=card-node-id")
class BpkCardCodeConnect {

    @FigmaProperty(FigmaType.Instance, "Content")
    val content: @Composable () -> Unit = {
        Text("Card content goes here")
    }

    @Composable
    fun CardExample() {
        BpkCard(
            onClick = { },
            content = content,
        )
    }
}
```

### Key Learnings
- **✅ No @FigmaVariant** needed for single-configuration components
- **✅ Instance property** for complex content
- **✅ Default content** provides clear example
- **✅ Simple onClick** - no complex interaction mapping

## 🎨 Pattern Comparison

### When to Use Each Pattern

| Pattern | Use Case | Example | Pros | Cons |
|---------|----------|---------|------|------|
| **Single Class** | Simple components with minimal variants | BpkCard | Simple, easy to maintain | Limited flexibility |
| **Separate Variant Classes** | Components with distinct configurations | BpkButton icons | Clean separation, parser-friendly | More files to maintain |
| **Typography Pattern** | Many style variations | BpkText styles | Explicit, no complex mapping | Many classes (18 for text) |

### Complexity Guidelines

```kotlin
// ✅ GOOD: Simple and direct
@FigmaVariant(key = "Style", value = "Hero 1")
class BpkTextHero1CodeConnect {
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(text = text, style = BpkTheme.typography.hero1)
    }
}

// ❌ BAD: Complex logic parser can't handle
@FigmaConnect(url = "...")
class BpkTextCodeConnect {
    @FigmaProperty(FigmaType.Enum, "Style")
    val style: TextStyle = when(figmaStyle) {
        "Hero 1" -> BpkTheme.typography.hero1
        "Hero 2" -> BpkTheme.typography.hero2
        // ... 16 more cases
        else -> BpkTheme.typography.bodyDefault
    }
}
```

## 🔧 Advanced Implementation Patterns

### Property Composition

```kotlin
// Multiple properties working together
@FigmaConnect(url = "...")
class BpkButtonAdvancedCodeConnect {

    @FigmaProperty(FigmaType.Text, "Label")
    val text: String = "Button"

    @FigmaProperty(FigmaType.Boolean, "Full Width")
    val fullWidth: Boolean = false

    @FigmaProperty(FigmaType.Boolean, "Loading")
    val loading: Boolean = false

    @Composable
    fun ButtonExample() {
        BpkButton(
            text = text,
            type = BpkButtonType.Primary,
            onClick = { },
            modifier = if (fullWidth) Modifier.fillMaxWidth() else Modifier,
            loading = loading,
        )
    }
}
```

### Conditional Rendering

```kotlin
// Conditional icon based on boolean property
@FigmaConnect(url = "...")
class BpkButtonConditionalCodeConnect {

    @FigmaProperty(FigmaType.Text, "Label")
    val text: String = "Button"

    @FigmaProperty(FigmaType.Boolean, "Show Icon")
    val showIcon: Boolean = false

    @Composable
    fun ButtonExample() {
        BpkButton(
            text = text,
            type = BpkButtonType.Primary,
            onClick = { },
            leadingIcon = if (showIcon) {
                { BpkIcon(BpkIcons.ArrowLeft, contentDescription = null) }
            } else null,
        )
    }
}
```

## 📊 Generated Output Examples

### BpkButton Generated Code
When designers use BpkButton in Figma, they see:

```kotlin
BpkButton(
    text = "Search flights",
    type = BpkButtonType.Primary,
    size = BpkButtonSize.Medium,
    enabled = true,
    onClick = { },
)
```

### BpkText Generated Code
When designers use BpkText with Hero 1 style:

```kotlin
BpkText(
    text = "Welcome to Backpack",
    style = BpkTheme.typography.hero1,
)
```

### Dynamic Property Values
Properties update based on Figma component configuration:

```kotlin
// When Style = "Secondary", Size = "Large"
BpkButton(
    text = "Continue",
    type = BpkButtonType.Secondary,
    size = BpkButtonSize.Large,
    enabled = true,
    onClick = { },
)

// When Style = "Featured", Size = "Small"
BpkButton(
    text = "Book now",
    type = BpkButtonType.Featured,
    size = BpkButtonSize.Small,
    enabled = true,
    onClick = { },
)
```

## 🎯 Best Practices Summary

### From Real Implementations

1. **Keep it simple**: Direct property mapping works best
2. **One variant per class**: Easier for parser and maintenance
3. **Meaningful defaults**: "Sample text", "Button", realistic values
4. **Consistent naming**: Follow `Bpk[Component][Variant]CodeConnect` pattern
5. **Test thoroughly**: Every variant should parse and generate clean code

### Proven Patterns

- ✅ **BpkButton pattern**: Separate classes for icon variants
- ✅ **BpkText pattern**: Individual classes for typography styles
- ✅ **BpkCard pattern**: Single class for simple components
- ✅ **Direct enum mapping**: `Figma.mapping()` for dropdowns
- ✅ **Boolean from enum**: Map enum values to boolean states

---

**Back to**: [Main Documentation](README.md) | **See Also**: [Annotations Guide](annotations-guide.md)
