# Badge

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](../../README.md#installation) for a complete installation guide.

## Usage

The Badge component can be used in both XML and Kotlin

Example of a success type badge in XML

```xml
<net.skyscanner.backpack.badge.BpkBadge
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:layout_margin="@dimen/bpkSpacingBase"
  app:message="Message"
  app:type="success"/>
```

Example of a success type badge in Kotlin

```Kotlin
import net.skyscanner.backpack.badge.BpkBadge

BpkBadge(context).apply {
   message = 'Message'
   type = 'success'
}
```
