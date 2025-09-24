# Quick Reference

## Commands

```bash
# Testing
npx @figma/code-connect parse --verbose
npx @figma/code-connect list
./gradlew :backpack-compose:compileDebugKotlin

# Publishing
npx @figma/code-connect publish --dry-run
npx @figma/code-connect publish
```

## Annotations

```kotlin
@FigmaConnect(url = "https://www.figma.com/design/...")
@FigmaVariant(key = "Type", value = "Primary") // If variants
class BpkComponentCodeConnect {
    @FigmaProperty(FigmaType.Text, "Label")
    val text: String = "Default text"

    @FigmaProperty(FigmaType.Enum, "Style")
    val type: ComponentType = Figma.mapping(
        "Primary" to ComponentType.Primary,
        "Secondary" to ComponentType.Secondary,
    )

    @Composable
    fun ComponentExample() {
        BpkComponent(text = text, type = type)
    }
}
```

## Property Types

| Type | Kotlin | Usage |
|------|--------|--------|
| `FigmaType.Text` | `String` | Labels, content |
| `FigmaType.Boolean` | `Boolean` | Toggles |
| `FigmaType.Enum` | Custom enum | Dropdowns |
| `FigmaType.Instance` | `@Composable () -> Unit` | Nested components |

## Common Issues

| Error | Fix |
|-------|-----|
| "Property not found" | Check Figma Dev Mode for exact name |
| "Invalid enum value" | Verify case-sensitive spelling |
| "Parse error" | Use separate variant classes |
