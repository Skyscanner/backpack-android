# Backpack Android

> Backpack is a collection of design resources, reusable components and guidelines for creating Skyscanner's products.

[![CI Status](https://img.shields.io/travis/Skyscanner/backpack-android.svg?style=flat)](https://travis-ci.org/Skyscanner/backpack-android)

[![Release](https://jitpack.io/v/skyscanner/backpack-android.svg)](https://jitpack.io/#skyscanner/backpack-android)
[![license](https://img.shields.io/github/license/Skyscanner/backpack-android.svg)](https://github.com/Skyscanner/backpack-android)
[![platform](https://img.shields.io/badge/platform-android-green.svg)](https://github.com/Skyscanner/backpack-android)

## Installation

Backpack is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). To install
all of it, add the following line to your `build.gradle` (in your app module) in the `dependencies` block:

```gradle
implementation 'com.github.skyscanner:backpack-android:24.0.0'
```

If your app resolves dependencies through Jitpack you're all set, if not add in your root `build.gradle`

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
```
#### Note that Backpack is expected to be used with AndroidX. Please refer to [AndroidX migration guide](https://developer.android.com/jetpack/androidx/migrate) to setup.

### Demo application
The Backpack demo application is a good way of referring to the variants available for a component and their correct usage. The code is available under `/app` directory. [The app can be downloaded from HockeyApp](https://rink.hockeyapp.net/apps/dbf61ff1e7e245ddad0bd92ac24a85e1/app_versions/1) or by scanning the QR code below.

![QR code](https://chart.googleapis.com/chart?cht=qr&chl=https%3A%2F%2Frink.hockeyapp.net%2Fapps%2Fdbf61ff1e7e245ddad0bd92ac24a85e1%2Fapp_versions%2F1&chs=256x256)

## Components

* [Badge](docs/Badge/README.md)
* [Bottom Nav](docs/BottomNav/README.md)
* [Button](docs/Button/README.md)
* [Card](docs/Card/README.md)
* [Calendar](docs/Calendar/README.md)
* [Checkbox](docs/Checkbox/README.md)
* [Chip](docs/Chip/README.md)
* [Dialog](docs/Dialog/README.md)
* [Horizontal Nav](docs/HorizontalNav/README.md)
* [Floating Action Button](docs/FloatingActionButton/README.md)
* [Interactive Star Rating](docs/InteractiveStarRating/README.md)
* [Nav Bar](docs/NavBar/README.md)
* [Panel](docs/Panel/README.md)
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
