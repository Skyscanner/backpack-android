# Snackbar

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Snackbar can be used in Kotlin/Java

```Kotlin
import net.skyscanner.backpack.snackbar.BpkSnackbar

BpkSnackbar.make(view, "Message", BpkSnackbar.LENGTH_SHORT)
        .setAction("Action") {}
        .show()
```

An example with title and icon

```Kotlin
import net.skyscanner.backpack.snackbar.BpkSnackbar

BpkSnackbar.make(view, "Message", BpkSnackbar.LENGTH_SHORT)
        .setTitle("Title")
        .setIcon(R.drawable.bpk_tick_circle)
        .setAction("Action") {}
        .show()
```

An example with icon-only action

```Kotlin
import net.skyscanner.backpack.snackbar.BpkSnackbar

BpkSnackbar.make(view, "Message", BpkSnackbar.LENGTH_SHORT)
        .setAction(R.drawable.bpk_close) {}
        .show()
```

## Theme Props

- `snackbarTextColor`
- `snackbarActionColor`
- `snackbarBackgroundColor`

Styles can be changed globally through `bpkSnackbarStyle`.
The fonts will be applied globally from the current theme.

Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/THEMING.md) for more information.
