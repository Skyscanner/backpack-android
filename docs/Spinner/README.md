# Spinner

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Spinner component can be used in both XML and Kotlin/Java

Example of a primary spinner in XML

```xml
<net.skyscanner.backpack.spinner.BpkSpinner
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  app:type="primary"
  app:small="false" />
```

Example of a primary spinner in Kotlin

```Kotlin
import net.skyscanner.backpack.spinner.BpkSpinner

BpkSpinner(context).apply {
  type = BpkSpinner.Type.PRIMARY
  small = false
}
```

## Theme Props

- `spinnerColor`

Styles for the `primary` spinner can be changed globally through `bpkSpinnerPrimaryStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/master/docs/THEMING.md) for more information.
