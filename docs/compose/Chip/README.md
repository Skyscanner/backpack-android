# Chip

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Chip:

```Kotlin
import net.skyscanner.backpack.compose.chip.BpkChip

BpkChip(
  text = "Chip text",
)
```

Example of a selected Chip:

```Kotlin
import net.skyscanner.backpack.compose.chip.BpkChip

BpkChip(
  text = "Chip text",
  selected = selected,
  onSelectedChange = { selected -> },
)
```

Example of a disabled Chip:

```Kotlin
import net.skyscanner.backpack.compose.chip.BpkChip

BpkChip(
  text = "Chip text",
  enabled = false,
)
```

Example of a Chip with leading icon:

```Kotlin
import net.skyscanner.backpack.compose.chip.BpkChip
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Deals

BpkChip(
  text = "Chip text",
  icon = BpkIcon.Deals,
)
```

Example of a Chip on dark background:

```Kotlin
import net.skyscanner.backpack.compose.chip.BpkChip
import net.skyscanner.backpack.compose.chip.BpkChipStyle

BpkChip(
  text = "Chip text",
  style = BpkChipStyle.OnDark,
)
```

Example of a Chip with different semantic type:

```Kotlin
import net.skyscanner.backpack.compose.chip.BpkChip
import net.skyscanner.backpack.compose.chip.BpkChipType

BpkChip(
  text = "Chip text",
  type = BpkChipType.Dismiss,
)
```
