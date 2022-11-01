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

### Snapshot testing
We use snapshot testing to ensure there are no unintended changes to UI components.

#### Setup

Create an AVD using the following command

> Note: Currently, snapshot testing doesn't work properly on M1 chips. Consider recording snapshots on Intel-based macs or using GitHub Actions.

```
# x86
$ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd --name "bpk-droid-avd" --force --package "system-images;android-24;google_apis;x86" --device "Nexus 4" && cp bpk-droid-local.ini ~/.android/avd/bpk-droid-avd.avd/config.ini


# ARM
$ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd --name "bpk-droid-avd" --force --package "system-images;android-24;google_apis;arm64-v8a" --device "Nexus 4" && cp bpk-droid-local-arm.ini ~/.android/avd/bpk-droid-avd.avd/config.ini
```

Create an SD card for the snapshot tests

```
# Linux
$ANDROID_HOME/emulator/mksdcard -l e 512M sd.img
# OSX
hdiutil create -megabytes 512 -fs MS-DOS -layout NONE -o sd && mv sd.dmg sd.img
```

Snapshot testing depends on a python package which can be installed as:

```
pip3 install Pillow==9.0.1
```

Make sure no emulator or physical devices are attached, otherwise tests will fail.
To start the emulator and attach an SD card to it, run

```
$ANDROID_HOME/tools/emulator -avd bpk-droid-avd -sdcard sd.img &
```

#### Creating tests

Snapshot tests live in the `app` module. For a new test class extend from `BpkSnapshotTest`.
Each test function will result in a separate snapshot test - try to  keep the tests simple and add a test for each state of a component.
Use the `setDimensions` function in the `setup()` function to set the right dimension for the snapshots.

For `View` components you can create the component, set the states and then call `snap` to take the snapshots.
For `Compose` components use the `composed` function to wrap your component - this will generate the snapshot.

By default snapshot tests run on 4 variants - default, dark mode, RTL and themed (skipped for compose). In some cases you may want to only run a snapshot test on some variants - for example if a component has many different states without layout changes you may want to consider skipping RTL.

You can do this by adding the `assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)` function with the desired variants.
**IMPORTANT**: Make sure this is called _before_ the test activity is run to avoid unnecessary delay. In a view test make it the first line of your test. In a compose test call it before the `composed` function, rather than inside it.

#### Verifying changes
After adding new snapshot tests or making UI changes, run

```
 ./scripts/record_screenshot_tests.sh
```

This will generate the latest snapshots. Verify the changes & generated snapshots are as expected and commit the changes.

To test changes use

```
./scripts/verify_screenshot_tests.sh
```

If the check fails you either need to fix the issue if a change was unintended or record script above instead to update the snapshots.

### Using CI for generating snapshot

Alternatively, you can use GitHub Actions CI to generate the snapshots.
"Record snapshots" command in PR comments will trigger the CI and lead to new snapshot commits pushed to your PR.
Notice that this run must be approved for external contributors.
Since CI run cannot trigger CI checks again,
you need to commit something after the snapshots have been generated to trigger the CI check.

If you don't have anything to commit, you can use this to trigger the CI:

```
git commit --allow-empty -m "Trigger CI" --no-verify && git push
```

### Espresso tests
If your component contains logic that can't be verified via snapshot tests you can use espresso to test the logic. These tests live in the `Backpack` (for View components) or `backpack-compose` (for Compose components) module, depending on the component.

To run connected tests run

```
./gradlew Backpack:connectedCheck // for view components
./gradlew backpack-compose:connectedCheck // for compose components
```

### Deploying locally
To test your branch/implementation in a codebase, publish the dependency locally (replacing the version with your desired version):

```
./gradlew publishToMavenLocal -Pversion=x.x.x-SNAPSHOT
```

To use this dependency make sure to add `mavenLocal()` to your repository list in your codebase, as well as updating backpack to the snapshot version.
```
implementation 'net.skyscanner.backpack:backpack-android:x.x.x-SNAPSHOT'
```

## Demo app screens

To verify changes during development, generate docs screenshots and showcase our components internally & externally we provide a demo app with all our components.
To add a new component open `net/skyscanner/backpack/demo/data/ComponentRegistry.kt` and add your component to the list. For compose components use `composeStory` and use a newly created component story from the `compose` package. For view components you can add `story NodeData { Story of R.layout.fragment_x }` for simple components, or use a custom story instead of `Story` for more complex stories.

## Docs screenshots

To make our documentation clearer we include screenshots on the docs site for each components.

### Setup

Before running the script install and start the docs emulator.

```
# x86
$ANDROID_HOME/cmdline-tools/latest/bin/avdmanager --verbose create avd --force --name "bpk-droid-screenshot-avd" --device "pixel" --package "system-images;android-30;google_apis;x86" --tag "google_apis" --abi "x86"
$ANDROID_HOME/tools/emulator -avd bpk-droid-screenshot-avd


# ARM
$ANDROID_HOME/cmdline-tools/latest/bin/avdmanager --verbose create avd --force --name "bpk-droid-screenshot-avd" --device "pixel" --package "system-images;android-30;google_apis;arm64-v8a" --tag "google_apis" --abi "arm64-v8a"
$ANDROID_HOME/tools/emulator -avd bpk-droid-screenshot-avd
```

### Generating screenshots

To add a new screenshot open `net/skyscanner/backpack/docs/DocsRegistry.kt` and add the screenshot to the `screenshots` list.
**Important**: For the screenshot to be generated the name must match the title of the page.

Run `./scripts/generate_screenshots.sh` to capture all screenshots. Files will be saved in the correct directory.

> Note: Python is required.

Verify the screenshots and commit the changes.

> Note: Unfortunately the screenshot generation may also contain changes to other screenshots. Unless they are intended remove these changes and only commit the ones with intended changes.

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
