# RadioButton

## Installation

Backpack Compose is available
through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the
main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a RadioButton:

```Kotlin
import net.skyscanner.backpack.compose.radiobutton.BpkRadioButton

BpkRadioButton(
  text = "Radiobutton text",
  selected = true,
  onClick = { },
)
```

Example of a RadioButton with custom content:

```Kotlin
import net.skyscanner.backpack.compose.radiobutton.BpkRadioButton

BpkRadioButton(
  selected = true,
  onClick = { },
) { selected ->
  MyCustomContent()
}
```
