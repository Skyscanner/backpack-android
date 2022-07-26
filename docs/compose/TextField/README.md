# TextField

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

> Note: If TextField is used inside `BpkFieldSet` it will inherit its default status from `BpkFieldSet`.

Example of a TextField:

```Kotlin
import net.skyscanner.backpack.compose.textfield.BpkTextField

BpkTextField(
  value = value,
  onValueChange = { value -> },
  placeholder = "Placeholder",
)
```

Example of a TextField with leading icon:

```Kotlin
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.textfield.BpkTextField
import net.skyscanner.backpack.compose.tokens.Accessibility

BpkTextField(
  value = value,
  onValueChange = { value -> },
  placeholder = "Placeholder",
  icon = BpkIcon.Accessibility,
)
```

Example of a multiline TextField:

```Kotlin
import net.skyscanner.backpack.compose.textfield.BpkTextField

BpkTextField(
  value = value,
  onValueChange = { value -> },
  placeholder = "Placeholder",
  maxLines = 3,
)
```

Example of a TextField with error status:

```Kotlin
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.textfield.BpkTextField

BpkTextField(
  value = value,
  onValueChange = { value -> },
  placeholder = "Placeholder",
  status = BpkFieldStatus.Error("Error text"),
)
```
