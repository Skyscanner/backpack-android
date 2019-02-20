# Theming

Theming for suported components is done using the default Android [theming](https://developer.android.com/guide/topics/ui/look-and-feel/themes) mechanism.

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