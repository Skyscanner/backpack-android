# Floating Action Button

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a `BpkFab`:

```Kotlin
import net.skyscanner.backpack.compose.fab.BpkFab
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Search

BpkFab(
  onClick = { showToast(context = context) },
  icon = BpkIcon.Search,
  contentDescription = stringResource(R.string.content_description),
) {
    content()
}
```
