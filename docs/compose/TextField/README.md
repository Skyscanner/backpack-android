# TextField

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.textfield)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/textfield)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/default.png" alt="TextField component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/default_dm.png" alt="TextField component - dark mode" width="375" /> |

## Error

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/error.png" alt="Error TextField component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/error_dm.png" alt="Error TextField component - dark mode" width="375" /> |

## Validated

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/validated.png" alt="Validated TextField component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/validated_dm.png" alt="Validated TextField component - dark mode" width="375" /> |

## Disabled

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/disabled.png" alt="Disabled TextField component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/disabled_dm.png" alt="Disabled TextField component - dark mode" width="375" /> |

## TextArea

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/text-area.png" alt="TextArea component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/text-area_dm.png" alt="TextArea component - dark mode" width="375" /> |

## TextArea Error

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/text-area-error.png" alt="Error TextArea component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/text-area-error_dm.png" alt="Error TextArea component - dark mode" width="375" /> |

## TextArea Validated

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/text-area-validated.png" alt="Validated TextArea component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/text-area-validated_dm.png" alt="Validated TextArea component - dark mode" width="375" /> |

## TextArea Disabled

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/text-area-disabled.png" alt="Disabled TextArea component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/TextField/screenshots/text-area-disabled_dm.png" alt="Disabled TextArea component - dark mode" width="375" /> |

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
import net.skyscanner.backpack.icon.BpkIcon
import net.skyscanner.backpack.compose.textfield.BpkTextField
import net.skyscanner.backpack.compose.tokens.Accessibility

BpkTextField(
  value = value,
  onValueChange = { value -> },
  placeholder = "Placeholder",
  icon = BpkIcon.Accessibility,
)
```

Example of a TextField with clear action:

```Kotlin
import net.skyscanner.backpack.icon.BpkIcon
import net.skyscanner.backpack.compose.textfield.BpkTextField
import net.skyscanner.backpack.compose.textfield.BpkClearAction
import net.skyscanner.backpack.compose.tokens.Accessibility

BpkTextField(
  value = value,
  onValueChange = { value -> },
  placeholder = "Placeholder",
  icon = BpkIcon.Accessibility,
  clearAction = BpkClearAction("Clear"){ value = "" }
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

Example of a TextArea:

```Kotlin
import net.skyscanner.backpack.compose.textfield.BpkTextArea

BpkTextArea(
  value = value,
  onValueChange = { value -> },
  placeholder = "Placeholder",
)
```
Example of a TextArea with error status:

```Kotlin
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.textfield.BpkTextArea

BpkTextArea(
  value = value,
  onValueChange = { value -> },
  placeholder = "Placeholder",
  status = BpkFieldStatus.Error("Error text"),
)
```
