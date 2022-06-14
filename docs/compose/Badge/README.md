# Badge

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Badge:

```Kotlin
import net.skyscanner.backpack.compose.badge.BpkBadge
import net.skyscanner.backpack.compose.badge.BpkBadgeType

BpkBadge(
  text = "Badge text",
  type = BpkBadgeType.Normal,
)
```

Example of a Badge with icon:

```Kotlin
import net.skyscanner.backpack.compose.badge.BpkBadge
import net.skyscanner.backpack.compose.badge.BpkBadgeType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.TickCircle

BpkBadge(
  text = "Badge text",
  type = BpkBadgeType.Normal,
  icon = BpkIcon.TickCircle,
)
```
