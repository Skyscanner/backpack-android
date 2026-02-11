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

Example of a basic BpkCellGroup with simple text items:

```Kotlin
import net.skyscanner.backpack.compose.cellitem.BpkCellGroup
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

BpkCellGroup {
    item {
        BpkText(
            text = "Profile Settings",
            style = BpkTheme.typography.label1,
            modifier = Modifier.padding(BpkSpacing.Base),
        )
    }
    item {
        BpkText(
            text = "Notifications",
            style = BpkTheme.typography.label1,
            modifier = Modifier.padding(BpkSpacing.Base),
        )
    }
    item {
        BpkText(
            text = "Language",
            style = BpkTheme.typography.label1,
            modifier = Modifier.padding(BpkSpacing.Base),
        )
    }
}
```

Example of a BpkCellGroup with custom cell layouts:

```Kotlin
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import net.skyscanner.backpack.compose.cellitem.BpkCellGroup
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.switch.BpkSwitch
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

var notificationsEnabled by remember { mutableStateOf(true) }

BpkCellGroup {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(BpkSpacing.Base),
            horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BpkIcon(
                icon = BpkIcon.Account,
                contentDescription = "Account",
                size = BpkIconSize.Large,
            )
            Column(modifier = Modifier.weight(1f)) {
                BpkText(
                    text = "Profile Settings",
                    style = BpkTheme.typography.label1,
                )
                BpkText(
                    text = "Manage your account",
                    style = BpkTheme.typography.caption,
                    color = BpkTheme.colors.textSecondary,
                )
            }
        }
    }
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(BpkSpacing.Base),
            horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BpkIcon(
                icon = BpkIcon.Hotels,
                contentDescription = "Notifications",
                size = BpkIconSize.Large,
            )
            Column(modifier = Modifier.weight(1f)) {
                BpkText(
                    text = "Notifications",
                    style = BpkTheme.typography.label1,
                )
                BpkText(
                    text = "Enable push notifications",
                    style = BpkTheme.typography.caption,
                    color = BpkTheme.colors.textSecondary,
                )
            }
            BpkSwitch(
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it },
                content = {},
            )
        }
    }
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(BpkSpacing.Base),
            horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BpkIcon(
                icon = BpkIcon.Accessibility,
                contentDescription = "Language",
                size = BpkIconSize.Large,
            )
            Column(modifier = Modifier.weight(1f)) {
                BpkText(
                    text = "Language",
                    style = BpkTheme.typography.label1,
                )
                BpkText(
                    text = "App display language",
                    style = BpkTheme.typography.caption,
                    color = BpkTheme.colors.textSecondary,
                )
            }
            BpkText(
                text = "English",
                style = BpkTheme.typography.label2,
                color = BpkTheme.colors.textSecondary,
            )
        }
    }
}
```

## Key Features

- **Flexible Content**: Accepts any composable content within each item, allowing full customization of cell layouts.
- **Automatic Dividers**: Automatically adds dividers between all items in the group.
- **Rounded Corners**: The group always uses rounded corners as per design specifications.
- **Consistent Styling**: Provides a unified background and styling for grouped content.
- **Simple API**: Use the `item {}` function to add items without manual divider management.

## Design Notes

The Cell Group component has fixed styling according to design specifications:
- Always uses rounded corners
- Always uses default surface background
- Dividers are automatically managed between items
- Content within each item is completely customizable