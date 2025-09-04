# Inventory Card

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.inventorycard)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/inventorycard)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/InventoryCard/screenshots/default.png" alt="Inventory Card component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/InventoryCard/screenshots/default_dm.png" alt="Inventory Card component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The `BpkInventoryCard` component displays an image, title, subtitle, and action button in a card layout. It's designed for presenting inventory items or selections in a visually consistent manner.

Example of a basic Inventory Card:

```Kotlin
import net.skyscanner.backpack.compose.inventorycard.BpkInventoryCard

BpkInventoryCard(
    title = "London to Barcelona",
    subtitle = "Direct • 2h 15m",
    buttonText = "Select",
    onButtonClick = { /* Handle button click */ },
    imagePainter = painterResource(R.drawable.flight_image),
    imageContentDescription = "Flight route",
)
```

Example of a clickable Inventory Card:

```Kotlin
import net.skyscanner.backpack.compose.inventorycard.BpkInventoryCard

BpkInventoryCard(
    title = "Hotel Marina Bay",
    subtitle = "5 stars • Beach view",
    buttonText = "Book",
    onButtonClick = { /* Handle button click */ },
    onCardClick = { /* Handle card click */ },
    imagePainter = painterResource(R.drawable.hotel_image),
    imageContentDescription = "Hotel view",
)
```

Example of a disabled Inventory Card:

```Kotlin
import net.skyscanner.backpack.compose.inventorycard.BpkInventoryCard

BpkInventoryCard(
    title = "Car Rental",
    subtitle = "Economy • Automatic",
    buttonText = "Unavailable",
    onButtonClick = { },
    imagePainter = painterResource(R.drawable.car_image),
    imageContentDescription = "Car rental",
    enabled = false,
)
```

## API

### BpkInventoryCard

| Parameter | Type | Description |
| --- | --- | --- |
| `title` | `String` | The main title text to display |
| `subtitle` | `String` | The subtitle or caption text to display |
| `buttonText` | `String` | The text to display on the action button |
| `onButtonClick` | `() -> Unit` | Callback when the button is clicked |
| `imagePainter` | `Painter` | The painter for the image to display |
| `imageContentDescription` | `String?` | Content description for the image |
| `modifier` | `Modifier` | Modifier to be applied to the card |
| `enabled` | `Boolean` | Whether the button is enabled (default: true) |
| `onCardClick` | `() -> Unit` | Optional callback when the card is clicked (second overload) |

## Accessibility

The component supports:
- Screen reader announcements for all text content
- Content descriptions for images
- Proper button click actions with semantic meanings
- Support for both light and dark themes
- RTL layout support