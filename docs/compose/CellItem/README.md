# Cell Item

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.cellitem)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/cellitem)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/default.png" alt="Cell Item component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/default_dm.png" alt="Cell Item component - dark mode" width="375" /> |

## With Chevron

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/with-chevron.png" alt="Cell Item with Chevron" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/with-chevron_dm.png" alt="Cell Item with Chevron - dark mode" width="375" /> |

## With Switch

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/with-switch.png" alt="Cell Item with Switch" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/with-switch_dm.png" alt="Cell Item with Switch - dark mode" width="375" /> |

## With Text

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/with-text.png" alt="Cell Item with Text" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/with-text_dm.png" alt="Cell Item with Text - dark mode" width="375" /> |

## With Link

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/with-link.png" alt="Cell Item with Link" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/with-link_dm.png" alt="Cell Item with Link - dark mode" width="375" /> |

## With Image

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/with-image.png" alt="Cell Item with Image" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/with-image_dm.png" alt="Cell Item with Image - dark mode" width="375" /> |

## Surface Low Contrast

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/surface-low-contrast.png" alt="Cell Item with Surface Low Contrast" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/surface-low-contrast_dm.png" alt="Cell Item with Surface Low Contrast - dark mode" width="375" /> |

## Rounded Corner

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/rounded-corner.png" alt="Cell Item with Rounded Corner" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/rounded-corner_dm.png" alt="Cell Item with Rounded Corner - dark mode" width="375" /> |

## Surface Low Contrast + Rounded

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/surface-low-contrast-+-rounded.png" alt="Cell Item with Surface Low Contrast and Rounded Corner" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/surface-low-contrast-+-rounded_dm.png" alt="Cell Item with Surface Low Contrast and Rounded Corner - dark mode" width="375" /> |


## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a basic BpkCellItem:

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem

BpkCellItem(
  title = "Title",
)
```

Example of a BpkCellItem with icon and body:

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Account

BpkCellItem(
  icon = BpkIcon.Account,
  title = "Title",
  body = "Description",
)
```

Example of a clickable BpkCellItem:

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Account

BpkCellItem(
  icon = BpkIcon.Account,
  title = "Title",
  body = "Description",
  onClick = { /* Handle click */ },
)
```

Example of a BpkCellItem with Surface Low Contrast style:

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellItemStyle

BpkCellItem(
  title = "Title",
  body = "Description",
  style = BpkCellItemStyle.SurfaceLowContrast,
)
```

Example of a BpkCellItem with Rounded corners:

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellItemCorner

BpkCellItem(
  title = "Title",
  body = "Description",
  corner = BpkCellItemCorner.Rounded,
)
```

## Slot Types

BpkCellItem supports different slot types via the `BpkCellItemSlot` sealed interface:

### Chevron (Navigation Indicator)

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellItemSlot

BpkCellItem(
  title = "Settings",
  onClick = { /* Navigate */ },
  slot = BpkCellItemSlot.Chevron,
)
```

### Switch (Toggle Control)

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellItemSlot

var enabled by remember { mutableStateOf(true) }
BpkCellItem(
  title = "Notifications",
  slot = BpkCellItemSlot.Switch(
    checked = enabled,
    onCheckedChange = { enabled = it },
  ),
)
```

### Text (Value Display)

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellItemSlot

BpkCellItem(
  title = "Language",
  slot = BpkCellItemSlot.Text("English"),
)
```

### Link (Clickable Link)

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellItemSlot

BpkCellItem(
  title = "Privacy Policy",
  slot = BpkCellItemSlot.Link(
    text = "View",
    onClick = { /* Handle click */ },
  ),
)
```

### Image (Image Display)

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellItemSlot

BpkCellItem(
  title = "Partner",
  slot = BpkCellItemSlot.Image(R.drawable.partner_logo),
)
```

## Complete Example

```Kotlin
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellItemCorner
import net.skyscanner.backpack.compose.cellitem.BpkCellItemSlot
import net.skyscanner.backpack.compose.cellitem.BpkCellItemStyle
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Account

var notificationsEnabled by remember { mutableStateOf(true) }

BpkCellItem(
  icon = BpkIcon.Account,
  title = "Notifications",
  body = "Enable push notifications",
  style = BpkCellItemStyle.SurfaceLowContrast,
  corner = BpkCellItemCorner.Rounded,
  slot = BpkCellItemSlot.Switch(
    checked = notificationsEnabled,
    onCheckedChange = { notificationsEnabled = it },
  ),
)
```