# FieldSet

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.fieldset)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/fieldset)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/FieldSet/screenshots/default.png" alt="FieldSet component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/FieldSet/screenshots/default_dm.png" alt="FieldSet component - dark mode" width="375" /> |

## Error

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/FieldSet/screenshots/error.png" alt="Error FieldSet component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/FieldSet/screenshots/error_dm.png" alt="Error FieldSet component - dark mode" width="375" /> |

## Validated

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/FieldSet/screenshots/validated.png" alt="Validated FieldSet component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/FieldSet/screenshots/validated_dm.png" alt="Validated FieldSet component - dark mode" width="375" /> |

## Disabled

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/FieldSet/screenshots/disabled.png" alt="Disabled FieldSet component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/FieldSet/screenshots/disabled_dm.png" alt="Disabled FieldSet component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

FieldSet is a component which wraps its content (user input or form element) and adds optional title, description and
error labels around it depending on the field status.

The statuses available are Default, Disabled, Error and Validated. The statuses are automatically dispatched to
the child element (if the children support it).

Example of a FieldSet with Validated status:

```Kotlin
import net.skyscanner.backpack.compose.fieldset.BpkFieldSet
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus

BpkFieldSet(
  status = BpkFieldStatus.Validated,
  label = "Title (optional)",
  description = "Description (optional)",
) {
    // field content – for instance, BpkTextField()
}
```

Example of a FieldSet with Error status:

```Kotlin
import net.skyscanner.backpack.compose.fieldset.BpkFieldSet
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus

BpkFieldSet(
  status = BpkFieldStatus.Error("Error text"),
  label = "Title (optional)",
  description = "Description (optional)",
) {
    // field content – for instance, BpkTextField()
}
```
