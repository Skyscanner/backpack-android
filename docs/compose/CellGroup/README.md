# Cell Group

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.cellitem)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/cellitem)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellGroup/screenshots/default.png" alt="Cell Group component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CellGroup/screenshots/default_dm.png" alt="Cell Group component - dark mode" width="375" /> |


## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a basic BpkCellGroup:

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellGroup
import net.skyscanner.backpack.compose.cellitem.BpkCellItem

BpkCellGroup {
  item {
    BpkCellItem(
      title = "Profile Settings",
      body = "Manage your account",
      onClick = {},
    )
  }
  item {
    BpkCellItem(
      title = "Notifications",
      body = "Enable push notifications",
    )
  }
  item {
    BpkCellItem(
      title = "Language",
      body = "App display language",
      onClick = {},
    )
  }
}
```

Example of a BpkCellGroup with various accessories:

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellGroup
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryChevron
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessorySwitch
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryText

BpkCellGroup {
  item {
    BpkCellItem(
      title = "Settings",
      onClick = { /* Navigate */ },
      slot = {
        BpkCellAccessoryChevron()
      },
    )
  }
  item {
    BpkCellItem(
      title = "Notifications",
      slot = {
        BpkCellAccessorySwitch(
          checked = true,
          onCheckedChange = { /* Handle change */ },
        )
      },
    )
  }
  item {
    BpkCellItem(
      title = "Language",
      onClick = { /* Select language */ },
      slot = {
        BpkCellAccessoryText("English")
      },
    )
  }
}
```

## Key Features

- **Dividers**: Automatically adds dividers between all cell items.
- **Rounded Corners**: The group always uses rounded corners as per design specifications.
- **Consistent Styling**: Provides a unified background and styling for grouped cells.
- **Simple API**: Use the `item {}` function to add cells without manual divider management.

## Design Notes

The Cell Group component has fixed styling according to design specifications:
- Always uses rounded corners
- Always uses default surface background
- Dividers are automatically managed between items
