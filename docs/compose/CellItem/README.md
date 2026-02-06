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

## With Logo

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/with-logo.png" alt="Cell Item with Logo" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/with-logo_dm.png" alt="Cell Item with Logo - dark mode" width="375" /> |

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
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/surface-low-contrast-and-rounded.png" alt="Cell Item with Surface Low Contrast and Rounded Corner" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/surface-low-contrast-and-rounded_dm.png" alt="Cell Item with Surface Low Contrast and Rounded Corner - dark mode" width="375" /> |


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
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.tokens.Account

BpkCellItem(
  icon = {
    BpkIcon(
      icon = BpkIcon.Account,
      contentDescription = "Account",
      size = BpkIconSize.Large,
    )
  },
  title = "Title",
  body = "Description",
)
```

Example of a clickable BpkCellItem:

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.tokens.Account

BpkCellItem(
  icon = {
    BpkIcon(
      icon = BpkIcon.Account,
      contentDescription = "Account",
      size = BpkIconSize.Large,
    )
  },
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

Example of a BpkCellItem with custom slot:

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.compose.switch.BpkSwitch

BpkCellItem(
  icon = {
    BpkIcon(
      icon = BpkIcon.Account,
      contentDescription = "Account",
      size = BpkIconSize.Large,
    )
  },
  title = "Title",
  body = "Description",
  slot = {
    BpkSwitch(
      checked = true,
      onCheckedChange = { /* Handle change */ },
    )
  },
)
```

## Standard Slot Components

Backpack provides standard slot components for common use cases:

### Chevron

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryChevron

BpkCellItem(
  title = "Settings",
  onClick = { /* Navigate */ },
  slot = {
    BpkCellAccessoryChevron()
  },
)
```

### Switch

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessorySwitch

BpkCellItem(
  title = "Notifications",
  slot = {
    BpkCellAccessorySwitch(
      checked = true,
      onCheckedChange = { /* Handle change */ },
    )
  },
)
```

### Text

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryText

BpkCellItem(
  title = "Language",
  onClick = { /* Select language */ },
  slot = {
    BpkCellAccessoryText("English")
  },
)
```

### Logo

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryLogo

BpkCellItem(
  title = "Partner Name",
  slot = {
    BpkCellAccessoryLogo(R.drawable.partner_logo)
  },
)
```
