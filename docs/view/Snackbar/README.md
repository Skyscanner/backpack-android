# Snackbar

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.snackbar)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/snackbar)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Snackbar/screenshots/default.png" alt="Snackbar component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Snackbar/screenshots/default_dm.png" alt="Snackbar component - dark mode" width="375" /> |

## Icon

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Snackbar/screenshots/icon.png" alt="Icon Snackbar component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Snackbar/screenshots/icon_dm.png" alt="Icon Snackbar component - dark mode" width="375" /> |

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
        .setIcon(R.drawable.bpk_tick_circle, "Confirm")
        .setAction("Action") {}
        .show()
```

An example with icon-only action

```Kotlin
import net.skyscanner.backpack.snackbar.BpkSnackbar

BpkSnackbar.make(view, "Message", BpkSnackbar.LENGTH_SHORT)
        .setAction(R.drawable.bpk_close, "Close") {}
        .show()
```

## Theme Props

- `snackbarTextColor`
- `snackbarActionColor`
- `snackbarBackgroundColor`

Styles can be changed globally through `bpkSnackbarStyle`.
The fonts will be applied globally from the current theme.

Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.
