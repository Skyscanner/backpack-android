# CardList

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.cardlist)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/cardlist)

## Rail

| Day                                                                                                                                                              | Night                                                                                                                                                                           |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CardList/screenshots/rail.png" alt="CardList component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CardList/screenshots/rail_dm.png" alt="CardList component - dark mode" width="375" /> |

## Rail with section header button

| Day                                                                                                                                                                              | Night                                                                                                                                                                                           |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CardList/screenshots/rail-with-headerbutton.png" alt="CardList component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CardList/screenshots/rail-with-headerbutton_dm.png" alt="CardList component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a CardList:

## Rail

* `AccessibilityHeaderTagEnabled`: Used to disable `Heading()` accessibility tag from internal `BpkSectionHeader` - Optional, true by default.

```Kotlin
import androidx.annotation.StringRes
import net.skyscanner.backpack.compose.cardlist.BpkCardList

BpkCardList(
    title = stringResource(R.string.card_list_title),
    description = stringResource(R.string.card_list_description),
    totalCards = 3) { index ->
    // content
}
```

## Rail with section header button

```Kotlin
import androidx.annotation.StringRes
import net.skyscanner.backpack.compose.cardlist.BpkCardList
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderButton

private const val INITIALLY_SHOWN_CARDS = 12

BpkCardList(
    title = stringResource(R.string.card_list_title),
    description = stringResource(R.string.card_list_description),
    totalCards = 3,
    headerButton = BpkSectionHeaderButton(
        text = stringResource(R.string.card_list_header_button_text),
        onClick = {},
    )) { index ->
    // content
}
```
