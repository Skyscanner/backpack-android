# Flare

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Flare:

```Kotlin
import net.skyscanner.backpack.compose.flare.BpkFlare

BpkFlare {
  // content
}
```

Example of a Flare pointing up:

```Kotlin
import net.skyscanner.backpack.compose.flare.BpkFlare

BpkFlare(pointerDirection = BpkFlarePointerDirection.Up) {
  // content
}
```

Example of a Flare with rounded corners:

```Kotlin
import net.skyscanner.backpack.compose.flare.BpkFlare

BpkFlare(radius = BpkFlareRadius.Medium) {
  // content
}
```

Example of a Flare with content inset to take the flare into account when arranging the content:

```Kotlin
import net.skyscanner.backpack.compose.checkbox.BpkChecbox

BpkFlare(background = BpkTheme.colors.primary, insetContent = true) {
  // content
}
```
