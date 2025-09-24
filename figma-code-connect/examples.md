# Examples

## BpkButton with Icon Variants

```kotlin
@FigmaConnect(url = "https://www.figma.com/design/...")
@FigmaVariant(key = "Icon", value = "None")
class BpkButtonCodeConnect {
    @FigmaProperty(FigmaType.Text, "Label")
    val text: String = "Button"

    @FigmaProperty(FigmaType.Enum, "Style")
    val type: BpkButtonType = Figma.mapping(
        "Primary" to BpkButtonType.Primary,
        "Secondary" to BpkButtonType.Secondary,
    )

    @Composable
    fun ButtonExample() {
        BpkButton(text = text, type = type, onClick = { })
    }
}

@FigmaConnect(url = "https://www.figma.com/design/...")
@FigmaVariant(key = "Icon", value = "Left")
class BpkButtonIconLeftCodeConnect {
    @FigmaProperty(FigmaType.Text, "Label")
    val text: String = "Button"

    @Composable
    fun ButtonExample() {
        BpkButton(
            text = text,
            type = BpkButtonType.Primary,
            onClick = { },
            leadingIcon = { BpkIcon(BpkIcons.ArrowLeft, contentDescription = null) }
        )
    }
}
```

## BpkText Typography Variants

```kotlin
@FigmaConnect(url = "https://www.figma.com/design/...")
@FigmaVariant(key = "Style", value = "Hero 1")
class BpkTextHero1CodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(text = text, style = BpkTheme.typography.hero1)
    }
}

@FigmaConnect(url = "https://www.figma.com/design/...")
@FigmaVariant(key = "Style", value = "Body Default")
class BpkTextBodyDefaultCodeConnect {
    @FigmaProperty(FigmaType.Text, "Text Prop")
    val text: String = "Sample text"

    @Composable
    fun TextExample() {
        BpkText(text = text, style = BpkTheme.typography.bodyDefault)
    }
}
```

## BpkCard Simple Component

```kotlin
@FigmaConnect(url = "https://www.figma.com/design/...")
class BpkCardCodeConnect {
    @FigmaProperty(FigmaType.Instance, "Content")
    val content: @Composable () -> Unit = { Text("Card content") }

    @Composable
    fun CardExample() {
        BpkCard(onClick = { }, content = content)
    }
}
```

## Patterns

- **Single Class**: Simple components (BpkCard)
- **Separate Variants**: Distinct configurations (BpkButton icons)
- **Typography Pattern**: Many style variations (BpkText)
- **Keep it simple**: Direct property mapping works best
- **One variant per class**: Easier for parser and maintenance
