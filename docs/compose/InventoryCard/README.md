# Inventory Card

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.inventorycard)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/inventorycard)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/InventoryCard/screenshots/default.png" alt="Inventory Card component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/InventoryCard/screenshots/default_dm.png" alt="Inventory Card component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Inventory Card component is designed to display inventory items with an image, title, description, and action button. It follows Backpack design specifications with proper spacing, typography, and accessibility features.

### Basic Usage

```Kotlin
import net.skyscanner.backpack.compose.inventorycard.BpkInventoryCard

BpkInventoryCard(
    title = "Premium Hotel Room",
    description = "Spacious room with city view and modern amenities",
    buttonText = "Book Now",
    onButtonClick = { /* Handle booking action */ },
)
```

### With Image

```Kotlin
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

BpkInventoryCard(
    title = "Luxury Suite",
    description = "Executive suite with panoramic views and premium services",
    buttonText = "Reserve",
    onButtonClick = { /* Handle reservation action */ },
    image = {
        Image(
            painter = painterResource(id = R.drawable.hotel_suite),
            contentDescription = "Luxury hotel suite interior",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    },
    buttonContentDescription = "Reserve luxury suite",
)
```

### Clickable Card

```Kotlin
BpkInventoryCard(
    title = "Standard Room",
    description = "Comfortable accommodation with essential amenities",
    buttonText = "Select",
    onButtonClick = { /* Handle selection action */ },
    onCardClick = { /* Handle card click action */ },
    buttonContentDescription = "Select standard room",
)
```

## Parameters

| Parameter | Type | Description | Default |
| --- | --- | --- | --- |
| `title` | `String` | The main title text displayed in the card | Required |
| `description` | `String` | The description text displayed below the title | Required |
| `buttonText` | `String` | The text displayed on the action button | Required |
| `onButtonClick` | `() -> Unit` | Callback invoked when the action button is clicked | Required |
| `modifier` | `Modifier` | Modifier to be applied to the card container | `Modifier` |
| `image` | `(@Composable BoxScope.() -> Unit)?` | Optional composable content for the image area at the top of the card | `null` |
| `buttonContentDescription` | `String?` | Optional content description for the button for accessibility | `null` |
| `onCardClick` | `(() -> Unit)?` | Optional callback invoked when the card itself is clicked | `null` |

## Accessibility

The Inventory Card component is designed with accessibility in mind:

- **Screen Reader Support**: The card has appropriate content descriptions and semantic roles
- **Keyboard Navigation**: All interactive elements are keyboard navigable
- **Touch Target Size**: Interactive elements meet minimum touch target size requirements
- **Content Descriptions**: Supports custom content descriptions for buttons and images
- **State Communication**: Button states and interactions are properly communicated to assistive technologies

### Accessibility Best Practices

1. **Provide meaningful content descriptions**: Use the `buttonContentDescription` parameter to provide clear descriptions for screen readers
2. **Image descriptions**: When using the `image` parameter, ensure your image composable includes appropriate `contentDescription`
3. **Clear button text**: Use descriptive button text that clearly indicates the action being performed

## Design Tokens

The component uses the following Backpack design tokens:

- **Typography**: `BpkTheme.typography.hero1` for titles, `BpkTheme.typography.caption` for descriptions
- **Spacing**: `BpkSpacing.Md` for vertical spacing, `BpkSpacing.Sm` for text spacing
- **Card**: `BpkCardCorner.Small` for rounded corners, `BpkCardElevation.Default` for elevation
- **Button**: `BpkButtonSize.Default` and `BpkButtonType.Primary` for the action button

## RTL Support

The Inventory Card component fully supports Right-to-Left (RTL) layouts. All spacing, alignment, and text direction will automatically adapt based on the current locale.

## Theming

The component automatically adapts to light and dark themes using Backpack's theming system. All colors, typography, and spacing will adjust according to the current theme.
