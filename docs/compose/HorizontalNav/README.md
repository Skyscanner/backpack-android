# Horizontal Nav

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Horizontal Nav (with default size):

```Kotlin
import net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNav
import net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNavSize
import net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNavTab

val tabs = listOf(
  BpkHorizontalNavTab(
    title = "Tab 1",
  ),
  BpkHorizontalNavTab(
    title = "Tab 2",
  ),
)

BpkHorizontalNav(
  tabs = tabs,
  activeIndex = activeIndex,
  size = BpkHorizontalNavSize.Default,
  onChanged = { index -> },
)
```

Example of a Horizontal Nav (with small size):

```Kotlin
import net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNav
import net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNavSize
import net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNavTab

val tabs = listOf(
  BpkHorizontalNavTab(
    title = "Tab 1",
  ),
  BpkHorizontalNavTab(
    title = "Tab 2",
  ),
)

BpkHorizontalNav(
  tabs = tabs,
  activeIndex = activeIndex,
  size = BpkHorizontalNavSize.Small,
  onChanged = { index -> },
)
```

Example of a Horizontal Nav (with icon):

```Kotlin
import net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNav
import net.skyscanner.backpack.compose.horizontalnav.BpkHorizontalNavTab
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.CloseCircle
import net.skyscanner.backpack.compose.tokens.TickCircle

val tabs = listOf(
  BpkHorizontalNavTab(
    title = "Tab 1",
    icon = BpkIcon.TickCircle,
  ),
  BpkHorizontalNavTab(
    title = "Tab 2",
    icon = BpkIcon.CloseCircle,
  ),
)

BpkHorizontalNav(
  tabs = tabs,
  activeIndex = activeIndex,
  onChanged = { index -> },
)
```
