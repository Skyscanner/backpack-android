# Contributing

In this document we describe how to set up this repository for development and the release process.

## Access

> Skyscanner employees only

Please ensure you have push rights to this repository, rather than forking the repository for contributions. Follow the "Engineering Contribution" guide in the Backpack space in confluence to get access.

## Environment

To set up the Android environment, install Android Studio. Make sure that "Android SDK Command-line Tools (latest)" are installed as well (in Android SDK manager). Once installed, use the following commands to set up the Android SDK:

```
echo "export ANDROID_HOME=\"$HOME/Library/Android/sdk\"" >> ~/.bash_profile
echo "export ANDROID_SDK_ROOT=\"$HOME/Library/Android/sdk\"" >> ~/.bash_profile
source ~/.bash_profile
```

You may also have to install "Android SDK Command Line Tools" from the SDK tools screen in Android Studio.

## Setup

Given that you have a compatible environment as stated above you can now set up the project.

+  Open the project in Android Studio
+ If you are a Skyscanner employee, search the internal documentation for _"Guide – Setup Internal Backpack Android Builds"_ and follow the instructions.

## Creating components

To create a new component in Compose you can use the following script to setup the most common required parts of a component.

To run it execute:
```
./scripts/generate-component.py
```
and follow instructions, or provide arguments directly:
```
./scripts/generate-component.py [NameOfComponent] [packagename]
```

Note: This currently is not supported for creating components using the view system.

## Testing

### Snapshot testing
We use snapshot testing to ensure there are no unintended changes to UI components.

#### Setup

> Note: Currently, snapshot tests run on Apple chips will result in a different snapshot to Intel-based laptops or CI.
> You should use it for debug purposes only.

Install system images & create an AVD
```
# x86
$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager "system-images;android-28;google_apis;x86"
$ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd --name "bpk-droid-avd" --force --package "system-images;android-28;google_apis;x86" --device "Nexus 4" && cp bpk-droid-local.ini ~/.android/avd/bpk-droid-avd.avd/config.ini

# ARM
$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager "system-images;android-28;google_apis;arm64-v8a"
$ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd --name "bpk-droid-avd" --force --package "system-images;android-28;google_apis;arm64-v8a" --device "Nexus 4" && cp bpk-droid-local-arm.ini ~/.android/avd/bpk-droid-avd.avd/config.ini
```

Create an SD card for the snapshot tests

```
# Linux
$ANDROID_HOME/emulator/mksdcard -l e 512M sd.img
# OSX
hdiutil create -megabytes 512 -fs MS-DOS -layout NONE -o sd && mv sd.dmg sd.img
```

Make sure no emulator or physical devices are attached, otherwise tests will fail.
To start the emulator and attach an SD card to it, run

```
$ANDROID_HOME/emulator/emulator -avd bpk-droid-avd -sdcard sd.img &
```

#### Creating tests

Snapshot tests live in the `app` module. For a new test class extend from `BpkSnapshotTest`.
Each test function will result in a separate snapshot test - try to  keep the tests simple and add a test for each state of a component.

Wrap your component with the correct states in the `snap` function to take the snapshot. There are various configurations supported:

To set the dimension for your snapshot use the `width` and/or `height` property on the `snap()` function.

To set the background colour use the `background` property on the `snap` function. By default the snapshots will have the `canvas` background.

By default snapshot tests run on 4 variants - default, dark mode, RTL and themed (skipped for compose). In some cases you may want to only run a snapshot test on some variants - for example if a component has many different states without layout changes you may want to consider skipping RTL.
You can do this by adding the `@Variant` annotation to either the test class (applies to all tests in class) or function with the desired variants, like this:

```
@Variant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
```

