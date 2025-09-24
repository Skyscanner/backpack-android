# Component Integration

## Creating Code Connect Files

Create `Component.figma.kt` alongside your component:

```kotlin
@FigmaConnect(url = "https://www.figma.com/design/...")
@FigmaVariant(key = "Type", value = "Primary") // If multiple variants
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

## Integration Patterns

### Single Component
For simple components with minimal variants:
```kotlin
@FigmaConnect(url = "...")
class BpkCardCodeConnect {
    @FigmaProperty(FigmaType.Instance, "Content")
    val content: @Composable () -> Unit = { Text("Card content") }

    @Composable
    fun CardExample() {
        BpkCard(onClick = { }, content = content)
    }
}
```

### Multiple Variants
Create separate classes for distinct variants:
```kotlin
@FigmaConnect(url = "...")
@FigmaVariant(key = "Icon", value = "None")
class BpkButtonCodeConnect { /* ... */ }

@FigmaConnect(url = "...")
@FigmaVariant(key = "Icon", value = "Left")
class BpkButtonIconLeftCodeConnect { /* ... */ }
```

### Typography Styles
Individual classes for each style variant:
```kotlin
@FigmaConnect(url = "...")
@FigmaVariant(key = "Style", value = "Hero 1")
class BpkTextHero1CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(text = text, style = BpkTheme.typography.hero1)
    }
}
```

## Best Practices

- Use separate classes for distinct variants
- Keep property mappings simple and direct
- Match Figma property names exactly (case-sensitive)
- Provide meaningful default values
- Test with `figma connect parse --verbose`
