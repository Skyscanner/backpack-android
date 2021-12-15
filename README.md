# Backpack Android

> Backpack is a collection of design resources, reusable components and guidelines for creating Skyscanner's products.

[![CI Status](https://github.com/Skyscanner/backpack-android/workflows/CI/badge.svg)](https://github.com/Skyscanner/backpack-android/actions)

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![license](https://img.shields.io/github/license/Skyscanner/backpack-android.svg)](https://github.com/Skyscanner/backpack-android)
[![platform](https://img.shields.io/badge/platform-android-green.svg)](https://github.com/Skyscanner/backpack-android)

## Installation

Backpack is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). To install
all of it, add the following line to your `build.gradle` (in your app module) in the `dependencies` block:

```gradle
implementation 'net.skyscanner.backpack:backpack-android:33.2.0'
```

If your app resolves dependencies through Maven Central you're all set, if not add in your root `build.gradle`

```gradle
repositories {
    mavenCentral()
}
```

You also need to inherit your app theme from Backpack:
```xml
  <style name="AppTheme" parent="BpkTheme">
    <!-- Customize your theme here. -->
  </style>
```

#### Note that Backpack is expected to be used with AndroidX. Please refer to [AndroidX migration guide](https://developer.android.com/jetpack/androidx/migrate) to setup.

### Demo application
The Backpack demo application is a good way of referring to the variants available for a component and their correct usage. The code is available under `/app` directory. [The app can be downloaded from App Center](https://install.appcenter.ms/orgs/backpack/apps/backpack-android/distribution_groups/everyone) or by scanning the QR code below.

![QR code](qr.png)

## Components

* [Badge](docs/view/Badge/README.md)
* [Bottom Nav](docs/view/BottomNav/README.md)
* [Bottom Sheet](docs/view/BottomSheet/README.md)
* [Button](docs/view/Button/README.md)
* [Card](docs/view/Card/README.md)
* [Calendar](docs/view/Calendar/README.md) (Deprecated)
* [Calendar2](docs/view/Calendar2/README.md)
* [Checkbox](docs/view/Checkbox/README.md)
* [Chip](docs/view/Chip/README.md)
* [Dialog](docs/view/Dialog/README.md)
* [Horizontal Nav](docs/view/HorizontalNav/README.md)
* [Floating Action Button](docs/view/FloatingActionButton/README.md)
* [Interactive Star Rating](docs/view/InteractiveStarRating/README.md)
* [Map Markers](docs/view/Maps/README.md)
* [Nav Bar](docs/view/NavBar/README.md)
* [Nudger](docs/view/Nudger/README.md)
* [Panel](docs/view/Panel/README.md)
* [RadioButton](docs/view/RadioButton/README.md)
* [Overlay](docs/view/Overlay/README.md)
* [Rating](docs/view/Rating/README.md)
* [Slider](docs/view/Slider/README.md)
* [Snackbar](docs/view/Snackbar/README.md)
* [Spinner](docs/view/Spinner/README.md)
* [Star Rating](docs/view/StarRating/README.md)
* [Switch](docs/view/Switch/README.md)
* [Text](docs/view/Text/README.md)
* [Text Field](docs/view/TextField/README.md)
* [Text Spans](docs/view/TextSpans/README.md)
* [Toast](docs/view/Toast/README.md)

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
