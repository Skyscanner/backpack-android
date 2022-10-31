# TextField

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.textfield)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/textfield)

## Default

| Day | Night |
| --- | --- |
| ![TextField component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/default.png) |![TextField component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/default_dm.png) |

## Error

| Day | Night |
| --- | --- |
| ![Error TextField component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/error.png) |![Error TextField component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/error_dm.png) |

## Validated

| Day | Night |
| --- | --- |
| ![Validated TextField component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/validated.png) |![Validated TextField component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/validated_dm.png) |

## Disabled

| Day | Night |
| --- | --- |
| ![Disabled TextField component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/disabled.png) |![Disabled TextField component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/disabled_dm.png) |

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
