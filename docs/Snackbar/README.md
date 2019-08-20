# Snackbar

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Snackbar can be used in Kotlin/Java

```Kotlin
import net.skyscanner.backpack.snackbar.BpkSnackbar

BpkSnackbar.make(view, "Message", BpkSnackbar.LENGTH_SHORT)
        .setAction("Action") {}
        .show()
```

## Theme Props

- `snackbarTextColor`
- `snackbarActionColor`
- `snackbarBackgroundColor`

Styles can be changed globally through `bpkSnackbarStyle`.
The fonts will be applied globally from the current theme.
 
Check [theming](https://github.com/Skyscanner/backpack-android/blob/master/docs/THEMING.md) for more information.
