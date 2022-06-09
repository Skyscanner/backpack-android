# Icon

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of an icon:

```Kotlin
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.BpkIcon

BpkIcon(
  icon = BpkIcon.LongArrowLeft,
  contentDescription = myContentDescription,
)
```

Example of a large icon:

```Kotlin
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.tokens.BpkIcon

BpkIcon(
  icon = BpkIcon.LongArrowLeft,
  contentDescription = myContentDescription,
  size = BpkIconSize.Large,
)
```
