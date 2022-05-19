# Panel

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Panel:

```Kotlin
import net.skyscanner.backpack.compose.panel.BpkPanel

BpkPanel {
    content()
}
```

Example of a Panel with no padding:

```Kotlin
import net.skyscanner.backpack.compose.panel.BpkPanel
import net.skyscanner.backpack.compose.panel.BpkPanelPadding

BpkPanel(padding = BpkPanelPadding.None) {
  content()
}
```
