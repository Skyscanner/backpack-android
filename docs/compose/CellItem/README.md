# Cell Item

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.cellitem)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/cellitem)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/default.png" alt="Cell Item component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellItem/screenshots/default_dm.png" alt="Cell Item component - dark mode" width="375" /> |

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

Example of a BpkCellItem with icon and description:

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Account

BpkCellItem(
  icon = BpkIcon.Account,
  iconContentDescription = "Account",
  title = "Title",
  description = "Description",
)
```

Example of a clickable BpkCellItem with divider:

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Account

BpkCellItem(
  icon = BpkIcon.Account,
  iconContentDescription = "Account",
  title = "Title",
  description = "Description",
  showDivider = true,
  onClick = { /* Handle click */ },
)
```

Example of a BpkCellItem with custom accessory:

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.compose.switch.BpkSwitch

BpkCellItem(
  icon = BpkIcon.Account,
  iconContentDescription = "Account",
  title = "Title",
  description = "Description",
  accessory = {
    BpkSwitch(
      checked = true,
      onCheckedChange = { /* Handle change */ },
    )
  },
)
```

## Cell Accessories

Backpack provides standard accessory components for common use cases:

### Chevron Accessory

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryChevron

BpkCellItem(
  title = "Settings",
  onClick = { /* Navigate */ },
  accessory = {
    BpkCellAccessoryChevron()
  },
)
```

### Switch Accessory

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessorySwitch

BpkCellItem(
  title = "Notifications",
  accessory = {
    BpkCellAccessorySwitch(
      checked = true,
      onCheckedChange = { /* Handle change */ },
    )
  },
)
```

### Text Accessory

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryText

BpkCellItem(
  title = "Language",
  onClick = { /* Select language */ },
  accessory = {
    BpkCellAccessoryText("English")
  },
)
```

### Logo Accessory

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryLogo

BpkCellItem(
  title = "Partner Name",
  accessory = {
    BpkCellAccessoryLogo(R.drawable.partner_logo)
  },
)
```

## Cell Group

Group multiple cell items together in a card:

```Kotlin
import androidx.compose.foundation.layout.Column
import net.skyscanner.backpack.compose.cellitem.BpkCellGroup
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryChevron

BpkCellGroup {
  Column {
    BpkCellItem(
      title = "Profile",
      description = "Manage your account",
      showDivider = true,
      onClick = { /* Navigate */ },
      accessory = {
        BpkCellAccessoryChevron()
      },
    )
    BpkCellItem(
      title = "Settings",
      description = "App preferences",
      onClick = { /* Navigate */ },
      accessory = {
        BpkCellAccessoryChevron()
      },
    )
  }
}
```
