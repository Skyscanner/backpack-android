# Checkbox

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Checkbox:

```Kotlin
import net.skyscanner.backpack.compose.checkbox.BpkCheckbox

BpkCheckbox(
  text = "Checkbox text",
  checked = true,
  onCheckedChange = { checked -> },
)
```

Example of a Checkbox with intermediate state:

```Kotlin
import net.skyscanner.backpack.compose.checkbox.BpkCheckbox

BpkCheckbox(
  text = "Checkbox text",
  checked = true,
  onCheckedChange = { checked -> },
)
```

Example of a Checkbox with custom content:

```Kotlin
import net.skyscanner.backpack.compose.checkbox.BpkCheckbox

BpkCheckbox(
  checked = true,
  onCheckedChange = { checked -> },
) { checked ->
    MyCustomContent()
}
```
