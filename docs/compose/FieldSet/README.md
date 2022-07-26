# FieldSet

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
