# Backpack Android

> Backpack is a collection of design resources, reusable components and guidelines for creating Skyscanner's products.

[![CI Status](https://github.com/Skyscanner/backpack-android/workflows/CI/badge.svg)](https://github.com/Skyscanner/backpack-android/actions)

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![license](https://img.shields.io/github/license/Skyscanner/backpack-android.svg)](https://github.com/Skyscanner/backpack-android)
[![platform](https://img.shields.io/badge/platform-android-green.svg)](https://github.com/Skyscanner/backpack-android)

Backpack for Android supports two targets: **Android View** system and **Jetpack Compose**.


### Demo application
The Backpack demo application is a good way of referring to the variants available for a component and their correct usage.
The code is available under `/app` directory.
[The app can be downloaded from App Center](https://install.appcenter.ms/orgs/backpack/apps/backpack-android/distribution_groups/everyone)
or by scanning the QR code below.

![QR code](qr.png)

## Installation

Backpack is available through Maven Central. Thus, before adding Backpack to your project,
make sure Maven Central is in your repositories list. Add this to your root `build.gradle`:

```gradle
repositories {
    mavenCentral()
}
```


Add the following dependencies to your `build.gradle` (in your app module) in the `dependencies` block:

```gradle
implementation 'net.skyscanner.backpack:backpack-android:33.2.0' // for Android View system
implementation 'net.skyscanner.backpack:backpack-compose:x.x.x' // for Compose (not released yet)
```

All Backpack components must to be used within `BpkTheme` scope:

Android View system:
```xml
  <style name="AppTheme" parent="BpkTheme">
    <!-- Customize your theme here. -->
  </style>
```

Compose:

```kotlin
BpkTheme {
    BpkText("Hello from Compose!")
}
```

## Features

### Android View system

You can find the list of the available components, as well as the code samples and the screenshots [here](https://backpack.github.io/components/text?platform=android).

All design system tokens exist as public Android resources. Here's the list of the token types and the samples of its usage:

* [border radii](https://backpack.github.io/tokens/radius) – `@dimen/bpkBorderRadiusSm` (XML), `R.dimen.bpkBorderRadiusSm` (Java/Kotlin)
* [colours](https://backpack.github.io/guidelines/colour) – `@color/bpkSkyBlue` (XML), `R.color.bpkSkyBlue` (Java/Kotlin)
* [semantic colours](https://backpack.github.io/guidelines/colour) – `@color/bpkTextPrimary` (XML), `R.color.bpkTextPrimary` (Java/Kotlin)
* [elevations](https://backpack.github.io/tokens/shadow) – `@dimen/bpkElevationSm` (XML), `R.dimen.bpkElevationSm` (Java/Kotlin)
* [spacings](https://backpack.github.io/tokens/spacing) – `@dimen/bpkSpacingLg` (XML), `R.dimen.bpkSpacingLg` (Java/Kotlin)
* [text styles](https://backpack.github.io/tokens/typesetting) – `?bpkTextBaseSize` (XML)

### Jetpack Compose

> Backpack for Jetpack Compose status is experimental.

Backpack for Compose targets **stable Compose 1.0.0** release.

You can find the list of the available components, as well as the code samples and the screenshots [here](https://backpack.github.io/components/text?platform=compose).

The design system tokens are located in a `net.skyscanner.backpack.compose.tokens` package.
Here's the list of the token types and the samples of its usage:

* [border radii](https://backpack.github.io/tokens/radius) – `BpkBorderRadius.Lg`
* [colours](https://backpack.github.io/guidelines/colour) – `BpkColor.SkyBlue`
* [semantic colours](https://backpack.github.io/guidelines/colour) – `BpkTheme.colors.textPrimary`
* [elevations](https://backpack.github.io/tokens/shadow) – `BpkElevation.Sm`
* [spacings](https://backpack.github.io/tokens/spacing) – `BpkSpacing.Sm`
* [text styles](https://backpack.github.io/tokens/typesetting) – `BpkTheme.typography.heading4`

## Contributing to Backpack

Please see the [Contributing guide][0] for instructions on contributing to this project.

## License

Backpack is available under the Apache 2.0 license. See the LICENSE file for more info.

[0]: CONTRIBUTING.md
