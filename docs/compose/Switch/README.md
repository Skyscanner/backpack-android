# Switch

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Switch:

```Kotlin
import net.skyscanner.backpack.compose.switch.BpkSwitch

BpkSwitch(
  text = "Switch text",
  checked = true,
  onCheckedChange = { checked -> },
)
```

Example of a Switch with custom content:

```Kotlin
import net.skyscanner.backpack.compose.switch.BpkSwitch

BpkSwitch(
  checked = true,
  onCheckedChange = { checked -> },
) { checked ->
  MyCustomContent()
}
```
