# BpkSwapButton

BpkSwapButton is a round button component that rotates 180 degrees when clicked. It's typically used for swapping between two states or reversing an action, such as swapping origin and destination fields in a travel app.

## Installation

Ensure that you have the Backpack Compose dependency in your `build.gradle`:

```kotlin
dependencies {
    implementation 'net.skyscanner:backpack-compose:<version>'
}
```

## Usage

### Basic Usage

```kotlin
BpkSwapButton(
    onClick = { /* Handle swap action */ },
    contentDescription = "Swap origin and destination",
)
```

### With Different Styles

```kotlin
// Canvas Default (default)
BpkSwapButton(
    onClick = { /* Handle swap action */ },
    style = BpkSwapButtonStyle.CanvasDefault,
    contentDescription = "Swap",
)

// Canvas Contrast
BpkSwapButton(
    onClick = { /* Handle swap action */ },
    style = BpkSwapButtonStyle.CanvasContrast,
    contentDescription = "Swap",
)

// Surface Contrast
BpkSwapButton(
    onClick = { /* Handle swap action */ },
    style = BpkSwapButtonStyle.SurfaceContrast,
    contentDescription = "Swap",
)
```

### With Enabled State

```kotlin
BpkSwapButton(
    onClick = { /* Handle swap action */ },
    enabled = false,
    contentDescription = "Swap",
)
```

## API

### BpkSwapButton

```kotlin
@Composable
fun BpkSwapButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: BpkSwapButtonStyle = BpkSwapButtonStyle.CanvasDefault,
    contentDescription: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
)
```

#### Parameters

- `onClick: () -> Unit` - Callback invoked when the button is clicked. The rotation animation is handled internally.
- `modifier: Modifier = Modifier` - Modifier to be applied to the button for layout and styling customization.
- `enabled: Boolean = true` - Whether the button is enabled and responds to clicks.
- `style: BpkSwapButtonStyle = BpkSwapButtonStyle.CanvasDefault` - The visual style of the button. Available options:
  - `CanvasDefault` - Light background with canvas border
  - `CanvasContrast` - Contrast canvas background with canvas border
  - `SurfaceContrast` - Surface contrast background with accent border
- `contentDescription: String? = null` - Content description for accessibility. Should be provided for TalkBack users.
- `interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }` - Interaction source to observe interactions with the button.

### BpkSwapButtonStyle

Enum class defining the available button styles:

```kotlin
enum class BpkSwapButtonStyle {
    CanvasDefault,
    CanvasContrast,
    SurfaceContrast,
}
```

## Accessibility

BpkSwapButton is fully accessible:

- **Screen Reader Support**: Provide a meaningful `contentDescription` parameter to describe the swap action to TalkBack users.
- **Keyboard Navigation**: The button is fully keyboard accessible and can be activated using the Enter key.
- **Disabled State**: When disabled, the button is not interactive and screen readers announce it as disabled.
- **Touch Target**: The button has a 48dp touch target size, meeting the minimum accessibility requirements.

## Examples

### In a Travel App

```kotlin
var showRotation by remember { mutableStateOf(false) }

Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically,
) {
    Column(modifier = Modifier.weight(1f)) {
        BpkText("From")
        BpkText("London (LHR)")
    }

    BpkSwapButton(
        onClick = { /* Swap locations */ },
        contentDescription = "Swap origin and destination",
    )

    Column(modifier = Modifier.weight(1f)) {
        BpkText("To")
        BpkText("Paris (CDG)")
    }
}
```

## Design

- **Shape**: Circular (48dp diameter)
- **Border**: 2dp stroke surrounding the icon
- **Icon**: Swap icon in the center
- **Animation**: 300ms smooth 180-degree rotation on click
- **States**: Enabled (interactive) and Disabled (non-interactive)
- **Styles**: Three visual styles for different backgrounds
  - Canvas Default: Canvas background with canvas border
  - Canvas Contrast: Canvas contrast background with canvas contrast border
  - Surface Contrast: Surface contrast background with core accent border

## Testing

BpkSwapButton includes comprehensive tests covering:

- Visual snapshots in light and dark themes
- Interaction behavior (click handling)
- Disabled state rendering
- Accessibility semantics

Run tests with:

```bash
./gradlew test
```