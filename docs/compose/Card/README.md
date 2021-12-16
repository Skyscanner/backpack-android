# Card

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Card with default (small) corner:

```Kotlin
import net.skyscanner.backpack.compose.card.BpkCard

BpkCard {
    // content
}
```

Example of a Card with large corner:

```Kotlin
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardCorner

BpkCard(corner = BpkCardCorner.Large) {
    // content
}
```

Example of a clickable Card with forced focus:

```Kotlin
import net.skyscanner.backpack.compose.card.BpkCard

val interactionSource = remember {
  MutableInteractionSource().apply {
    tryEmit(FocusInteraction.Focus())
  }
}

BpkCard(interfactionSource = interactionSource) {
    // content
}
```
