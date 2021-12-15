## backpack-android architecture overview:

`backpack-android` is a traditional Gradle Android lib project, composed of the following modules.

- app: The example app
- Backpack: The Backpack Android components for the traditional view system
- backpack-common: Common code shared between compose and traditional Backpack
- backpack-lint: Custom lint rules to ensure backpack is used and conventions are followed

## App

### Stories

Stories are defined programmatically in  tree-like structure that is then used to build the UI.

Important classes/packages are:

- **`{javaSrc}/data/ComponentRegistry`**

  Defines a tree of `stories` used to build the UI:

  ```kotlin
  "Chip" story NodeData { ChipStory() },
  "Panel" story NodeData { Story of R.layout.fragment_panel },
  "Spinner" story NodeData({ children -> SubStory of children },
    mapOf(
      "Default" story NodeData { Story of R.layout.fragment_spinner },
      "Small" story NodeData { Story of R.layout.fragment_spinner_small }
    )),
  ```

- **`res/layout/`**
  Where layouts for each story is defined. They are named `fragment_{story_name}`.

- **`{javaSrc}/stories`**
  If a story needs dynamic behaviours it will have an accompanying Kotlin class inside the `stories` package.

### Theming

Theming for the example app is set in the `Settings` view and saved in the local shared preferences
file.

Important classes/packages are:

- **`{javaSrc}/SettingsActivity`**
  Activity that shows the theming configuration.

- **`{javaSrc}/BackpackDemoApplication`**
  Where all initialization logic resides.

- **`{javaSrc}/data/SharedPreferences`**
  Used to read/write shared preferences for the example app, including the current theme.


## Backpack

The `Backpack` lib is where all traditional view components live. All packages, except `utils` are Backpack components.

### Component's architecture

There are only a few things that should be observed about the architecture of the components:

1. Whenever possible the Backpack component should extend from the Android base component and add the additional extra functionality instead of wrapping the Android component inside another `ViewGroup`. This is to keep the view hierarchy as small as possible.
2. All props should be available via XML and programmatically. Whenever a component is available in both XML and code all its properties should also be available in both.
3. Changing a property programmatically should immediately reflect on the UI.
4. Components should be "extensible", i.e. `open class` in Kotlin. It's also important to think about the component being used that way when writing/reviewing it.

More can be found here: https://github.com/Skyscanner/backpack-android/tree/main/decisions

### Theming
- Uses the native Android theming mechanism https://developer.android.com/guide/topics/ui/look-and-feel/themes
- Each themeable component exposes a global attribute that can be used to theme all instances of that component.
  - E.g. BpkChip can be themed in two ways:
    ```xml
    // app/res/values/themes.xml

    <style name="RedChip">
      <item name="chipSelectedBackgroundColor">@colors/bpkPanjin</item>
    </style>
    ```

    1. Individually through the `style` prop:

    ```xml
      <BpkChip style="@style/RedChip" />
    ```

    2. Globally via the `bpkChipStyle` attribute:

    ```xml
    // app/res/values/style.xml
    <style name="RedChip">
      <item name="chipSelectedBackgroundColor">@colors/bpkPanjin</item>
    </style>

    <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
      <item name="bpkChipStyle">@style/RedChip</item>
    </style>
    ```

- Themes can also be set programmatically using a `ContextThemeWrapper`:

  ```Kotlin
  val themedContext = ContextThemeWrapper(context, R.style.RedChip)
  val chip = BpkChip(themedContext)
  ```

### Caveats

The `style` attribute will take precedence over the global `bpkChipStyle`, meaning it is possible to
have a different style for a single component.

It is also possible to set theming properties directly via `xml`:

```xml
  <BpkChip style="@style/RedChip" app:chipSelectedBackgroundColor="@colors/bpkPanjin" />
```

This is not advertised and is not a supported way for theming to be used. If you do this, we cannot guarantee that small changes to Backpack will not break your UI.

## Testing

There are currently three types of tests in `backpack-android`:

- Connected tests:
  - Live inside the `Backpack` module and run inside an emulator, hence "connected". This includes mostly unit testing for visual components.

- Unit tests:
  - Live inside the `Backpack` module and do not need an emulator to run. This includes all other unit tests that don't require an emulator.

- Snapshot tests:
  - Live inside the `app` module and run inside an emulator. Some of those tests are very flaky in the
  CI and will be annotated with `@FlakyTest`. This means they will not run on CI but still run locally before a release.
