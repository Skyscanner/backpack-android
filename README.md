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
implementation 'net.skyscanner.backpack:backpack-android:36.0.0' // for Android View system
implementation 'net.skyscanner.backpack:backpack-compose:0.2.0' // for Compose (beta)
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

* [border radii](Backpack/src/main/res/values/backpack.radii.xml) – `@dimen/bpkBorderRadiusSm` (XML), `R.dimen.bpkBorderRadiusSm` (Java/Kotlin)
* [colours](Backpack/src/main/res/values/backpack.color.xml) – `@color/bpkSkyBlue` (XML), `R.color.bpkSkyBlue` (Java/Kotlin)
* [semantic colours](Backpack/src/main/res/values/backpack.semantic.color.xml) – `@color/bpkTextPrimary` (XML), `R.color.bpkTextPrimary` (Java/Kotlin)
* [elevations](Backpack/src/main/res/values/backpack.elevation.xml) – `@dimen/bpkElevationSm` (XML), `R.dimen.bpkElevationSm` (Java/Kotlin)
* [spacings](Backpack/src/main/res/values/backpack.dimensions.spacing.xml) – `@dimen/bpkSpacingLg` (XML), `R.dimen.bpkSpacingLg` (Java/Kotlin)
* [text styles](Backpack/src/main/res/values/backpack.text.xml) – `?bpkTextBaseSize` (XML)

### Jetpack Compose

> Backpack for Jetpack Compose status is experimental.

Backpack for Compose targets **stable Compose 1.0.0** release.

You can find the list of the available components, as well as the code samples and the screenshots [here](https://backpack.github.io/components/text?platform=compose).

The design system tokens are located in a `net.skyscanner.backpack.compose.tokens` package.
Here's the list of the token types and the samples of its usage:

* [border radii](backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/tokens/BpkBorderRadius.kt) – `BpkBorderRadius.Lg`
* [colours](backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/tokens/BpkColor.kt) – `BpkColor.SkyBlue`
* [semantic colours](backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/tokens/BpkColors.kt) – `BpkTheme.colors.textPrimary`
* [elevations](backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/tokens/BpkElevation.kt) – `BpkElevation.Sm`
* [spacings](backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/tokens/BpkSpacing.kt) – `BpkSpacing.Sm`
* [text styles](backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/tokens/BpkTypography.kt) – `BpkTheme.typography.heading4`

## Contributing to Backpack

Please see the [Contributing guide][0] for instructions on contributing to this project.

## License

Backpack is available under the Apache 2.0 license. See the LICENSE file for more info.

[0]: CONTRIBUTING.md
