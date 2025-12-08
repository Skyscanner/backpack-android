# Figma Code Connect

Show real Android code snippets in Figma Dev Mode for Backpack components.

## Setup (One-time)

**1. Get a Figma token**
- Go to https://www.figma.com/settings → Personal Access Tokens → Create new token

**2. Set the token**
```bash
export FIGMA_ACCESS_TOKEN="your_token"
```
Add this to your `.zshrc` or `.bashrc` to persist it.

**3. Verify it works**
```bash
npx figma connect publish --dry-run
```

## Add Code Connect to a Component

**1. Create the file**

Create `ComponentName.figma.kt` in `app/src/main/java/net/skyscanner/backpack/demo/figma/componentname/`

**2. Get the Figma node URL**

In Figma: Right-click component → Copy link

**3. Write the code**

```kotlin
package net.skyscanner.backpack.demo.figma.componentname

import androidx.compose.runtime.Composable
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType

@FigmaConnect(url = "https://www.figma.com/design/xxx?node-id=xxx")
class BpkComponentCodeConnect {

    @FigmaProperty(FigmaType.Text, "Label")  // Must match Figma property name exactly
    val text: String = "Default"

    @Composable
    fun Example() {
        BpkComponent(text = text)
    }
}
```

**4. Test it**
```bash
npx figma connect publish --dry-run
```

**5. Publish it**
```bash
npx figma connect publish
```

## Property Mappings

### Text
```kotlin
@FigmaProperty(FigmaType.Text, "Label")
val text: String = "Default"
```

### Boolean
```kotlin
@FigmaProperty(FigmaType.Boolean, "Enabled")
val enabled: Boolean = true
```

### Enum (dropdown in Figma)
```kotlin
@FigmaProperty(FigmaType.Enum, "Style")
val type: BpkButtonType = Figma.mapping(
    "Primary" to BpkButtonType.Primary,
    "Secondary" to BpkButtonType.Secondary,
)
```

### Multiple states from one property
```kotlin
// Same Figma property can map to different Kotlin properties
@FigmaProperty(FigmaType.Enum, "State")
val enabled: Boolean = Figma.mapping(
    "Normal" to true,
    "Disabled" to false,
)

@FigmaProperty(FigmaType.Enum, "State")
val loading: Boolean = Figma.mapping(
    "Normal" to false,
    "Loading" to true,
)
```

## Handling Variants

When a Figma component has variants (e.g., Button with Icon: None/Left/Right), create separate classes:

```kotlin
@FigmaConnect(url = "https://...")
@FigmaVariant(key = "Icon", value = "None")
class BpkButtonCodeConnect {
    @Composable
    fun Example() {
        BpkButton(text = "Label", onClick = { })
    }
}

@FigmaConnect(url = "https://...")
@FigmaVariant(key = "Icon", value = "Left")
class BpkButtonLeftIconCodeConnect {
    @Composable
    fun Example() {
        BpkButton(
            text = "Label",
            icon = BpkIcon.ArrowRight,
            position = BpkButtonIconPosition.Start,
            onClick = { },
        )
    }
}
```

## Troubleshooting

| Problem | Solution |
|---------|----------|
| "Property not found" | Open Figma Dev Mode, check exact property name (case-sensitive) |
| "Invalid enum value" | Check exact spelling of enum values in Figma |
| Parse errors | Simplify - use separate classes per variant |
| Auth failed | Check `echo $FIGMA_ACCESS_TOKEN` |

## CI/CD

- **PRs**: Automatically validated with `--dry-run`
- **Releases**: Automatically published to Figma

Required secret: `FIGMA_ACCESS_TOKEN`
