# Backpack Android

> Backpack is a collection of design resources, reusable components and guidelines for creating Skyscanner's products.

[![CI Status](https://github.com/Skyscanner/backpack-android/workflows/CI/badge.svg)](https://github.com/Skyscanner/backpack-android/actions)

[![Release](https://jitpack.io/v/skyscanner/backpack-android.svg)](https://jitpack.io/#skyscanner/backpack-android)
[![license](https://img.shields.io/github/license/Skyscanner/backpack-android.svg)](https://github.com/Skyscanner/backpack-android)
[![platform](https://img.shields.io/badge/platform-android-green.svg)](https://github.com/Skyscanner/backpack-android)

## Installation

Backpack is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). To install
all of it, add the following line to your `build.gradle` (in your app module) in the `dependencies` block:

```gradle
implementation 'com.github.skyscanner:backpack-android:28.0.3'
```

If your app resolves dependencies through Jitpack you're all set, if not add in your root `build.gradle`

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
```
#### Note that Backpack is expected to be used with AndroidX. Please refer to [AndroidX migration guide](https://developer.android.com/jetpack/androidx/migrate) to setup.

### Demo application
The Backpack demo application is a good way of referring to the variants available for a component and their correct usage. The code is available under `/app` directory. [The app can be downloaded from App Center](https://install.appcenter.ms/orgs/Backpack/apps/Backpack-Android) or by scanning the QR code below.

![QR code](data:image/png;base64,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)

## Components

* [Badge](docs/Badge/README.md)
* [Bottom Nav](docs/BottomNav/README.md)
* [Bottom Sheet](docs/BottomSheet/README.md)
* [Button](docs/Button/README.md)
* [Card](docs/Card/README.md)
* [Calendar](docs/Calendar/README.md)
* [Checkbox](docs/Checkbox/README.md)
* [Chip](docs/Chip/README.md)
* [Dialog](docs/Dialog/README.md)
* [Horizontal Nav](docs/HorizontalNav/README.md)
* [Floating Action Button](docs/FloatingActionButton/README.md)
* [Interactive Star Rating](docs/InteractiveStarRating/README.md)
* [Map Markers](docs/Maps/README.md)
* [Nav Bar](docs/NavBar/README.md)
* [Panel](docs/Panel/README.md)
* [RadioButton](docs/RadioButton/README.md)
* [Overlay](docs/Overlay/README.md)
* [Rating](docs/Rating/README.md)
* [Snackbar](docs/Snackbar/README.md)
* [Spinner](docs/Spinner/README.md)
* [Star Rating](docs/StarRating/README.md)
* [Switch](docs/Switch/README.md)
* [Text](docs/Text/README.md)
* [Text Field](docs/TextField/README.md)
* [Text Spans](docs/TextSpans/README.md)
* [Toast](docs/Toast/README.md)

## Usage

### Radii

The Backpack radii tokens are available as [dimension resource](Backpack/src/main/res/values/backpack.radii.xml).

The supported tokens are

+ `bpkBorderRadiusSm`
+ `bpkBorderRadiusPill`

### Elevation

The Backpack elevation tokens are available as [dimension resource](Backpack/src/main/res/values/backpack.elevation.xml).

The supported tokens are

+ `bpkElevationXs`
+ `bpkElevationSm`
+ `bpkElevationBase`
+ `bpkElevationLg`
+ `bpkElevationXl`

### Text Styles

The Backpack text styles are available as [style resources](Backpack/src/main/res/values/backpack.text.xml).

The supported styles are

+ `bpkTextXs`
+ `bpkTextXsEmphasized`
+ `bpkTextSm`
+ `bpkTextSmEmphasized`
+ `bpkTextBase`
+ `bpkTextBaseEmphasized`
+ `bpkTextLg`
+ `bpkTextLgEmphasized`
+ `bpkTextXl`
+ `bpkTextXlEmphasized`
+ `bpkTextXl`
+ `bpkTextXlEmphasized`
+ `bpkTextXxl`
+ `bpkTextXxlEmphasized`

### Color
```xml
<TextView
  android:text="This is Backpack Blue 500!"
  android:textColor="@color/bpkSkyBlue" />
```

```kotlin
R.color.bpkSkyBlue
```
### Gradient

The Backpack gradient component is available with the `BPKGradient` utility class. It accepts the direction of the gradient as an optional parameter.

```java
BpkGradients.getPrimary(context);
BpkGradients.getPrimary(context, GradientDrawable.Orientation.LEFT_RIGHT);
```

## Contributing to Backpack

Please see the [Contributing guide][0] for instructions on contributing to this project.

## License

Backpack is available under the Apache 2.0 license. See the LICENSE file for more info.

[0]: CONTRIBUTING.md
