# Theming

Theming for supported components is done using the default Android [theming](https://developer.android.com/guide/topics/ui/look-and-feel/themes) mechanism.

Each supported component can be themed individually using the `style` property, or globally by setting `{componentName}Style` in your theme.

## Example

Define a theme in `themes.xml`

```xml
 <style name="MyCustomTheme" parent="AppTheme">
    ...
    <item name="bpkSwitchStyle">@style/BpkSwitch.Red</item>
  </style>

  <style name="BpkSwitch.Red" parent="">
    <item name="switchPrimaryColor">@color/bpkRed500</item>
  </style>
```

You can theme all instances of `BpkSwitch` in your app/activity by setting the `theme` to `MyCustomTheme`, or change it to a single view by either setting the `style` to `@style/BpkSwitch.Red` or to your custom theme.

__For supported attributes check each component's documentation.__

## Theme overlays and runtime theming

Another option to apply a custom theme during runtime without having to change your application/activity theme is to 
use the `bpkThemeOverlay` attribute.

E.g.:

```xml
 <style name="AppTheme">
    <item name="bpkThemeOverlay">@style/MyCustomTheme</item>
  </style>
```

**By default this attribute will have no effect**, in order to enforce it you need to wrapped the context with `ThemeOverlayEnforcement`.

E.g.:

```Kotlin
override fun attachBaseContext(newBase: Context) {
  if (useBpkTheme) {
    super.attachBaseContext(ThemeOverlayEnforcement(newBase))
  } else {
    super.attachBaseContext(newBase)
  }
}
```