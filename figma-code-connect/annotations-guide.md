# Annotations Guide

## Setup

1. Check Figma Dev Mode for exact property names and values
2. Copy the component URL from Figma

## Core Annotations

### @FigmaConnect
Links your class to a Figma component:
```kotlin
@FigmaConnect(url = "https://www.figma.com/design/irZ3YBx8vOm16ICkAr7mB3/Backpack-Components?node-id=2965-0")
class BpkButtonCodeConnect {
    // Implementation
}
```

### @FigmaVariant
For components with multiple variants:
```kotlin
@FigmaVariant(key = "Icon", value = "None")
class BpkButtonCodeConnect { }

@FigmaVariant(key = "Icon", value = "Left")
class BpkButtonIconLeftCodeConnect { }
```

### @FigmaProperty
Maps Figma properties to Kotlin properties:

#### Text
```kotlin
@FigmaProperty(FigmaType.Text, "Label")
val text: String = "Button"
```

#### Boolean
```kotlin
@FigmaProperty(FigmaType.Boolean, "Enabled")
val enabled: Boolean = true
```

#### Enum
```kotlin
@FigmaProperty(FigmaType.Enum, "Style")
val type: BpkButtonType = Figma.mapping(
    "Primary" to BpkButtonType.Primary,
    "Secondary" to BpkButtonType.Secondary,
)
```

#### Instance (for nested components)
```kotlin
@FigmaProperty(FigmaType.Instance, "Icon")
val icon: (@Composable () -> Unit)? = null
```

## Implementation

```kotlin
@FigmaConnect(url = "...")
@FigmaVariant(key = "Type", value = "Standard")
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

## Rules

- Property names must match Figma Dev Mode exactly (case-sensitive)
- Use separate classes for different variants
- Keep property mapping simple - no complex logic
- File must end with `.figma.kt`