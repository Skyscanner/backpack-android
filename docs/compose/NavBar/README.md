# Navigation Bar

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.navigationbar)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/navigationbar)

## Default

| Day | Night |
| --- | --- |
| ![NavBar component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/NavBar/screenshots/default.png) |![NavBar component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/NavBar/screenshots/default_dm.png) |

## Collapsible

| Day | Night |
| --- | --- |
| ![Collapsible NavBar component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/NavBar/screenshots/collapsible.png) |![Collapsible NavBar component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/NavBar/screenshots/collapsible_dm.png) |

## Installation

Backpack Compose is available
through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the
main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

> Note: By default, navigation bar will include (and consume) window insets. If you don't want this, set `insets` parameter
> to null.

Example of a navigation bar with back navigation icon

```Kotlin
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.NavIcon

BpkTopNavBar(
  title = stringResource(R.string.navigation_bar_title),
  navIcon = NavIcon.Back(contentDescription = stringResource(R.string.navigation_back)) { /** onClick **/ },
)
```

Example of a navigation bar with close navigation icon

```Kotlin
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.NavIcon

BpkTopNavBar(
  title = stringResource(R.string.navigation_bar_title),
  navIcon = NavIcon.Close(contentDescription = stringResource(R.string.navigation_close)) { /** onClick **/ },
)
```

Example of a navigation bar with no navigation icon

```Kotlin
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.NavIcon

BpkTopNavBar(
  title = stringResource(R.string.navigation_bar_title),
  navIcon = NavIcon.None,
)
```

Example of a navigation bar with icon actions

```Kotlin
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.navigationbar.IconAction

BpkTopNavBar(
  title = stringResource(R.string.navigation_bar_title),
  navIcon = NavIcon.Back(contentDescription = stringResource(R.string.navigation_back)) { /** onClick **/ },
  actions = listOf(
    IconAction(icon = BpkIcon.AccountIdCard,
      contentDescription = stringResource(R.string.navigation_id_card)) { /** onClick **/ },
    IconAction(icon = BpkIcon.Accessibility,
      contentDescription = stringResource(R.string.navigation_accessibility)) { /** onClick **/ },
    IconAction(icon = BpkIcon.Account, contentDescription = stringResource(R.string.navigation_account)) { /** onClick **/ },
  ),
)
```

Example of a navigation bar with text action

```Kotlin
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.navigationbar.TextAction

BpkTopNavBar(
  title = stringResource(R.string.navigation_bar_title),
  navIcon = NavIcon.Back(contentDescription = stringResource(R.string.navigation_back)) { /** onClick **/ },
  action = TextAction(text = stringResource(R.string.navigation_text_action)) { /** onClick **/ },
)
```

Example of a collapsible navigation bar:

```Kotlin
import androidx.compose.foundation.lazy.LazyColumn
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.navigationbar.IconAction
import net.skyscanner.backpack.compose.navigationbar.nestedScroll
import net.skyscanner.backpack.compose.navigationbar.rememberTopAppBarState
import net.skyscanner.backpack.compose.text.BpkText

val state = rememberTopAppBarState()

Column(modifier.nestedScroll(state)) {
  BpkTopNavBar(
    state = state,
    title = stringResource(R.string.navigation_bar_title),
    navIcon = NavIcon.Back(contentDescription = stringResource(R.string.navigation_back)) { /** onClick **/ },
    actions = listOf(
      IconAction(
        icon = BpkIcon.AccountIdCard,
        contentDescription = stringResource(R.string.navigation_id_card)
      ) { /** onClick **/ },
      IconAction(
        icon = BpkIcon.Accessibility,
        contentDescription = stringResource(R.string.navigation_accessibility)
      ) { /** onClick **/ },
      IconAction(
        icon = BpkIcon.Account,
        contentDescription = stringResource(R.string.navigation_account)
      ) { /** onClick **/ },
    ),
  )

  LazyColumn {
    items(10) {
      BpkText(text = "Item #$it")
    }
  }
}
```
