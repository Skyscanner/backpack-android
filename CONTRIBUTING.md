# Contributing

In this document we describe how to setup this repository for development and the release process.

## Environment

We use Node in this project. To manage the language runtime we recommend using [`nvm`][1] respectively. The required Node version is in `.nvmrc`.

With `nvm` use `nvm use` to set the correct node version in your machine.

To setup the Android environment install Android studio. Once installed, use the following commands to setup the android sdk

```
echo "export ANDROID_HOME=\"$HOME/Library/Android/sdk\"" >> ~/.bash_profile
echo "export ANDROID_SDK_ROOT=\"$HOME/Library/Android/sdk\"" >> ~/.bash_profile
source ~/.bash_profile
```

Install system images
```
$ANDROID_HOME/tools/bin/sdkmanager "system-images;android-21;google_apis;x86"
```

## Setup

Given that you have a compatible environment as stated above you can now setup the project.

+ `npm install` to install npm dependencies
+ `npm run build` to build Backpack tokens
+  Open the project in Android studio

## Testing

#### Snapshot testing
Create an AVD using the following commands

```
$ANDROID_HOME/tools/bin/avdmanager create avd --name "bpk-droid-avd" --force --package "system-images;android-21;google_apis;x86" --device "Nexus 4" && cp bpk-droid-local.ini ~/.android/avd/bpk-droid-avd.avd/config.ini
$ANDROID_HOME/tools/mksdcard -l e 512M sd.img
```

Snapshot testing depends on a python package which can be installed as:

```
easy_install Pillow
```

To start the emulator and attach an sdcard to it, run

```
$ANDROID_HOME/tools/emulator -avd bpk-droid-avd -sdcard sd.img &
```

After adding new snapshot tests, run

```
./gradlew app:recordDebugAndroidTestScreenshotTest
```

To test changes use

```
./gradlew app:verifyDebugAndroidTestScreenshotTest
```

#### Espresso tests
To run connected tests run

```
./gradlew Backpack:connectedCheck
```

#### Validating changes
Backpack provides the ability to build and publish any specific branch from a fork for testing. To use your own branch change the dependency in the below format:

```
implementation 'com.github.<github-username>:backpack-android:<branch-name>'
```

## Demo app shortcuts

- `Shift + D` : Toggle story view between RTL and LTR
- `Shift + T` : Hide the toolbar
- `Shift + H` : Show/Hide debug markers

## Code Style
Code style is ensured by [ktlint](https://github.com/shyiko/ktlint). It runs automatically during the `check` phase but can also be executed by running `gradlew ktlint`.

To auto fix problems run `gradlew ktlintFormat`.

## Releasing

> Backpack team only

 - Move everything in `UNRELEASED.md` to `CHANGELOG.md` with the intended release version as the heading.
 - Run `npm run release` and follow the process through, you'll be asked which semantic version the release. Once released verify the artefacts on [`jitpack`][3]

## Docs

Run `npm run docs` to generate API docs. Docs will be generated in the `build/docs` folder by default. To provide a different output folder use `npm run docs -- -PdokkaOutput=/path/to/folder`

Run `npm run docs:clean` to remove generated docs.



[1]: https://github.com/creationix/nvm
[3]: https://jitpack.io/#Skyscanner/backpack-android
