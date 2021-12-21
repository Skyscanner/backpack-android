# Backpack Android

> Backpack is a collection of design resources, reusable components and guidelines for creating Skyscanner's products.

[![CI Status](https://github.com/Skyscanner/backpack-android/workflows/CI/badge.svg)](https://github.com/Skyscanner/backpack-android/actions)

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![license](https://img.shields.io/github/license/Skyscanner/backpack-android.svg)](https://github.com/Skyscanner/backpack-android)
[![platform](https://img.shields.io/badge/platform-android-green.svg)](https://github.com/Skyscanner/backpack-android)

Backpack for Android supports two targets: Android View system and Jetpack Compose.

Backpack is available through Maven Central. Thus, before adding Backpack to your project,
make sure Maven Central is in your repositories list. Add this to your root `build.gradle`:

```gradle
repositories {
    mavenCentral()
}
```

### Demo application
The Backpack demo application is a good way of referring to the variants available for a component and their correct usage.
The code is available under `/app` directory.
[The app can be downloaded from App Center](https://install.appcenter.ms/orgs/backpack/apps/backpack-android/distribution_groups/everyone)
or by scanning the QR code below.

![QR code](qr.png)

## Installation – Android View system

Add the following dependency to your `build.gradle` (in your app module) in the `dependencies` block:

```gradle
implementation 'net.skyscanner.backpack:backpack-android:33.2.0'
```

You also need to inherit your app theme from Backpack:
```xml
  <style name="AppTheme" parent="BpkTheme">
    <!-- Customize your theme here. -->
  </style>
```

You can find the list of the available components, as well as code samples and its screenshots [here](https://backpack.github.io/components/text?platform=android).

## Installation – Jetpack Compose

> Backpack for Jetpack Compose status is experimental.

Add the following dependency to your `build.gradle` (in your app module) in the `dependencies` block:

```gradle
implementation 'net.skyscanner.backpack:backpack-compose:1.0.0'
```

All Compose components need to be used in `BpkTheme` scope:

```kotlin
BpkTheme {
    BpkText("Hello from Compose!")
}
```

Backpack for Compose targets stable Compose 1.0.0 release.

You can find the list of the available components, as well as code samples and its screenshots [here](https://backpack.github.io/components/text?platform=compose).

## Contributing to Backpack

Please see the [Contributing guide][0] for instructions on contributing to this project.

## License

Backpack is available under the Apache 2.0 license. See the LICENSE file for more info.

[0]: CONTRIBUTING.md
