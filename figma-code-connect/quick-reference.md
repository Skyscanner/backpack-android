# Quick Reference - Figma Code Connect

## 🚀 Essential Commands

```bash
# Setup & Installation
npm install -g @figma/code-connect
export FIGMA_ACCESS_TOKEN="your_token_here"
figma connect init

# Development & Testing
figma connect parse --verbose                    # Test all components
figma connect parse path/to/Component.figma.kt --verbose  # Test specific file
figma connect list                              # List all components
./gradlew :backpack-compose:compileDebugKotlin  # Kotlin compilation test

# Publishing
figma connect publish --dry-run                 # Test publishing (recommended)
figma connect publish                           # Publish to Figma
figma connect status                            # Check connection
```

## 📝 Annotation Quick Reference

### Basic Component
```kotlin
@FigmaConnect(url = "https://www.figma.com/design/...")
class BpkComponentCodeConnect {
    @FigmaProperty(FigmaType.Text, "Label")
    val text: String = "Default text"

    @Composable
    fun ComponentExample() {
        BpkComponent(text = text)
    }
}
```

### Component with Variants
```kotlin
@FigmaConnect(url = "https://www.figma.com/design/...")
@FigmaVariant(key = "Type", value = "Primary")
class BpkComponentPrimaryCodeConnect {
    // Implementation
}

@FigmaConnect(url = "https://www.figma.com/design/...")
@FigmaVariant(key = "Type", value = "Secondary")
class BpkComponentSecondaryCodeConnect {
    // Implementation
}
```

## 🎛️ Property Types

| Figma Type | Kotlin Type | Example |
|------------|-------------|---------|
| `FigmaType.Text` | `String` | `@FigmaProperty(FigmaType.Text, "Label")` |
| `FigmaType.Boolean` | `Boolean` | `@FigmaProperty(FigmaType.Boolean, "Enabled")` |
| `FigmaType.Enum` | Custom enum | `@FigmaProperty(FigmaType.Enum, "Style")` |
| `FigmaType.Instance` | `@Composable () -> Unit` | `@FigmaProperty(FigmaType.Instance, "Icon")` |

## 🔧 Common Patterns

### Enum Mapping
```kotlin
@FigmaProperty(FigmaType.Enum, "Style")
val type: BpkButtonType = Figma.mapping(
    "Primary" to BpkButtonType.Primary,
    "Secondary" to BpkButtonType.Secondary,
)
```

### Boolean from Enum
```kotlin
@FigmaProperty(FigmaType.Enum, "State")
val enabled: Boolean = Figma.mapping(
    "Normal" to true,
    "Disabled" to false,
)
```

### Conditional Properties
```kotlin
@FigmaProperty(FigmaType.Boolean, "Show Icon")
val showIcon: Boolean = false

@Composable
fun ComponentExample() {
    BpkComponent(
        icon = if (showIcon) { { BpkIcon(...) } } else null
    )
}
```

## ✅ Testing Checklist

- [ ] `./gradlew :backpack-compose:compileDebugKotlin` succeeds
- [ ] `figma connect parse --verbose` finds your component  
- [ ] `figma connect list` shows correct variant count
- [ ] `figma connect publish --dry-run` validates successfully
- [ ] Generated templates are valid Kotlin code
- [ ] Property names match Figma Dev Mode exactly
- [ ] Enum values are case-sensitive correct## 🚨 Common Issues

| Issue | Solution |
|-------|----------|
| "Property not found" | Check Figma Dev Mode for exact property name |
| "Invalid enum value" | Verify case-sensitive spelling |
| "Parse error" | Simplify property mapping, use separate variant classes |
| "Compilation error" | Check imports and Kotlin syntax |

## 📁 File Structure

```
backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/
├── button/
│   ├── BpkButton.kt
│   └── BpkButton.figma.kt
├── text/
│   ├── BpkText.kt
│   └── BpkText.figma.kt
└── component/
    ├── BpkComponent.kt
    └── BpkComponent.figma.kt
```

## 🎯 Success Criteria

✅ **Technical**: All components compile, parse correctly, generate valid code
✅ **Design Team**: Code snippets appear in Figma Dev Mode
✅ **Maintenance**: Easy to add new variants and update existing ones

---

**Full Documentation**: [README.md](README.md) | **Getting Started**: [getting-started.md](getting-started.md)
