# Backpack Android

> Backpack is a collection of design resources, reusable components and guidelines for creating Skyscanner's products.

[![CI Status](http://img.shields.io/travis/Skyscanner/backpack-android.svg?style=flat)](https://travis-ci.org/Skyscanner/backpack-android)
[![Greenkeeper badge](https://badges.greenkeeper.io/Skyscanner/backpack-android.svg)](https://greenkeeper.io/)
[![Github tag](https://img.shields.io/github/tag/skyscanner/backpack-android.svg)](https://github.com/Skyscanner/backpack-android)
[![license](https://img.shields.io/github/license/Skyscanner/backpack-android.svg)](https://github.com/Skyscanner/backpack-android)
[![platform](https://img.shields.io/badge/platform-android-green.svg)](https://github.com/Skyscanner/backpack-android)

## Installation

Backpack is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). To install
all of it, add the following line to your `build.gradle` (in your app module) in the `dependencies` block:

```gradle
implementation 'com.github.skyscanner:backpack-android:0.5.0'
```

If your app resolves dependencies through Jitpack you're all set, if not add in your root `build.gradle`

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
```
## Components
* [Badge](docs/Badge/README.md)
* [Panel](docs/Panel/README.md)
* [Text](docs/Text/README.md)
* [Button](docs/Button/README.md)
* [Card](docs/Card/README.md)
* [Spinner](docs/Spinner/README.md)

## Usage

### Radii

The Backpack raddi tokens are available as [dimension resource](Backpack/src/main/res/values/backpack.radii.xml).

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
  android:textColor="@color/bpkBlue500" />
```

```kotlin
R.color.bpkBlue500
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