You can use the following set of rules to determine the variants:
* There should be at least one test covering all the variants (usually it's `default()`)
* If you expect the test to behave differently in layout, include `Rtl` variant
* If you expect the colours to be different in the dark mode, include `DarkMode` variant
* If you have customisable theme properties which may change the colours in this specific test, include `Themed` variant
* If the same test variance is covered in other tests, consider having only one test with different variants. For instance,
if you have tests for toolbar with home icon, action icon, action text and one combined test, which includes all of this
elements displayed together, consider having the variants only for the last test.

For some more complex components with many different types you may want to make use of the `Parameterized` test runner to test all variants.
To ensure snapshots get saved for all parameters pass the `tags` property in the `BpkSnapshotTest` constructor. For an example look at `BpkButtonTest`.

#### Debugging snapshot tests
After adding new snapshot tests or making UI changes, run

```
 ./scripts/record_screenshot_tests.sh
```

This will generate the snapshots. Verify the changes & generated snapshots are as expected,
but don't commit the snapshot files – our CI uses different emulator environment to validate snapshots. Therefore, snapshots,
generated on the local machine will be different, and the CI will fail. You should only use it for debugging purposes.

### Debugging individual test classes

While you're creating your snapshot tests or are debugging an issue it may be helpful to run an individual test class. You can do that with the following command:

```
./gradlew ossDebugExecuteScreenshotTests -Precord -PdirectorySuffix=default -Pandroid.testInstrumentationRunnerArguments.class=net.skyscanner.backpack.package.YourClassTest
```

You can replace the `directorySuffix` property with `dm`, `rtl` or `themed` depending on what you're trying to test.

### Verifying the snapshot tests with the CI

Our CI will always update the snapshots on each run. It's responsibility of the contributor and the reviewer
to validate and verify it. The snapshots for the PR can be found in "Files changed" tab. The snapshots for the commit can be
found in the same tab once you click on the comment.

Since CI run cannot trigger CI checks again, you need to commit something after the snapshots have been generated to trigger the CI check.

If you don't have any pending changes, you can use an empty commit again to trigger the CI:

```
git commit --allow-empty -m "Trigger CI" && git push
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

To add a new component, add an annotation to `components` package:

```kotlin
import net.skyscanner.backpack.meta.ComponentMarker

@ComponentMarker("My Component") // name of the component to be used in UI. Also used for the docs folder
annotation class MyComponent
```

To add a new demo screen (we call it a story), annotate your composable function with the component and story annotations:

```kotlin
@Composable
@MyComponent
@ComposeStory
fun MyStory(modifier: Modifier = Modifier) {
  // your story
}
```

Here's an example of a simple View story:

```kotlin

@Composable
@MyComponent
@ViewStory
fun MyViewStory(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.my_layout, modifier)
```

In a case you have multiple sections for the component, you need to specify the name of each story in the annotation:

```kotlin
@ComposeStory("Story name") // note this name will also be used for the screenshot
```

## Docs screenshots

To make our documentation clearer we include screenshots on the docs site for each components.

By default, every story will have a screenshot generated. To exclude a story from the screenshot generation,
set `kind` attribute to `DemoOnly`: `@ComposeStory(kind = StoryKind.DemoOnly)`.

If you want to create a screenshot for a story that is not included in the demo app,
set `kind` attribute to `ScreenshotOnly`: `@ViewStory(kind = StoryKind.ScreenshotOnly)`.

Run `./gradlew :app:recordScreenshots` to capture all screenshots. Files will be saved in the correct directory.

> Note: Python is required.

The generated screenshots will be saved in the component folder in `docs` directory.
By default, the screenshots will be named `default.png` and `default_dm.png` for day and night mode respectively.
If you specified the story name, it'll be converted to lowercase with spaces replaced with dashes:
`Test story name` -> `test-story-name.png`.

To include it to readme, you can use the following syntax (for Compose, for View replace the path):

```md

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ComponentName/screenshots/default.png" alt="ComponentName component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/ComponentName/screenshots/default_dm.png" alt="ComponentName component - dark mode" width="375" /> |

```

In case you want to auto-perform some actions for screenshots, you can check whether you're in a screenshot mode by following:

```kotlin
val automationMode = LocalAutomationMode.current
```

Verify the screenshots and commit the changes.

> Note: Sometimes the screenshot generation may also contain changes to other screenshots. Unless they are intended remove these changes and only commit the ones with intended changes.

## Token generation

In some cases you may need to re-generate tokens after an update in foundation. Token generation currently relies on node. To manage the language runtime we recommend using [`nvm`](https://github.com/creationix/nvm). The required Node version is in `.nvmrc`.

With `nvm`, use `nvm use` to set the correct Node version in your machine.

To update tokens:
- Ensure the version for foundations + SVGs is up-to-date in `package.json`
- Run `npm install` to ensure the node package is installed
- Run `./gradlew generateTokens` to generate the tokens
- Commit the changes

## Code Style
Code style is ensured by [detekt](https://github.com/detekt/detekt). It runs automatically during the `check` phase but can also be executed by running `./gradlew detekt`.

To auto fix problems run `./gradlew detekt --auto-correct`.

## Experimental changes

Want to run A/B experiments on features that entail changes to Backpack components? Continue reading below 👇

<details>
<summary>When is a component change considered experimental?</summary>

If the component or change you want to contribute to Backpack is not stable and it depends on the results of an experiment then it is considered experimental.

</details>

<details>
<summary>What do you need to do to mark a component or part of a component as experimental?</summary>

For experimental changes or experimental components, you should use the `ExperimentalBackpackApi` annotation. This will require consumers to opt in to use the API and therefore highlight that the component or property is experimental.

For major changes, you should create a new experimental V2 component. If the experiment is successful, the old component should be deprecated.

Any follow-up changes to experimental components will not be considered breaking.
</details>

<details>
<summary>When should documentation be created and published?</summary>

Each Bpk component has a corresponding README file which contains information about the component such as usage examples and API documentation. Our components' full documentation is at [skyscanner.design](https://www.skyscanner.design). New experimental components should have a README file, but don’t need to be published to [skyscanner.design](https://www.skyscanner.design). Make sure the README file reflects the component is experimental! When an experiment has run and is considered successful and so the change is stable, documentation can be published.

For changes to existing components, make sure the API documentation is updated to indicate if something is experimental.
    
Major changes will often require a migration guide. If an experiment is considered succesful, you should add a migration guide within the docs folder located in the respective component folder.

</details>

<details>
<summary>How long does experimentation code live in Backpack?</summary>

Experimentation code should be cleaned up at most 2 weeks after an experiment has completed. In the case of a successful experiment, annotations should be removed and documentation should be published. In the case of an unsuccessful experiment, the code should be removed altogether.
</details>

<details>
<summary>Examples</summary>

Here’s an end-to-end example on how to add an experimental prop to a Bpk component:

1. Reach out to Koala with the proposed change
2. Contribute code changes. Make sure the API table is updated too!
```kotlin
@Composable
fun BpkText(
  text: String,
  color: Color,
  @ExperimentalBackpackApi sparkles: Boolean? = false,
  ...
)
```
3. Released by Koala
4. Adopt changes in project
5. Run experiment
    - if experiment is successful, publish documentation (only Koala members) and remove experimental code.
    - if experiment is unsuccessful and further iterations are needed, repeat from step 2. Otherwise, remove experimental code. That’s all!
</details>


## How we review Backpack contributions

Please see the [code review guidelines](https://github.com/Skyscanner/backpack/blob/main/CODE_REVIEW_GUIDELINES.md).

## Releasing

> Backpack team only

 - Publish the latest draft on the [releases pages](https://github.com/Skyscanner/backpack-android/releases)
 - Ensure CI runs the release workflow successfully
 - Once released verify the artifacts are available

 The release workflow will also trigger our design docs publish. If successful, you should see your component changes at [skyscanner.design](https://skyscanner.design).
 > Note: Don't forget that new components need to be added manually!


## Docs

Run `./gradlew dokkaHtmlMultiModule` to generate API docs. Docs will be generated in the `dokka` folder.
