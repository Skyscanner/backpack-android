# Contributing

In this document we describe how to set up this repository for development and the release process.

## Access

> Skyscanner employees only

Please ensure you have push rights to this repository, rather than forking the repository for contributions. Follow the "Engineering Contribution" guide in the Backpack space in confluence to get access. 

## Environment

We use Node in this project. To manage the language runtime we recommend using [`nvm`][1]. The required Node version is in `.nvmrc`.

With `nvm`, use `nvm use` to set the correct Node version in your machine.

To set up the Android environment, install Android Studio. Make sure that "Android SDK Command-line Tools (latest)" are installed as well (in Android SDK manager). Once installed, use the following commands to set up the Android SDK:

```
echo "export ANDROID_HOME=\"$HOME/Library/Android/sdk\"" >> ~/.bash_profile
echo "export ANDROID_SDK_ROOT=\"$HOME/Library/Android/sdk\"" >> ~/.bash_profile
source ~/.bash_profile
```

You may also have to install "Android SDK Command Line Tools" from the SDK tools screen in Android Studio.

Install system images
```
# x86
$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager "system-images;android-24;google_apis;x86"
$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager "system-images;android-30;google_apis;x86"

# ARM
$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager "system-images;android-24;google_apis;arm64-v8a"
$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager "system-images;android-30;google_apis;arm64-v8a"
```

## Setup

Given that you have a compatible environment as stated above you can now set up the project.

+ `npm install` to install npm dependencies
+ `npm run build` to build Backpack tokens
+  Open the project in Android Studio
+ If you are a Skyscanner employee, search the internal documentation for _"Guide – Setup Internal Backpack Android Builds"_ and follow the instructions.


## Testing

#### Snapshot testing
Create an AVD using the following command

> Note: Currently, screenshot testing doesn't work properly on M1 chips.

```
# x86
$ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd --name "bpk-droid-avd" --force --package "system-images;android-24;google_apis;x86" --device "Nexus 4" && cp bpk-droid-local.ini ~/.android/avd/bpk-droid-avd.avd/config.ini


# ARM
$ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd --name "bpk-droid-avd" --force --package "system-images;android-24;google_apis;arm64-v8a" --device "Nexus 4" && cp bpk-droid-local.ini ~/.android/avd/bpk-droid-avd.avd/config.ini
```

Create an SD card for the screenshot tests (Linux)

```
$ANDROID_HOME/emulator/mksdcard -l e 512M sd.img
```

Create an SD card for the screenshot tests (OSX)

```
hdiutil create -megabytes 512 -fs MS-DOS -layout NONE -o sd && mv sd.dmg sd.img
```

Snapshot testing depends on a python package which can be installed as:

```
pip3 install Pillow
```

To start the emulator and attach an SD card to it, run

```
$ANDROID_HOME/tools/emulator -avd bpk-droid-avd -sdcard sd.img &
```

After adding new snapshot tests, run

```
 ./scripts/record_screenshot_tests.sh
```

To test changes use

```
./scripts/verify_screenshot_tests.sh
```

#### Espresso tests
To run connected tests run

```
./gradlew Backpack:connectedCheck
```

#### Deploying locally
To test your branch/implementation in a codebase, publish the dependency locally (replacing the version with your desired version):

```
./gradlew publishToMavenLocal -Pversion=x.x.x-SNAPSHOT
```

To use this dependency make sure to add `mavenLocal()` to your repository list in your codebase, as well as updating backpack to the snapshot version.
```
implementation 'net.skyscanner.backpack:backpack-android:x.x.x-SNAPSHOT'
```

## Taking screenshots

Before running the script install and start the docs emulator.

```
# x86
$ANDROID_HOME/cmdline-tools/latest/bin/avdmanager --verbose create avd --force --name "bpk-droid-screenshot-avd" --device "pixel" --package "system-images;android-30;google_apis;x86" --tag "google_apis" --abi "x86"
$ANDROID_HOME/tools/emulator -avd bpk-droid-screenshot-avd


# ARM
$ANDROID_HOME/cmdline-tools/latest/bin/avdmanager --verbose create avd --force --name "bpk-droid-screenshot-avd" --device "pixel" --package "system-images;android-30;google_apis;arm64-v8a" --tag "google_apis" --abi "arm64-v8a"
$ANDROID_HOME/tools/emulator -avd bpk-droid-screenshot-avd
```

Run `./scripts/generate_screenshots.sh` to capture all screenshots. Files will be saved in the correct directory.

> Note: Python is required.

### Adding new screenshots

To add new screenshots, add a new entry to `net/skyscanner/backpack/docs/DocsRegistry.kt`

## Demo app shortcuts

- `Shift + D` : Toggle story view between RTL and LTR
- `Shift + T` : Hide the toolbar

## Code Style
Code style is ensured by [ktlint](https://github.com/shyiko/ktlint). It runs automatically during the `check` phase but can also be executed by running `gradlew ktlint`.

To auto fix problems run `gradlew ktlintFormat`.

## How we review Backpack contributions

Please see the [code review guidelines](https://github.com/Skyscanner/backpack/blob/main/CODE_REVIEW_GUIDELINES.md).

## Releasing

> Backpack team only

 - Publish the latest draft on the [releases pages](https://github.com/Skyscanner/backpack-android/releases)
 - Ensure CI runs the release workflow successfully
 - Once released verify the artifacts are available

## Docs

Run `npm run docs` to generate API docs. Docs will be generated in the `build/docs` folder by default. To provide a different output folder use `npm run docs -- -PdokkaOutput=/path/to/folder`

Run `npm run docs:clean` to remove generated docs.



[1]: https://github.com/creationix/nvm
