# Spinner

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Spinner:

```Kotlin
import net.skyscanner.backpack.compose.spinner.BpkSpinner
import net.skyscanner.backpack.compose.spinner.BpkSpinnerSize

BpkSpinner(
  size = BpkSpinnerSize.Large,
)
```

Example of a Spinner on dark surface:

```Kotlin
import net.skyscanner.backpack.compose.spinner.BpkSpinner
import net.skyscanner.backpack.compose.spinner.BpkSpinnerSize
import net.skyscanner.backpack.compose.spinner.BpkSpinnerStype

BpkSpinner(
  size = BpkSpinnerSize.Large,
  style = BpkSpinnerStyle.OnDarkSurface,
)
```

When style is not specified, the Spinner sets it based on `LocalContentColor`. When color is more close to white, and it's
in light mode `BpkSpinnerStyle.OnDarkSurface` will be used – otherwise it falls back to `BpkSpinnerStyle.TextPrimary`.
