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
import net.skyscanner.backpack.compose.cellitem.BpkCellItemData
import net.skyscanner.backpack.compose.icon.BpkIcon

BpkCellGroup(
    items = listOf(
        BpkCellItemData(
            title = "Profile Settings",
            icon = BpkIcon.Account,
        ),
        BpkCellItemData(
            title = "Notifications",
            icon = BpkIcon.Hotels,
        ),
        BpkCellItemData(
            title = "Language",
            icon = BpkIcon.Accessibility,
        ),
    ),
)
```

Example of a BpkCellGroup with body text and slots:

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellGroup
import net.skyscanner.backpack.compose.cellitem.BpkCellItemData
import net.skyscanner.backpack.compose.cellitem.BpkCellItemSlot
import net.skyscanner.backpack.compose.icon.BpkIcon

var notificationsEnabled by remember { mutableStateOf(true) }

BpkCellGroup(
    items = listOf(
        BpkCellItemData(
            title = "Profile Settings",
            body = "Manage your account",
            icon = BpkIcon.Account,
            onClick = { /* Navigate to profile */ },
            slot = BpkCellItemSlot.Chevron,
        ),
        BpkCellItemData(
            title = "Notifications",
            body = "Enable push notifications",
            icon = BpkIcon.Hotels,
            slot = BpkCellItemSlot.Switch(
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it },
            ),
        ),
        BpkCellItemData(
            title = "Language",
            body = "App display language",
            icon = BpkIcon.Accessibility,
            slot = BpkCellItemSlot.Text("English"),
        ),
    ),
)
```

## Key Features

- **Type-Safe**: Accepts only `List<BpkCellItemData>`, ensuring consistent cell item structure.
- **Automatic Dividers**: Automatically adds dividers between all items in the group.
- **Rounded Corners**: The group always uses rounded corners as per design specifications.
- **Consistent Styling**: Provides a unified background and styling for grouped content.
- **Lazy Rendering**: Uses `LazyColumn` for efficient rendering of large lists.
- **BpkCellItem Integration**: Automatically inflates `BpkCellItemData` into `BpkCellItem` components.

## Design Notes

The Cell Group component has fixed styling according to design specifications:
- Always uses rounded corners
- Always uses default surface background
- Dividers are automatically managed between items
- Each item is rendered as a `BpkCellItem` with consistent styling